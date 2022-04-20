package com.techelevator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CurrentMoney
{
    private BigDecimal currentMoney = new BigDecimal(0.0);
    private static final BigDecimal[] COIN_VALUES = {new BigDecimal(".25"), new BigDecimal(".10"), new BigDecimal(".05")};

    //region GETTER
    public BigDecimal getCurrentMoney() {return currentMoney;}
    //endregion

    public void addMoney(BigDecimal addAmount)
    {
        currentMoney = currentMoney.add(addAmount.abs());
    }

    public void subtractMoney (BigDecimal subtractAmount)
    {
        if (currentMoney.compareTo(subtractAmount) >= 0)
        {
            currentMoney = currentMoney.subtract(subtractAmount.abs());
        }
    }

    public boolean sufficientBalance(BigDecimal itemCost)
    {
        return currentMoney.compareTo(itemCost) >= 0;
    }

    public static Map calculateChange(BigDecimal currentMoney)
    {
        Map<String, Integer> change = new HashMap();
        change.put("Nickels", 0);
        change.put("Dimes", 0);
        change.put("Quarters", 0);

        BigDecimal changeCounterDummy = currentMoney;

        while (changeCounterDummy.compareTo(new BigDecimal(0)) > 0) {
            if (COIN_VALUES[0].compareTo(changeCounterDummy) < 1)
            {
                int amount = change.get("Quarters");
                change.put("Quarters", amount + 1);
                changeCounterDummy = changeCounterDummy.subtract(COIN_VALUES[0]);
            }
            else if (COIN_VALUES[1].compareTo(changeCounterDummy) <= 0)
            {
                int amount = change.get("Dimes");
                change.put("Dimes", amount + 1);
                changeCounterDummy = changeCounterDummy.subtract(COIN_VALUES[1]);
            }
            else if (COIN_VALUES[2].compareTo(changeCounterDummy) <= 0)
            {
                int amount = change.get("Nickels");
                change.put("Nickels", amount + 1);
                changeCounterDummy = changeCounterDummy.subtract(COIN_VALUES[2]);
            }
            else
            {
                break;
            }
        }
        return change;
    }
}