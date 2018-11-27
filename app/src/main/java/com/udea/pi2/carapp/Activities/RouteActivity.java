package com.udea.pi2.carapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.udea.pi2.carapp.Adapters.CarAdapter;
import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Car;
import com.udea.pi2.carapp.model.Route;
import com.udea.pi2.carapp.model.State;
import com.udea.pi2.carapp.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    LinearLayout ly_back_time;
    TextInputEditText et_car_brand;
    TextInputEditText et_car_plaque;
    TextInputEditText et_price;
    TextInputEditText et_back_time;
    CheckBox ch_one_trip, ch_round_trip;
    ImageButton btn_arrive;
    ImageButton btn_departure;

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
    private String STR_UDEA_LOCATION = "6.268143,-75.568782";
    private String STR_UDEA_NAME = "Universidad de Antioquia";

    private double latArrive;
    private double lngArrive;
    private double latDeparture;
    private double lngDeparture;
    private Car carSelect;
    private User userCurrent;
    private State state;
    private boolean isClickArrive, isClickDeparture = false;

    private boolean isCheckRoundTrip, isCheckOneTrip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setTitle("Crear ruta");
        et_arrived_time = (TextInputEditText) findViewById(R.id.input_arrive_time);
        //et_departure_time = (TextInputEditText) findViewById(R.id.input_departure_time);
        et_back_time = (TextInputEditText) findViewById(R.id.input_back_time);
        et_price = (TextInputEditText) findViewById(R.id.input_price);

        ly_back_time = (LinearLayout) findViewById(R.id.ly_back_time);
        et_route_date = (TextInputEditText) findViewById(R.id.input_route_date);
        et_arrived = (TextInputEditText) findViewById(R.id.input_arrive);
        et_departure = (TextInputEditText) findViewById(R.id.input_departure);
        rv_car = (RecyclerView) findViewById(R.id.rv_route_car);
        ly_list_car = (LinearLayout) findViewById(R.id.ly_list_car);

        ly_select_car = (LinearLayout) findViewById(R.id.ly_car_select);
        et_car_brand = (TextInputEditText) findViewById(R.id.input_car_marca);
        et_car_plaque = (TextInputEditText) findViewById(R.id.input_car_placa);

        ch_one_trip = (CheckBox) findViewById(R.id.checkbox_one_trip);
        ch_round_trip = (CheckBox)findViewById(R.id.checkbox_round_trip);
        btn_arrive = (ImageButton) findViewById(R.id.btn_route_arrive_location);
        btn_departure = (ImageButton) findViewById(R.id.btn_route_departure_location);

        ch_round_trip.setChecked(true);
        isCheckRoundTrip=true;

        getCars();
        //set default UDEA location
        String[] latlong =  STR_UDEA_LOCATION.split(",");
        //save latitude and longitude
        latArrive = Double.parseDouble(latlong[0]);
        lngArrive = Double.parseDouble(latlong[1]);
        //set name udea
        et_arrived.setText(STR_UDEA_NAME);
        State.findById(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                state = (State) id;
            }

            @Override
            public void onError(Object model, String message) {
                System.out.print(message);
            }
        },"OFa1SeEqBgBsb3CwrgBD");

    }

    public void changeCar(View view){
        ly_list_car.setVisibility(View.VISIBLE);
        ly_select_car.setVisibility(View.GONE);
    }

    public void initRecyclerView(ArrayList<Car> cars){

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_car.setLayoutManager(lm);

        CarAdapter adapter = new CarAdapter(cars, new CallbackRecyclerCar() {
            @Override
            public void onClickItem(Object item) {
                carSelect = (Car)item;
                showSelectCar(carSelect);

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
                userCurrent = (User) id;
                Car.findSelfCars(new CallbackModel() {
                    @Override
                    public void onSuccess(Object id) {
                        initRecyclerView((ArrayList<Car>) id);
                    }

                    @Override
                    public void onError(Object model, String message) {

                    }
                },userCurrent.getId());

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
                et_arrived_time.setText(formatHour(hourOfDay,minute));
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    public void clickBackTime(View view){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                et_back_time.setText(formatHour(hourOfDay, minute));
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

    public void clickSwapLocation(View v){
        //save lat, lng and str EditText
        double lat = this.latArrive;
        double lng = this.lngArrive;
        String str = et_arrived.getText().toString();

        this.latArrive = this.latDeparture;
        this.lngArrive = this.lngDeparture;
        et_arrived.setText(et_departure.getText().toString());

        this.latDeparture = lat;
        this.lngDeparture = lng;
        et_departure.setText(str);

        if(et_departure.getText().toString().equals(STR_UDEA_NAME)){
            btn_departure.setVisibility(View.INVISIBLE);
            btn_arrive.setVisibility(View.VISIBLE);

            et_departure.setClickable(false);
            et_departure.setCursorVisible(false);
            et_departure.setFocusable(false);
            et_departure.setFocusableInTouchMode(false);

            et_arrived.setClickable(true);
            et_arrived.setCursorVisible(true);
            et_arrived.setFocusable(true);
            et_arrived.setFocusableInTouchMode(true);
        }
        else if(et_arrived.getText().toString().equals(STR_UDEA_NAME)) {
            btn_departure.setVisibility(View.VISIBLE);
            btn_arrive.setVisibility(View.INVISIBLE);
            et_arrived.setClickable(false);
            et_arrived.setCursorVisible(false);
            et_arrived.setFocusable(false);
            et_arrived.setFocusableInTouchMode(false);

            et_departure.setClickable(true);
            et_departure.setCursorVisible(true);
            et_departure.setFocusable(true);
            et_departure.setFocusableInTouchMode(true);
        }
    }


    public void onClickOneTrip(View view){

        if (isCheckRoundTrip){
            ch_round_trip.setChecked(false);
            isCheckRoundTrip = false;
        }else{
            ch_one_trip.setChecked(true);
        }
        ly_back_time.setVisibility(View.GONE);
        isCheckOneTrip = true;
    }

    public void onClickRoundTrip(View view){

        if (isCheckOneTrip){
           ch_one_trip.setChecked(false);
           isCheckOneTrip = false;
        }else{
            ch_round_trip.setChecked(true);
        }
        ly_back_time.setVisibility(View.VISIBLE);
        isCheckRoundTrip = true;
    }

    public void saveRoute(View v){
        Route route = new Route();
        route.setCar(carSelect);
        route.setOwner(userCurrent);
        route.setArrivalLat(latArrive);
        route.setArrivalLng(lngArrive);
        String dtStart = et_route_date.getText().toString() + " " +et_arrived_time.getText().toString();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMyyyy h:mm a");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        route.setArrivalTime((double) date.getTime());
        route.setDepartureLat(latDeparture);
        route.setDepartureLng(lngDeparture);
        route.setDepartureTime(route.getArrivalTime()-(30*60));
        route.setState(state);
        route.setArriveDir(et_arrived.getText().toString());
        route.setDepartureDir(et_departure.getText().toString());
        String price = et_price.getText().toString();
        route.setPrice(Double.valueOf(price));

        route.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                Toast.makeText(getApplicationContext(),"Ruta guardada correctamente.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Object model, String message) {

            }
        });

        if(ch_round_trip.isChecked()){

            dtStart = et_route_date.getText().toString() + " " +et_back_time.getText().toString();
            date = new Date();
            format = new SimpleDateFormat("dd-MMyyyy h:mm a");
            try {
                date = format.parse(dtStart);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Route round = new Route();
            round.setCar(carSelect);
            round.setOwner(userCurrent);
            round.setDepartureLat(latArrive);
            round.setDepartureLng(lngArrive);
            round.setDepartureTime((double) date.getTime());
            round.setArrivalLat(latDeparture);
            round.setArrivalLng(lngDeparture);
            round.setArrivalTime((double) date.getTime() + (30*60));
            round.setState(state);
            route.setArriveDir(et_departure.getText().toString());
            route.setDepartureDir(et_arrived.getText().toString());
            price = et_price.getText().toString();
            round.setPrice(Double.valueOf(price));

            round.save(new CallbackModel() {
                @Override
                public void onSuccess(Object id) {
                    Toast.makeText(getApplicationContext(),"Ruta guardada correctamente.",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(Object model, String message) {

                }
            });
        }
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

    @NonNull
    private String formatHour(int hourOfDay, int minute){
        String AM_PM = (hourOfDay >= 12) ? "p.m." : "a.m.";
        hourOfDay = (hourOfDay > 12) ? (hourOfDay - 12) : hourOfDay;
        //Formateo el hora obtenido: antepone el 0 si son menores de 10
        String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
        //Formateo el minuto obtenido: antepone el 0 si son menores de 10
        String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
        //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
        //Muestro la hora con el formato deseado
        return (horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
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
