package tn.esprit.Entity;

public class Transport {
private int IDTr;
private String Brand;
private String Type;
private int Distance;
private int ChargingTime;
private boolean available;
private int IDtourist;
    public Transport(String brand, String type, int distance, int chargingTime, int touristID) {
        this.Brand = brand;
        this.Type = type;
        this.Distance = distance;
        this.ChargingTime = chargingTime;
        this.IDtourist = touristID;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "Brand='" + Brand + '\'' +
                ", Type='" + Type + '\'' +
                ", distance=" + Distance +
                ", chargingTime=" + ChargingTime +
                ", touristID=" + IDtourist +
                '}';
    }

    public int getIDTr() {
        return IDTr;
    }

    public void setIDTr(int IDTr) {
        this.IDTr = IDTr;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        this.Distance = distance;
    }

    public int getChargingTime() {
        return ChargingTime;
    }

    public void setChargingTime(int chargingTime) {
        this.ChargingTime = chargingTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getTouristID() {
        return IDtourist;
    }

    public void setTouristID(int touristID) {
        this.IDtourist = touristID;
    }


}
