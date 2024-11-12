package com.example.pantrypalandroidprototype.controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


/**
 * The controller class for our application.
 */
public class ControllerActivity extends AppCompatActivity implements IAddItemsView.Listener, ICashPaymentView.Listener  {

    IMainView mainView; // keep track of the UI object
    Sale curSale; // keep track of current sale

    /**
     * This method is called by the Android framework whenever the activity is created or recreated.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create main screen template
        this.mainView = new MainView(this, this);
        setContentView(this.mainView.getRootView()); // must be called from controller

        this.mainView.displayFragment(new AddItemsFragment(this)); // display first screen

        this.curSale = new Sale(); // start out with an empty sale
    }

    /* IAddItemsView.Listener implementation start */
    /**
     * Called when an item is to be added onto the sale.
     *
     * @param name name of product to add
     * @param qty number of units to add
     */
    @Override
    public void onAddItem(final String name, final int qty, @NonNull final IAddItemsView view) {
        this.curSale.addLineItem(name, qty);
        view.updateSaleDisplay(this.curSale);
    }

    /**
     * Called when the user is done adding items.
     */
    @Override
    public void onItemsDone() {
        final double total = this.curSale.getTotal();
        this.mainView.displayFragment(new CashPaymentFragment(total, this));
    }
    /* IAddItemsView.Listener implementation end */

    /* ICashPaymentView.Listener implementation start */

    /**
     * Called when the user has provided an amount of cash to pay for the sale.
     *
     * @param amount the amount of cash provided
     * @param view the view the event originated from
     */
    @Override
    public void onCashTendered(final double amount, @NonNull final ICashPaymentView view) {
        final double change = this.curSale.makeCashPayment(amount);
        view.updateDisplayOnPaid(change);

    }
    /* ICashPaymentView.Listener implementation end */
}