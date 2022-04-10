package com.techelevator.items;

public class Gum_Item implements Item
{
    private String name = "";
    private String slot = "";
    private double price = 0.00;
    private int inventory = 0;

    //region GETTERS
    @Override
    public String getName() {return name;}
    @Override
    public double getPrice() {return price;}
    @Override
    public String getSlot() {return slot;}
    @Override
    public int getInventory() {return inventory;}
    //endregion

    //region SETTERS
    public void setName(String name) {this.name = name;}

    public void setSlot(String slot) {this.slot = slot;}

    public void setPrice(double price) {this.price = price;}

    public void setInventory(int inventory) {this.inventory = inventory;}
    //endregion
}