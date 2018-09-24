package com.example.sofiaalejandro.carapp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;


import model.Car;

public class FormCarActivity extends AppCompatActivity {
    private String brand;
    private String line;
    private String model;
    private String plaque;
    private String num_passenger;
    private String obs;
    private TextInputEditText inputBrand;
    private TextInputEditText inputLine;
    private TextInputEditText inputModel;
    private TextInputEditText inputPlaque;
    private TextInputEditText inputNum;
    private TextInputEditText inputObs;
    private Car car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_car);
        car = new Car();
        init();
        validate();
    }

    public void saveCar(View view){

        validate();
        String brand = inputBrand.getText().toString();
        car.setBrand(brand);

        String line = inputLine.getText().toString();
        //car.setLine(line);

        String model = inputModel.getText().toString();
        car.setModel(model);

        String plaque = inputPlaque.getText().toString();
        car.setPlaque(plaque);

        String numPass = inputNum.getText().toString();
        int numPassenger = Integer.parseInt(numPass);
        car.setPassegerNum(numPassenger);

        String obs = inputObs.getText().toString();
        car.setName(obs);
        //car.save();

    }

    public void init(){
        inputBrand = (TextInputEditText) findViewById(R.id.input_brand);
        inputBrand.setOnFocusChangeListener(focusChangeListener(inputBrand));

        inputLine = (TextInputEditText) findViewById(R.id.input_line);
        inputLine.setOnFocusChangeListener(focusChangeListener(inputLine));

        inputModel = (TextInputEditText) findViewById(R.id.input_model);
        inputModel.setOnFocusChangeListener(focusChangeListener(inputModel));

        inputPlaque = (TextInputEditText) findViewById(R.id.input_plaque);
        inputPlaque.setOnFocusChangeListener(focusChangeListener(inputPlaque));

        inputNum = (TextInputEditText) findViewById(R.id.input_num_passenger);
        inputNum.setOnFocusChangeListener(focusChangeListener(inputNum));

        inputObs = (TextInputEditText) findViewById(R.id.input_obser);
        inputObs.setOnFocusChangeListener(focusChangeListener(inputObs));
    }

    public void validate(){




        }

        public View.OnFocusChangeListener focusChangeListener(final TextInputEditText input){

        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    input.setError("Vacio");
                }
            }
        };
            return listener;
        }

        public TextWatcher textChangedLister(final TextInputEditText input){
            TextWatcher listerner = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().isEmpty()){
                        input.setError("Vacío");
                    }
                }
            };
            return listerner;
        }
    }


