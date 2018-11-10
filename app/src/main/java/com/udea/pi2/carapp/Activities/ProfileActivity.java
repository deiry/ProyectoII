package com.udea.pi2.carapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.udea.pi2.carapp.Adapters.CarAdapter;
import com.udea.pi2.carapp.R;

import java.util.ArrayList;

import callback.CallbackModel;
import model.Car;
import model.Model;
import model.User;

public class ProfileActivity extends AppCompatActivity {
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    Boolean isFABOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormCarActivity.class);
                startActivity(intent);
            }
        });

        //getCars();
    }

    @Override
    protected void onResume() {
        getCars();
        super.onResume();
    }

    private void showFABMenu(){
        isFABOpen=true;
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
        //fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
       // fab3.animate().translationY(0);
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
                        setCars((ArrayList<Car>) id);
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

    public void setCars(ArrayList<Car> cars) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewCarsProfile);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        CarAdapter carsAdapter = new CarAdapter(cars);
        rv.setAdapter(carsAdapter);
    }
}
