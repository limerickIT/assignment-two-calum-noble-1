/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.service.BeerService;
import com.sd4.service.BreweryService;
import com.sd4.service.CategoryService;
import com.sd4.service.StyleService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/beers")
public class BeerController { 
    
    @Autowired
    private BeerService beerService;
    
    @Autowired
    private BreweryService breweryService;
    
    @Autowired
    private StyleService styleService;
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("count")
    public long getCount() {
        return beerService.count();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        beerService.deleteByID(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity add(@RequestBody Beer a) {
        beerService.saveBeer(a);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity edit(@RequestBody Beer a) { //the edit method should check if the Beer object is already in the DB before attempting to save it.
        beerService.saveBeer(a);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @GetMapping(value = "/{size}/{id}/", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<BufferedImage> getBeerImage(@PathVariable long id, @PathVariable String size) throws IOException {
            
        Optional<Beer> b = beerService.findOne(id);
        
        String path = "src/main/resources/static/assets/images/" + size + "/" + id + ".jpg";
        BufferedImage img = ImageIO.read(new File(path));
        
        return ResponseEntity.ok(img);

    }
  
    @GetMapping(value = "/listBeers", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<Beer> getAllBeers(){
        
        List<Beer> aList = beerService.findAll();
        
        for (final Beer b : aList){
            long id = b.getId();
            Link selfLink = linkTo(BeerController.class).slash(id).withSelfRel();
            b.add(selfLink);
            
            Link beerLink = new Link("http://localhost:8888/beers/beerInfo/" + id).withRel("beerInfo");
            b.add(beerLink);
        }
        
        Link link = linkTo(BeerController.class).withSelfRel();
        
        CollectionModel<Beer> result = CollectionModel.of(aList, link);
        
        return result;
    }
    
    @GetMapping(value = "/beerInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> beerInfo(@PathVariable long id){
         
         Optional<Beer> b = beerService.findOne(id);
         HashMap<String,String> map = new HashMap<>();
         
         if (!b.isPresent()){
            map.put("ERROR", "ERROR");
        }
        else{
            String name = b.get().getName();
            String desc = b.get().getDescription();
            Long breweryID = b.get().getBrewery_id();
            
            Optional<Brewery> br = breweryService.findOne(breweryID);
            
            String breweryName = br.get().getName();
            
            map.put("Brewery: ", breweryName);
            map.put("Description: ", desc);
            map.put("Name: ", name);
            
        }
         
        return map;
     }

}