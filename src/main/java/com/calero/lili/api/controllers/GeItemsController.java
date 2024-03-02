package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.geItems.GeItemCreationRequestDto;
import com.calero.lili.api.dtos.geItems.GeItemCreationResponseDto;
import com.calero.lili.api.services.GeItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1.0/items")
@CrossOrigin(originPatterns = "*")

public class GeItemsController {

    private final GeItemsService geItemsService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public GeItemCreationResponseDto create(
            @RequestBody GeItemCreationRequestDto request) {
        return geItemsService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GeItemCreationResponseDto update(
            @PathVariable("id") UUID id,
            @RequestBody GeItemCreationRequestDto request) {
        return geItemsService.update(id, request);
    }

}
