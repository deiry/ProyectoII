package Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

    @Column(name = ROU_CN_DEPARTURE_LAT)
    private double departureLat;

    @Column(name = ROU_CN_DEPARTURE_LNG)
    private double departureLng;

    @Column(name = ROU_CN_ARRIVAL_LAT)
    private double arrivalLat;

    @Column(name = ROU_CN_ARRIVAL_LNG)
    private double arrivalLng;
}
