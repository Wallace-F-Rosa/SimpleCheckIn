package ferancini.app.simplecheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager lm;
    private Criteria criteria;
    public int TEMPO_REQUISICAO_LATLONG = 5000;
    public int DISTANCIA_MIN_METROS = 0;

    private TextView txtLat;
    private TextView txtLon;
    private Spinner spnCat;
    private AutoCompleteTextView edtLocal;

    private String latitude = "";
    private String longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CheckIn> adapterCheckIn = new ArrayAdapter<CheckIn>(this,
                android.R.layout.simple_dropdown_item_1line,
                CheckIn.getCheckIns()
        );
        edtLocal = (AutoCompleteTextView)findViewById(R.id.edtLocal);
        edtLocal.setAdapter(adapterCheckIn);
        adapterCheckIn.notifyDataSetChanged();
        ArrayAdapter<Categoria> adapterCat = new ArrayAdapter<Categoria>(this,
                android.R.layout.simple_dropdown_item_1line,
                Categoria.getCategorias());
        spnCat = (Spinner)findViewById(R.id.spnCat);
        spnCat.setAdapter(adapterCat);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraCriterioLocation();
        iniciaGeolocation(this);
    }

    @Override
    protected void onDestroy() {
        lm.removeUpdates(this);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        txtLat = (TextView) findViewById(R.id.txtLat);
        txtLon = (TextView) findViewById(R.id.txtLon);
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        txtLat.setText("Latitude : "+location.getLatitude());
        txtLon.setText("Longitude : "+location.getLongitude());
    }

    public void configuraCriterioLocation() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        PackageManager packageManager = getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);

        if (hasGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            Log.i("LOCATION", "usando GPS");
        } else {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            Log.i("LOCATION", "usando WI-FI ou dados");
        }
    }

    @SuppressLint("MissingPermission")
    public void iniciaGeolocation(Context ctx) {
        //obtem o melhor provedor habilitado com o critério
        String provider = lm.getBestProvider(criteria, true);

        if (provider == null) {
            Log.e("PROVEDOR", "Nenhum provedor encontrado");
        } else {
            Log.i("PROVEDOR", "Esta sendo utilizado o provedor " + provider);

            lm.requestLocationUpdates(provider, TEMPO_REQUISICAO_LATLONG, DISTANCIA_MIN_METROS, (LocationListener) ctx);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void clickCheckIn(View v){
        String Local = edtLocal.getText().toString();
        Categoria cat = (Categoria) spnCat.getSelectedItem();

        if(latitude == "" || longitude == ""){
            Toast.makeText(this,"Aguarde a localização ser determinada",Toast.LENGTH_LONG).show();
            return;
        }

        if(Local == null || Local=="" || cat == null){
            Toast.makeText(this,"Alguns dados do check-in não foram preenchidos!",Toast.LENGTH_LONG).show();
            return;
        }

        CheckIn.insertCheckIn(Local,cat,latitude,longitude);
        Toast.makeText(this,"Check-in realizado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_map:
                Intent it = new Intent(this, MapCheckInActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(it);
                finish(); // FINISH HIM!!
                break;

            case R.id.action_gestao:
                Intent itG = new Intent(this, GestaoActivity.class);
                startActivity(itG);
                break;

            case R.id.action_lugares:
                break;

            default: break;
        }

        return true;
    }
}