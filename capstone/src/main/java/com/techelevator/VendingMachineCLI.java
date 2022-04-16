package com.techelevator;

import com.techelevator.items.Item;
import com.techelevator.view.Display;
import com.techelevator.view.Menu;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;


public class VendingMachineCLI
{
    private final Menu menu;
    static ProductChoices productChoices;
    static CurrentMoney currentMoney;
    public static Display display;
    NumberFormat currency = NumberFormat.getCurrencyInstance();  //format currency
    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("d-MMM-uuuu HH-mma");
    String timeStamp = LocalDateTime.now().format(dateTimeFormat);



    //region Menu String Arrays
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALESREPORT   = "";
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

    //endregion

    public static void main(String[] args) throws FileNotFoundException
    {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }

    public VendingMachineCLI(Menu menu) throws FileNotFoundException
    {
        this.menu = menu;
        productChoices = new ProductChoices(new File(System.getProperty("user.dir")+"/capstone/vendingmachine.csv"));
        currentMoney = new CurrentMoney();
        display = new Display();
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
                    display.sendToDisplay("Goodbye!");
                    System.exit(0);
                    break;

                /*HIDDEN SALES REPORT MENU*/
                case MAIN_MENU_OPTION_SALESREPORT:
                    //implement sales report
                    try(Scanner fileScanner = new Scanner(Logger.generateSalesReport(productChoices)))
                    {
                        display.sendToDisplay("\n####################################");
                        display.sendToDisplay("SALES REPORT FOR "+ timeStamp);
                        display.sendToDisplay("####################################\n");
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            display.sendToDisplay(line);
                        }
                    }
                    catch(FileNotFoundException e)
                        {
                            System.err.println(e.getMessage());
                        }
            }
        }
    }

    public void displayItems()
    {
        for (String f : productChoices.getProductChoices().keySet())
        {
            Item currentIterationProduct = productChoices.getProductChoices().get(f);
            display.sendToDisplayOnSameLine(" " + currentIterationProduct.getSlot() + " |");
            display.sendToDisplayOnSameLine(" " + currentIterationProduct.getName() + " |");
            display.sendToDisplayOnSameLine(" " + currency.format(currentIterationProduct.getPrice()) + " | ");

            if (currentIterationProduct.getInventory() != 0)
            {
                display.sendToDisplay(String.valueOf(currentIterationProduct.getInventory()));
            }
            else
            {
                display.sendToDisplay("SOLD OUT");
            }
        }
    }

    public void purchaseItems()
    {
        String purchaseChoice = (String) menu.getChoiceFromPurchaseOptions(PURCHASE_MENU_OPTIONS,
                "\nCurrent Money Provided: " + currency.format(currentMoney.getCurrentMoney()));

        switch (purchaseChoice) {
            case PURCHASE_MENU_FEED_MONEY:
                String feedChoice = (String) menu.getChoiceFromPurchaseOptions(PURCHASE_MENU_FEED_MONEY_OPTIONS, "\nCurrent Money Provided: " + currency.format(currentMoney.getCurrentMoney()));

                feedMoney(feedChoice);
                purchaseItems();
                break;

            case PURCHASE_MENU_SELECT_PRODUCT:
                displayItems();
                processPurchase(menu.getChoiceFromProductChoiceMap(productChoices));
                break;

            case PURCHASE_MENU_FINISH_TRANSACTION:
                Map<String, Integer> change = CurrentMoney.calculateChange(currentMoney.getCurrentMoney());
                display.sendToDisplay("Your Change is " + change.get("Quarters") + " quarters, " + change.get("Dimes")
                        + " dimes, and " + change.get("Nickels") + " nickels.");
                processTransaction("GIVE CHANGE: ", false, currentMoney.getCurrentMoney());
                break;
        }
    }

    private void feedMoney(String feedChoice)
    {
        // Takes incoming string, chosen by user from menu from array, to add money into machine in whole bill amounts
        switch (feedChoice) {
            case FEED_MONEY_ONE_DOLLAR:
                processTransaction("FEED MONEY: ", true, BigDecimal.valueOf(1) );
                break;
            case FEED_MONEY_TWO_DOLLAR:
                processTransaction("FEED MONEY: ", true, BigDecimal.valueOf(2));
                break;
            case FEED_MONEY_FIVE_DOLLAR:
                processTransaction("FEED MONEY: ", true, BigDecimal.valueOf(5));
                break;
            case FEED_MONEY_TEN_DOLLAR:
                processTransaction("FEED MONEY: ", true, BigDecimal.valueOf(10));
                break;
        }
    }


    private void processPurchase(Item chosenItem)
    {
        if (chosenItem != null)
        {
            if(currentMoney.isBalanceIsEnoughForPurchase(chosenItem.getPrice()))
            {
                if(chosenItem.isInStock())
                {
                    processTransaction("DISPENSED: "+chosenItem.getName() + " " + chosenItem.getSlot() + " ", false, chosenItem.getPrice());
                    display.sendToDisplay("Dispensed " + chosenItem.getName() + ", for " + currency.format(chosenItem.getPrice()) + "!\nYou have " + currency.format(currentMoney.getCurrentMoney()) + " remaining on your balance\n");
                    display.sendToDisplay(chosenItem.dispenseItem());
                }
                else display.sendToDisplay("That item is SOLD OUT");
            }
            else display.sendToDisplay("Insufficient funds");
        }
        purchaseItems();
    }

    private void processTransaction (String transaction, boolean isDeposit, BigDecimal transactionAmount)
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

}
