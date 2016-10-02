package com.example.lakshay.myappportfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

Toolbar toolbar;


    public void onbclick(View v)
    {
        Button b=(Button)v;
        Toast.makeText(this, "Launching " + b.getText() +" ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("My App Portfolio");
        toolbar.setTitleTextColor(-1);


    }
}
