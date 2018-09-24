package com.example.sofiaalejandro.carapp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.sofiaalejandro.carapp.Activities.model.Vehicle;

public class FormCarActivity extends AppCompatActivity {
    private String brand;
    private String line;
    private String model;
    private String plaque;
    private String num_passenger;
    private String obs;
    private TextInputEditText inputEditText;
    private Vehicle vehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_car);
        vehicle = new Vehicle();
    }

    public void saveCar(View view){
        inputEditText = (TextInputEditText) findViewById(R.id.input_brand);
        String brand = inputEditText.getText().toString();
       


    }

    public void validate(){
        TextInputEditText input = (TextInputEditText) findViewById(R.id.input_brand);
        String brand = input.getText().toString();
        if (brand.isEmpty()){

        }
    }

}
