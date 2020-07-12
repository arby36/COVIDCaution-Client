package com.hack3atx.covidcaution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

//HOME-PAGE-CODE
public class MainActivity2 extends AppCompatActivity {

    Button qrcodeButton;
    Button seeAlert;
    Button menu;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        qrcodeButton = (Button) findViewById(R.id.button);
        seeAlert =(Button) findViewById((R.id.button2));
        menu = (Button) findViewById(R.id.button3);
        signOut = (Button) findViewById(R.id.signOutButton);

        qrcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });


        seeAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        TextView nameFile;
        nameFile = findViewById(R.id.textView2);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserFullName", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        nameFile.setText(name);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }

}