package ferancini.app.simplecheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.zip.Inflater;

public class MapCheckInActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_check_in);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //Assumimos que sempre há atualização nos check-ins
    @Override
    protected void onResume() {
        super.onResume();
        if(mMap != null){
            mMap.clear();
            List<CheckIn> listCheckIn = CheckIn.getCheckIns();
            for (CheckIn checkin : listCheckIn){
                LatLng latlon = new LatLng(Double.parseDouble(checkin.getLatitude()), Double.parseDouble(checkin.getLongitude()));

                mMap.addMarker(new MarkerOptions()
                        .position(latlon)
                        .title(checkin.getLocal())
                        .snippet("Categoria : "+checkin.getCat().getNome()+" Visitas:"+checkin.getQtdVisitas())
                );
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        List<CheckIn> listCheckIn = CheckIn.getCheckIns();
        for (CheckIn checkin : listCheckIn){
            LatLng latlon = new LatLng(Double.parseDouble(checkin.getLatitude()), Double.parseDouble(checkin.getLongitude()));

            mMap.addMarker(new MarkerOptions()
                    .position(latlon)
                    .title(checkin.getLocal())
                    .snippet("Categoria : "+checkin.getCat().getNome()+" Visitas:"+checkin.getQtdVisitas())
            );
        }
        if(listCheckIn.size() > 0){
            CheckIn primeiro = listCheckIn.get(0);
            CameraUpdate c = CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(new LatLng(Double.parseDouble(primeiro.getLatitude()), Double.parseDouble(primeiro.getLongitude())))
                            .tilt(60)
                            .zoom(18)
                            .build());

            mMap.animateCamera(c);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_voltar:
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                break;

            case R.id.action_gestao:
                Intent itG = new Intent(this, GestaoActivity.class);
                startActivity(itG);
                break;

        }
        return true;
    }
}