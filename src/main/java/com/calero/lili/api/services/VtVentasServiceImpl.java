package com.calero.lili.api.services;


import com.calero.lili.api.dtos.vtVentas.*;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.NotFoundException;
import com.calero.lili.api.repositories.VtClientesRepository;
import com.calero.lili.api.repositories.entities.*;
import com.calero.lili.api.repositories.projections.VtVentaDetalleProjection;
import com.calero.lili.api.repositories.projections.VtVentasProjection;
import com.calero.lili.api.specification.VtVentasFilterSpecification;
import com.calero.lili.api.specification.VtVentasSpecification;
import com.calero.lili.api.utils.DateUtils;
import com.calero.lili.api.repositories.VtVentaDetalleRepository;
import com.calero.lili.api.repositories.VtVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;


import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VtVentasServiceImpl {

    private final VtVentaRepository vtVentaRepository;
    private final VtVentaDetalleRepository vtVentaDetalleRepository;
    private final VtClientesRepository vtClientesRepository;

    private static final Logger logger = LoggerFactory.getLogger(VtVentasServiceImpl.class);


    @Transactional
    public VtVentaCreationResponseDto create(String idData, String idEmpresa, VtVentaCreationRequestDto request) {

        Optional<VtVentasProjection> existingFactura = vtVentaRepository.findFirst(idData, idEmpresa, request.getTipoVenta(), request.getSerie(), request.getSecuencia());

        if (existingFactura.isPresent()) {
            throw new AlreadyExistsException(MessageFormat.format("La factura ya existe Tipo: {0} Serie: {1} Secuencia: {2}", request.getTipoVenta(), request.getSerie(), request.getSecuencia()));
        }

        VtClientesEntity cliente = vtClientesRepository
                .findById(request.getIdCliente())
                .orElseThrow(() -> new NotFoundException(format("cliente con UUID {0} no existe", request.getIdCliente())));

        VtVentaEntity vtVentaEntity = vtVentaRepository.save(toEntity(idData, idEmpresa, request, cliente));

        //Informacion Adicional
        List<VtVentaEntity.InformacionAdicional> vtVentaInfoAdicional = request
                .getInformacionAdicional()
                .stream()
                .map(detail -> toInfoAdicional(detail))
                .collect(Collectors.toList());
        vtVentaEntity.setInformacionAdicional(vtVentaInfoAdicional);

        //Formas de pago SRI
        List<VtVentaEntity.FormasPagoSri> formasPagoSri = request
                .getFormasPagoSri()
                .stream()
                .map(detail -> toFormasPagoSri(detail))
                .collect(Collectors.toList());
        vtVentaEntity.setFormasPagoSri(formasPagoSri);

        if (request.getDatosFacturaModificada().getMotivo() != null)
        {
            VtVentaEntity.DatosFacturaModificada datosFacturaModificada = new VtVentaEntity.DatosFacturaModificada();
            datosFacturaModificada.setMotivo(request.getDatosFacturaModificada().getMotivo());
            datosFacturaModificada.setModificadaTipoVenta(request.getDatosFacturaModificada().getModificadaTipoVenta());
            datosFacturaModificada.setModificadaSerie(request.getDatosFacturaModificada().getModificadaSerie());
            datosFacturaModificada.setModificadaSecuencia(request.getDatosFacturaModificada().getModificadaSecuencia());
            vtVentaEntity.setDatosFacturaModificada(datosFacturaModificada);
        }

        //Detalles
        List<VtVentaDetalleEntity> vtVentaDetalleEntity = request
                .getDetalleItems()
                .stream()
                .map(detail -> toDetalleEntity(vtVentaEntity, detail))
                .collect(Collectors.toList());
        vtVentaDetalleRepository.saveAll(vtVentaDetalleEntity);

        return toCreationResponse(vtVentaEntity);
    }

    @Transactional
    public VtVentaCreationResponseDto update(String idData, String idEmpresa, UUID id, VtVentaCreationRequestDto request) {
        VtVentaEntity vtVentaEntity = vtVentaRepository.findById(id).orElseThrow(()-> new NotFoundException(format("El registro con id {0} no exists", id )));
       // vtVentaEntity.setObligadoContabilidad(request.getObligadoContabilidad());
        vtVentaEntity.setSerie(request.getSerie());
       // vtVentaEntity.setTipoEmision(request.getTipoEmision());
        VtVentaEntity updated = vtVentaRepository.save(vtVentaEntity);

        //Detalles
//        vtVentaDetalleRepository.deleteAllByIdFactura(id);
//        List<VtVentaDetalleEntity> vtVentaDetalleEntity = request
//                .getDetalleItems()
//                .stream()
//                .map(detail -> toDetalleEntity(updated, detail))
//                .collect(Collectors.toList());
//        vtVentaDetalleRepository.saveAll(vtVentaDetalleEntity);

        return toCreationResponse(vtVentaEntity);
    }

    public void delete(String idData, String idEmpresa,UUID id) {
        vtVentaRepository.deleteByIdDataAndEmpresaAndIdVenta(idData, idEmpresa, id);
    }


    public VtVentaResponseFindByIdDto findByUuid(String idData, String idEmpresa, UUID id) {
        VtVentaEntity vtVentaEntity = vtVentaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(format("La factura con ID {0} no exixte", id)));

        return toResponse(vtVentaEntity, vtVentaEntity.getVentaDetalleEntities());
    }

    public Page<VtVentaReportDto> findAllPaginate(String idData, String idEmpresa, VtVentaListFilterDto filters, Pageable pageable) {
        // OJO SIEMPRE ENVIAR FECHAS

        LocalDate fechaEmisionDesde = null;
        LocalDate fechaEmisionHasta = null;
        if (filters.getFechaEmisionDesde() !=null){
            fechaEmisionDesde = DateUtils.toLocalDate(filters.getFechaEmisionDesde());
        }
        if (filters.getFechaEmisionHasta() !=null) {
            fechaEmisionHasta = DateUtils.toLocalDate(filters.getFechaEmisionHasta());
        }

        System.out.println(fechaEmisionDesde);
        System.out.println(fechaEmisionHasta);

        Page<VtVentasProjection> page = vtVentaRepository.findAllPaginate(idData, idEmpresa, fechaEmisionDesde, fechaEmisionHasta, filters.getTipoVenta(),  filters.getSerie(), filters.getSecuencia(), pageable);

        // OJO SIN FECHAS y quitar en el repositorio el betwen
        //Page<VtFacturasProjection> page = vtVentaRepository.findAllPaginate(idData, idEmpresa, null, null, pageable);

        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(this::projectionToDtoReport)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

/*
    public Page<VtFacturaReportDto> findAllPaginate(String idData, Long idEmpresa, VtFacturaListFilterDto filters, Pageable pageable) {
        Page<VtFacturasProjection> page = vtVentaRepository.findAllPaginate(idData, pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(this::projectionToDtoReport)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }
 */

    public Page<VtVentaDetalleSummaryDto> findAllPaginateDetails(VtVentaDetalleListFilterDto filters, Pageable pageable) {
        Page<VtVentaDetalleProjection> page = vtVentaRepository.findAllPaginateDetails(filters.getSupplierNumber(), filters.getSupplierName(), pageable);
        return new PageImpl<>(
                page.stream().map(this::toDto).collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements()
        );
    }

    private VtVentaReportDto projectionToDtoReport(VtVentasProjection projection) {
        VtVentaReportDto dto = new VtVentaReportDto();
        dto.setIdSucursal(projection.getIdSucursal());
        dto.setIdVenta(projection.getIdVenta());
        dto.setTipoVenta(projection.getTipoVenta());
        dto.setSerie(projection.getSerie());
        dto.setSecuencia(projection.getSecuencia());
        dto.setAutorizacionSri(projection.getAutorizacionSri());

        dto.setIdCliente(projection.getIdCliente());
        dto.setCliente(projection.getIdCliente());
        dto.setNumeroIdentificacion(projection.getNumeroIdentificacion());
        dto.setCliente(projection.getCliente());
        dto.setNumeroIdentificacion(projection.getNumeroIdentificacion());
        dto.setRelacionado(projection.getRelacionado());
        dto.setTipoCliente(projection.getTipoCliente());
        dto.setMail(projection.getMail());

        dto.setFechaEmision(projection.getFechaEmision());
        dto.setModificadaTipoVenta(projection.getModificadaTipoVenta());
        dto.setModificadaSerie(projection.getModificadaSerie());
        dto.setModificadaSecuencia(projection.getModificadaSecuencia());
        dto.setConcepto(projection.getConcepto());
        dto.setBaseCero(projection.getBaseCero());
        dto.setBaseGravada1(projection.getBaseGravada1());
        dto.setPorcentajeIva1(projection.getPorcentajeIva1());
        dto.setIva1(projection.getIva1());
        dto.setBaseGravada2(projection.getBaseGravada2());
        dto.setPorcentajeIva2(projection.getPorcentajeIva2());
        dto.setIva2(projection.getIva2());

        dto.setBaseNoObjeto(projection.getBaseNoObjeto());

        dto.setBaseExenta(projection.getBaseExenta());

        dto.setTdscto(projection.getTdscto());
        dto.setTdescuento(projection.getTdescuento());
        dto.setTotal(projection.getTotal());
        dto.setTipodoc(projection.getTipodoc());
        dto.setTipo(projection.getTipo());
        dto.setCodigoDocumento(projection.getCodigoDocumento());
        dto.setItems(projection.getItems());
        dto.setFechaVencimiento(projection.getFechaVencimiento());
        dto.setFechaanu(projection.getFechaanu());
        dto.setAgenteRetencion(projection.getAgenteRetencion());
        dto.setContacto(projection.getContacto());
        dto.setDiasCredito(projection.getDiasCredito());
        dto.setCuotas(projection.getCuotas());
        dto.setCzona(projection.getCzona());
        dto.setTipoContribuyente(projection.getTipoContribuyente());
        dto.setDocumentoElectronico(projection.getDocumentoElectronico());
        dto.setTipoEmision(projection.getTipoEmision());
        dto.setAmbiente(projection.getAmbiente());
        dto.setEstadoDocumento(projection.getEstadoDocumento());
        dto.setClaveAcceso(projection.getClaveAcceso());
        dto.setFechaAutorizacion(projection.getFechaAutorizacion());
        dto.setMensaje(projection.getMensaje());
        dto.setXml(projection.getXml());
        dto.setEmailEstado(projection.getEmailEstado());
        //dto.setFormasPagoSri(projection.getFormasPagoSri());
        dto.setIdVendedor(projection.getIdVendedor());
        dto.setAnulada(projection.getAnulada());
        dto.setImpresa(projection.getImpresa());
        return dto;
    }

    private VtVentaDetalleSummaryDto toDto(VtVentaDetalleProjection projection) {
        VtVentaDetalleSummaryDto dto = new VtVentaDetalleSummaryDto();
        dto.setIdVenta(projection.getIdVenta());
        dto.setSerie(projection.getSerie());
        dto.setSecuencia(projection.getSecuencia());
        dto.setCodigoItem(projection.getCodigoItem());
        dto.setItem(projection.getItem());
        //dto.setIdFactura(projection.getI());
//        dto.setSupplierNumber(projection.getSupplierNumber());
//        dto.setSupplierName(projection.getSupplierName());
        dto.setPrecioUnitario(projection.getPrecioUnitario());
        dto.setIdCliente(projection.getIdCliente());
        dto.setCliente(projection.getCliente());
        return dto;
    }
    private VtVentaEntity toEntity(String idData, String idEmpresa, VtVentaCreationRequestDto request, VtClientesEntity cliente) {
        VtVentaEntity vtVentaEntity = new VtVentaEntity();

        //vtVentaEntity.setIssuerNumber(request.getIssuer().getNumber());
        //vtVentaEntity.setIssuerName(request.getIssuer().getName());

        vtVentaEntity.setIdVenta(UUID.randomUUID());

        vtVentaEntity.setIdData(idData);
        vtVentaEntity.setIdEmpresa(idEmpresa);
        vtVentaEntity.setIdSucursal(request.getIdSucursal());

        vtVentaEntity.setFechaEmision(DateUtils.toLocalDate(request.getFechaEmision()));
        vtVentaEntity.setTipoVenta(request.getTipoVenta());
        vtVentaEntity.setSerie(request.getSerie());
        vtVentaEntity.setSecuencia(request.getSecuencia());

            switch (request.getTipoVenta()) {
                case "FAC":
                    vtVentaEntity.setCodigoDocumento("01");
                    break;
                case "NCR":
                    vtVentaEntity.setCodigoDocumento("04");
                    break;
                case "NDB":
                    vtVentaEntity.setCodigoDocumento("05");
                    break;
            }

        VtVentaEntity.ClienteDatos clienteDatos = new VtVentaEntity.ClienteDatos();
        clienteDatos.setCliente(request.getClienteDatos().getCliente());
        clienteDatos.setTipoIdentificacion(request.getClienteDatos().getTipoIdentificacion());
        clienteDatos.setNumeroIdentificacion(request.getClienteDatos().getNumeroIdentificacion());
        clienteDatos.setDireccion(request.getClienteDatos().getDireccion());
        clienteDatos.setTelefonos(request.getClienteDatos().getTelefonos());
        clienteDatos.setTipoCliente(request.getClienteDatos().getTipoCliente());
        clienteDatos.setRelacionado(request.getClienteDatos().getRelacionado());
        clienteDatos.setEmail(request.getClienteDatos().getEmail());
        vtVentaEntity.setClienteDatos(clienteDatos);

        vtVentaEntity.setEmailEstado("0");
        vtVentaEntity.setEstadoDocumento("0");

        //vtVentaEntity.setDetalleItems(request.getDetalleItems());

        vtVentaEntity.setImpresa(Boolean.FALSE);
        //vtVentaEntity.setAnulada(Boolean.FALSE);
        vtVentaEntity.setDocumentoElectronico("1");

//        if (request.getTipoVenta()=="NCR" || request.getTipoVenta()=="NDB" ){
//            VtVentaEntity.DatosFacturaModificada mod = new VtVentaEntity.DatosFacturaModificada();
//            mod.setMotivo(request.getDatosFacturaModificada().getMotivo());
//            mod.setModificadaTipoVenta(request.getDatosFacturaModificada().getModificadaTipoVenta());
//            mod.setModificadaTipoVenta(request.getDatosFacturaModificada().getModificadaTipoVenta());
//            mod.setModificadaSerie(request.getDatosFacturaModificada().getModificadaSerie());
//            mod.setModificadaSecuencia(request.getDatosFacturaModificada().getModificadaSecuencia());
//            vtVentaEntity.setDatosFacturaModificada(mod);
//        }

        String tipoComprobante= vtVentaEntity.getCodigoDocumento();
        String ruc ="1714406236001";
        String ambiente ="2";
        String codigoNumerico ="12345678";
        String tipoEmision="1";


        VtVentaEntity.DatosDocumentoElectronico dde = new VtVentaEntity.DatosDocumentoElectronico();
        dde.setAmbiente("2");
        dde.setTipoEmision("1");
        dde.setContribuyenteEspecial("2");
        dde.setObligadoContabilidad("N");
        dde.setTipoContribuyente("1");
        dde.setAgenteRetencion("001");
        dde.setClaveAcceso(vtVentaEntity.getAutorizacionSri());
        vtVentaEntity.setDatosDocumentoElectronico(dde);


//        vtVentaEntity.setDatosFacturaModificada(request.getDatosFacturaModificada());

        vtVentaEntity.setItems(request.getItems());

        vtVentaEntity.setSubtotal(request.getSubTotal());
        vtVentaEntity.setDescuento(request.getDescuento());

        if (request.getBaseCero().compareTo(BigDecimal.ZERO) != 0 ){
            vtVentaEntity.setBaseCero(request.getBaseCero());
        }else{
            vtVentaEntity.setBaseCero(new BigDecimal(0));
        }

        if (request.getBaseGravada1().compareTo(BigDecimal.ZERO) != 0 ){
            vtVentaEntity.setBaseGravada1(request.getBaseGravada1());
            vtVentaEntity.setPorcentajeIva1(request.getPorcentajeIva1());
            vtVentaEntity.setIva1(request.getIva1());
        }else{
            vtVentaEntity.setBaseGravada1(new BigDecimal(0));
            vtVentaEntity.setPorcentajeIva1(0);
            vtVentaEntity.setIva1(new BigDecimal(0));
        }

        if (request.getBaseGravada2().compareTo(BigDecimal.ZERO) != 0 ){
            vtVentaEntity.setBaseGravada2(request.getBaseGravada2());
            vtVentaEntity.setPorcentajeIva2(request.getPorcentajeIva2());
            vtVentaEntity.setIva2(request.getIva2());
        }else{
            vtVentaEntity.setBaseGravada2(new BigDecimal(0));
            vtVentaEntity.setPorcentajeIva2(0);
            vtVentaEntity.setIva2(new BigDecimal(0));
        }

        if (request.getBaseNoObj().compareTo(BigDecimal.ZERO) != 0 ){
            vtVentaEntity.setBaseNoObjeto(request.getBaseNoObj());
        }else{
            vtVentaEntity.setBaseNoObjeto(new BigDecimal(0));
        }

        if (request.getBaseExenta().compareTo(BigDecimal.ZERO) != 0 ){
            vtVentaEntity.setBaseExenta(request.getBaseExenta());
        }else{
            vtVentaEntity.setBaseExenta(new BigDecimal(0));
        }

        vtVentaEntity.setTotal(request.getTotal());

//
//        if (request.getBaseGravada1().compareTo(BigDecimal.ZERO) != 0 ){
//            VtVentaEntity.Valores val = new VtVentaEntity.Valores();
//            val.setImpuesto("IV");
//            val.setCodigo("1");
//            val.setBase(request.getBaseGravada1());
//            val.setPorcentaje(request.getPorcentajeIva1());
//            val.setValor(request.getValorIva1());
//            listaDetalle.add(val);
//        }
//
//        if (request.getBaseGravada2().compareTo(BigDecimal.ZERO) != 0 ){
//            VtVentaEntity.Valores val = new VtVentaEntity.Valores();
//            val.setImpuesto("IV");
//            val.setCodigo("1");
//            val.setBase(request.getBaseGravada2());
//            val.setPorcentaje(request.getPorcentajeIva2());
//            val.setValor(request.getValorIva2());
//            listaDetalle.add(val);
//        }
//
//        if (request.getBaseNoObj().compareTo(BigDecimal.ZERO) != 0 ){
//            VtVentaEntity.Valores val = new VtVentaEntity.Valores();
//            val.setImpuesto("IV");
//            val.setCodigo("6");
//            val.setBase(request.getBaseNoObj());
//            val.setPorcentaje(0);
//            val.setValor(new BigDecimal("0"));
//            listaDetalle.add(val);
//        }
//        if (request.getBaseExenta().compareTo(BigDecimal.ZERO) != 0 ){
//            VtVentaEntity.Valores val = new VtVentaEntity.Valores();
//            val.setImpuesto("IV");
//            val.setCodigo("7");
//            val.setBase(request.getBaseExenta());
//            val.setPorcentaje(0);
//            val.setValor(new BigDecimal("0"));
//            listaDetalle.add(val);
//        }

//        vtVentaEntity.setValores(listaDetalle);
        //vtVentaEntity.setValores(request.getValores());
        //vtVentaEntity.setTotal(getTotal(request.getDetalleItems()));
        //vtVentaEntity.setTotalDescuento(getDescuento(request.getDetalleItems()));

        //vtVentaEntity.setSupplier(supplier);
        vtVentaEntity.setCliente(cliente);
        //vtVentaEntity.setInformacionAdicional(request.getInformacionAdicional());
        //vtVentaEntity.setFormasPagoSri(request.getFormasPagoSri());
        return vtVentaEntity;
    }
    private VtVentaDetalleEntity toDetalleEntity(VtVentaEntity vtVentaEntity, VtVentaCreationRequestDetailDto vtVentaCreationRequestDetailDto) {
        VtVentaDetalleEntity vtVentaDetalleEntity = new VtVentaDetalleEntity();
        vtVentaDetalleEntity.setVenta(vtVentaEntity);
        vtVentaDetalleEntity.setIdVentaDetalle(UUID.randomUUID());
        GeItemsEntity geItemsEntity = new GeItemsEntity();
        geItemsEntity.setIdItem(vtVentaCreationRequestDetailDto.getIdItem());
        vtVentaDetalleEntity.setItems(geItemsEntity);
        vtVentaDetalleEntity.setCodigoItem(vtVentaCreationRequestDetailDto.getCodigoItem());
        vtVentaDetalleEntity.setItem(vtVentaCreationRequestDetailDto.getItem());
        vtVentaDetalleEntity.setPrecioUnitario(vtVentaCreationRequestDetailDto.getPrecioUnitario());
        vtVentaDetalleEntity.setCantidad(vtVentaCreationRequestDetailDto.getCantidad());
        vtVentaDetalleEntity.setDescuento(vtVentaCreationRequestDetailDto.getDescuento());
        return vtVentaDetalleEntity;
    }

    private VtVentaEntity.InformacionAdicional toInfoAdicional(VtVentaCreationRequestInformacionAdicionalDto vtVentaCreationRequestInformacionAdicionalDto) {
        VtVentaEntity.InformacionAdicional informacionAdicional = new VtVentaEntity.InformacionAdicional();
        informacionAdicional.setNombre(vtVentaCreationRequestInformacionAdicionalDto.getNombre());
        informacionAdicional.setValor(vtVentaCreationRequestInformacionAdicionalDto.getValor());
        return informacionAdicional;
    }

    private VtVentaEntity.FormasPagoSri toFormasPagoSri(VtVentaCreationRequestFormasPagoDto vtVentaCreationRequestFormasPagoDto) {
        VtVentaEntity.FormasPagoSri formasPagoSri = new VtVentaEntity.FormasPagoSri();
        formasPagoSri.setFormaPago(vtVentaCreationRequestFormasPagoDto.getFormaPago());
        formasPagoSri.setPlazo(vtVentaCreationRequestFormasPagoDto.getPlazo());
        formasPagoSri.setUnidadTiempo(vtVentaCreationRequestFormasPagoDto.getUnidadTiempo());
        formasPagoSri.setValor(vtVentaCreationRequestFormasPagoDto.getValor());
        return formasPagoSri;
    }
    private VtVentaCreationResponseDto toCreationResponse(VtVentaEntity vtVentaEntity) {
        VtVentaCreationResponseDto response = new VtVentaCreationResponseDto();
        response.setIdFactura(vtVentaEntity.getIdVenta());
        //response.setDescuento(vtVentaEntity.getTotalDescuento());
        //response.setIva(vtVentaEntity.getIva());
        //response.setTotal(vtVentaEntity.getTotal());
        return response;
    }

    private VtVentaResponseFindByIdDto toResponse(VtVentaEntity vtVentaEntity, List<VtVentaDetalleEntity> vtVentaDetalleEntity) {
        return VtVentaResponseFindByIdDto
                .builder()
                .idVenta(vtVentaEntity.getIdVenta())
                //.issuerNumber(vtVentaEntity.getIssuerNumber())
                //.issuerName(vtVentaEntity.getIssuerName())
                //.idCliente(vtVentaEntity.getCliente().getIdCliente())
                .fechaEmision(DateUtils.toString(vtVentaEntity.getFechaEmision()))
                .serie(vtVentaEntity.getSerie())
                .secuencia(vtVentaEntity.getSecuencia())
//                .numeroIdentificacion(vtVentaEntity.getNumeroIdentificacion())
//                .telefonos(vtVentaEntity.getTelefonos())
//                .emails(Arrays.asList(vtVentaEntity.getEmails().split(";")))
                //.baseCero(vtVentaEntity.getBaseCero())
                // .baseGravada(vtVentaEntity.getBaseGravada())
                //.baseExenta(vtVentaEntity.getBaseExenta())
                //.baseNoObjeto(vtVentaEntity.getBaseNoObjeto())
                // .iva(vtVentaEntity.getIva())
                // .descuento(vtVentaEntity.getTotalDescuento())
                //.total(vtVentaEntity.getTotal())
                .vtVentaDetalleResponseDtos(vtVentaDetalleEntity.stream().map(this::toResponse).collect(Collectors.toList()))
                .build();
    }

    private VtVentaDetalleResponseDto toResponse(VtVentaDetalleEntity vtVentaDetalleEntity) {
        return VtVentaDetalleResponseDto
                .builder()
                //.idItem(vtFacturaDetalleEntity.getIdItem())
                .codigoItem(vtVentaDetalleEntity.getCodigoItem())
                .item(vtVentaDetalleEntity.getItem())
                .precioUnitario(vtVentaDetalleEntity.getPrecioUnitario())
                .cantidad(vtVentaDetalleEntity.getCantidad())
//                .taxAmount(vtVentaDetalleEntity.getTaxAmount())
                .descuento(vtVentaDetalleEntity.getDescuento())
                .subTotal(
                        calcularSubTotal(
                                vtVentaDetalleEntity.getPrecioUnitario(),
                                vtVentaDetalleEntity.getCantidad(),
//                                vtVentaDetalleEntity.getTaxAmount(),
                                vtVentaDetalleEntity.getDescuento()
                        )
                )
                .build();
    }

    /*
    private void validateIfExists(String idData, String idEmpresa, String tipoVenta, String serie, String secuencia) {
        Optional<VtFacturaEntity> existingInvoice = vtVentaRepository.findFirst(idData, idEmpresa, tipoVenta, serie, secuencia);
        if (existingInvoice.isPresent()) {
            throw new AlreadyExistsException(
                    format("El documento tipo {0} serie {1} secuencia {2} ya existe", tipoVenta, serie, secuencia)
            );
        }
    }

     */
    private BigDecimal getDescuento(List<VtVentaCreationRequestDetailDto> vtFacturaCreationDetailDto) {
        return vtFacturaCreationDetailDto
                .stream()
                .map(VtVentaCreationRequestDetailDto::getDescuento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    private BigDecimal getIva(List<VtVentaCreationRequestDetailDto> vtFacturaCreationDetailDto) {
//        return vtFacturaCreationDetailDto
//                .stream()
//                .map(VtVentaCreationRequestDetailDto::getTaxAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
    private BigDecimal calcularSubTotal(
            BigDecimal precioUnitario,
            BigDecimal cantidad,
//            BigDecimal taxAmount,
            BigDecimal descuento) {
//        return precioUnitario.multiply(cantidad).add(taxAmount).subtract(descuento);
        return precioUnitario.multiply(cantidad).subtract(descuento);

    }

    private BigDecimal getTotal(List<VtVentaCreationRequestDetailDto> vtFacturaCreationDetailDto) {
        return vtFacturaCreationDetailDto
                .stream()
                .map(detail ->
                        calcularSubTotal(
                                detail.getPrecioUnitario(),
                                detail.getCantidad(),
                                detail.getDescuento()
                        )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Busca facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<List<VtFacturaEntity>> Lista de facturas encontradas.
     */
    public Optional<List<VtVentaEntity>> findFacturasWithSpecification(VtVentasFilterSpecification filter, Pageable pageable) {
        try {
            logger.info("Iniciando la búsqueda de facturas con los siguientes filtros: {}", filter);
            Specification<VtVentaEntity> spec = VtVentasSpecification.buildFromFilter(filter);
            List<VtVentaEntity> facturas = vtVentaRepository.findAll(spec);
            if (facturas.isEmpty()) {
                logger.warn("No se encontraron facturas con los filtros proporcionados.");
            } else {
                logger.info("Se encontraron {} facturas con los filtros proporcionados.", facturas.size());
            }
            return Optional.of(facturas);
        } catch (Exception e) {
            logger.error("Error durante la búsqueda de facturas: ", e);
            return Optional.empty();
        }
    }

    /**
     * Busca detalles de facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<List<VtFacturaDetalleEntity>> Lista de detalles de facturas encontradas.
     */
    public Optional<List<VtVentaDetalleEntity>> findFacturaDetailsWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Iniciando la búsqueda de detalles de factura con los siguientes filtros: {}", filter);
            Specification<VtVentaDetalleEntity> spec = VtVentasSpecification.buildFromFilterForDetails(filter);
            List<VtVentaDetalleEntity> detalles = vtVentaDetalleRepository.findAll(spec);
            if (detalles.isEmpty()) {
                logger.warn("No se encontraron detalles de factura con los filtros proporcionados.");
            } else {
                logger.info("Se encontraron {} detalles de factura con los filtros proporcionados.", detalles.size());
            }
            return Optional.ofNullable(detalles);
        } catch (Exception e) {
            logger.error("Error durante la búsqueda de detalles de factura: ", e);
            return Optional.empty();
        }
    }

    /**
     * Calcula los totales de facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<Map<String, BigDecimal>> Mapa con los totales calculados.
     */
    public Optional<Map<String, BigDecimal>> calculateInvoiceTotalsWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Calculando totales de facturas con los siguientes filtros: {}", filter);
            Specification<VtVentaEntity> spec = VtVentasSpecification.buildFromFilter(filter);
            List<VtVentaEntity> facturas = vtVentaRepository.findAll(spec);

            //  BigDecimal totalDescuento = facturas.stream().map(VtVentaEntity::getTotalDescuento).reduce(BigDecimal.ZERO, BigDecimal::add);
            // BigDecimal totalSubtotal = facturas.stream().map(VtVentaEntity::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, BigDecimal> totales = new HashMap<>();
            // totales.put("totalDescuento", totalDescuento);
            // totales.put("totalSubtotal", totalSubtotal);

            return Optional.of(totales);
        } catch (Exception e) {
            logger.error("Error durante el cálculo de totales de facturas: ", e);
            return Optional.empty();
        }
    }

    /**
     * Calcula los totales de los detalles de facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<Map<String, BigDecimal>> Mapa con los totales calculados.
     */
    public Optional<Map<String, BigDecimal>> calculateInvoiceDetailsTotalsWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Calculando totales de detalles de factura con los siguientes filtros: {}", filter);
            Specification<VtVentaDetalleEntity> spec = VtVentasSpecification.buildFromFilterForDetails(filter);
            List<VtVentaDetalleEntity> detalles = vtVentaDetalleRepository.findAll(spec);

            BigDecimal totalDescuento = detalles.stream().map(VtVentaDetalleEntity::getDescuento).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalSubtotal = detalles.stream().map(detail -> detail.getPrecioUnitario().multiply(detail.getCantidad()).subtract(detail.getDescuento())).reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, BigDecimal> totales = new HashMap<>();
            totales.put("totalDescuento", totalDescuento);
            totales.put("totalSubtotal", totalSubtotal);

            return Optional.of(totales);
        } catch (Exception e) {
            logger.error("Error durante el cálculo de totales de detalles de factura: ", e);
            return Optional.empty();
        }
    }



}
