package com.udea.pi2.carapp.model;

import android.util.Log;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callback.CallbackModel;

@Table(name = "tbl_route_passenger")
public class RoutePassenger extends Model {

    final static public String RP_CN_PASSENGER = "users";
    final static public String RP_CN_ROUTE = "route";

    @Column(name = RP_CN_PASSENGER)
    private ArrayList<User> users;

    @Column(name = RP_CN_ROUTE)
    private Route route;

    /* constructors */
    public RoutePassenger() {
        users = new ArrayList<User>();
    }

    public RoutePassenger(ArrayList<User> users, Route route) {
        this.users = users;
        this.route = route;
    }

    public void addUser(User user){
        users.add(user);
    }

    /* getters and setters methods*/
    public ArrayList<User> getUser() {
        return users;
    }

    public void setUser(ArrayList<User> user) {
        this.users = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public List<JSONObject> modelToJSON(){
        return null;
    }

    static public void findById(CallbackModel callbackModel,String id){
        Model.findById(callbackModel, id, RoutePassenger.class.getSimpleName());
    }

    @Override
    public void save(final CallbackModel callbackModel) {
        /*this.user.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                user.setId((String) id);
                saveRoute(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {

            }
        });*/
        this.saveModel(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                setId((String)id);
                callbackModel.onSuccess(id);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        });
    }

    private void saveRoute(final CallbackModel callbackModel){
        route.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                route.setId((String) id);
                saveRoutePassenger(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        });
    }

    private void saveRoutePassenger(final CallbackModel callbackModel){
        this.saveModel(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                setId((String) id);
                callbackModel.onSuccess(id);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model, message);
            }
        });
    }

    public RoutePassenger getThis(){
        return this;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(RP_CN_PASSENGER, this.usersToArray());
        map.put(RP_CN_ROUTE, this.route.getId());
        return map;
    }
    
    private List<String> usersToArray(){
        String[] array = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            array[i] = u.getId();
        }
        /*for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            array += "\"" + u.getId() + "\"";
            if(i<users.size()-1){
                array += ",";
            }
        }
        return array + "]";*/
        return Arrays.asList(array);
    }

    @Override
    public void mapToModel(final CallbackModel callbackModel, Map<String, Object> mapRequest) {

        HashMap<String, Object> map = (HashMap<String, Object>) mapRequest;
        try{
            final List<String> list = (List<String>) map.get(RP_CN_PASSENGER);
            final int size = list.size();
            Route.findById(new CallbackModel() {
                @Override
                public void onSuccess(Object id) {
                    setRoute((Route) id);
                    int count = 1;
                    for (String user : list){
                        final int finalCount = count;
                        User.findById(new CallbackModel() {
                            @Override
                            public void onSuccess(Object id) {
                                users.add((User) id);
                                if(size == finalCount){
                                    callbackModel.onSuccess(users);
                                }
                            }

                            @Override
                            public void onError(Object model, String message) {
                                callbackModel.onError(model,message);
                            }
                        },(String) user, User.class.getSimpleName());
                        count++;
                    }
                    //callbackModel.onSuccess(getThis());

                }

                @Override
                public void onError(Object model, String message) {
                    callbackModel.onError(model,message);
                }
            },(String) map.get(RP_CN_ROUTE));
        }
        catch (NullPointerException e){
            Log.e("ERROR ", e.getMessage());
            callbackModel.onError(this,"error");
        }
    }
}
