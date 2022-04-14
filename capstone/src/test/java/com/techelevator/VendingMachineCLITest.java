package com.techelevator;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import static org.junit.Assert.*;

public class VendingMachineCLITest
{


    @Test
    public void run()
    {
    }

    @Test
    public void displayItems()
    {
        System.out.println(System.getProperty("user.dir"));
        try
        {
            Random random = new Random();
            VendingMachineCLI testmachine = new VendingMachineCLI();
            int testAmount = random.nextInt(15 - 1) + 1;
            //int testAmount = 5;
            int testDummy = testAmount;
            while (testDummy > 0)
            {
                if (testDummy >= 10) {testDummy-=10; testmachine.feedMoney(VendingMachineCLI.PURCHASE_MENU_FEED_MONEY_OPTIONS[3]);}
                else if (testDummy >= 5) {testDummy-=5; testmachine.feedMoney(VendingMachineCLI.PURCHASE_MENU_FEED_MONEY_OPTIONS[2]); }
                else if (testDummy >= 2) {testDummy-=2; testmachine.feedMoney(VendingMachineCLI.PURCHASE_MENU_FEED_MONEY_OPTIONS[1]); }
                else {testDummy-=1; testmachine.feedMoney(VendingMachineCLI.PURCHASE_MENU_FEED_MONEY_OPTIONS[0]); }
            }
            System.out.println("test amount = " + testAmount + " currentMoney = " + testmachine.testShowCurrentMoney());
            assertEquals((double) testAmount, testmachine.testShowCurrentMoney(), .01);

        }
        catch (FileNotFoundException exception)
        {
            System.err.println("Caught in test");

        }
    }

    @Test
    public void purchaseItems()
    {

    }
}