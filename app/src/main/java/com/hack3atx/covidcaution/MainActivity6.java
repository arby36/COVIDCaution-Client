package com.hack3atx.covidcaution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//Create Page
public class MainActivity6 extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    Button button;
    Button button2;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        editText1 = findViewById(R.id.TITLE );
        editText2 = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button10);
        button2 = findViewById(R.id.button9);
        imageView = findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                String text = "Location name = " + text1 + " Address = " + text2;
                if(!text.equals("")){
                    new ImageDownloaderTask(imageView).execute("https://api.qrserver.com/v1/create-qr-code/?size=1000x1000&data=" + text);
                } else {
                    Toast.makeText(MainActivity6.this, "Enter something!", Toast.LENGTH_LONG).show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity6.this, MainActivity8.class);
                startActivity(intent);
            }
        });
    }
}
//