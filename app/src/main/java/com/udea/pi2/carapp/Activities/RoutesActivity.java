package com.udea.pi2.carapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udea.pi2.carapp.Adapters.RouteAdapter;
import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Route;
import com.udea.pi2.carapp.model.RoutePassenger;
import com.udea.pi2.carapp.model.User;

import java.util.ArrayList;

import callback.CallbackModel;


public class RoutesActivity extends AppCompatActivity {
    /**
     * if TYPE_ROUTES = 0 => GET SELF ROUTES
     * if TYPE_ROUTES = 1 => GET ROUTES
     */
    private int TYPE_ROUTES = 0;
    private String ID_CURRENT_USER = null;
    public RoutePassenger route;
    public ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        setTitle("Mis rutas");
        TYPE_ROUTES = getIntent().getIntExtra("typeRoutes",0);
        ID_CURRENT_USER = getIntent().getStringExtra("idCurrentUser");

        createRoutesTest();

    }

    private void createRoutesTest() {
        RoutePassenger.findById(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                route = (RoutePassenger) id;
            }

            @Override
            public void onError(Object model, String message) {
                System.out.print(model.toString() + message);
            }
        },"LnCQKYezXlVDiypaml0E");


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

    private void setRoutes(ArrayList<Route> routes) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_routes_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        RouteAdapter carsAdapter = new RouteAdapter(routes);
        rv.setAdapter(carsAdapter);
    }

    public void addRoute(View v){
        Intent intent = new Intent(this,RouteActivity.class);
        intent.putExtra("idCurrentUser",ID_CURRENT_USER);
        startActivity(intent);
    }
}
