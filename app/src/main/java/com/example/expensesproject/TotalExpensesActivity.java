package com.example.expensesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TotalExpensesActivity extends AppCompatActivity {

    private ListView expensesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_expenses);

        expensesListView = findViewById(R.id.expensesListView);

        // Display the list of expenses
        displayExpenses();

        // Setup the back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity and return to the previous activity (main menu)
                finish();
            }
        });
    }

    private void displayExpenses() {
        ArrayList<String> expenseNames = new ArrayList<>();
        for (Expense expense : MainActivity.expenses) {
            expenseNames.add(expense.getName() + ": $" + expense.getAmount());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenseNames);
        expensesListView.setAdapter(adapter);
    }
}
