package com.brytech.apirestvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.brytech.apirestvolley.model.Holiday;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;

    EditText dia, mes, anio;
    Button btn_get, btn_post;

    ArrayAdapter adapter;
    ArrayList<String> datos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dia = findViewById(R.id.Txt_Dia);
        dia.getText().toString();
        mes = findViewById(R.id.Txt_Mes);
        mes.getText().toString();
        anio = findViewById(R.id.Txt_Anio);
        anio.getText().toString();

        btn_get = findViewById(R.id.Btn_Get);
        btn_post = findViewById(R.id.Btn_Post);

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

                    getHoliday();
                }
                if (v.getId() == R.id.Btn_Post) {

                    postHoliday();
                }
            }
        };
        btn_get.setOnClickListener(B);
        btn_post.setOnClickListener(B);
    }

    private void getHoliday() {

        String url = "https://holidays.abstractapi.com/v1/?api_key=b2f3456201be4a81a43f0306fdb33d76&country=EC&year=2022&month=12&day=25";

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

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject json = null;
            Holiday holiday = new Holiday();

            try {
                json = jsonArray.getJSONObject(i);

                holiday.setName(json.getString("name"));
                holiday.setCountry(json.getString("country"));
                holiday.setLocation(json.getString("location"));
                holiday.setDate(json.getString("date"));
                holiday.setDate_year(json.getString("date_year"));
                holiday.setDate_month(json.getString("date_month"));
                holiday.setDate_day(json.getString("date_day"));
                holiday.setWeek_day(json.getString("week_day"));

                //AGREGAR LOS DATOS AL LISTVIEW
                datos.add("Nombre Feriado: " + holiday.getName());
                datos.add("Ubicacion: " + holiday.getLocation());
                datos.add("Codigo Pais: " + holiday.getCountry());
                datos.add("Fecha Feriado: " + holiday.getDate());
                datos.add("Año Feriado: " + holiday.getDate_year());
                datos.add("Mes Feriado: " + holiday.getDate_month());
                datos.add("Dia Feriado: " + holiday.getDate_day());
                datos.add("Dia de la Semana Feriado: " + holiday.getWeek_day());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void postHoliday() {


    }
}