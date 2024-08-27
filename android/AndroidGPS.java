 
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


public class LocationUtils {
    static LocationManager locationManager;
    static Location location;
    static boolean continueListener = true;

    public Location init(Activity activity, Handler handler) {
        String bestP = LocationManager.GPS_PROVIDER;
        if (locationManager == null) {
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            locationManager.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {

                }
            });
            bestP = locationManager.getBestProvider(getCri(), true);
            String finalBestP = bestP;
            handler.postDelayed(new Runnable() {
                @SuppressLint("MissingPermission")
                @Override
                public void run() {
                    locationManager.requestLocationUpdates(finalBestP, 1000, 1, locationListener);
                    if (continueListener) {
                        handler.postDelayed(this, 1000 * 20);
                    }
                }
            }, 1000 * 20);
            locationManager.requestLocationUpdates(bestP, 1000, 100, locationListener);
        }
        location = locationManager.getLastKnownLocation(bestP);
        return location;
    }

    @SuppressLint("MissingPermission")
    public static Location getLocation() {
        if (location == null) {
            String bestP = locationManager.getBestProvider(getCri(), true);
            location = locationManager.getLastKnownLocation(bestP);
        }
        return location;
    }

    public static void setLocation(Location location) {
        LocationUtils.location = location;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            LocationUtils.location = location;
        }
    };

    private static Criteria getCri() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
}
