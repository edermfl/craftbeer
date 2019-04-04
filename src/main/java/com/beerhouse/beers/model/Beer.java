package com.beerhouse.beers.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "beer")
public class Beer {

    @Column(length = 100)
    private String alcoholContent;

    @Column(length = 100)
    private String category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ingredients;

    @Column(length = 100)
    private String name;

    @Column(length = 19, scale = 2)
    private BigDecimal price;

    /**
     * Default constructor
     */
    Beer() {
    }

    /**
     * @param pName
     * @param pCategory
     * @param pPrice
     * @param pIngredients
     * @param pAlcoholContent
     */
    public Beer(final String pName, final String pCategory, final BigDecimal pPrice, final String pIngredients,
		    final String pAlcoholContent) {
	alcoholContent = pAlcoholContent;
	category = pCategory;
	ingredients = pIngredients;
	name = pName;
	price = pPrice;
    }

    public String getAlcoholContent() {
	return alcoholContent;
    }

    public String getCategory() {
	return category;
    }

    public Long getId() {
	return id;
    }

    public String getIngredients() {
	return ingredients;
    }

    public String getName() {
	return name;
    }

    public BigDecimal getPrice() {
	return price;
    }

    public void setAlcoholContent(final String pAlcoholContent) {
	alcoholContent = pAlcoholContent;
    }

    public void setCategory(final String pCategory) {
	category = pCategory;
    }

    public void setId(final Long pId) {
	id = pId;
    }

    public void setIngredients(final String pIngredients) {
	ingredients = pIngredients;
    }

    public void setName(final String pName) {
	name = pName;
    }

    public void setPrice(final BigDecimal pPrice) {
	price = pPrice;
    }
}
