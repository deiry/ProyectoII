package model;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

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
    private String departureTime;

    @Column(name = ROU_CN_ARRIVAL_TIME, notNull = true)
    private double arrivalTime;

    @Column(name = ROU_CN_STATE, notNull = true)
    private State state;

    @Column(name = ROU_CN_CAR, notNull = true)
    private Car car;

    /*constructors*/
    public Route() {
    }

    public Route(double departureLat, double departureLng, double arrivalLat, double arrivalLng,
                 double price, String departureTime, double arrivalTime, State state, Car car) {
        this.departureLat = departureLat;
        this.departureLng = departureLng;
        this.arrivalLat = arrivalLat;
        this.arrivalLng = arrivalLng;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.state = state;
        this.car = car;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
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

    @Override
    public List<JSONObject> modelToJSON(){
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }
}
