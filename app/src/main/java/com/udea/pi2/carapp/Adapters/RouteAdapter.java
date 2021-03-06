package com.udea.pi2.carapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udea.pi2.carapp.R;
import com.udea.pi2.carapp.model.Route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private ArrayList<Route> routes;
    private ViewGroup parent;

    public RouteAdapter(ArrayList<Route> routes) {
        this.routes = routes;
    }

    @NonNull
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_own_route,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RouteAdapter.ViewHolder holder, int position) {
        Route route = routes.get(position);
        //set lat and lng
        holder.tv_from.setText(route.getDepartureDir());
        holder.tv_to.setText(route.getArriveDir());
        //set Dates
        //Date dateArrived = new Date((long)route.getArrivalTime());
        //DateFormat df = new SimpleDateFormat("h:mm a");

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy H:mm a");
        Date date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((long) route.getArrivalTime());
        date = calendar.getTime();
        String strDate = format.format(date);

        holder.tv_arrive_date.setText(strDate);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis((long)route.getDepartureTime());
        date = calendar2.getTime();
        strDate = format.format(date);



        Date dateDeparture = new Date((long)route.getDepartureTime());
        holder.tv_deperture_date.setText(strDate);

    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_from;
        public TextView tv_to;
        public TextView tv_deperture_date;
        public TextView tv_arrive_date;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_from = itemView.findViewById(R.id.item_own_route_from);
            tv_to = itemView.findViewById(R.id.item_own_route_to);
            tv_arrive_date = itemView.findViewById(R.id.item_own_route_hour_arrive);
            tv_deperture_date = itemView.findViewById(R.id.item_own_route_hour_run);
        }
    }
}
