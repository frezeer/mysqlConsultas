package ventas.sergio.morales.com.mysqlconsultas;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static ventas.sergio.morales.com.mysqlconsultas.R.id.btnRegresar;
import static ventas.sergio.morales.com.mysqlconsultas.R.id.btnSalir;

public class Listados extends AppCompatActivity {

    ListView listaResultado;
    Button   btnSalir , btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);
        listaResultado = (ListView)findViewById(R.id.lvLista);
        btnSalir =(Button) findViewById(R.id.btnSalir);
        btnBack =(Button) findViewById(R.id.btnRegresar);
        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("direccion");
        EnviarRecibirDatos(dato);

        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Haz Finalizado Esta Aplicacion", Toast.LENGTH_SHORT).show();
                finish();
                onDestroy();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent Ventana;
                Ventana = new Intent(Listados.this, MainActivity.class);
                startActivity(Ventana);
            }
        });
    }
    public void EnviarRecibirDatos(String URL){
        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");

                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    public void CargarListView(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        //for(int i=0;i<ja.length();i+=4){
            for(int i=0;i<ja.length();i+=3){
            try {

                //lista.add(ja.getString(i)+" "+ja.getString(i+1)+" "+ja.getString(i+2)+" "+ja.getString(i+3));
                lista.add(ja.getString(i)+" "+ja.getString(i+1)+" "+ja.getString(i+2));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listaResultado.setAdapter(adaptador);

    }
}
