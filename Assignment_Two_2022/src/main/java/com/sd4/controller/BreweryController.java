/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.controller;

import com.sd4.model.Brewery;
import com.sd4.service.BreweryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/breweries")
public class BreweryController {

    @Autowired
    private BreweryService breweryService;
    
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
     public ModelAndView getAllBreweries(){
         return new ModelAndView("/viewBrewerys", "brewerys", breweryService.findAll());
     }
    
    @GetMapping("/breweries/{id}")
    public ResponseEntity<Brewery> getOne(@PathVariable long id) {
       Optional<Brewery> o =  breweryService.findOne(id);
       
       if (!o.isPresent()) 
            return new ResponseEntity(HttpStatus.NOT_FOUND);
         else 
            return ResponseEntity.ok(o.get());
    }
    
    @GetMapping("/breweries/count")
    public long getCount() {
        return breweryService.count();
    }
    @DeleteMapping("/breweries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        breweryService.deleteByID(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PostMapping("/breweries/")
    public ResponseEntity add(@RequestBody Brewery a) {
        breweryService.saveBrewery(a);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    
    @PutMapping("/breweries/")
    public ResponseEntity edit(@RequestBody Brewery a) { //the edit method should check if the Brewery object is already in the DB before attempting to save it.
        breweryService.saveBrewery(a);
        return new ResponseEntity(HttpStatus.OK);
    }
}