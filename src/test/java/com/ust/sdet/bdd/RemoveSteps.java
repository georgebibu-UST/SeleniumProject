package com.ust.sdet.bdd;

import io.cucumber.java.en.When;

public class RemoveSteps {

    private final World world;

    public RemoveSteps(World world) {
        this.world = world;
    }

    @When("I remove the item from the cart")
    public void iRemoveTheItemFromTheCart() {
        world.cart.removeFirstItem();
    }
}
