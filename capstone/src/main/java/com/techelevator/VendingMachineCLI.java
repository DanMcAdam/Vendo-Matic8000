package com.techelevator;

import com.techelevator.items.Item;
import com.techelevator.view.Menu;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;


public class VendingMachineCLI
{
    private Menu menu;
    static ProductChoices productChoices;
    static CurrentMoney currentMoney;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();  //format currency

    //region Menu Strings
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALESREPORT   = "Sales Report";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS,
            MAIN_MENU_OPTION_PURCHASE,
            MAIN_MENU_OPTION_EXIT,
            MAIN_MENU_OPTION_SALESREPORT};



    private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY,
            PURCHASE_MENU_SELECT_PRODUCT,
            PURCHASE_MENU_FINISH_TRANSACTION};

    private static final String FEED_MONEY_ONE_DOLLAR = "Add $1";
    private static final String FEED_MONEY_TWO_DOLLAR = "Add $2";
    private static final String FEED_MONEY_FIVE_DOLLAR = "Add $5";
    private static final String FEED_MONEY_TEN_DOLLAR = "Add $10";
    private static final String[] PURCHASE_MENU_FEED_MONEY_OPTIONS = {FEED_MONEY_ONE_DOLLAR,
            FEED_MONEY_TWO_DOLLAR,
            FEED_MONEY_FIVE_DOLLAR,
            FEED_MONEY_TEN_DOLLAR};

    private static final double[] COIN_VALUES = {.25, .1, .05};
    //endregion


    public VendingMachineCLI(Menu menu) throws FileNotFoundException
    {
        this.menu = menu;
        productChoices = new ProductChoices();
        currentMoney = new CurrentMoney();
    }

    public void run()
    {
        while (true)
        {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            /* DISPLAY ITEMS*/
            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS))
            {
                displayItems();
            }

            /*PURCHASE OPTION*/
            else if (choice.equals(MAIN_MENU_OPTION_PURCHASE))
            {
                purchaseItems();
            }

            /*EXIT OPTION*/
            else if (choice.equals(MAIN_MENU_OPTION_EXIT))
            {
                System.out.println("Goodbye!");
                System.exit(0);
            }

            else if (choice.equals(MAIN_MENU_OPTION_SALESREPORT))
            {
                //implement sales report
            }
        }
    }

    public void displayItems()
    {
        for (String f : productChoices.getProductChoices().keySet())
        {
            System.out.print(" " + productChoices.getProductChoices().get(f).getSlot() + " |");
            System.out.print(" " + productChoices.getProductChoices().get(f).getName() + " |");
            System.out.print(" " + formatter.format(productChoices.getProductChoices().get(f).getPrice()) + " | ");

            if (productChoices.getProductChoices().get(f).getInventory() != 0)
            {
                System.out.println(productChoices.getProductChoices().get(f).getInventory());
            }
            else
            {
                System.out.println("SOLD OUT");
            }
        }
    }

    public void purchaseItems()
    {
        String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS, "\nCurrent Money Provided: " + formatter.format(currentMoney.getCurrentMoney()).toString());

        if (purchaseChoice.equals(PURCHASE_MENU_FEED_MONEY))
        {
            String feedChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_FEED_MONEY_OPTIONS, "");

            //region Money amounts input
            if (feedChoice.equals(FEED_MONEY_ONE_DOLLAR))
            {
                processTransaction("FEED MONEY: ", true, 1);
            }
            else if (feedChoice.equals(FEED_MONEY_TWO_DOLLAR))
            {
                processTransaction("FEED MONEY: ", true, 2);
            }
            else if (feedChoice.equals(FEED_MONEY_FIVE_DOLLAR))
            {
                processTransaction("FEED MONEY: ", true, 5);
            }
            else if (feedChoice.equals(FEED_MONEY_TEN_DOLLAR))
            {
                processTransaction("FEED MONEY: ", true, 10);
            }
            //endregion
            purchaseItems();
        }
        else if (purchaseChoice.equals(PURCHASE_MENU_SELECT_PRODUCT))
        {
            displayItems();
            processPurchase(menu.getChoiceFromProductChoiceMap(productChoices));
        }
        else if (purchaseChoice.equals(PURCHASE_MENU_FINISH_TRANSACTION))
        {
            int[] coinAmounts = new int[3];
            int coinValueCounter = 0;
            double changeCounterDummy = currentMoney.getCurrentMoney();
            while (changeCounterDummy > 0.0001)
            {
                if (COIN_VALUES[coinValueCounter] > currentMoney.getCurrentMoney())
                {
                    coinValueCounter++;
                }
                else
                {
                    coinAmounts[coinValueCounter]++;
                    changeCounterDummy -= COIN_VALUES[coinValueCounter];
                }
            }
            System.out.println("Your Change is " + coinAmounts[0] + " quarters, " + coinAmounts[1] + " dimes, and " + coinAmounts[2] + " nickels.");
            processTransaction("GIVE CHANGE: ", false,  currentMoney.getCurrentMoney());
        }
    }


    private void processPurchase(Item chosenItem)
    {
        if (chosenItem != null)
        {
            if (currentMoney.checkIfSufficientFunds(chosenItem.getPrice()))
            {
                if (chosenItem.getInventory() >= 1)
                {
                    processTransaction(chosenItem.getName() + " " + chosenItem.getSlot() + " ", false, chosenItem.getPrice());
                    System.out.println("You have chosen " + chosenItem.getName() + ", the cost of the item is " + formatter.format(chosenItem.getPrice()) + " and you have " + formatter.format(currentMoney.getCurrentMoney()) + " remaining on your balance\n");
                    System.out.println(chosenItem.dispenseItem());

                }
                else chosenItem.dispenseItem();
            }
            else System.out.println("Insufficient funds");
        }
        purchaseItems();
    }

    private void processTransaction (String transaction, boolean isDeposit, double transactionAmount)
    {
        if (isDeposit)
        {
            currentMoney.addMoney(transactionAmount);
        }
        else
        {
            currentMoney.subtractMoney(transactionAmount);
        }
        Logger.log(transaction + formatter.format(transactionAmount) + " " + formatter.format(currentMoney.getCurrentMoney()));
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
