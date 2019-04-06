package com.beerhouse.beers.controller;

import com.beerhouse.beers.model.Beer;
import com.beerhouse.beers.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Map;

@RestController
public class BeerController {

    final static String DEFAULT_LINES = "10";

    final static String DEFAULT_PAGE = "0";

    @Autowired
    BeerService beerService;

    @GetMapping(value = "/beers")
    public ResponseEntity<Page<Beer>> getAllBeers(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
		    @RequestParam(value = "lines", defaultValue = DEFAULT_LINES) Integer lines) {
	return ResponseEntity.ok().body(beerService.listAllBeers(page, lines));
    }

    @GetMapping(value = "/beers/{id}")
    public ResponseEntity<Beer> getBeer(@PathVariable(value = "id") Long id) {
	return ResponseEntity.ok().body(beerService.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @PostMapping(value = "/beers")
    public ResponseEntity<Beer> newBeer(@RequestBody Beer beer) {
	beerService.save(beer);
	final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(beer.getId())
			.toUri();

	return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/beers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Beer> partialUpdateBeer(@RequestBody Map<String, Object> updates, @PathVariable(value = "id") Long id) {
	beerService.update(updates, id);
	return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/beers/{id}")
    public ResponseEntity<Beer> deleteBeer(@PathVariable(value = "id") Long id) {
	beerService.delete(id);
	return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/beers/{id}")
    public ResponseEntity<Beer> saveBeer(@RequestBody Beer newBeer, @PathVariable(value = "id") Long id) {
	Beer beer = beerService.findById(id).orElse(null);
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
	beerService.save(beer);
	return ResponseEntity.ok().build();
    }
}
