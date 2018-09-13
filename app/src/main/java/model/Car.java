package model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "tbl_car")
public class Car extends Model{
    final static public String CAR_CN_NAME = "name";
    final static public String CAR_CN_COLOR = "color";
    final static public String CAR_CN_PLAQUE = "plaque";
    final static public String CAR_CN_DRIVER = "driver";
    final static public String CAR_CN_PASSENGER_NUM = "passengerNum";
    final static public String CAR_CN_BRAND = "brand";
    final static public String CAR_CN_MODEL = "model";

    @Column(name = CAR_CN_NAME)
    private String name;

    @Column(name = CAR_CN_COLOR)
    private String color;

    @Column(name = CAR_CN_PLAQUE, unique = true)
    private String plaque;

    @Column(name = CAR_CN_DRIVER)
    private User driver;

    @Column(name = CAR_CN_PASSENGER_NUM, notNull = true)
    private int passegerNum;

    @Column(name = CAR_CN_BRAND)
    private String brand;

    @Column(name = CAR_CN_MODEL)
    private String model;

    /*constructors*/
    public Car() {
    }

    /* getters and setters methods*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public int getPassegerNum() {
        return passegerNum;
    }

    public void setPassegerNum(int passegerNum) {
        this.passegerNum = passegerNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
