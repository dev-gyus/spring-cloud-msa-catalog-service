package com.example.catalogservice.controller;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {
    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status(){
        return String.format("it's working in user service on Port %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> findAllUsers(){
        Iterable<CatalogEntity> catalogs = catalogService.getAllCatalogs();
        List<ResponseCatalog> res = new ArrayList<>();
        catalogs.forEach(entity ->
                res.add(new ModelMapper().map(entity, ResponseCatalog.class))
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
