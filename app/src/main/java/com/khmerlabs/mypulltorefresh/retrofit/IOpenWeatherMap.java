package com.khmerlabs.mypulltorefresh.retrofit;


import com.khmerlabs.mypulltorefresh.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {

    @GET("weather")
    Observable<WeatherResult> getWeatherByCoordinates(@Query("lat") String lat,
                                                      @Query("lon") String lon,
                                                      @Query("appId") String appId,
                                                      @Query("units") String units);

    @GET("weather")
    Observable<WeatherResult> getWeatherByNameAndCountryCode(@Query("q") String q,
                                                             @Query("appId") String appId,
                                                             @Query("units") String units);
}
