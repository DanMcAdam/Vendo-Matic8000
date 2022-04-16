package com.techelevator.items;

import java.math.BigDecimal;

public interface Item
{
    String getName();
    BigDecimal getPrice();
    String getSlot();
    int getInventory();
    String dispenseItem();
    default boolean isInStock()
    {
        return this.getInventory() > 0;
    }
}