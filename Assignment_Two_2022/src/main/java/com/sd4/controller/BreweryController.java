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
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping(value = "/listBreweries", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<Brewery> getAllBreweries(){
        
        List<Brewery> aList = breweryService.findAll();
        
        Link link = linkTo(BeerController.class).withSelfRel();
        
        CollectionModel<Brewery> result = CollectionModel.of(aList, link);
        
        return result;
    }
    
    @GetMapping(value = "/map/{id}", produces = MediaType.TEXT_HTML_VALUE)
     public String breweryMap(@PathVariable long id){
         
         Optional<Brewery> br = breweryService.findOne(id);
         
         if (!br.isPresent()){
            return "Error";
        }
        else{
            Long breweryGeoID = br.get().getId();
            String breweryName = br.get().getName();
            String breweryAddress1 = br.get().getAddress1();
            String breweryAddress2 = br.get().getAddress2();
            String breweryCity = br.get().getCity();
            String breweryState = br.get().getState();
            
            String fullAddress = breweryName + " " + breweryAddress1 + " " + breweryAddress2 + " " + breweryCity + " " + breweryState;
            
            Optional<Breweries_Geocode> brG = breweryGeoService.findOne(breweryGeoID);
            
            Double breweryLatitude= brG.get().getLatitude();
            Double breweryLongitude = brG.get().getLongitude();
           
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
        
        Optional<Brewery> br = breweryService.findOne(id);
        
        String name = "";
        String email = "";
        String website = "";
        String phone = "";
        String addressFull = "";
        
        if (!br.isPresent()) {
            //return error
        } else {
            
            name = br.get().getName();
            email = br.get().getEmail();
            phone = br.get().getPhone();
            website = br.get().getWebsite();
            
            addressFull = br.get().getName()+", "+br.get().getAddress1()+", "+br.get().getAddress2()+ ", "+br.get().getCity()+ ", "+br.get().getState()+ ", " +br.get().getCountry();
            
        }
        
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("BEGIN:VCARD");
        buffer.append("\nFN:").append(name);
        buffer.append("\nADR:").append(addressFull);
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