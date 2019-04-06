package com.beerhouse.beers.service;

import com.beerhouse.beers.model.Beer;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Optional;

/**
 *
 */
public interface BeerService {

    /**
     * Delete a cerveja do id informado
     *
     * @param pId
     */
    void delete(Long pId);

    /**
     * Busca uma bcerveja por id
     *
     * @param pId da cerveja
     * @return Cerveja ou null se não encontrar
     */
    Optional<Beer> findById(Long pId);

    /**
     * Lista todas as cervejas cadastradas
     *
     * @param pPage
     * @param pLines
     * @return lista de cervejas
     */
    Page<Beer> listAllBeers(final Integer pPage, final Integer pLines);

    /**
     * Inclui ou altera um cerveja específica
     *
     * @param pBeer
     */
    Beer save(Beer pBeer);

    /**
     * altera um cerveja específica  com o dados informados no mapa
     *
     * @param pProperties
     */
    Beer update(Map<String, Object> pProperties, Long pId);
}
