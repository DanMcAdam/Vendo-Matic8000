package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CurrentMoneyTest
{

    @Test
    public void getCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();

        BigDecimal amount = BigDecimal.valueOf(5.50);

        testMoney.addMoney(amount);
        assertEquals(testMoney.getCurrentMoney(), amount);
    }

    @Test
    public void testAddNegativeValueToCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        BigDecimal testAmount = BigDecimal.valueOf(-5);

        testMoney.addMoney(testAmount);
        assertEquals(testAmount.multiply(BigDecimal.valueOf(-1)), testMoney.getCurrentMoney());
    }
    @Test
    public void testAddZeroToCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        BigDecimal testAmount = new BigDecimal(0.0);
        testMoney.addMoney(testAmount);
        assertEquals(testAmount, testMoney.getCurrentMoney());
    }

    @Test
    public void testSubtractNegativeValueFromCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        BigDecimal subtractAmount = BigDecimal.valueOf(-5);
        testMoney.addMoney(BigDecimal.valueOf(5));
        testMoney.subtractMoney(subtractAmount);
        assertEquals(BigDecimal.valueOf(0), testMoney.getCurrentMoney());
    }

    @Test
    public void testSubtractMoreThanBalanceFromCurrentMoney()
    {
        CurrentMoney testMoney = new CurrentMoney();
        BigDecimal subtractAmount = new BigDecimal(5);
        testMoney.addMoney(BigDecimal.valueOf(2));
        testMoney.subtractMoney(subtractAmount);
        assertEquals(BigDecimal.valueOf(2), testMoney.getCurrentMoney());
    }

    @Test
    public void testCalculateChange()
    {
        CurrentMoney testMoney = new CurrentMoney();
        testMoney.addMoney(BigDecimal.valueOf(7.15));
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
        testMoney.addMoney(BigDecimal.valueOf(.05));
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
        testMoney.addMoney(BigDecimal.valueOf(.1));
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
        testMoney.addMoney(BigDecimal.valueOf(.25));
        Map<String, Integer> testChange = new HashMap();
        testChange.put("Nickels", 0);
        testChange.put("Dimes", 0);
        testChange.put("Quarters", 1);
        assertEquals(testChange, CurrentMoney.calculateChange(testMoney.getCurrentMoney()));
    }
}