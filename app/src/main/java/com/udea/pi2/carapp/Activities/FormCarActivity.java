package com.udea.pi2.carapp.Activities;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.udea.pi2.carapp.R;


import java.util.ArrayList;

import callback.CallbackModel;
import com.udea.pi2.carapp.model.Car;
import com.udea.pi2.carapp.model.User;
import petrov.kristiyan.colorpicker.ColorPicker;

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
    private User currentUser;
    private String colorC;
    private TextView tvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_car);
        setTitle("Registro de vehículo");
        car = new Car();
        init();
        getCurrentUser();
        validate();
    }

    public void getCurrentUser() {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        User.findByEmail(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
               currentUser = (User) id;
            }

            @Override
            public void onError(Object model, String message) {

            }
        },email);
    }

    public void saveCar(View view) {

        String brand = inputBrand.getText().toString();
        car.setBrand(brand);

        String line = inputLine.getText().toString();
        //car.set(line);

        String model = inputModel.getText().toString();
        car.setModel(model);

        String plaque = inputPlaque.getText().toString();
        car.setPlaque(plaque);

        String numPass = inputNum.getText().toString();
        if(numPass.isEmpty()) numPass="0";
        int numPassenger = Integer.parseInt(numPass);
        car.setPassegerNum(numPassenger);

        String obs = inputObs.getText().toString();
        car.setName(obs);

        car.setDriver(currentUser);

        car.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                finish();
            }

            @Override
            public void onError(Object model, String message) {

            }
        });

        car.setColor(colorC);

    }

    @Override
    public void finish() {
        super.finish();
    }

    public void init() {

        inputBrand = (TextInputEditText) findViewById(R.id.input_brand);
        inputBrand.setOnFocusChangeListener(focusChangeListener(inputBrand));
        inputBrand.addTextChangedListener(textChangedLister(inputBrand));

        inputLine = (TextInputEditText) findViewById(R.id.input_line);
        inputLine.setOnFocusChangeListener(focusChangeListener(inputLine));
        inputLine.addTextChangedListener(textChangedLister(inputLine));

        inputModel = (TextInputEditText) findViewById(R.id.input_model);
        inputModel.setOnFocusChangeListener(focusChangeListener(inputModel));
        inputModel.addTextChangedListener(textChangedLister(inputModel));

        inputPlaque = (TextInputEditText) findViewById(R.id.input_plaque);
        inputPlaque.setOnFocusChangeListener(focusChangeListener(inputPlaque));
        inputPlaque.addTextChangedListener(textChangedLister(inputPlaque));

        inputNum = (TextInputEditText) findViewById(R.id.input_num_passenger);
        inputNum.setOnFocusChangeListener(focusChangeListener(inputNum));
        inputNum.addTextChangedListener(textChangedLister(inputNum));

        inputObs = (TextInputEditText) findViewById(R.id.input_obser);
        inputObs.setOnFocusChangeListener(focusChangeListener(inputObs));
        inputObs.addTextChangedListener(textChangedLister(inputObs));

        tvColor = (TextView) findViewById(R.id.tv_color);
        colorC = "";
    }

    public void validate() {
    }

    public View.OnFocusChangeListener focusChangeListener(final TextInputEditText input) {

        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && input.getText().toString().isEmpty()) {
                    input.setError("Vacío");
                } else if (!input.getText().toString().isEmpty()) {
                    input.setError(null);
                    TextInputLayout h = (TextInputLayout) findViewById(R.id.input_ly_brand);
                    h.setErrorEnabled(false);
                }
            }
        };
        return listener;
    }

    public TextWatcher textChangedLister(final TextInputEditText input) {
        TextWatcher listerner = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    input.setError("Vacío");
                } else {
                    input.setError(null);
                    TextInputLayout h = (TextInputLayout) findViewById(R.id.input_ly_brand);
                    h.setErrorEnabled(false);
                }
            }
        };
        return listerner;
    }

    public void openColorPicker(View view) {
        final ColorPicker colorPicker = new ColorPicker(this);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#ffffff");
        colors.add("#000000");
        colors.add("#b3b3b3");
        colors.add("#bb0a30");
        colors.add("#104E8B");
        colors.add("#691F01");

        colorPicker.setColors(colors)
                    .setColumns(5)
                    .setRoundColorButton(true)
                    .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                        @Override
                        public void onChooseColor(int position, int color) {
                            colorC = String.valueOf(color);
                            tvColor.setTextColor(color);
                            tvColor.setTypeface(null, Typeface.BOLD);
                        }

                        @Override
                        public void onCancel() {

                        }
                    })
                    .show();


    }
}


