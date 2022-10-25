package com.example.api;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserView extends AppCompatActivity {

    TextView noID, autoName, autoUser, autoRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        //Vinculación de elementos de interfaz gráfica
        noID = findViewById(R.id.noID);
        autoName = findViewById(R.id.autoName);
        autoUser = findViewById(R.id.autoUser);
        autoRol = findViewById(R.id.autoRol);

        noID.setText(getIntent().getStringExtra("paramsId"));
        autoName.setText(getIntent().getStringExtra("paramsName"));
        autoUser.setText(getIntent().getStringExtra("paramsUser"));
        autoRol.setText(getIntent().getStringExtra("paramsRol"));
    }
}
