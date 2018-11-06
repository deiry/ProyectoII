package com.udea.pi2.carapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.udea.pi2.carapp.R;

import java.io.IOException;
import java.util.Calendar;


public class RouteActivity extends AppCompatActivity {

    TextInputLayout ly_arrived_time;
    TextInputEditText et_arrived_time;
    TextInputEditText et_route_date;
    TextInputEditText et_arrived;
    TextInputEditText et_departure;

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    final int year = c.get(Calendar.YEAR);
    final int month = c.get(Calendar.MONTH);
    final int day = c.get(Calendar.DAY_OF_MONTH);

    int REQUEST_CODE = 1;
    int REQUEST_CODE2 = 2;

    private double latArrive;
    private double lngArrive;
    private double latDeparture;
    private double lngDeparture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        ly_arrived_time = (TextInputLayout) findViewById(R.id.input_ly_arrive_time);
        et_arrived_time = (TextInputEditText) findViewById(R.id.input_arrive_time);
        et_route_date = (TextInputEditText) findViewById(R.id.input_route_date);
        et_arrived = (TextInputEditText) findViewById(R.id.input_arrive);
        et_departure = (TextInputEditText) findViewById(R.id.input_departure);

    }

    public void clickArriveLocation(View v){
        Intent intent = new Intent(this, MapActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void clickDepartureLocation(View v){
        Intent intent = new Intent(this, MapActivity.class);
        startActivityForResult(intent, REQUEST_CODE2);
    }

    public void clickArriveTime(View v){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM = (hourOfDay > 12) ? "p.m." : "a.m.";
                hourOfDay = (hourOfDay > 12) ? (hourOfDay - 12) : hourOfDay;
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                //Muestro la hora con el formato deseado
                et_arrived_time.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    public void clickArriveDate(View v){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                Log.i("date",String.valueOf(yy)+ " " +String.valueOf(mm) + " " + String.valueOf(dd));
                et_route_date.setText(String.valueOf(dd) + "-" + String.valueOf(mm+1) + "-" + String.valueOf(yy));
            }
        },year,month,day);

        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String[] latlong =  data.getDataString().split(",");
                latArrive = Double.parseDouble(latlong[0]);
                lngArrive = Double.parseDouble(latlong[1]);
                et_arrived.setText(String.format("%.5f", latArrive)+","+String.format("%.5f", lngArrive));
            }
        }
        else if(requestCode == REQUEST_CODE2) {
            if (resultCode == RESULT_OK) {
                String[] latlong =  data.getDataString().split(",");
                latDeparture = Double.parseDouble(latlong[0]);
                lngDeparture = Double.parseDouble(latlong[1]);
                et_departure.setText(String.format("%.5f", latDeparture)+","+String.format("%.5f", lngDeparture));

                try {
                    calculateDepartureTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ApiException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
/*
* https://github.com/pjwelcome/GoogleMapsDirections/blob/master/app/src/main/java/com/multimeleon/android/googlemapsdirections/MapsActivity.java
* */
    private void calculateDepartureTime() throws InterruptedException, ApiException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCEGzJctSn-RbH2OR7uHJh2fqEaLFGMKl4")
                .build();
        DirectionsResult result = DirectionsApi.newRequest(context)
                .mode(TravelMode.DRIVING)
                .origin(new LatLng(latDeparture,lngDeparture))
                .destination(new LatLng(latArrive, lngArrive))
                //.departureTime()
                .await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(result));
    }


}
