package com.example.maptracking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptracking.objects.AddressDO;
import com.example.maptracking.common.AppConstants;
import com.example.maptracking.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    private Location location;
    private LocationManager locationManager;
    BitmapDescriptor currentDescriptorIcon,destDescriptorIcon;
    double latitude, longitude,destLat=17.5287,destLong=78.2667;
    private LatLngBounds.Builder builder;
    AddressDO addressDO;
    TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        initControls(mapFragment);
    }

    private void initControls(SupportMapFragment mapFragment) {
        tvHeader=findViewById(R.id.tvHeader);
        Bitmap bitmap = getBitmapFromVectorDrawable(getApplicationContext(),R.drawable.pin);
        currentDescriptorIcon =BitmapDescriptorFactory.fromBitmap(bitmap);

        Bitmap bitmap1 = getBitmapFromVectorDrawable(getApplicationContext(),R.drawable.pin_blue);
        destDescriptorIcon =BitmapDescriptorFactory.fromBitmap(bitmap1);
        builder=new LatLngBounds.Builder();
        Bundle b=getIntent().getExtras();
        addressDO= (AddressDO) b.getSerializable(AppConstants.AddressKey);
        destLat=addressDO.latitude;
        destLong=addressDO.longitude;
        tvHeader.setText(addressDO.address);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMyLocationEnabled(false);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        location = locationManager.getLastKnownLocation(provider);
        if(location!=null){
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            setCurrentLocation();
        }
        else
            Toast.makeText(getApplicationContext(),"Location Null..",Toast.LENGTH_SHORT).show();
        getLocation();
    }

    private void setCurrentLocation() {
        LatLng currentLoacation = new LatLng(latitude,longitude);
        MarkerOptions markerOptions1 = new MarkerOptions();
        //BitmapDescriptor currentLocIcon = BitmapDescriptorFactory.fromResource(R.drawable.pin);
        markerOptions1.position(currentLoacation).icon(currentDescriptorIcon);
        Marker marker = mMap.addMarker(markerOptions1);

        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(currentLoacation, 17);
        mMap.moveCamera(yourLocation);
        LatLng destLatLong = new LatLng(destLat,destLong);
        zoomToDestinationandCurrentLocation(destLatLong,currentLoacation,mMap,null);
    }
    private void zoomToDestinationandCurrentLocation(LatLng destLatLong, LatLng currentLoacation, GoogleMap gMap, String str) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(destLatLong).icon(destDescriptorIcon);

        Marker m = gMap.addMarker(markerOptions);
        //m.setTag(info);  //setting info popup to marker

        builder.include(currentLoacation);      //zoom to show two markers
        builder.include(markerOptions.getPosition());

        LatLngBounds bounds = builder.build();

        int width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int height =getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.15); // offset from edges of the map 15% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        gMap.animateCamera(cu);
        showCurvedPolyline(currentLoacation,destLatLong,0.2,gMap);
    }
    private void showCurvedPolyline(LatLng p1, LatLng p2, double k, GoogleMap gMap) {
        //Draw polyline
        //Calculate distance and heading between two points
        double d = SphericalUtil.computeDistanceBetween(p1,p2);
        double h = SphericalUtil.computeHeading(p1, p2);

        //Midpoint position
        LatLng p = SphericalUtil.computeOffset(p1, d*0.5, h);

        //Apply some mathematics to calculate position of the circle center
        double x = (1-k*k)*d*0.5/(2*k);
        double r = (1+k*k)*d*0.5/(2*k);

        LatLng c = SphericalUtil.computeOffset(p, x, h + 90.0);

        //Polyline options
        PolylineOptions options = new PolylineOptions();
        List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dash(1), new Gap(0));

        //Calculate heading between circle center and two points
        double h1 = SphericalUtil.computeHeading(c, p1);
        double h2 = SphericalUtil.computeHeading(c, p2);

        //Calculate positions of points on circle border and add them to polyline options
        int numpoints = 100;
        double step = (h2 -h1) / numpoints;

        for (int i=0; i < numpoints; i++) {
            LatLng pi = SphericalUtil.computeOffset(c, r, h1 + i * step);
            options.add(pi);
        }
        gMap.addPolyline(options.width(5).color(Color.BLUE).geodesic(false).pattern(pattern));
    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable =  AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    void getLocation() {
        try {
            Log.i("getln","inside getLocation");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1*1000,
                    1, this);
        }
        catch(SecurityException e) {
            Log.i("getln","inside catch Block");
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        setCurrentLocation();
        Toast.makeText(getApplicationContext(),"latitude: "+location.getLatitude()+" "+"longitude: "+location.getLongitude(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }
}
