package com.ust.sdet.Selenide;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;

class ResultsTest {
    @Test
    void validateCatalogItems() {
        open("http://localhost:5173/catalog");
        ElementsCollection rows = $$("[data-test='product-card']");
        rows.shouldHave(size(3));
        rows.shouldHave(texts("Footwear", "Bag", "Electronics"));
        rows.filterBy(text("Bag")).shouldHave(size(1));
    }
}