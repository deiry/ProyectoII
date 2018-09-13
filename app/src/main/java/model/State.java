package model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tbl_car")
public class State extends Model{

    final static public String ST_CN_NAME = "name";

    @Column(name = ST_CN_NAME, notNull = true, unique = true)
    private String name;

    public State() {
    }
}
