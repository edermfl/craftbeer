package com.beerhouse.beers.service.impl;

import com.beerhouse.beers.model.Beer;
import com.beerhouse.beers.repository.BeerRepository;
import com.beerhouse.beers.service.BeerService;
import net.vidageek.mirror.dsl.AccessorsController;
import net.vidageek.mirror.dsl.Mirror;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BeerServiceImpl implements BeerService {

    @Autowired
    public BeerRepository beerRepository;

    @Override public void delete(final Long pId) {
	if (pId != null) {
	    beerRepository.delete(pId);
	}
    }

    @Override public Optional<Beer> findById(final Long pId) {
	return Optional.ofNullable(beerRepository.findOne(pId));
    }

    @Override public Page<Beer> listAllBeers(final Integer pPage, final Integer pLines) {
	PageRequest pageRequest = new PageRequest(pPage, pLines);

	Page<Beer> allBeers = beerRepository.findAll(pageRequest);
	return allBeers;
    }

    @Override public Beer save(final Beer pBeer) {
	return beerRepository.save(pBeer);
    }

    @Override public Beer update(final Map<String, Object> pProperties, final Long pId) {
	Beer beer = findById(pId).orElseThrow(EntityNotFoundException::new);
	pProperties.remove("id");
	pProperties.computeIfPresent("price", (k, v) -> v = new BigDecimal(v.toString()));
	AccessorsController mirror = new Mirror().on(beer);
	pProperties.forEach((k, v) -> mirror.set().field(k).withValue(v));

	return beerRepository.save(beer);
    }
}
