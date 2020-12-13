package ferancini.app.simplecheckin;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapCheckInActivity extends FragmentActivity implements OnMapReadyCallback {

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
}