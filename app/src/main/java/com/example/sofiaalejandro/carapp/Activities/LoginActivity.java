package com.example.sofiaalejandro.carapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sofiaalejandro.carapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import callback.CallbackModel;
import model.Car;
import model.Route;
import model.RoutePassenger;
import model.State;
import model.User;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "AUTH";
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mStatusTextView = findViewById(R.id.status);

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

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            mStatusTextView.setText(account.getDisplayName());
            findViewById(R.id.btn_signin_google).setVisibility(View.GONE);
            findViewById(R.id.btn_signout_google).setVisibility(View.VISIBLE);

            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            mStatusTextView.setText("Desconectado");

            findViewById(R.id.btn_signin_google).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_signout_google).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    // [START signOut]
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(this,HomeActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                else{

                }

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }*/

    /**
     * this method is when user has click in google button
     * @param v
     */
    public void signinGoogleAcount(View v){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signoutGoogleAcount(View v){
        signOut();
    }





}
