package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.adDatas.AdDataCreationRequestDto;
import com.calero.lili.api.dtos.adDatas.AdDataCreationResponseDto;
import com.calero.lili.api.dtos.adDatas.AdDataListFilterDto;
import com.calero.lili.api.dtos.adDatas.AdDataReportDto;
import com.calero.lili.api.services.AdDatasServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1.0/datas")
@CrossOrigin(originPatterns = "*")

public class AdDatasController {

    private final AdDatasServiceImpl adDatasService;

    @PostMapping("{idData}")
    @ResponseStatus(HttpStatus.CREATED)
    public AdDataCreationResponseDto create(
            @PathVariable("id") String idData,
            @RequestBody AdDataCreationRequestDto request) {
        return adDatasService.create(idData, request);
    }

    @PutMapping("{idData}")
    @ResponseStatus(HttpStatus.OK)
    public AdDataCreationResponseDto update(
            @PathVariable("idData") String idData,
            @RequestBody AdDataCreationRequestDto request) {
        return adDatasService.update(idData, request);
    }

    @GetMapping("{idData}")
    @ResponseStatus(HttpStatus.OK)
    public AdDataCreationResponseDto findById(@PathVariable("idData") String idData) {
        return adDatasService.findByIdData(idData);
    }

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public Page<AdDataReportDto> findAllPaginate(
            AdDataListFilterDto filters,
            Pageable pageable) {
        return adDatasService.findAllPaginate(filters, pageable);
    }

}
