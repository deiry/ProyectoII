package com.udea.pi2.carapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.udea.pi2.carapp.Adapters.CarAdapter;
import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Car;
import com.udea.pi2.carapp.model.User;

import java.util.ArrayList;
import java.util.Calendar;

import callback.CallbackModel;
import callback.CallbackRecyclerCar;


public class RouteActivity extends AppCompatActivity {

    TextInputLayout ly_arrived_time;
    TextInputEditText et_arrived_time;
    TextInputEditText et_route_date;
    TextInputEditText et_arrived;
    TextInputEditText et_departure;
    RecyclerView rv_car;
    LinearLayout ly_list_car;
    LinearLayout ly_select_car;
    TextInputEditText et_car_brand;
    TextInputEditText et_car_plaque;


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
        rv_car = (RecyclerView) findViewById(R.id.rv_route_car);
        ly_list_car = (LinearLayout) findViewById(R.id.ly_list_car);

        ly_select_car = (LinearLayout) findViewById(R.id.ly_car_select);
        et_car_brand = (TextInputEditText) findViewById(R.id.input_car_marca);
        et_car_plaque = (TextInputEditText) findViewById(R.id.input_car_placa);

        getCars();

    }

    public void initRecyclerView(ArrayList<Car> cars){

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_car.setLayoutManager(lm);

        CarAdapter adapter = new CarAdapter(cars, new CallbackRecyclerCar() {
            @Override
            public void onClickItem(Object item) {
                Car carCurrent = (Car) item;
                showSelectCar(carCurrent);

            }
        });
        rv_car.setAdapter(adapter);
    }

    public void showSelectCar(Car car){
        et_car_brand.setText(car.getBrand());
        et_car_brand.setEnabled(false);
        et_car_plaque.setText(car.getPlaque());
        et_car_plaque.setEnabled(false);
        ly_list_car.setVisibility(View.GONE);
        ly_select_car.setVisibility(View.VISIBLE);

    }

    public void getCars() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        User.findByEmail(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                User u = (User) id;
                Car.findSelfCars(new CallbackModel() {
                    @Override
                    public void onSuccess(Object id) {
                        initRecyclerView((ArrayList<Car>) id);
                    }

                    @Override
                    public void onError(Object model, String message) {

                    }
                },u.getId());

            }

            @Override
            public void onError(Object model, String message) {

            }
        },email);
    }



    public void clickArriveLocation(View v){
        Intent intent = new Intent(this, MapActivity.class);
        //enviamos la ubicacion de partida
        if(latDeparture != 0 && lngDeparture != 0){
            intent.putExtra("lat",latDeparture);
            intent.putExtra("lng", lngDeparture);
            intent.putExtra("tittleMarker","Punto de Partida");
            intent.putExtra("tittleActivity", "Ubicación de llegada");
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void clickDepartureLocation(View v){
        Intent intent = new Intent(this, MapActivity.class);
        //enviamos la ubicacion de llegada
        if(latArrive != 0 && lngArrive != 0){
            intent.putExtra("lat",latArrive);
            intent.putExtra("lng", lngArrive);
            intent.putExtra("tittleMarker","Punto de Llegada");
            intent.putExtra("tittleActivity", "Ubicación de salida");
        }
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
                calculateDepartureTime();
            }
        }
    }
/*
* https://github.com/pjwelcome/GoogleMapsDirections/blob/master/app/src/main/java/com/multimeleon/android/googlemapsdirections/MapsActivity.java
* */
    private void calculateDepartureTime(){
        double distance = distance(this.latArrive,this.latDeparture,this.lngArrive,this.lngDeparture,0,0);
        double time = distance/(30000/60);

        String strArriveTime = et_arrived_time.getText().toString();
        if (!strArriveTime.isEmpty()) {
            int hours = Integer.parseInt(strArriveTime.substring(0,2));
            int minutes = Integer.parseInt(strArriveTime.substring(3,5));
            time = Math.ceil(time);
            if(minutes - time < 0){
                minutes = minutes + 59 - (int) time;
                hours--;
            }
            else
            {
                minutes = minutes - (int) time;
            }

        }





    }

    public void onClickIda(View view){


    }

    public void oncClickIdaVuelta(View view){

    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
