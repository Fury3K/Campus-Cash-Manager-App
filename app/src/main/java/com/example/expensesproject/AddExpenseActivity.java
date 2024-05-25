package com.example.expensesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText, expenseAmountEditText;
    private Button addExpenseButton;

    private double weeklyAllowance;
    private double totalExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseNameEditText = findViewById(R.id.expenseNameEditText);
        expenseAmountEditText = findViewById(R.id.expenseAmountEditText);
        addExpenseButton = findViewById(R.id.addExpenseButton);

        // Get weekly allowance and total expenses from MainActivity
        weeklyAllowance = getIntent().getDoubleExtra("weeklyAllowance", 0.0);
        totalExpenses = getIntent().getDoubleExtra("totalExpenses", 0.0);

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expenseName = expenseNameEditText.getText().toString().trim();
                String expenseAmountString = expenseAmountEditText.getText().toString().trim();
                if (!expenseName.isEmpty() && !expenseAmountString.isEmpty()) {
                    double expenseAmount = Double.parseDouble(expenseAmountString);
                    double remainingAllowance = weeklyAllowance - totalExpenses;
                    if (remainingAllowance > 0) {
                        if (expenseAmount <= remainingAllowance) {
                            // Handle adding expense here
                            addExpense(expenseName, expenseAmount);
                            // Pass back the updated total expenses to MainActivity
                            Intent intent = new Intent();
                            intent.putExtra("totalExpenses", getTotalExpenses());
                            setResult(RESULT_OK, intent);
                            finish(); // Finish the activity here
                        } else {
                            // Display error message
                            Toast.makeText(AddExpenseActivity.this, "Expense amount exceeds remaining allowance!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Display error message if remaining allowance is zero
                        Toast.makeText(AddExpenseActivity.this, "Your remaining allowance is zero. Please add allowance first.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (expenseName.isEmpty()) {
                        expenseNameEditText.setError("Please enter the expense name");
                    }
                    if (expenseAmountString.isEmpty()) {
                        expenseAmountEditText.setError("Please enter the expense amount");
                    }
                }
            }
        });
    }

    private void addExpense(String name, double amount) {
        MainActivity.expenses.add(new Expense(name, amount));
        double updatedTotalExpenses = getTotalExpenses();
        Intent intent = new Intent();
        intent.putExtra("totalExpenses", updatedTotalExpenses);
        setResult(RESULT_OK, intent);
        totalExpenses = updatedTotalExpenses; // Update totalExpenses
    }

    private double getTotalExpenses() {
        double total = 0.0;
        for (Expense expense : MainActivity.expenses) {
            total += expense.getAmount();
        }
        return total;
    }
}
