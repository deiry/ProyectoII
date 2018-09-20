package model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Model{

    final static public String USR_CN_NAME = "name";
    final static public String USR_CN_EMAIL = "email";
    final static public String USR_CN_TOKEN = "token";

    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public void save() {

    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(USR_CN_NAME, this.name);
        map.put(USR_CN_EMAIL, this.email);
        map.put(USR_CN_TOKEN, this.token);
        return map;
    }
}
