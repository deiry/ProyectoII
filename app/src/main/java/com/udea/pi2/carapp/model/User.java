package com.udea.pi2.carapp.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callback.CallbackModel;

public class User extends Model{

    final static public String USR_CN_NAME = "name";
    final static public String USR_CN_EMAIL = "email";
    final static public String USR_CN_TOKEN = "token";

    //private String id;
    private String name;
    private String email;
    private String token;

    public User() {
    }

    public User(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<JSONObject> modelToJSON(){
        List<JSONObject> list = new ArrayList<>();
        JSONObject jsonClass = new JSONObject();
        try{
            jsonClass.put(CLASS_NAME,this.getClass().getSimpleName());
            jsonClass.put(MODEL,this);
        }catch (JSONException e){
            Log.e("ErrorJSON",e.getMessage());
        }

        list.add(jsonClass);

        return list;
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

            }
        });
    }

    static public void findById(CallbackModel callbackModel,String id){
        Model.singleRecord(callbackModel,"id",id,"User");
    }

    static public void findByEmail(CallbackModel callbackModel,String email){
        Model.singleRecord(callbackModel,"email",email,"User");
    }

    static public void findAll(CallbackModel callbackModel){
        Model.multiRecord(callbackModel,"",null,User.class.getSimpleName());
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(USR_CN_NAME, this.name);
        map.put(USR_CN_EMAIL, this.email);
        map.put(USR_CN_TOKEN, this.token);
        return map;
    }

    @Override
    public void mapToModel(CallbackModel callbackModel, Map<String, Object> mapRequest) {

        HashMap<String, Object> map = (HashMap<String, Object>) mapRequest;
        this.setName((String) map.get(USR_CN_NAME));
        this.setEmail((String) map.get(USR_CN_EMAIL));
        this.setToken((String) map.get(USR_CN_TOKEN));

        callbackModel.onSuccess(this);
    }
}
