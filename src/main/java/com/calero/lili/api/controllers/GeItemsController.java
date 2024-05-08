package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.errors.ListCreationResponseDto;
import com.calero.lili.api.dtos.geItems.*;
import com.calero.lili.api.services.GeItemsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1.0/items")
@CrossOrigin(originPatterns = "*")

public class GeItemsController {

    private final GeItemsServiceImpl geItemsService;

    @PostMapping("{idData}/{idEmpresa}")
    @ResponseStatus(HttpStatus.CREATED)
    public GeItemCreationResponseDto create(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @RequestBody GeItemCreationRequestDto request) {
        return geItemsService.create(idData, idEmpresa, request);
    }

    @PostMapping("/createList/{idData}/{idEmpresa}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ListCreationResponseDto createList(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @Valid @RequestBody GeItemListCreationRequestDto request) {
        System.out.println(request);
        return geItemsService.createListItems(idData, idEmpresa, request);
    }

    @PutMapping("{idData}/{idEmpresa}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeItemCreationResponseDto update(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @PathVariable("id") UUID id,
            @RequestBody GeItemCreationRequestDto request) {
        return geItemsService.update(idData, idEmpresa, id, request);
    }

    @DeleteMapping("{idData}/{idEmpresa}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("idData") String idData,
                       @PathVariable("idEmpresa") String idEmpresa,
                       @PathVariable("id") UUID id) {
        geItemsService.delete(idData, idEmpresa, id);
    }

    @GetMapping("{idData}/{idEmpresa}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeItemCreationResponseDto findById(@PathVariable("idData") String idData,
                                              @PathVariable("idEmpresa") String idEmpresa,
                                              @PathVariable("id") UUID id) {
        return geItemsService.findById(idData, idEmpresa, id);
    }

    @GetMapping("{idData}/{idEmpresa1}")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<GeItemReportDto> findAllPaginate(@PathVariable("idData") String idData,
                                         @PathVariable("idEmpresa1") String idEmpresa1,
                                         GeItemListFilterDto filters,
                                         Pageable pageable) {
        //log.info("Filters = {}", filters);
        //System.out.println("xxxxxxxxxxxxxxxxxx");
        return geItemsService.findAllPaginate(idData, idEmpresa1, filters, pageable);
    }

//    @GetMapping("/filtrados/{idData}/{idEmpresa}")
//    @ResponseStatus(code = HttpStatus.OK)
//    public List<GeItemsProjection> findAll (@PathVariable("idData") String idData,
//                                                 GeItemListFilterDto filters
//    ){
//        return geItemsService.findAll(idData, filters);
//    }

}
