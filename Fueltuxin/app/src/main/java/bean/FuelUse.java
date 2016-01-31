package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-01-19.
 *
 * oil use model
 */
public class FuelUse implements Serializable{

    private  int id;

    private String date ;

    private String stationName;

    private float odometer;

    private String fuelGrade;

    private float fuelAmount;

    private float fuelUnitCost;

    private String fuelCost;

    private String fileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    public float getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(float fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public float getFuelUnitCost() {
        return fuelUnitCost;
    }

    public void setFuelUnitCost(float fuelUnitCost) {
        this.fuelUnitCost = fuelUnitCost;
    }

    public String getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(String fuelCost) {
        this.fuelCost = fuelCost;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FuelUse{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", stationName='" + stationName + '\'' +
                ", odometer=" + odometer +
                ", fuelGrade='" + fuelGrade + '\'' +
                ", fuelAmount=" + fuelAmount +
                ", fuelUnitCost=" + fuelUnitCost +
                ", fuelCost='" + fuelCost + '\'' +
                '}';
    }
}
