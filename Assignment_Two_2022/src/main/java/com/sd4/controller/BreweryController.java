/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sd4.model.Breweries_Geocode;
import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;
import com.sd4.service.BreweryGeoService;
import com.sd4.service.BreweryService;
import java.awt.image.BufferedImage;
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

@RestController
@RequestMapping("/breweries")
public class BreweryController {

    @Autowired
    private BreweryService breweryService;
    
    @Autowired
    private BreweryGeoService breweryGeoService;
    
    @Autowired
    private BreweryRepository breweryRespository;
    
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

     @GetMapping(path = "/qrCode/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage qrCode(@PathVariable long id) throws WriterException {
        
        Optional<Brewery> b = breweryService.findOne(id);
        
        String name = "";
        String email = "";
        String website = "";
        String phone = "";
        String address = "";
        
        if (!b.isPresent()) {
            //return error
        } else {
            
            name = b.get().getName();
            email = b.get().getEmail();
            phone = b.get().getPhone();
            website = b.get().getWebsite();
            
            address = b.get().getName()+", "+b.get().getAddress1()+", "+b.get().getAddress2()+ ", "+b.get().getCity()+ ", "+b.get().getState()+ ", " +b.get().getCountry();
            
        }
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("BEGIN:VCARD");
        buffer.append("\nFN:").append(name);
        buffer.append("\nADR:").append(address);
        buffer.append("\nTEL:").append(phone);
        buffer.append("\nURL:").append(website);
        buffer.append("\nEMAIL:").append(email);
        buffer.append("\nEND:VCARD");
        
        String qrCode = buffer.toString();
        
        QRCodeWriter wr = new QRCodeWriter();
        
        BitMatrix mtx = wr.encode(qrCode, BarcodeFormat.QR_CODE, 400, 400);
        
        
        return MatrixToImageWriter.toBufferedImage(mtx);

    }
}