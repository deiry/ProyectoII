package model;

import android.content.Context;
import android.util.Log;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.CarAdapter;
import callback.CallbackModel;

@Table(name = "tbl_car")
public class Car extends Model{
    final static public String CAR_CN_NAME = "name";
    final static public String CAR_CN_COLOR = "color";
    final static public String CAR_CN_PLAQUE = "plaque";
    final static public String CAR_CN_DRIVER = "driver";
    final static public String CAR_CN_PASSENGER_NUM = "passengerNum";
    final static public String CAR_CN_BRAND = "brand";
    final static public String CAR_CN_MODEL = "model";

    @Column(name = CAR_CN_NAME)
    private String name;

    @Column(name = CAR_CN_COLOR)
    private String color;

    @Column(name = CAR_CN_PLAQUE, unique = true)
    private String plaque;

    @Column(name = CAR_CN_DRIVER)
    private User driver;

    @Column(name = CAR_CN_PASSENGER_NUM, notNull = true)
    private int passegerNum;

    @Column(name = CAR_CN_BRAND)
    private String brand;

    @Column(name = CAR_CN_MODEL)
    private String model;

    /*constructors*/
    public Car() {
    }

    public Car(String name, String color, String plaque, User driver,
               int passegerNum, String brand, String model) {
        this.name = name;
        this.color = color;
        this.plaque = plaque;
        this.driver = driver;
        this.passegerNum = passegerNum;
        this.brand = brand;
        this.model = model;
    }

    /* getters and setters methods*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public int getPassegerNum() {
        return passegerNum;
    }

    public void setPassegerNum(int passegerNum) {
        this.passegerNum = passegerNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<JSONObject> modelToJSON(){
        List<JSONObject> list = new ArrayList<>();
        JSONObject jsonClass = new JSONObject();
        CarAdapter carAdapter = new CarAdapter(this);
        try {
            jsonClass.put(CLASS_NAME,this.getClass().getSimpleName());
            jsonClass.put(MODEL,carAdapter);
        }catch (JSONException e){
            Log.e("ErrorJSON",e.getMessage());
        }

        list.add(jsonClass);;

        list.addAll(this.driver.modelToJSON());

        return list;
    }

    @Override
    public void save(final CallbackModel callbackModel) {
        this.driver.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                driver.setId((String) id);
                saveCar(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);

            }
        });
    }

    private void saveCar(final CallbackModel callbackModel){
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

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(CAR_CN_NAME, this.name);
        map.put(CAR_CN_COLOR, this.color);
        map.put(CAR_CN_BRAND, this.brand);
        map.put(CAR_CN_MODEL, this.model);
        map.put(CAR_CN_PASSENGER_NUM, this.passegerNum);
        map.put(CAR_CN_PLAQUE, this.plaque);
        map.put(CAR_CN_DRIVER, this.driver.getId());
        return map;
    }
}
