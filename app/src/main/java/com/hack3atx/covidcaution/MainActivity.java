package com.hack3atx.covidcaution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//LOGIN-PAGE-SIGN-UP-PAGE
public class MainActivity extends AppCompatActivity {
    public void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
    private String name, email, password;

    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;

    Button submitButton;

    private FirebaseAuth mAuth;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        nameInput = (EditText) findViewById(R.id.nameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        submitButton = (Button) findViewById(R.id.submitButton);

        sp = getSharedPreferences("UserFullName", Context.MODE_PRIVATE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", name);
                editor.commit();
                Toast.makeText(MainActivity.this, "Information Saved.", Toast.LENGTH_LONG).show();

                if (currentUser != null) {
                    updateUI(currentUser);
                } else {
                    if (email.equals("") || password.equals("")) {
                        Context context = getApplicationContext();
                        CharSequence text = "Please enter a valid email and password.";
                        int duration = Toast.LENGTH_LONG;
                        Toast.makeText(context, text, duration).show();
                    } else {
                        mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        mAuth.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            FirebaseUser user = mAuth.getCurrentUser();
                                                            updateUI(user);
                                                        } else {
                                                            Context context = getApplicationContext();
                                                            CharSequence text = "Please enter a valid email and password.";
                                                            int duration = Toast.LENGTH_LONG;
                                                            Toast.makeText(context, text, duration).show();
                                                        }
                                                    }
                                                    });
                                    }

                                }

                            });

                    }

                }

            }
        });
    }
}
