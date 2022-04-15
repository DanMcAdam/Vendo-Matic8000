package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CurrentMoneyTest
{

    @Test
    public void getCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();

        double amount = 5.50;

        testMoney.addMoney(amount);
        assertEquals(testMoney.getCurrentMoney(), amount, .001);
    }

    @Test
    public void testAddNegativeValueToCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        double testAmount = -5;

        testMoney.addMoney(testAmount);
        assertEquals(-testAmount, testMoney.getCurrentMoney(), .001);
    }
    @Test
    public void testAddZeroToCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        double testAmount = 0;
        testMoney.addMoney(testAmount);
        assertEquals(testAmount, testMoney.getCurrentMoney(), .001);
    }

    @Test
    public void testSubtractNegativeValueFromCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        double subtractAmount = -5;
        testMoney.addMoney(5);
        testMoney.subtractMoney(subtractAmount);
        assertEquals(0, testMoney.getCurrentMoney(), .001);
    }

    @Test
    public void testSubtractMoreThanBalanceFromCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        double subtractAmount = 5;
        testMoney.addMoney(2);
        testMoney.subtractMoney(subtractAmount);
        assertEquals(2, testMoney.getCurrentMoney(), .001);
    }

    @Test
    public void testCalculateChange()
    {
        CurrentMoney testMoney = new CurrentMoney();
        testMoney.addMoney(7.15);
        Map<String, Integer> testChange = new HashMap();
        testChange.put("Nickels", 1);
        testChange.put("Dimes", 1);
        testChange.put("Quarters", 28);
        assertEquals(testChange, CurrentMoney.calculateChange(testMoney.getCurrentMoney()));
    }

    @Test
    public void testCalculateChangeWithOneNickel()
    {
        CurrentMoney testMoney = new CurrentMoney();
        testMoney.addMoney(.05);
        Map<String, Integer> testChange = new HashMap();
        testChange.put("Nickels", 1);
        testChange.put("Dimes", 0);
        testChange.put("Quarters", 0);
        assertEquals(testChange, CurrentMoney.calculateChange(testMoney.getCurrentMoney()));
    }

    @Test
    public void testCalculateChangeWithOneDime()
    {
        CurrentMoney testMoney = new CurrentMoney();
        testMoney.addMoney(.1);
        Map<String, Integer> testChange = new HashMap();
        testChange.put("Nickels", 0);
        testChange.put("Dimes", 1);
        testChange.put("Quarters", 0);
        assertEquals(testChange, CurrentMoney.calculateChange(testMoney.getCurrentMoney()));
    }

    @Test
    public void testCalculateChangeWithOneQuarter()
    {
        CurrentMoney testMoney = new CurrentMoney();
        testMoney.addMoney(.25);
        Map<String, Integer> testChange = new HashMap();
        testChange.put("Nickels", 0);
        testChange.put("Dimes", 0);
        testChange.put("Quarters", 1);
        assertEquals(testChange, CurrentMoney.calculateChange(testMoney.getCurrentMoney()));
    }
}