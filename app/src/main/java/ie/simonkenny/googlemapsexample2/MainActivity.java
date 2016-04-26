package ie.simonkenny.googlemapsexample2;

import android.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MapFragment mMapFragment;
    LatLng limerickLocation = new LatLng(52.66136550517293, -8.624267575625026);

    ArrayList<PictureMarkerDataModel> markerDataCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();

        markerDataCollection = new ArrayList<>();
        markerDataCollection.add(
                new PictureMarkerDataModel(
                        R.drawable.rubbish,
                        "Apartment 2",
                        "Crows have pulled rubbish out of the bins",
                        new LatLng(52.65657586737293, -8.632850644472683)
                )
        );
        markerDataCollection.add(
                new PictureMarkerDataModel(
                        R.drawable.graffiti,
                        "Graffiti",
                        "Some nice graffiti in an alley",
                        new LatLng(52.663864238301855, -8.619117734316433)
                )
        );

        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                for (PictureMarkerDataModel markerData : markerDataCollection) {
                    googleMap.addMarker(new MarkerOptions()
                                    .position(markerData.getPosition())
                                    .title(markerData.getTitle())
                                    .snippet(markerData.getSnippet())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    );
                }
                googleMap.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(limerickLocation, 7));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Toast.makeText(getApplicationContext(), "Marker clicked: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        for (PictureMarkerDataModel markerData : markerDataCollection) {
                            if (markerData.getTitle().equals(marker.getTitle())) {
                                // create info contents as View
                                View contentView = getLayoutInflater().inflate(R.layout.info_window_contents, null);
                                //View.inflate(getApplicationContext(), R.layout.info_window_contents, null);
                                // Set image
                                ImageView contentImageView = (ImageView) contentView.findViewById(R.id.info_window_image);
                                contentImageView.setImageResource(markerData.getImageResId());
                                // Set title
                                TextView contentTitleTextView = (TextView) contentView.findViewById(R.id.info_window_title);
                                contentTitleTextView.setText(markerData.getTitle());
                                // Set snippet
                                TextView contentSnippetTextView = (TextView) contentView.findViewById(R.id.info_window_snippet);
                                contentSnippetTextView.setText(markerData.getSnippet());
                                // return newly created View
                                return contentView;
                            }
                        }
                        return null;
                    }
                });
            }
        });

        Button goButton = (Button) findViewById(R.id.go_button);
        if (goButton != null) {
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.animateCamera(CameraUpdateFactory
                                    .newLatLngZoom(limerickLocation, 14));
                        }
                    });
                }
            });
        }
    }
}
