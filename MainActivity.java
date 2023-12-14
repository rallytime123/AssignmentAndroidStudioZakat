package com.example.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    EditText weightEditText, typeEditText, valueEditText;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weightEditText = findViewById(R.id.weightEditText);
        typeEditText = findViewById(R.id.typeEditText);
        valueEditText = findViewById(R.id.valueEditText);
        resultTextView = findViewById(R.id.resultTextView);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });
    }

    private void calculateZakat() {
        try {
            double weight = Double.parseDouble(weightEditText.getText().toString());
            String type = typeEditText.getText().toString().toLowerCase();
            double value = Double.parseDouble(valueEditText.getText().toString());

            if ((type.equals("keep") && weight < 85) || (type.equals("wear") && weight < 200)) {
                resultTextView.setText("Total Value of Gold: 0\n" +
                        "Total Gold Value that is Zakat Payable: 0\n" +
                        "Total Zakat: 0 (Weight does not exceed threshold for zakat)");
                return;
            }

            double X = (type.equals("keep")) ? 85 : 200;

            double zakatPayable = Math.max(weight - X, 0) * value;
            double zakat = 0.025 * zakatPayable;
            double totalValue = weight * value;



            displayResults(totalValue, zakatPayable, zakat);
        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input. Please enter valid numbers.");
        }




    }

    private void displayResults(double totalValue, double zakatPayable, double zakat) {
        String result = "Total Value of Gold: " + totalValue
                + "\nTotal Gold Value that is Zakat Payable: " + zakatPayable
                + "\nTotal Zakat: " + zakat;

        resultTextView.setText(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_share) {
            shareResults();
            return true;
        } else if (itemId == R.id.action_about) {
            openAboutActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void shareResults() {
        // Get the URL of your GitHub repository
        String githubUrl = "https://github.com/rallytime123/AssignmentAndroidStudioZakat/";

        // Create a message to share
        String shareMessage = "Check out our My Zakat Gold Calculator app on GitHub: " + githubUrl;

        // Create an intent to share the message
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        // Start the activity to show the share options
        startActivity(Intent.createChooser(shareIntent, "Share Zakat Gold Calculator"));
    }
}
