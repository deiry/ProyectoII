package com.udea.pi2.carapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.udea.pi2.carapp.Adapters.CarAdapter;
import com.udea.pi2.carapp.R;

import java.util.ArrayList;

import callback.CallbackModel;
import com.udea.pi2.carapp.model.Car;
import com.udea.pi2.carapp.model.User;

public class ProfileActivity extends AppCompatActivity {
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    EditText et_profile_name;
    EditText et_profile_email;
    ImageView iv_profile;

    Boolean isFABOpen = false;
    private User currentUser;
    private String email;
    private String name;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Mi perfil");

  //      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        et_profile_name = (EditText) findViewById(R.id.et_profile_name);
        et_profile_email = (EditText) findViewById(R.id.et_profile_email);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);

        et_profile_email.setInputType(0);
        et_profile_name.setInputType(0);

        getCurrentUser();

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });*/

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormCarActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showUser(){

        try {
            et_profile_email.setText(email);
            et_profile_name.setText(name);
            Picasso.get()
                    .load(uri)
                    .resize(200, 200)
                    .centerCrop()
                    .into(iv_profile);
        }catch (Exception e){

        }
    }
    public void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            email = user.getEmail();
            name = user.getDisplayName();
            uri = user.getPhotoUrl();
            User.findByEmail(new CallbackModel() {
                @Override
                public void onSuccess(Object id) {
                    currentUser = (User) id;
                    showUser();
                }

                @Override
                public void onError(Object model, String message) {

                }
            }, email);
        }
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
                /*Car.findAll(new CallbackModel() {
                    @Override
                    public void onSuccess(Object id) {
                        setCars((ArrayList<Car>) id);
                    }

                    @Override
                    public void onError(Object model, String message) {

                    }
                });*/
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

    public void goToCar(View v){

    }
}
