package com.beerhouse.beers.service.impl;

import com.beerhouse.beers.model.Beer;
import com.beerhouse.beers.repository.BeerRepository;
import com.beerhouse.beers.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BeerServiceImpl implements BeerService {

    @Autowired
    public BeerRepository beerRepository;

    @Override public List<Beer> listAllBeers() {
	List<Beer> allBeers = beerRepository.findAll();
	return allBeers;
    }

    @Override public void save(final Beer pBeer) {
	beerRepository.save(pBeer);
    }
}
