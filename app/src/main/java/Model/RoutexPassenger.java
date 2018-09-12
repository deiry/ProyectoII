package Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tbl_route_passenger")
public class RoutexPassenger extends Model {

    final static public String RP_CN_PASSENGER = "passenger";
    final static public String RP_CN_ROUTE = "route";

    @Column(name = RP_CN_PASSENGER)
    private Passenger passenger;

    @Column(name = RP_CN_ROUTE)
    private Route route;

    public RoutexPassenger(Passenger passenger, Route route) {
        this.passenger = passenger;
        this.route = route;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
