package com.udea.pi2.carapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.udea.pi2.carapp.Adapters.RecyclerItemClickListener;
import com.udea.pi2.carapp.Adapters.RouteAdapter;
import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Route;
import com.udea.pi2.carapp.model.User;

import java.util.ArrayList;

import callback.CallbackModel;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "FIRE";
    private FirebaseAuth auth;
    NavigationView navigationView;
    User currentUser;
    Route routeSelect;

    //View vars
    RecyclerView rv_routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Inicio");
        rv_routes = findViewById(R.id.rv_routes_enables);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Actualizado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", onClickListener()).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getCurrentUser();
        getAllRoutes();
    }

    public void setRoutes(final ArrayList<Route> routes){

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_routes.setLayoutManager(lm);
        RouteAdapter carsAdapter = new RouteAdapter(routes);
        rv_routes.setAdapter(carsAdapter);
        rv_routes.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv_routes ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        routeSelect = routes.get(position);
                        String id = routeSelect.getId();
                        Intent intent = new Intent(HomeActivity.this, ConcretarRouteActivity.class);
                        intent.putExtra("idRoute", id);
                        startActivity(intent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));



    }

    public View.OnClickListener onClickListener(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllRoutes();
            }
        };

    }

    private void getAllRoutes() {

            Route.findAll(new CallbackModel() {
                @Override
                public void onSuccess(Object id) {
                    setRoutes((ArrayList<Route>) id);
                }

                @Override
                public void onError(Object model, String message) {

                }
            });
        }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void finishHome(){
        this.finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find_route) {
            // Handle the camera action
        } else if (id == R.id.nav_self_routes) {
            Intent intent = new Intent(this, RoutesActivity.class);
            intent.putExtra("idCurrentUser",currentUser.getId());
            startActivity(intent);
        }
        else if (id == R.id.nav_signout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finishHome();
                        }
                    });
        }
        else if(id == R.id.nav_profile){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getCurrentUser() {
        View headerView = navigationView.getHeaderView(0);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            String email = user.getEmail();
            String name = user.getDisplayName();
            Uri uri = user.getPhotoUrl();
            User.findByEmail(new CallbackModel() {
                @Override
                public void onSuccess(Object id) {
                    currentUser = (User) id;
                }

                @Override
                public void onError(Object model, String message) {

                }
            },email);
            getUserFireSotore(email);
            TextView tv_email = (TextView) headerView.findViewById(R.id.email_current_user);
            TextView tv_name = (TextView) headerView.findViewById(R.id.name_current_user);
            ImageView image = (ImageView) headerView.findViewById(R.id.image_current_user);
            try {
                tv_email.setText(email);
                tv_name.setText(name);
                Picasso.get()
                        .load(uri)
                        .resize(200, 200)
                        .centerCrop()
                        .into(image);
            }catch (Exception e){

            }

        }
        else{
            auth.signOut();
            //Intent intent = new Intent(this, LoginActivity.class);
            //startActivity(intent);
            //this.finish();

        }
    }

    private void getUserFireSotore(String email){


    }
}
