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

    //Objetos a usar
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

        //Asociación de elementos del layout
        listUsers = findViewById(R.id.listUsers);
        btnCreate = findViewById(R.id.btnCreate);
        btnSearch = findViewById(R.id.btnSearch);
        writeID = findViewById(R.id.writeID);

        context = this;

        //Se cargan los usuarios: Al proporcionar -1, se cargan todos, no se busca un usuario específico
        cargarUsuario(-1);

        //Botón de buscar usuario
        btnSearch.setOnClickListener(v -> {
            //Valida si el campo de búsqueda está vacío y, si lo está, carga todos los usuarios
            if (writeID.getText().toString().isEmpty()) {
                cargarUsuario(-1);
                return;
            }
            //De lo contrario, busca el usuario con el ID proporcionado
            cargarUsuario(Integer.parseInt(writeID.getText().toString()));
        });

        //Botón de crear usuario
        btnCreate.setOnClickListener(v -> {
            //Envía a la actividad Create
            intent = new Intent(context, Create.class);
            startActivity(intent);
        });

        //Valida el item seleccionado de la lista
        listUsers.setOnItemClickListener((adapterView, view, i, l) -> {
            //Obtiene los datos de la lista y los separa para enviarlos a la actividad UserView
            String[] cadenas = listUsers.getItemAtPosition(i).toString().split(" - ");
            intent = new Intent(context, UserView.class);
            intent.putExtra("paramsId", cadenas[0]);
            intent.putExtra("paramsName", cadenas[1]);
            intent.putExtra("paramsUser", cadenas[2]);
            intent.putExtra("paramsRol", cadenas[3]);
            startActivity(intent);
        });
    }

    //Método para cargar un usuario que toma un id
    private void cargarUsuario(int id) {
        //Hace la petición GET
        Call<InfoResponse> respInfo = (new InfoServices().getInfoService());
        respInfo.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                //Si se pudo realizar la petición
                Log.i("Info", "Conexión establecida");
                listaUsuarios = new ArrayList<>();
                InfoResponse r = response.body();
                //Itera los elementos del array data que se obtiene de la respuesta de la petición
                for (int i = 0; i < r.data.size(); i++) {
                    if (id == -1) {
                        //Si el id es -1, va agregando cada usuario al ArrayList listaUsuarios
                        user = new InfoApi(
                                r.data.get(i).getId(),
                                r.data.get(i).getNames(),
                                r.data.get(i).getUsername(),
                                r.data.get(i).getRol()
                        );
                        listaUsuarios.add(user.toString());
                    } else if (r.data.get(i).getId() == id) {
                        //Si el id es cualquier otro, solo agrega el usuario que corresponde al ArrayList listaUsuarios
                        user = new InfoApi(
                                r.data.get(i).getId(),
                                r.data.get(i).getNames(),
                                r.data.get(i).getUsername(),
                                r.data.get(i).getRol()
                        );
                        listaUsuarios.add(user.toString());
                    }
                }
                //Si la lista está vacía, entonces no encontró al usuario
                if (listaUsuarios.isEmpty()){
                    Toast.makeText(context, "No se encuentra el usuario #"+id, Toast.LENGTH_SHORT).show();
                }
                //Añade listaUsuarios al ListView listUsers
                listUsers.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1, listaUsuarios));
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                //Si no se pudo realizar la petición
                Log.i("Info", "Conexión denegada");
                Log.i("Info", t.getCause().getMessage());
            }
        });
    }
}