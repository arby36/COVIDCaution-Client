package com.hack3atx.covidcaution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String name, email, password;

    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;

    Button submitButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = (EditText) findViewById(R.id.nameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                saveData();

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, nameView.getName().toString());
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }
}
