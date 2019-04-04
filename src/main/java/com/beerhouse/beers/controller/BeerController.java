package com.beerhouse.beers.controller;

import com.beerhouse.beers.model.Beer;
import com.beerhouse.beers.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @GetMapping(value = "/beers")
    public List<Beer> getAllBeers() {
	return beerService.listAllBeers();
    }

    @PostMapping(value = "/defaultDatas")
    public String insertsDefaultDatas() {
	Beer skol = new Beer("Skol", "Pilsen", new BigDecimal("1.00"), "Àgua, Cereais não maltados e Lúpulo", "");
	beerService.save(skol);
	Beer heineken = new Beer("Heineken", "Premium Larger", new BigDecimal("1.80"), "Malte de Cevada, Água e Lúpulo", "");
	beerService.save(heineken);
	Beer eisenbahn = new Beer("Eisenbahn", "Pilsenr", new BigDecimal("1.50"), "Lúpulo, Cevada, Trigo e Água", "");
	beerService.save(eisenbahn);
	return "Sucesso na criação da massa de dados\n";
    }

    @PostMapping(value = "/beers")
    public ResponseEntity<Beer> newBeer(@RequestBody Beer beer) {
	beerService.save(beer);
	return new ResponseEntity<>(beer, HttpStatus.CREATED);
    }
}
