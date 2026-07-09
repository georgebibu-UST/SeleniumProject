package com.ust.sdet.Selenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.ElementsCollection;

public class SelenideCatalogPageTest {
    @Test
    void searchShowsResults() {
        open("http://localhost:5173/catalog");
        CatalogPage catalog = page(CatalogPage.class);
        ElementsCollection results = catalog.searchFor("headphones").cards();
        assertFalse(results.isEmpty(), "No products found");
        results.forEach(card -> {
            card.shouldBe(visible);
        });
    }
}