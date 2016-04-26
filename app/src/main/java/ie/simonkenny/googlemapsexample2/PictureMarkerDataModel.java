package ie.simonkenny.googlemapsexample2;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by simonkenny on 24/04/2016.
 */
public class PictureMarkerDataModel {

    private final String title;
    private final String snippet;
    private final int imageResId;
    private final LatLng position;

    public PictureMarkerDataModel(int imageResId, String title, String snippet, LatLng position) {
        this.imageResId = imageResId;
        this.title = title;
        this.snippet = snippet;
        this.position = position;
    }

    public int getImageResId() {
        return imageResId;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getTitle() {
        return title;
    }
}
