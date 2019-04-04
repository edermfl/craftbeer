package com.beerhouse.beers.service;

import com.beerhouse.beers.model.Beer;

import java.util.List;

public interface BeerService {

    List<Beer> listAllBeers();

    void save(Beer pBeer);
}
