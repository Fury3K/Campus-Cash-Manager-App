package com.example.expensesproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RemoveExpenseActivity extends AppCompatActivity {

    private ListView expensesListView;
    private ArrayAdapter<String> adapter;
    private TextView remainingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_expense);

        expensesListView = findViewById(R.id.expensesListView);
        remainingTextView = findViewById(R.id.remainingTextView);

        // Display the list of expenses
        displayExpenses();

        // Setup the remove expense button
        Button removeExpenseButton = findViewById(R.id.removeExpenseButton);
        removeExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the selected expense
                int selectedPosition = expensesListView.getCheckedItemPosition();
                if (selectedPosition != ListView.INVALID_POSITION) {
                    removeExpense(selectedPosition);
                }
            }
        });

        // Setup the back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and go back to the previous one
            }
        });

        // Display the remaining allowance initially
        remainingTextView.setText("Remaining Allowance: $" + MainActivity.remainingAllowance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list of expenses when returning to this activity
        displayExpenses();
    }

    private void displayExpenses() {
        ArrayList<String> expenseNames = new ArrayList<>();
        for (Expense expense : MainActivity.expenses) {
            expenseNames.add(expense.getName() + ": $" + expense.getAmount());
        }

        Log.d("RemoveExpenseActivity", "Expenses to display: " + expenseNames.toString());

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, expenseNames);
        expensesListView.setAdapter(adapter);
        expensesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Ensure the ListView has the updated data
        adapter.notifyDataSetChanged();
    }

    private void removeExpense(int position) {
        // Get the expense to remove
        Expense removedExpense = MainActivity.expenses.get(position);

        // Update remaining allowance by adding back the amount of the removed expense
        MainActivity.remainingAllowance += removedExpense.getAmount();

        // Remove the expense from the list
        MainActivity.expenses.remove(position);

        // Recalculate total expenses
        double totalExpenses = 0.0;
        for (Expense expense : MainActivity.expenses) {
            totalExpenses += expense.getAmount();
        }

        // Update MainActivity's total expenses
        MainActivity.setTotalExpenses(totalExpenses);

        // Pass back the updated total expenses to MainActivity
        Intent intent = new Intent();
        intent.putExtra("totalExpenses", totalExpenses);
        setResult(RESULT_OK, intent);

        // Update the displayed list
        adapter.remove(adapter.getItem(position));
        adapter.notifyDataSetChanged();

        // Display updated remaining allowance
        if (remainingTextView != null) {
            remainingTextView.setText("Remaining Allowance: $" + MainActivity.remainingAllowance);
        }
    }
}
