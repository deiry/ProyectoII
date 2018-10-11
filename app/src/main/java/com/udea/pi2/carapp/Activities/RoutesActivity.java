package com.udea.pi2.carapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udea.pi2.carapp.R;


public class RoutesActivity extends AppCompatActivity {
    /**
     * if TYPE_ROUTES = 0 => GET SELF ROUTES
     * if TYPE_ROUTES = 1 => GET ROUTES
     */
    private int TYPE_ROUTES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        TYPE_ROUTES = getIntent().getIntExtra("typeRoutes",0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TYPE_ROUTES == 0){
            getSelfRoutes();
        }
        else if(TYPE_ROUTES == 1){
            getAllRoutes();
        }
        else{
            Toast.makeText(this, "No Soportado",Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllRoutes() {
    }

    private void getSelfRoutes() {
    }
}
