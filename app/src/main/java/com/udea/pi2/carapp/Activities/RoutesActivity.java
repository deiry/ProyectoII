package com.udea.pi2.carapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Route;

import java.util.ArrayList;

import callback.CallbackModel;


public class RoutesActivity extends AppCompatActivity {
    /**
     * if TYPE_ROUTES = 0 => GET SELF ROUTES
     * if TYPE_ROUTES = 1 => GET ROUTES
     */
    private int TYPE_ROUTES = 0;
    private String ID_CURRENT_USER = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        TYPE_ROUTES = getIntent().getIntExtra("typeRoutes",0);
        ID_CURRENT_USER = getIntent().getStringExtra("idCurrentUser");
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
        if(ID_CURRENT_USER != null){

        }
    }

    private void getSelfRoutes() {
        if(ID_CURRENT_USER != null){
            Route.findSelfRoutes(new CallbackModel() {
                @Override
                public void onSuccess(Object id) {
                    setRoutes((ArrayList<Route>) id);
                }

                @Override
                public void onError(Object model, String message) {

                }
            },ID_CURRENT_USER);
        }
    }

    private void setRoutes(ArrayList<Route> id) {
        Toast.makeText(this,id.toString(),Toast.LENGTH_LONG).show();
    }
}
