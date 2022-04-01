/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.service;

import com.sd4.model.Breweries_Geocode;
import com.sd4.repository.BreweryGeoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author calum
 */
@Service
public class BreweryGeoService {

    @Autowired
    private BreweryGeoRepository breweryGeoRepo;

    public Optional<Breweries_Geocode> findOne(Long id) {
        return breweryGeoRepo.findById(id);
    }
}
