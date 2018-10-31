package adapter;

import com.udea.pi2.carapp.model.Car;

public class CarAdapter {

    public String name;

    public String color;

    public String plaque;

    public String hasCode;

    public String driver;

    public int passegerNum;

    public String brand;

    public String model;



    public CarAdapter(){}

    public CarAdapter(Car car){
        this.name = car.getName();
        this.color = car.getColor();
        this.plaque = car.getPlaque();
        this.passegerNum = car.getPassegerNum();
        this.brand = car.getBrand();
        this.model = car.getModel();
    }

    public Car CarAdaptertoCar(){
        Car car =new Car();
        return car;
    }

}
