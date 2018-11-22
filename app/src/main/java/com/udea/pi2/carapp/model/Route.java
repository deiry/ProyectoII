package com.udea.pi2.carapp.model;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callback.CallbackModel;

@Table(name = "tbl_route")
public class Route extends Model{
    final static public String ROU_CN_DEPARTURE_LAT = "departureLat";
    final static public String ROU_CN_DEPARTURE_LNG = "departureLng";
    final static public String ROU_CN_ARRIVAL_LAT = "arrivalLat";
    final static public String ROU_CN_ARRIVAL_LNG = "arrivalLng";
    final static public String ROU_CN_PRICE = "price";
    final static public String ROU_CN_DEPARTURE_TIME = "departureTime";
    final static public String ROU_CN_ARRIVAL_TIME = "arrivalTime";
    final static public String ROU_CN_STATE = "state";
    final static public String ROU_CN_CAR = "car";
    final static public String ROU_CN_OWNER = "owner";

    @Column(name = ROU_CN_DEPARTURE_LAT, notNull = true)
    private double departureLat;

    @Column(name = ROU_CN_DEPARTURE_LNG, notNull = true)
    private double departureLng;

    @Column(name = ROU_CN_ARRIVAL_LAT, notNull = true)
    private double arrivalLat;

    @Column(name = ROU_CN_ARRIVAL_LNG, notNull = true)
    private double arrivalLng;

    @Column(name = ROU_CN_PRICE)
    private double price;

    @Column(name = ROU_CN_DEPARTURE_TIME, notNull = true)
    private double departureTime;

    @Column(name = ROU_CN_ARRIVAL_TIME, notNull = true)
    private double arrivalTime;

    @Column(name = ROU_CN_STATE, notNull = true)
    private State state;

    @Column(name = ROU_CN_CAR, notNull = true)
    private Car car;

    @Column(name = ROU_CN_OWNER, notNull = true)
    private User owner;


    /*constructors*/
    public Route() {
    }

    public Route(double departureLat, double departureLng, double arrivalLat, double arrivalLng,
                 double price, double departureTime, double arrivalTime, State state, Car car, User owner) {
        this.departureLat = departureLat;
        this.departureLng = departureLng;
        this.arrivalLat = arrivalLat;
        this.arrivalLng = arrivalLng;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.state = state;
        this.car = car;
        this.owner = owner;
    }

    /* getters and setters methods*/
    public double getDepartureLat() {
        return departureLat;
    }

    public void setDepartureLat(double departureLat) {
        this.departureLat = departureLat;
    }

    public double getDepartureLng() {
        return departureLng;
    }

    public void setDepartureLng(double departureLng) {
        this.departureLng = departureLng;
    }

    public double getArrivalLat() {
        return arrivalLat;
    }

    public void setArrivalLat(double arrivalLat) {
        this.arrivalLat = arrivalLat;
    }

    public double getArrivalLng() {
        return arrivalLng;
    }

    public void setArrivalLng(double arrivalLng) {
        this.arrivalLng = arrivalLng;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public List<JSONObject> modelToJSON(){
        return null;
    }

    @Override
    public void save(final CallbackModel callbackModel) {
        this.saveModel(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                setId((String) id);
                callbackModel.onSuccess(id);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        });
        /*this.car.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                car.setId((String) id);
                saveState(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        });*/
    }

    private void saveState(final CallbackModel callbackModel){
        this.state.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                state.setId((String)id);
                saveOwner(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model, message);
            }
        });
    }

    private void saveOwner(final CallbackModel callbackModel){
        this.owner.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                owner.setId((String)id);
                saveRoute(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model, message);
            }
        });
    }

    private void saveRoute(final CallbackModel callbackModel){
        this.saveModel(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                setId((String) id);
                callbackModel.onSuccess(id);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        });
    }

    static public void findSelfRoutes(CallbackModel callbackModel,String id){
        Model.multiRecord(callbackModel,ROU_CN_OWNER,id,Route.class.getSimpleName());
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ROU_CN_ARRIVAL_LAT, this.arrivalLat);
        map.put(ROU_CN_ARRIVAL_LNG, this.arrivalLng);
        map.put(ROU_CN_ARRIVAL_TIME, this.arrivalTime);
        map.put(ROU_CN_DEPARTURE_LAT, this.departureLat);
        map.put(ROU_CN_DEPARTURE_LNG, this.departureLng);
        map.put(ROU_CN_DEPARTURE_TIME, this.departureTime);
        map.put(ROU_CN_CAR, this.car.getId());
        map.put(ROU_CN_STATE, this.state.getId());
        map.put(ROU_CN_PRICE, this.price);
        map.put(ROU_CN_OWNER, this.owner.getId());


        return map;
    }

    @Override
    public void mapToModel(final CallbackModel callbackModel, Map<String, Object> mapRequest) {
        final HashMap<String, Object> map = (HashMap<String, Object>) mapRequest;
        this.setArrivalLat((Double) map.get(ROU_CN_ARRIVAL_LAT));
        this.setArrivalLng((Double) map.get(ROU_CN_ARRIVAL_LNG));
        this.setArrivalTime((Double) map.get(ROU_CN_ARRIVAL_TIME));
        this.setDepartureLat((Double) map.get(ROU_CN_DEPARTURE_LAT));
        this.setDepartureLng((Double) map.get(ROU_CN_DEPARTURE_LNG));
        this.setDepartureTime((Double) map.get(ROU_CN_DEPARTURE_TIME));
        this.setPrice((Double) map.get(ROU_CN_PRICE));
        Car.findById(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                setCar((Car) id);
                //callbackModel.onSuccess(getThis());
                User.findById(new CallbackModel() {
                    @Override
                    public void onSuccess(Object id) {
                        setOwner((User) id);
                        State.findById(new CallbackModel() {
                            @Override
                            public void onSuccess(Object id) {
                                setState((State) id);
                                callbackModel.onSuccess(getThis());
                            }

                            @Override
                            public void onError(Object model, String message) {
                                callbackModel.onError(model,message);
                            }
                        },(String) map.get(ROU_CN_STATE),State.class.getSimpleName());
                        //callbackModel.onSuccess(getThis());
                    }

                    @Override
                    public void onError(Object model, String message) {
                        callbackModel.onError(model,message);
                    }
                },(String) map.get(ROU_CN_OWNER), User.class.getSimpleName());
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        },(String) map.get(ROU_CN_CAR),Car.class.getSimpleName());

    }

    public Route getThis(){
        return this;
    }
}
