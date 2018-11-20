package com.udea.pi2.carapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Car;
import com.udea.pi2.carapp.model.Route;
import com.udea.pi2.carapp.model.State;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        setTitle("Mis rutas");
        TYPE_ROUTES = getIntent().getIntExtra("typeRoutes",0);
        ID_CURRENT_USER = getIntent().getStringExtra("idCurrentUser");

      //  createRoutesTest();

    }

    private void createRoutesTest() {
        //get current user
                User user = new User();
                user.id = ID_CURRENT_USER;
                Car car = new Car();
                car.id = "2iYrmzzLJWCXro7KTf63";

                for (int i = 0; i< 10;i++){
                    Route route = new Route();
                    route.setArrivalLat(i);
                    route.setArrivalLng(i);
                    route.setDepartureLat(i);
                    route.setDepartureLng(i);
                    route.setArrivalTime(i);
                    route.setDepartureTime(i-1);
                    route.setPrice(2000);
                    route.setState(new State("activo"));
                    route.setOwner(user);
                    route.setCar(car);

                    route.save(new CallbackModel() {
                        @Override
                        public void onSuccess(Object id) {
                            System.out.print(id);
                        }

                        @Override
                        public void onError(Object model, String message) {
                            System.out.print(message + model.toString());
                        }
                    });
                }



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

    public void addRoute(View v){
        Intent intent = new Intent(this,RouteActivity.class);
        intent.putExtra("idCurrentUser",ID_CURRENT_USER);
        startActivity(intent);
    }
}
