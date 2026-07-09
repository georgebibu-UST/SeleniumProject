Feature: Catalog sorting

    @sort
    Scenario: Sort products by price low to high
        Given the catalog is open
        When I sort products by "Price: Low to High"
        Then the products are sorted in ascending order