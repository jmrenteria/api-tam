package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.api.services.InfoServices;
import com.example.api.services.dataResponse.InfoResponse;
import com.example.api.services.models.InfoApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listUsers;
    ArrayList<String> listaUsuarios;
    Intent intent;
    InfoApi user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listUsers = findViewById(R.id.listUsers);

        MainActivity context = this;

        Call<InfoResponse> respInfo = (new InfoServices().getInfoService());
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                Log.i("Info", "Conexión establecida");
                cargarUsuario(response.body());
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });

        listUsers.setOnItemClickListener((adapterView, view, i, l) -> {
            String[] cadenas = listUsers.getItemAtPosition(i).toString().split(" - ");
            /*int id = Integer.parseInt(cadenas[0]);
            String names = cadenas[1];
            String user = cadenas[2];
            String rol = cadenas[3];*/
            intent = new Intent(context, UserView.class);
            intent.putExtra("paramsId", cadenas[0]);
            intent.putExtra("paramsName", cadenas[1]);
            intent.putExtra("paramsUser", cadenas[2]);
            intent.putExtra("paramsRol", cadenas[3]);
            startActivity(intent);
        });
    }

    private void cargarUsuario(InfoResponse r) {
        listaUsuarios = new ArrayList<>();
        for (int i = 0; i < r.data.size(); i++) {
            user = new InfoApi(
                    r.data.get(i).getId(),
                    r.data.get(i).getNames(),
                    r.data.get(i).getUsername(),
                    r.data.get(i).getRol()
            );
            listaUsuarios.add(user.toString());
        }
        listUsers.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaUsuarios));
    }
}