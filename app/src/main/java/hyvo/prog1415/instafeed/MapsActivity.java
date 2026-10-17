package hyvo.prog1415.instafeed;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import hyvo.prog1415.instafeed.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST = 101;
    private GoogleMap map;
    private FusedLocationProviderClient locationClient;
    private LatLng currentLocation;
    private FetchData fetchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.MAPS_API_KEY.isEmpty()) {
            Toast.makeText(this, "Add MAPS_API_KEY to local.properties to enable maps", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (fragment != null) fragment.getMapAsync(this);
        binding.res.setOnClickListener(view -> searchRestaurants());
    }

    private void searchRestaurants() {
        if (map == null || currentLocation == null) {
            Toast.makeText(this, "Wait for your location, then try again", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Uri.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
                .buildUpon().appendQueryParameter("location", currentLocation.latitude + "," + currentLocation.longitude)
                .appendQueryParameter("radius", "100").appendQueryParameter("type", "restaurant")
                .appendQueryParameter("key", BuildConfig.MAPS_API_KEY).build().toString();
        if (fetchData != null) fetchData.cancel(true);
        fetchData = new FetchData();
        fetchData.execute(map, url);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        map.setMyLocationEnabled(true);
        locationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
                .addOnSuccessListener(this, location -> {
                    if (location == null) {
                        Toast.makeText(this, "Location unavailable. Enable location and reopen the map.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    map.addMarker(new MarkerOptions().position(currentLocation).title("Current location"));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                }).addOnFailureListener(this, error -> Toast.makeText(this, "Unable to get location", Toast.LENGTH_LONG).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST && map != null) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                    return;
                }
            }
            Toast.makeText(this, "Location permission is needed to find nearby restaurants", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (fetchData != null) fetchData.cancel(true);
        super.onDestroy();
    }
}
