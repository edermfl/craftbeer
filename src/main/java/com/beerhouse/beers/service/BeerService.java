package com.beerhouse.beers.service;

import com.beerhouse.beers.model.Beer;

import java.util.List;

/**
 *
 */
public interface BeerService {

    /**
     * Busca uma bcerveja por id
     *
     * @param pId da cerveja
     * @return Cerveja ou null se não encontrar
     */
    Beer findById(Long pId);

    /**
     * Lista todas as cervejas cadastradas
     *
     * @return lista de cervejas
     */
    List<Beer> listAllBeers();

    /**
     * Inclui ou altera um cerveja específica
     *
     * @param pBeer
     */
    Beer save(Beer pBeer);
}
