package in.menukart.menukart.entities;

import java.io.Serializable;

public class MarkerData implements Serializable {
    private double latitude;
    private double longitude;
    private float markerColor;

    public MarkerData(double latitude, double longitude, float markerColor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.markerColor = markerColor;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getMarkerColor() {
        return markerColor;
    }

    public void setMarkerColor(float markerColor) {
        this.markerColor = markerColor;
    }
}

