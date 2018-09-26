package model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import callback.CallbackModel;

@Table(name = "tbl_route_passenger")
public class RoutePassenger extends Model {

    final static public String RP_CN_PASSENGER = "user";
    final static public String RP_CN_ROUTE = "route";

    @Column(name = RP_CN_PASSENGER)
    private User user;

    @Column(name = RP_CN_ROUTE)
    private Route route;

    /* constructors */
    public RoutePassenger() {
    }

    public RoutePassenger(User user, Route route) {
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

    @Override
    public List<JSONObject> modelToJSON(){
        return null;
    }

    @Override
    public void save(final CallbackModel callbackModel) {
        this.user.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                user.setId((String) id);
                saveRoute(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {

            }
        });
    }

    private void saveRoute(final CallbackModel callbackModel){
        route.save(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                route.setId((String) id);
                saveRoutePassenger(callbackModel);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model,message);
            }
        });
    }

    private void saveRoutePassenger(final CallbackModel callbackModel){
        this.saveModel(new CallbackModel() {
            @Override
            public void onSuccess(Object id) {
                setId((String) id);
                callbackModel.onSuccess(id);
            }

            @Override
            public void onError(Object model, String message) {
                callbackModel.onError(model, message);
            }
        });
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(RP_CN_PASSENGER, this.user.getId());
        map.put(RP_CN_ROUTE, this.route.getId());
        return map;
    }

    @Override
    public void mapToModel(CallbackModel callbackModel, Map<String, Object> mapRequest) {
    }
}
