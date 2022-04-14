package com.techelevator;

public class CurrentMoney
{
    private double currentMoney = 0.00;

    //region GETTER
    public double getCurrentMoney() {return currentMoney;}
    //endregion

    public void addMoney(double addAmount)
    {
        this.currentMoney += addAmount;
    }

    public void subtractMoney (double subtractAmount)
    {
        if (subtractAmount <= currentMoney)
        {
            currentMoney-=subtractAmount;
        }
    }

    public boolean checkIfSufficientFunds (double checkAmount)
    {
        if (checkAmount <= currentMoney)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}