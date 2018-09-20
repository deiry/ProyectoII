package model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@Table(name = "tbl_car")
public class State extends Model{

    final static public String ST_CN_NAME = "name";

    @Column(name = ST_CN_NAME, notNull = true, unique = true)
    private String name;

    public State() {
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
