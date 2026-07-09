package com.ust.sdet.bdd;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortSteps {

    private final World world;

    public SortSteps(World world) {
        this.world = world;
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortOption) {
        world.catalog.sortBy(sortOption);
    }

    @Then("the products are sorted in ascending order")
    public void theProductsAreSortedInAscendingOrder() {

        List<Integer> prices = world.catalog.prices();

        List<Integer> sorted = prices.stream()
                .sorted()
                .toList();

        assertEquals(sorted, prices, "Products are not sorted correctly");
    }
}