/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.controller;

import com.sd4.model.Breweries_Geocode;
import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;
import com.sd4.service.BreweryGeoService;
import com.sd4.service.BreweryService;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.vcard.VCard;
import net.glxn.qrgen.javase.QRCode;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/breweries")
public class BreweryController {

    @Autowired
    private BreweryService breweryService;
    
    @Autowired
    private BreweryGeoService breweryGeoService;
    
    @Autowired
    private BreweryRepository breweryRespository;
    
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
    
    @GetMapping(value = "/map/{id}", produces = MediaType.TEXT_HTML_VALUE)
     public String breweryMap(@PathVariable long id){
         
         Optional<Brewery> b = breweryService.findOne(id);
         
         if (!b.isPresent()){
            return "Error";
        }
        else{
            Long breweryGeoID = b.get().getId();
            String breweryName = b.get().getName();
            String breweryAddress1 = b.get().getAddress1();
            String breweryAddress2 = b.get().getAddress2();
            String breweryCity = b.get().getCity();
            String breweryState = b.get().getState();
            
            String fullAddress = breweryName + " " + breweryAddress1 + " " + breweryAddress2 + " " + breweryCity + " " + breweryState;
            
            Optional<Breweries_Geocode> br = breweryGeoService.findOne(breweryGeoID);
            
            Double breweryLatitude= br.get().getLatitude();
            Double breweryLongitude = br.get().getLongitude();
           
            return "<!DOCTYPE html>"+
            "<html>"+
            "<style>"+
            "#map {"+
              "height: 400px;"+
              "width: 100%;"+
            "}"+
            "#map {"+
              "height: 400px;"+
              "/* The height is 400 pixels */"+
              "width: 100%;"+
            "}"+
            "</style>"+
            "<script type=\"text/javascript\">"+
            "function initMap() {"+
              "const brewery = { lat: " + breweryLatitude + ", lng: " + breweryLongitude + "};"+
              "const map = new google.maps.Map(document.getElementById(\"map\"), {"+
                "zoom: 15,"+
                "center: brewery,"+
              "});"+
              "const marker = new google.maps.Marker({"+
                "position: brewery,"+
                "map: map,"+
              "});"+
            "}"+
            "</script>"+
              "<head>"+
                "<title>Brewery Map</title>"+
              "</head>"+
              "<body>"+
                "<h3>" + fullAddress + "</h3>"+
                "<!--The div element for the map -->"+
                "<div id=\"map\"></div>"+
                "<script"+
                    " " +
                  "src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCqkK6A7OAfFN0RC091w4TMNxCuLTNdQQM&callback=initMap\">" +
                    " " +
                  "async"+
                "></script>"+
              "</body>"+
            "</html>";
                 }
            }
     
 
}