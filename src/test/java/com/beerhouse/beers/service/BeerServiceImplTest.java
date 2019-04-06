package com.beerhouse.beers.service;

import com.beerhouse.beers.model.Beer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BeerServiceImplTest {

    @Autowired
    private BeerService beerService;

    @Test
    public void whenFindAllPagination() {

	Page<Beer> beerList = beerService.listAllBeers(0,10);

	Assert.assertEquals(2, beerList.getTotalPages());
	Assert.assertEquals(15, beerList.getTotalElements());
	Assert.assertEquals(10, beerList.getNumberOfElements());
	Assert.assertEquals(10, beerList.getContent().size());

    }

    @Test
    public void whenFindAllPagination20LinesBySearch() {

	Page<Beer> beerList = beerService.listAllBeers(0,20);

	Assert.assertEquals(1, beerList.getTotalPages());
	Assert.assertEquals(15, beerList.getTotalElements());
	Assert.assertEquals(15, beerList.getNumberOfElements());
	Assert.assertEquals(15, beerList.getContent().size());

    }

    @Test
    public void whenFindAllPaginationPage1() {

	Page<Beer> beerList = beerService.listAllBeers(1,10);

	Assert.assertEquals(2, beerList.getTotalPages());
	Assert.assertEquals(15, beerList.getTotalElements());
	Assert.assertEquals(5, beerList.getNumberOfElements());
	Assert.assertEquals(5, beerList.getContent().size());

	Beer beer = new Beer("Eder's Beer", "Lagger", new BigDecimal("10.5"), "Água, Lúpulo, malte e cevada", "5%");
	beerService.save(beer);

	Beer beer2 = new Beer("Eder's Wiss Beer", "Wiss", new BigDecimal("12.5"), "Água, Lúpulo, malte, cevada e trigo", "5%");
	beerService.save(beer2);

	Page<Beer> beerList2 = beerService.listAllBeers(1,10);

	Assert.assertEquals(2, beerList2.getTotalPages());
	Assert.assertEquals(17, beerList2.getTotalElements());
	Assert.assertEquals(7, beerList2.getNumberOfElements());
	Assert.assertEquals(7, beerList2.getContent().size());

    }

    @Test
    public void whenFindById() {
	Beer beer = beerService.findById(1l).orElse(null);

	Assert.assertNotNull(beer);
	Assert.assertEquals("Heineken", beer.getName());

    }

    @Test
    public void whenUpdateById() {
	Beer beer = beerService.findById(1l).orElse(null);

	Assert.assertNotNull(beer);
	Assert.assertEquals("Heineken", beer.getName());
	Assert.assertEquals("4.5%", beer.getAlcoholContent());

	Map<String, Object> updates = new HashMap<>();
	updates.put("alcoholContent", "4.2%");
	Beer updatedBeer = beerService.update(updates, 1l);

	Assert.assertEquals("Heineken", updatedBeer.getName());
	Assert.assertEquals("4.2%", updatedBeer.getAlcoholContent());

    }

    @Test
    public void whenCreateOrUpdate() {

	Beer beer = new Beer("Eder's Beer", "Lagger", new BigDecimal("10.5"), "Água, Lúpulo, malte e cevada", "5%");
	beerService.save(beer);

	Beer ederBeer = beerService.findById(beer.getId()).orElse(null);

	Assert.assertNotNull(ederBeer);
	Assert.assertEquals("Eder's Beer", ederBeer.getName());

	ederBeer.setName("The Best Eder's Beer");
	Beer updatedBeer = beerService.save(ederBeer);

	Assert.assertEquals("The Best Eder's Beer", updatedBeer.getName());

    }

}