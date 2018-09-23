package com.example.sofiaalejandro.carapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sofiaalejandro.carapp.R;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import callback.CallbackModel;
import model.Car;
import model.Route;
import model.RoutePassenger;
import model.State;
import model.User;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        User.findById(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                User u = (User) id;
            }

            @Override
            public void onError(Object model, String message) {

            }
        });

        /*User user = new User("David Alejandro",
                "jandro240@gmail.com",
                "");

        car = new Car("carro1",
                "verde",
                "KFV47D",
                    user,
                4,
                "Bajaj",
                "2015");



        final State state = new State();
        state.setName("Cancel");

        final Route route = new Route();
        route.setArrivalLat(-7.897695);
        route.setArrivalLng(6.034572);
        route.setArrivalTime(12341234);
        route.setDepartureLat(-6.456456);
        route.setDepartureLng(6.982344);
        route.setDepartureTime(1234567);
        route.setCar(car);
        route.setState(state);
        route.setPrice(2000);



        final RoutePassenger routePassenger = new RoutePassenger();
        routePassenger.setRoute(route);
        routePassenger.setUser(user);




        car.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                route.save(new CallbackModel() {
                    @Override
                    public void onSuccess(Object id) {
                        Toast.makeText(getApplicationContext(),(String) id , Toast.LENGTH_SHORT).show();
                        routePassenger.save(new CallbackModel() {
                            @Override
                            public void onSuccess(Object id) {
                                Toast.makeText(getApplicationContext(),(String) id , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Object model, String message) {

                            }
                        });
                    }

                    @Override
                    public void onError(Object model, String message) {

                    }
                });
            }

            @Override
            public void onError(Object model, String message) {

            }
        });*/
        /*Model.onChange(car.getClass(),car.getHashCode());
        Model.onChange(car.getDriver().getClass(),car.getDriver().getHashCode());*/


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                this.finish();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    /**
     * this method is when user has click in google button
     * @param v
     */
    public void signinGoogleAcount(View v){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }





}
