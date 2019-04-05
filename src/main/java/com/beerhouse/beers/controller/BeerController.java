package com.beerhouse.beers.controller;

import com.beerhouse.beers.model.Beer;
import com.beerhouse.beers.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @GetMapping(value = "/beers")
    public List<Beer> getAllBeers() {
	return beerService.listAllBeers();
    }

    @GetMapping(value = "/beers/{id}")
    public Beer getBeer(@PathVariable(value = "id") Long id) {
	Beer beer = beerService.findById(id);
	return beer;
    }

    @PostMapping(value = "/beers")
    public ResponseEntity<Beer> newBeer(@RequestBody Beer beer) {
	beerService.save(beer);
	return new ResponseEntity<>(beer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/beers/{id}")
    public ResponseEntity<Beer> saveBeer(@RequestBody Beer newBeer, @PathVariable(value = "id") Long id) {
	Beer beer = beerService.findById(id);
	if (beer != null) {
	    beer.setName(newBeer.getName());
	    beer.setIngredients(newBeer.getIngredients());
	    beer.setCategory(newBeer.getCategory());
	    beer.setAlcoholContent(newBeer.getAlcoholContent());
	    beer.setPrice(newBeer.getPrice());
	} else {
	    beer = newBeer;
	    beer.setId(id);
	}

	return new ResponseEntity<>(beerService.save(beer),HttpStatus.OK);
    }
    @PatchMapping(value = "/beers/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Beer> particalUpdateBeer(@RequestBody Map<String,Object> updates, @PathVariable(value = "id") Long id) {
	Beer beer = beerService.findById(id);
//	updates

	return new ResponseEntity<>(beerService.save(beer),HttpStatus.OK);
    }
}
