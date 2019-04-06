package com.beerhouse.beers.controller;

import com.beerhouse.beers.model.Beer;
import com.beerhouse.beers.service.BeerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebMvcTest(value = BeerController.class, secure = false)
public class BeerControllerTest {

    @MockBean
    private BeerService beerService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void getBeerById() throws Exception {
	Beer beer = new Beer("Eder's Beer", "Lagger", null, "Água, Lúpulo, malte e cevada", "5%");

	BDDMockito.given(beerService.findById(1l)).willReturn(Optional.of(beer));

	mvc.perform(get("/beers/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("name", is(beer.getName())))
			.andExpect(jsonPath("category", is(beer.getCategory())))
			.andExpect(jsonPath("ingredients", is(beer.getIngredients())))
			.andExpect(jsonPath("alcoholContent", is(beer.getAlcoholContent())))
	;
    }

    @Test
    public void postBeer() throws Exception {
	Beer beer = new Beer("Eder's Beer", "Lagger", new BigDecimal("10.5"), "Água, Lúpulo, malte e cevada", "5%");

	beerService.save(Mockito.any(Beer.class));
	Optional<Beer> newBeer = beerService.findById(beer.getId());

	Mockito.when(newBeer).thenReturn(Optional.of(beer));

	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/beers")
			.accept(MediaType.APPLICATION_JSON)
			.content("    {\n"
					+ "            \"alcoholContent\": \"4.0%\",\n"
					+ "            \"category\": \"Pilsen\",\n"
					+ "            \"id\": 8,\n"
					+ "            \"ingredients\": \"Lúpulo, Trigo e Água\",\n"
					+ "            \"name\": \"Bla bla bla Beer\",\n"
					+ "            \"price\": 1\n"
					+ "        }")
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void putBeer() throws Exception {
	Beer blaBlaBeer = new Beer("Bla Bla Beer", "Lagger", null, "Água, Lúpulo, malte e cevada", "5%");

	BDDMockito.given(beerService.findById(1l)).willReturn(Optional.of(blaBlaBeer));

	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/beers/1")
			.accept(MediaType.APPLICATION_JSON)
			.content("    {\n"
					+ "            \"name\": \"Eder's Beer\",\n"
					+ "            \"price\": 5.5\n"
					+ "        }")
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void putBeerWithoutContent() throws Exception {
	Beer beer = new Beer("Bla Bla Beer", "Lagger", null, "Água, Lúpulo, malte e cevada", "5%");

	BDDMockito.given(beerService.findById(1l)).willReturn(Optional.of(beer));

	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.put("/beers/1")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
    @Test
    public void deleteBeer() throws Exception {

	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.delete("/beers/1")
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	Assert.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}