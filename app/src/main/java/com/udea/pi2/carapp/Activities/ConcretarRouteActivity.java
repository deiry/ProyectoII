package com.udea.pi2.carapp.Activities;

import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.udea.pi2.carapp.Adapters.CarAdapter;
import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Car;
import com.udea.pi2.carapp.model.Route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import callback.CallbackModel;
import callback.CallbackRecyclerCar;

public class ConcretarRouteActivity extends AppCompatActivity {

    String id;
    Route route;
    ArrayList<Car> cars;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setTitle("Concretar ruta");

        id = getIntent().getStringExtra("idRoute");
        Route.findById(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                route = (Route) id;
                Toast.makeText(ConcretarRouteActivity.this, "Route "+ route.getOwner().getEmail(), Toast.LENGTH_SHORT).show();
                initView();
            }

            @Override
            public void onError(Object model, String message) {

            }
        }, id);


    }

    public void saveRoute(View view){
        Toast.makeText(this, "Guardado en proceso", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void initView(){

        findViewById(R.id.calendar).setVisibility(View.GONE);
        findViewById(R.id.btn_route_departure_location).setVisibility(View.GONE);
        findViewById(R.id.arrive_time).setVisibility(View.GONE);
        findViewById(R.id.btn_route_arrive_location).setVisibility(View.GONE);
        findViewById(R.id.renew_car).setVisibility(View.GONE);
        findViewById(R.id.ly_title_car).setVisibility(View.GONE);
       findViewById(R.id.ly_list_car_profile).setVisibility(View.GONE);
       findViewById(R.id.swaplocation).setVisibility(View.GONE);

        et_arrived_time = (TextInputEditText) findViewById(R.id.input_arrive_time);
        //et_departure_time = (TextInputEditText) findViewById(R.id.input_departure_time);
        et_back_time = (TextInputEditText) findViewById(R.id.input_back_time);
        et_price = (TextInputEditText) findViewById(R.id.input_price);




        ly_back_time = (LinearLayout) findViewById(R.id.ly_back_time);
        et_route_date = (TextInputEditText) findViewById(R.id.input_route_date);
        et_arrived = (TextInputEditText) findViewById(R.id.input_arrive);
        et_departure = (TextInputEditText) findViewById(R.id.input_departure);
        et_departure.setClickable(false);
        rv_car = (RecyclerView) findViewById(R.id.rv_route_car);
        ly_list_car = (LinearLayout) findViewById(R.id.ly_list_car);


        ly_select_car = (LinearLayout) findViewById(R.id.ly_car_select);
        et_car_brand = (TextInputEditText) findViewById(R.id.input_car_marca);
        et_car_plaque = (TextInputEditText) findViewById(R.id.input_car_placa);

        ch_one_trip = (CheckBox) findViewById(R.id.checkbox_one_trip);
        ch_round_trip = (CheckBox)findViewById(R.id.checkbox_round_trip);
        btn_arrive = (ImageButton) findViewById(R.id.btn_route_arrive_location);
        btn_departure = (ImageButton) findViewById(R.id.btn_route_departure_location);

        DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatTime = new SimpleDateFormat("HH:mm a");
        Date date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) route.getArrivalTime());
        date = calendar.getTime();
        String strDate = formatDate.format(date);
        et_route_date.setText(strDate);
        et_route_date.setClickable(false);


        et_departure.setText(route.getDepartureDir());
        et_departure.setClickable(false);
        et_departure.setEnabled(false);

        et_arrived_time.setText(formatTime.format(date));
        et_arrived_time.setClickable(false);
        et_arrived_time.setEnabled(false);

        et_arrived.setText(route.getArriveDir());
        et_arrived.setClickable(false);
        et_arrived.setEnabled(false);


        et_price.setText(Double.toString(route.getPrice()));
        et_price.setClickable(false);
        et_price.setEnabled(false);

        ch_one_trip.setVisibility(View.GONE);
        ch_round_trip.setVisibility(View.GONE);
        ly_back_time.setVisibility(View.GONE);
        showCars();
        showUser();

    }

    public void showUser(){
        findViewById(R.id.include_profile).setVisibility(View.VISIBLE);
        EditText et_profile_name;
        EditText et_profile_email;
        ImageView iv_profile;

        et_profile_name = (EditText) findViewById(R.id.et_profile_name);
        et_profile_email = (EditText) findViewById(R.id.et_profile_email);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);

        et_profile_email.setInputType(0);
        et_profile_name.setInputType(0);


        String email = route.getOwner().getEmail();
        String name = route.getOwner().getName();

        et_profile_email.setText(email);
        et_profile_name.setText(name);

        et_profile_email.setClickable(false);
        et_profile_name.setClickable(false);
        Uri uri;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            uri = user.getPhotoUrl();
        }


        try {


        }catch (Exception e){

        }
    }

    public void showCars(){

        TextView textView = findViewById(R.id.tv_car_text);
        textView.setText("Detalle del carro y conductor");
        cars = new ArrayList<>();
        cars.add(route.getCar());


        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_car.setLayoutManager(lm);

        CarAdapter adapter = new CarAdapter(cars, new CallbackRecyclerCar() {
            @Override
            public void onClickItem(Object item) {


            }
        });
        rv_car.setAdapter(adapter);
    }
}
