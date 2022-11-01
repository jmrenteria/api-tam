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

    //Objetos a usar
    EditText addName, addUser, addRol, addPassword;
    Button btnAddUser, btnBack2;
    Intent intent;

    Create context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Asociación de elementos del layout
        addName = findViewById(R.id.addName);
        addUser = findViewById(R.id.addUser);
        addRol = findViewById(R.id.addRol);
        addPassword = findViewById(R.id.addPassword);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnBack2 = findViewById(R.id.btnBack2);

        context = this;

        //Botón de agregar usuario
        btnAddUser.setOnClickListener(v -> {
            //Valida si los campos están vacíos
            if (addName.getText().toString().isEmpty() ||
                    addUser.getText().toString().isEmpty()|| 
                    addPassword.getText().toString().isEmpty() || 
                    addRol.getText().toString().isEmpty()){
                Toast.makeText(context, "Por favor, ingrese todos los valores", Toast.LENGTH_SHORT).show();
            }else {
                //Si todos los campos son válidos, crea un nuevo usuario
                createUser(new User(
                        addName.getText().toString(),
                        addUser.getText().toString(),
                        addPassword.getText().toString(),
                        addRol.getText().toString())
                );
            }
        });

        //Botón de volver
        btnBack2.setOnClickListener(v -> {
            //Envía a la actividad MainActivity
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
    }

    //Método para crear un usuario que toma un usuario
    private void createUser(User u) {
        //Hace la petición POST
        Call<InfoResponse> respInfo = (new InfoServices().postInfoService(u));
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                //Si se pudo realizar la petición
                Log.i("Info", "Conexión establecida");
                Log.i("Info", "Usuario creado");
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                //No se pudo realizar la petición
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }
}
