package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api.services.InfoServices;
import com.example.api.services.dataResponse.InfoResponse;
import com.example.api.services.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create extends AppCompatActivity {
    EditText addName, addUser, addRol, addPassword;
    Button btnAddUser, btnBack2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        addName = findViewById(R.id.addName);
        addUser = findViewById(R.id.addUser);
        addRol = findViewById(R.id.addRol);
        addPassword = findViewById(R.id.addPassword);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnBack2 = findViewById(R.id.btnBack2);

        Create context = this;

        btnAddUser.setOnClickListener(v -> {
            if (addName.getText().toString().isEmpty() || 
                    addUser.getText().toString().isEmpty()|| 
                    addPassword.getText().toString().isEmpty() || 
                    addRol.getText().toString().isEmpty()){
                Toast.makeText(context, "Por favor, ingrese todos los valores", Toast.LENGTH_SHORT).show();
            }else {
                createUser(new User(
                        addName.getText().toString(),
                        addUser.getText().toString(),
                        addPassword.getText().toString(),
                        addRol.getText().toString())
                );
            }
        });

        btnBack2.setOnClickListener(v -> {
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
    }

    private void createUser(User u) {
        Call<InfoResponse> respInfo = (new InfoServices().postInfoService(u));
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                Log.i("Info", "Conexión establecida");
                Log.i("Info", "Usuario creado");
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }
}
