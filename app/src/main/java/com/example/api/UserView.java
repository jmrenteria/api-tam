package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api.services.InfoServices;
import com.example.api.services.dataResponse.InfoResponse;
import com.example.api.services.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserView extends AppCompatActivity {

    TextView noID, autoName, autoUser, autoRol;
    Button btnUpdate, btnDelete, btnBack;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        //Vinculación de elementos de interfaz gráfica
        noID = findViewById(R.id.noID);
        autoName = findViewById(R.id.autoName);
        autoUser = findViewById(R.id.autoUser);
        autoRol = findViewById(R.id.autoRol);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        UserView context = this;

        String id = getIntent().getStringExtra("paramsId");
        noID.setText(id);
        autoName.setText(getIntent().getStringExtra("paramsName"));
        autoUser.setText(getIntent().getStringExtra("paramsUser"));
        autoRol.setText(getIntent().getStringExtra("paramsRol"));

        btnBack.setOnClickListener(v -> {
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            deleteUser(id);
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        btnUpdate.setOnClickListener(v -> {
            updateUser(id, new User(
                    autoName.getText().toString(),
                    autoUser.getText().toString(),
                    "0", //addPassword.getText().toString(),
                    autoRol.getText().toString())
            );
            intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });
    }

    private void deleteUser(String i) {
        Call<InfoResponse> respInfo = (new InfoServices().deleteInfoService(i));
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                Log.i("Info", "Conexión establecida");
                Log.i("Info", "Usuario eliminado");
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }

    private void updateUser(String i, User u) {
        Call<InfoResponse> respInfo = (new InfoServices().updateInfoService(i, u));
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                Log.i("Info", "Conexión establecida");
                Log.i("Info", "Usuario actualizado");
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }
}
