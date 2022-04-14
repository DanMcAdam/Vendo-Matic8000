package com.techelevator;

import com.techelevator.items.Item;
import com.techelevator.view.Menu;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class VendingMachineCLI
{
    private Menu menu;
    static ProductChoices productChoices;
    static CurrentMoney currentMoney;
    NumberFormat currency = NumberFormat.getCurrencyInstance();  //format currency

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

    //private static final double[] COIN_VALUES = {25, 10, 5};
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
            switch(choice)
            {
                /* DISPLAY ITEMS*/
                case MAIN_MENU_OPTION_DISPLAY_ITEMS:
                    displayItems();
                    break;

                /*PURCHASE OPTION*/
                case MAIN_MENU_OPTION_PURCHASE:
                    purchaseItems();
                    break;

                /*EXIT OPTION*/
                case MAIN_MENU_OPTION_EXIT:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;

                /*HIDDEN SALES REPORT MENU*/
                case MAIN_MENU_OPTION_SALESREPORT:
                    //implement sales report
                    System.out.println("HIDDEN MENU ACCESSED!");
                    break;
            }
//            /* DISPLAY ITEMS*/
//            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS))
//            {
//                displayItems();
//            }
//
//            /*PURCHASE OPTION*/
//            else if (choice.equals(MAIN_MENU_OPTION_PURCHASE))
//            {
//                purchaseItems();
//            }
//
//            /*EXIT OPTION*/
//            else if (choice.equals(MAIN_MENU_OPTION_EXIT))
//            {
//                System.out.println("Goodbye!");
//                System.exit(0);
//            }
//
//            /*HIDDEN SALES REPORT MENU*/
//            else if (choice.equals(MAIN_MENU_OPTION_SALESREPORT))
//            {
//                //implement sales report
//                System.out.println("HIDDEN MENU ACCESSED!");
//            }
        }
    }

    public void displayItems()
    {
        for (String f : productChoices.getProductChoices().keySet())
        {
            System.out.print(" " + productChoices.getProductChoices().get(f).getSlot() + " |");
            System.out.print(" " + productChoices.getProductChoices().get(f).getName() + " |");
            System.out.print(" " + currency.format(productChoices.getProductChoices().get(f).getPrice()) + " | ");

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
        String purchaseChoice = (String) menu.getChoiceFromPurchaseOptions(PURCHASE_MENU_OPTIONS, "\nCurrent Money Provided: " + currency.format(currentMoney.getCurrentMoney()));

        switch (purchaseChoice) {
            case PURCHASE_MENU_FEED_MONEY:
                String feedChoice = (String) menu.getChoiceFromPurchaseOptions(PURCHASE_MENU_FEED_MONEY_OPTIONS, "\nCurrent Money Provided: " + currency.format(currentMoney.getCurrentMoney()));

                //region Money Feed Options.
                switch (feedChoice) {
                    case FEED_MONEY_ONE_DOLLAR:
                        processTransaction("FEED MONEY: ", true, 1);
                        break;
                    case FEED_MONEY_TWO_DOLLAR:
                        processTransaction("FEED MONEY: ", true, 2);
                        break;
                    case FEED_MONEY_FIVE_DOLLAR:
                        processTransaction("FEED MONEY: ", true, 5);
                        break;
                    case FEED_MONEY_TEN_DOLLAR:
                        processTransaction("FEED MONEY: ", true, 10);
                        break;
                }
                //endregion
                purchaseItems();
                break;
            case PURCHASE_MENU_SELECT_PRODUCT:
                displayItems();
                processPurchase(menu.getChoiceFromProductChoiceMap(productChoices));
                break;
            case PURCHASE_MENU_FINISH_TRANSACTION:
                System.out.println("Your Change is " + currentMoney.calculateChange(currentMoney.getCurrentMoney()));
                break;
        }
        processTransaction("GIVE CHANGE: ", false, currentMoney.getCurrentMoney());
    }



    private void processPurchase(Item chosenItem)
    {
        if (chosenItem != null)
        {
//            if (currentMoney.checkIfSufficientFunds(chosenItem.getPrice()))
            if(currentMoney.getCurrentMoney()>=chosenItem.getPrice())
            {
//                if (chosenItem.getInventory() >= 1)
                if(chosenItem.getInventory()>0)
                {
                    processTransaction("DISPENSED: "+chosenItem.getName() + " " + chosenItem.getSlot() + " ", false, chosenItem.getPrice());
                    System.out.println("Dispensed " + chosenItem.getName() + ", for " + currency.format(chosenItem.getPrice()) + "!\nYou have " + currency.format(currentMoney.getCurrentMoney()) + " remaining on your balance\n");
                    System.out.println(chosenItem.dispenseItem());
                }
                //else chosenItem.dispenseItem();
                else System.out.println("That item is SOLD OUT");
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
        Logger.log(transaction + currency.format(transactionAmount) + " " + "REMAINING IN MACHINE: "+ currency.format(currentMoney.getCurrentMoney()));
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
