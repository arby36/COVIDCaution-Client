package com.hack3atx.covidcaution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//HOME-PAGE-CODE
public class MainActivity2 extends AppCompatActivity {

    Button qrcodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        qrcodeButton = (Button) findViewById(R.id.button);
        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        TextView nameFile;
        nameFile = findViewById(R.id.textView2);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserFullName", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        nameFile.setText(name);
    }
}