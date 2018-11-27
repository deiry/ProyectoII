package com.udea.pi2.carapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udea.pi2.carapp.R;

import java.util.ArrayList;

import com.udea.pi2.carapp.model.Car;

import callback.CallbackRecyclerCar;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private ArrayList<Car> cars;
    private ViewGroup parent;
    private CallbackRecyclerCar callback = null;

    public CarAdapter(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public CarAdapter(ArrayList<Car> cars, CallbackRecyclerCar callback) {
        this.cars = cars;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_car,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, final int position) {
        final Car car = cars.get(position);
        holder.tv_name.setText(car.getName());
        holder.tv_plate.setText(car.getPlaque());
        holder.tv_color.setText(car.getBrand());

        if (callback != null){
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClickItem(cars.get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_plate;
        public TextView tv_color;
        public LinearLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.item_car_name);
            tv_plate = itemView.findViewById(R.id.item_car_placa);
            tv_color = itemView.findViewById(R.id.item_car_color);
            parent = itemView.findViewById(R.id.ly_parent_item_car);
        }
    }
}
