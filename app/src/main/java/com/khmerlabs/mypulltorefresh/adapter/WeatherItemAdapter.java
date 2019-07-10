package com.khmerlabs.mypulltorefresh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmerlabs.mypulltorefresh.R;
import com.khmerlabs.mypulltorefresh.WeatherItem;
import com.khmerlabs.mypulltorefresh.common.Common;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WeatherItemAdapter extends ArrayAdapter<WeatherItem> {

    public WeatherItemAdapter(@NonNull Context context, int resource, @NonNull List<WeatherItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WeatherItem weatherItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.weather_item, parent, false);
        }
        TextView txtCityName = (TextView) convertView.findViewById(R.id.txt_city_name);
        TextView txtHumidity = (TextView) convertView.findViewById(R.id.txt_humidity);
        TextView txtPressure = (TextView) convertView.findViewById(R.id.txt_pressure);
        TextView txtTemperature = (TextView) convertView.findViewById(R.id.txt_temperature);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txt_description);
        TextView txtCurrentDateTime = (TextView) convertView.findViewById(R.id.txt_date_time);
        TextView txtWind = (TextView) convertView.findViewById(R.id.txt_wind);

        if (weatherItem != null) {
            txtCityName.setText(weatherItem.getCityName());
            txtHumidity.setText(new StringBuilder(weatherItem.getHumidity()).append("%"));
            txtPressure.setText(new StringBuilder(weatherItem.getPressure()).append(" hpa"));
            txtTemperature.setText(new StringBuilder(weatherItem.getTemperature()).append("°C"));
            txtDescription.setText(new StringBuilder("Weather in ").append(weatherItem.getCityName()));
            txtCurrentDateTime.setText(new StringBuilder("Local time: ").append(Common.getCurrentTimeUsingDate()));
            txtWind.setText(weatherItem.getWind());
        }
        return convertView;
    }
}
