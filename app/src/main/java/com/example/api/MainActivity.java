package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.api.services.InfoServices;
import com.example.api.services.dataResponse.InfoResponse;
import com.example.api.services.models.InfoApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listUsers;
    EditText writeID;
    Button btnCreate, btnSearch;

    ArrayList<String> listaUsuarios;
    Intent intent;
    InfoApi user;

    MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listUsers = findViewById(R.id.listUsers);
        btnCreate = findViewById(R.id.btnCreate);
        btnSearch = findViewById(R.id.btnSearch);
        writeID = findViewById(R.id.writeID);

        context = this;

        cargarUsuario(-1);

        btnSearch.setOnClickListener(v -> {
            if (writeID.getText().toString().isEmpty()) {
                cargarUsuario(-1);
                return;
            }
            cargarUsuario(Integer.parseInt(writeID.getText().toString()));
        });

        btnCreate.setOnClickListener(v -> {
            intent = new Intent(context, Create.class);
            startActivity(intent);
        });

        listUsers.setOnItemClickListener((adapterView, view, i, l) -> {
            String[] cadenas = listUsers.getItemAtPosition(i).toString().split(" - ");
            intent = new Intent(context, UserView.class);
            intent.putExtra("paramsId", cadenas[0]);
            intent.putExtra("paramsName", cadenas[1]);
            intent.putExtra("paramsUser", cadenas[2]);
            intent.putExtra("paramsRol", cadenas[3]);
            startActivity(intent);
        });
    }

    private void cargarUsuario(int id) {
        Call<InfoResponse> respInfo = (new InfoServices().getInfoService());
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                Log.i("Info", "Conexión establecida");
                listaUsuarios = new ArrayList<>();
                InfoResponse r = response.body();
                for (int i = 0; i < r.data.size(); i++) {
                    if (id == -1) {
                        user = new InfoApi(
                                r.data.get(i).getId(),
                                r.data.get(i).getNames(),
                                r.data.get(i).getUsername(),
                                r.data.get(i).getRol()
                        );
                        listaUsuarios.add(user.toString());
                    } else if (r.data.get(i).getId() == id) {
                        user = new InfoApi(
                                r.data.get(i).getId(),
                                r.data.get(i).getNames(),
                                r.data.get(i).getUsername(),
                                r.data.get(i).getRol()
                        );
                        listaUsuarios.add(user.toString());
                    }
                }
                if (listaUsuarios.isEmpty()){
                    Toast.makeText(context, "No se encuentra el usuario #"+id, Toast.LENGTH_SHORT).show();
                }
                listUsers.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, listaUsuarios));
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }
}