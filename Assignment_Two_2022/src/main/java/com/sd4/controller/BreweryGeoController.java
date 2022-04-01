/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.controller;

import com.sd4.model.Breweries_Geocode;
import com.sd4.service.BreweryGeoService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author calum
 */
public class BreweryGeoController {
    
    @Autowired
    private BreweryGeoService breweryGeoService;
    
    @GetMapping("/breweriesGeo/{id}")
    public ResponseEntity<Breweries_Geocode> getOne(@PathVariable long id) {
       Optional<Breweries_Geocode> o =  breweryGeoService.findOne(id);
       
       if (!o.isPresent()) 
            return new ResponseEntity(HttpStatus.NOT_FOUND);
         else 
            return ResponseEntity.ok(o.get());
    }
}
