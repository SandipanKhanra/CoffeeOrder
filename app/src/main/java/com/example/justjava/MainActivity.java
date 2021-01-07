package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {

        //Figure out if user wants Whipped cream or not
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        //Figure out if user wants Chocolate or not
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //Extracting user name
        EditText editText = (EditText) findViewById(R.id.user_name);
        String userName = editText.getText().toString();

        //Calculating price of order
        int totalPrice = calculatePrice(hasWhippedCream, hasChocolate);

        //Creating order summary
        String priceMessage = createOrderSummary(userName, totalPrice, hasWhippedCream, hasChocolate);

        //Creating intent for email app
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + userName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    private void display(int i) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + i);
    }


    /**
     * This method is called when plus button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "Invalid! Can't proceed", Toast.LENGTH_SHORT).show();
            return;
        } else
            quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this, "Invalid Input!", Toast.LENGTH_SHORT).show();
            return;
        } else
            quantity = quantity - 1;
        display(quantity);
    }

    /**
     * @param name            user name
     * @param price           price of order
     * @param addWhippedCream whether customer wants or not
     * @param addWhippedCream whether customer wants or not
     *                        Creating order summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String orderSummary = getString(R.string.order_summary_name,name);
        orderSummary += "\nAdd: Whipped Cream? " + addWhippedCream;
        orderSummary += "\nAdd: Chocolate? " + addChocolate;
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nTotal: $" + price;
        orderSummary += getString(R.string.thank_you);

        return orderSummary;

    }

    /**
     * Calculating total price
     *
     * @param addChocolate    Figure out if user wants Chocolate or not
     * @param addWhippedCream Figure out if user wants Whipped cream or not
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = 5;
        if (addWhippedCream)
            price += 1;
        if (addChocolate)
            price += 2;
        return price * quantity;
    }


}
