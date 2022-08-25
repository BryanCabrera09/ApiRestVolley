package com.brytech.apirestvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brytech.apirestvolley.model.Comment;
import com.brytech.apirestvolley.model.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;

    EditText id, nombre, precio;
    Button btn_get, btn_post;

    private RequestQueue rq;

    ArrayAdapter adapter;
    ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.Txt_Id);
        nombre = findViewById(R.id.Txt_Nombre);
        precio = findViewById(R.id.Txt_Precio);

        btn_get = findViewById(R.id.Btn_Get);
        btn_post = findViewById(R.id.Btn_Post);

        rq = Volley.newRequestQueue(this);

        Inciar_Control();

        list = findViewById(R.id.ListaHoliday);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, datos);
        list.setAdapter(adapter);

    }

    public void Inciar_Control() {

        View.OnClickListener B = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.Btn_Get) {

                    getComment();
                }
                if (v.getId() == R.id.Btn_Post) {

                    if (!nombre.getText().toString().isEmpty() && !precio.getText().toString().isEmpty()) {
                        postInsert();
                    } else {
                        Toast.makeText(MainActivity.this, "Los Campos Deben Estar Llenos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        btn_get.setOnClickListener(B);
        btn_post.setOnClickListener(B);
    }

    private void getComment() {

        String url = "https://jsonplaceholder.typicode.com/comments";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                PasarJsonComment(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error Al Obtener Get", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void PasarJsonComment(JSONArray jsonArray) {

        datos.clear();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject json = null;
            Comment comment = new Comment();

            try {
                json = jsonArray.getJSONObject(i);

                comment.setId(json.getString("id"));
                comment.setName(json.getString("name"));
                comment.setEmail(json.getString("email"));

                //AGREGAR LOS DATOS AL LISTVIEW
                datos.add(comment.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void postInsert() {

        String url = "https://scratchya.com.ar/videosandroidjava/volley/insertar.php";
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("descripcion", nombre.getText().toString());
            parametros.put("precio", precio.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.POST,
                url,
                parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("respuesta").toString().equals("ok")) {
                                Toast.makeText(MainActivity.this, "Datos Registrados Correctamente", Toast.LENGTH_SHORT).show();
                                nombre.setText("");
                                precio.setText("");
                                getProdutos();
                            } else
                                Toast.makeText(MainActivity.this, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimiento);
    }

    private void getProdutos() {

        String url = "https://scratchya.com.ar/videosandroidjava/volley/listar.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                PasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error Al Obtener Get", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void PasarJson(JSONArray jsonArray) {

        datos.clear();

        for (int i = 12; i < jsonArray.length(); i++) {

            JSONObject json = null;
            Productos productos = new Productos();

            try {
                json = jsonArray.getJSONObject(i);

                productos.setCodigo(json.getString("codigo"));
                productos.setNombre(json.getString("descripcion"));
                productos.setPrecio(json.getString("precio"));

                //AGREGAR LOS DATOS AL LISTVIEW
                datos.add(productos.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }
}
