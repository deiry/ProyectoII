package com.udea.pi2.carapp.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callback.CallbackModel;

@Table(name = "tbl_car")
public class State extends Model{

    final static public String ST_CN_NAME = "name";
    final static public String ST_CN_ID = "id";


    @Column(name = ST_CN_NAME, notNull = true, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public State(){

    }

    public State(String activo) {
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
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ST_CN_NAME, this.name);
        return map;
    }

    @Override
    public void mapToModel(CallbackModel callbackModel, Map<String, Object> mapRequest) {

        HashMap<String, Object> map = (HashMap<String, Object>) mapRequest;
        this.setName((String) map.get(ST_CN_NAME));

        callbackModel.onSuccess(this);
    }
}
