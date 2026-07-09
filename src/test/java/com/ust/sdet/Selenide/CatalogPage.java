package com.ust.sdet.Selenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class CatalogPage {
    private final SelenideElement searchInput = $("[data-test='search-input']");
    private final ElementsCollection cards = $$("[data-test='product-card']");
    private final SelenideElement resultCount = $("[data-test='catalog-result-count']");
    private final SelenideElement emptyState = $("[data-test='empty-search']");

    public CatalogPage openPage() {
        open("http://localhost:5173/catalog");
        return this;
    }

    public CatalogPage searchFor(String keyword) {
        searchInput.shouldBe(visible).setValue(keyword).pressEnter();
        return this;
    }

    public ElementsCollection cards() {
        return cards;
    }

    public SelenideElement resultCount() {
        return resultCount;
    }

    public SelenideElement emptyState() {
        return emptyState;
    }

    public ElementsCollection prices() {
        return $$("[data-test='product-card'][data-test='product-price']");
    }
}