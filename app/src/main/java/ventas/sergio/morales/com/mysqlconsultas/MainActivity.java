package ventas.sergio.morales.com.mysqlconsultas;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnConsultar, btnGuardar, btnLimpiar ,Siguiente ,btnSalir ,btnDelete;
    EditText etId, etNombres , etTelefono ;
    int color;

    //View contenedor = v.getRootView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        //contenedor.setBackgroundColor(color);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        etId = (EditText)findViewById(R.id.etId);
        etNombres = (EditText)findViewById(R.id.etNombres);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnSalir =(Button) findViewById(R.id.btnSalir);
        Siguiente = (Button) findViewById(R.id.Siguiente);
        etNombres.setHint("Ingresa el nombre");
        etNombres.requestFocus();
        btnDelete = (Button)findViewById(R.id.btnBorrar);
        btnDelete.setEnabled(false);
        btnConsultar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                http://localhost/webservices/consulta.php?id=1
                * */
                new ConsultarDatos().execute("http://192.168.15.168/webservices/consulta.php?id="+etId.getText().toString());
                btnDelete.setEnabled(true);
                etNombres.setText(null);
                etNombres.setHint("Ingresa el nombre");
                etTelefono.getText().clear();
                etTelefono.setHint("Ingresa el Telefono");
                //etId.getText().clear();
                //  //android:background="@android:color/holo_orange_light"
                //etId.setHint("Ingresa el Consecutivo");
               // String precio= this.precio.getText().toString();
                // if (TextUtils.isEmpty(precio_coste) {
                  //  Toast.makeText(this, "Ha dejado campos vacios",
                    //        Toast.LENGTH_LONG).show();
                //}
                etId.requestFocus();

            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(getApplicationContext(), "Haz Finalizado Esta Aplicacion", Toast.LENGTH_SHORT).show();
            }
        });


        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Ventana;
                Ventana = new Intent(MainActivity.this, Listados.class);
                String consulta = "http://192.168.15.168/webservices/listado.php";
                //String consulta = "http://192.168.15.168/webservices/listado.php";
                Ventana.putExtra("direccion", consulta);
                startActivity(Ventana);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CargarDatos().execute("http://192.168.15.168/webservices/delete.php?id="+etId.getText().toString());
                Toast.makeText(getApplicationContext(), "Se ha Borrado correctamente", Toast.LENGTH_LONG).show();
            }
        });

        btnLimpiar.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNombres.setText(null);
                etNombres.setHint("Ingresa el nombre");
                etTelefono.getText().clear();
                etTelefono.setHint("Ingresa el Telefono");
                etId.getText().clear();
                etId.setHint("Ingresa el Consecutivo");
                etNombres.requestFocus();
                btnGuardar.setEnabled(true);
                btnConsultar.setEnabled(false);
                btnDelete.setEnabled(false);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /*
                    http://192.168.15.168/webservices/registro.php?nombre=patomorales&tel=83440010
                    * */
                new CargarDatos().execute("http://192.168.15.168/webservices/registro.php?nombre="+etNombres.getText().toString() + "&tel=" + etTelefono.getText().toString());
                etNombres.setText(null);
                etNombres.setHint("Ingresa el nombre");
                etTelefono.clearFocus();
                etTelefono.getText().clear();
                etTelefono.setHint("Ingresa el Telefono");
                etId.getText().clear();
                etId.setHint("Ingresa el Consecutivo");
                etNombres.requestFocus();
                btnGuardar.setEnabled(false);
            }
        });
    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();
        }
    }

    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                etNombres.setText(ja.getString(1));
                etTelefono.setText(ja.getString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}