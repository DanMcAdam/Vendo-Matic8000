package com.techelevator;

import com.techelevator.view.Menu;
import java.io.FileNotFoundException;


public class VendingMachineCLI
{
	private Menu menu;
	static ProductChoices productChoices;

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
														MAIN_MENU_OPTION_PURCHASE,
														MAIN_MENU_OPTION_EXIT };


	public VendingMachineCLI(Menu menu) throws FileNotFoundException
	{
		this.menu = menu;
		productChoices = new ProductChoices();
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
				// do purchase
			}

			/*EXIT OPTION*/
			else if (choice.equals(MAIN_MENU_OPTION_EXIT))
			{
				System.out.println("Goodbye!");
				System.exit(0);
			}
		}
	}

	public void displayItems()
	{
		for (String f : productChoices.getProductChoices().keySet())
		{
			System.out.print(" " + productChoices.getProductChoices().get(f).getSlot() + " |");
			System.out.print(" " + productChoices.getProductChoices().get(f).getName() + " |");
			System.out.print(" " + productChoices.getProductChoices().get(f).getPrice() + " | ");

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

	public static void main(String[] args) throws FileNotFoundException
	{
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
