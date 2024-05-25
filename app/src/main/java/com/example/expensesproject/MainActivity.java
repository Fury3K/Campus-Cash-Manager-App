package com.example.expensesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_ALLOWANCE = 101; // Unique integer for request code
    private static final int REQUEST_CODE_ADD_EXPENSE = 102; // Unique integer for request code
    private static final int REQUEST_CODE_REMOVE_EXPENSE = 103; // Unique integer for request code

    private double allowance;
    private static double totalExpenses = 0.0; // Changed to static
    private TextView allowanceTextView, remainingTextView;
    private Button addAllowanceButton, addExpenseButton, totalExpensesButton, removeExpensesButton;

    public static ArrayList<Expense> expenses = new ArrayList<>();
    public static double remainingAllowance; // Variable to store remaining allowance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allowanceTextView = findViewById(R.id.allowanceTextView);
        remainingTextView = findViewById(R.id.remainingTextView);
        addAllowanceButton = findViewById(R.id.addAllowanceButton);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        totalExpensesButton = findViewById(R.id.totalExpensesButton);
        removeExpensesButton = findViewById(R.id.removeExpensesButton);

        // Calculate and display total expenses
        calculateTotalExpenses();

        // Set onClickListeners for buttons
        addAllowanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddAllowanceActivity
                Intent intent = new Intent(MainActivity.this, AddAllowanceActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ALLOWANCE);
            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddExpenseActivity
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                intent.putExtra("weeklyAllowance", allowance);
                intent.putExtra("totalExpenses", totalExpenses);
                startActivityForResult(intent, REQUEST_CODE_ADD_EXPENSE);
            }
        });

        totalExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the TotalExpensesActivity
                Intent intent = new Intent(MainActivity.this, TotalExpensesActivity.class);
                startActivity(intent);
            }
        });

        removeExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RemoveExpenseActivity
                Intent intent = new Intent(MainActivity.this, RemoveExpenseActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REMOVE_EXPENSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ALLOWANCE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("allowance")) {
                allowance = data.getDoubleExtra("allowance", 0.0);
                allowanceTextView.setText("Weekly Allowance: $" + allowance);

                // Update remaining allowance
                remainingAllowance = allowance - totalExpenses;
                // Display remaining allowance
                displayRemainingAllowance();
            }
        } else if (requestCode == REQUEST_CODE_ADD_EXPENSE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("totalExpenses")) {
                totalExpenses = data.getDoubleExtra("totalExpenses", 0.0);
                // Update remaining allowance
                remainingAllowance = allowance - totalExpenses;
                // Display remaining allowance
                displayRemainingAllowance();
            }
        } else if (requestCode == REQUEST_CODE_REMOVE_EXPENSE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("totalExpenses")) {
                totalExpenses = data.getDoubleExtra("totalExpenses", 0.0);
                // Update remaining allowance
                remainingAllowance = allowance - totalExpenses;
                // Display remaining allowance
                displayRemainingAllowance();
            }
        }
    }

    private void calculateTotalExpenses() {
        // Calculate total expenses
        totalExpenses = 0.0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        // Calculate remaining allowance
        remainingAllowance = allowance - totalExpenses;

        // Display remaining allowance
        displayRemainingAllowance();
    }

    private void displayRemainingAllowance() {
        if (remainingAllowance >= 0) {
            remainingTextView.setText("Remaining Allowance: $" + remainingAllowance);
        } else {
            remainingTextView.setText("Please wait until next week for more allowance.");
        }
    }

    public static double getTotalExpenses() {
        return totalExpenses;
    }

    public static void setTotalExpenses(double totalExpenses) {
        MainActivity.totalExpenses = totalExpenses;
    }

}
