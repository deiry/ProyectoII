package model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tbl_route_passenger")
public class Passenger extends Model {

    final static public String RP_CN_PASSENGER = "user";
    final static public String RP_CN_ROUTE = "route";

    @Column(name = RP_CN_PASSENGER)
    private User user;

    @Column(name = RP_CN_ROUTE)
    private Route route;

    /* constructors */
    public Passenger() {
    }

    public Passenger(User user, Route route) {
        this.user = user;
        this.route = route;
    }

    /* getters and setters methods*/
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}