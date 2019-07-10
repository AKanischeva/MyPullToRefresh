package com.khmerlabs.mypulltorefresh;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.khmerlabs.mypulltorefresh.adapter.WeatherItemAdapter;
import com.khmerlabs.mypulltorefresh.common.Common;
import com.khmerlabs.mypulltorefresh.model.WeatherResult;
import com.khmerlabs.mypulltorefresh.retrofit.IOpenWeatherMap;
import com.khmerlabs.mypulltorefresh.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    PullRefreshLayout layout;
    ListView listView;
    private WeatherItemAdapter adp;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private IOpenWeatherMap mService;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

        layout = (PullRefreshLayout) findViewById(R.id.pullToRefresh);
        listView = (ListView) findViewById(R.id.lisView);
        adp = new WeatherItemAdapter(this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<WeatherItem>());
        listView.setAdapter(adp);
        setEvents();
        adp.notifyDataSetChanged();
        requestCoordinates();
    }

    void setEvents() {
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Updating information!", Toast.LENGTH_SHORT).show();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (isNetworkAvailable()) {
                            requestCoordinates();
                        } else {
                            Toast.makeText(getApplicationContext(), "No internet available!", Toast.LENGTH_SHORT).show();
                        }
                        //Если раскомментировать тут, и закомментировать такую же строку ниже, то корректно работает сортировка каждый раз,
                        // но при старте приложения не появляется никакая информация, пока не обновишь страницу
//                        sortAdapter();
                        Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
                        layout.setRefreshing(false);
                    }
                });
            }
        });

    }

    private boolean isNetworkAvailable() {
        if (getApplicationContext() != null) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void requestCoordinates() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            buildLocationRequest();
                            buildLocationCallback();
                            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.currentLocation = locationResult.getLastLocation();
                getUpdateForWeather();
            }
        };
    }

    private void getUpdateForWeather() {
        adp = new WeatherItemAdapter(this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<WeatherItem>());
        refreshWeatherInformation(getWeatherResultCurrentLocation());
        refreshWeatherInformation(getWeatherResultMoscow());
        refreshWeatherInformation(getWeatherResultSaintPetersburg());
        sortAdapter();
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }


    private Observable<WeatherResult> getWeatherResultCurrentLocation() {
        return mService.getWeatherByCoordinates(String.valueOf(Common.currentLocation.getLatitude()),
                String.valueOf(Common.currentLocation.getLongitude()),
                Common.APP_ID,
                "metric");
    }

    private Observable<WeatherResult> getWeatherResultMoscow() {
        return mService.getWeatherByNameAndCountryCode(Common.MOSCOW,
                Common.APP_ID,
                "metric");
    }

    private Observable<WeatherResult> getWeatherResultSaintPetersburg() {
        return mService.getWeatherByNameAndCountryCode(Common.SPETERSBURG,
                Common.APP_ID,
                "metric");
    }

    private void refreshWeatherInformation(Observable<WeatherResult> weatherResult) {
        compositeDisposable.add(weatherResult
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) {
                        WeatherItem weatherItem = new WeatherItem(weatherResult.getName(),
                                new StringBuilder(weatherResult.getMain().getHumidity()).toString(),
                                new StringBuilder(weatherResult.getMain().getPressure()).toString(),
                                new StringBuilder(weatherResult.getMain().getTemp()).toString(),
                                new StringBuilder("Weather in ").append(weatherResult.getName()).toString(),
                                new StringBuilder("Local time: ").append(Common.getCurrentTimeUsingDate()).toString(),
                                weatherResult.getWind().toString());
                        switch (weatherItem.getCityName()) {
                            case "Moscow":
                                weatherItem.setId(1);
                                break;
                            case "Saint Petersburg":
                                weatherItem.setId(2);
                                break;
                            default:
                                weatherItem.setId(0);
                                break;
                        }
                        adp.add(weatherItem);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void sortAdapter() {
        adp.sort(new Comparator<WeatherItem>() {
            @Override
            public int compare(WeatherItem o1, WeatherItem o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        listView.setAdapter(adp);
        adp.notifyDataSetChanged();
    }
}
