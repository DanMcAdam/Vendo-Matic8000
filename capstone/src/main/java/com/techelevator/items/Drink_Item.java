package com.techelevator.items;

import com.techelevator.ProductChoices;

import java.math.BigDecimal;

public class Drink_Item implements Item
{
    private String name = "";
    private String slot = "";
    private BigDecimal price;
    private int inventory = 0;

    // Constructor
    public Drink_Item(String slot, String name, BigDecimal price)
    {
        this.name = name;
        this.slot = slot;
        this.price = price;
        this.inventory = ProductChoices.DEFAULT_STARTING_INVENTORY;
    }

    //region GETTERS
    @Override
    public String getName() {return name;}
    @Override
    public BigDecimal getPrice() {return price;}
    @Override
    public String getSlot() {return slot;}
    @Override
    public int getInventory() {return inventory;}
    //endregion

    @Override
    public String dispenseItem()
    {
        if (inventory != 0)
        {
            inventory--;
            return "Glug Glug, Yum!";
        }
        else return "Item is currently sold out";
    }

    //region SETTERS
    public void setName(String name) {this.name = name;}

    public void setSlot(String slot) {this.slot = slot;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public void setInventory(int inventory) {this.inventory = inventory;}
    //endregion
}