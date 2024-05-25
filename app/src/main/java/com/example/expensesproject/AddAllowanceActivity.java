package com.example.expensesproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddAllowanceActivity extends AppCompatActivity {

    private EditText allowanceEditText;
    private Button confirmAllowanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_allowance);

        allowanceEditText = findViewById(R.id.allowanceEditText);
        confirmAllowanceButton = findViewById(R.id.confirmAllowanceButton);

        confirmAllowanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allowanceString = allowanceEditText.getText().toString().trim();
                if (!allowanceString.isEmpty()) {
                    double allowance = Double.parseDouble(allowanceString);
                    // Pass the allowance back to MainActivity
                    Intent intent = new Intent();
                    intent.putExtra("allowance", allowance);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    allowanceEditText.setError("Please enter the weekly allowance");
                }
            }
        });
    }
}
