package model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User extends Model{

    final static public String USR_CN_NAME = "name";
    final static public String USR_CN_EMAIL = "email";
    final static public String USR_CN_TOKEN = "token";


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
            jsonClass.put(ID,getHashCode());
            jsonClass.put(MODEL,this);
        }catch (JSONException e){
            Log.e("ErrorJSON",e.getMessage());
        }

        list.add(jsonClass);

        return list;
    }
}
