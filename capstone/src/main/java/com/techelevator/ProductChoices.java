package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.items.*;


public class ProductChoices
{
    //LinkedHashMap saves the order, HashMap does not.
    public Map<String, Item> productChoices = new LinkedHashMap<>();  //Should or could this be made private?

    //region CONSTRUCTOR
    public ProductChoices() throws FileNotFoundException
    {
        populateItemMap();
    }
    //endregion

    //GETTER
    public Map<String, Item> getProductChoices() {return productChoices;}


    public void populateItemMap() throws FileNotFoundException
    {
        File inventory = new File("vendingmachine.csv");

        try(Scanner fileScanner = new Scanner(inventory))
        {
            while(fileScanner.hasNextLine())
            {
                String line = fileScanner.nextLine();
                //Split the line at the |.
                String[] splitLine = line.split("\\|");
                //Get the value at index "0" to find the slot.
                char slot = splitLine[0].charAt(0);
                //Switch between the values.
                switch (slot)
                {
                    case 'A':
                        Chips_Item chips = new Chips_Item();
                        chips.setSlot(splitLine[0]);
                        chips.setName(splitLine[1]);
                        chips.setPrice((Double.parseDouble(splitLine[2])));
                        chips.setInventory(5);
                        productChoices.put(splitLine[0], chips);
                        break;
                    case 'B':
                        Candy_Item candy = new Candy_Item();
                        candy.setSlot(splitLine[0]);
                        candy.setName(splitLine[1]);
                        candy.setPrice((Double.parseDouble(splitLine[2])));
                        candy.setInventory(5);
                        productChoices.put(splitLine[0], candy);
                        break;
                    case 'C':
                        Drink_Item drink = new Drink_Item();
                        drink.setSlot(splitLine[0]);
                        drink.setName(splitLine[1]);
                        drink.setPrice((Double.parseDouble(splitLine[2])));
                        drink.setInventory(5);
                        productChoices.put(splitLine[0], drink);
                        break;
                    case 'D':
                        Gum_Item gum = new Gum_Item();
                        gum.setSlot(splitLine[0]);
                        gum.setName(splitLine[1]);
                        gum.setPrice((Double.parseDouble(splitLine[2])));
                        gum.setInventory(5);
                        productChoices.put(splitLine[0], gum);
                        break;
                }
            }
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
}