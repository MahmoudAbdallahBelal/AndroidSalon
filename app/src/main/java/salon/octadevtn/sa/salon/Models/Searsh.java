package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Searsh {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("salons")
    @Expose
    private List<Salon> salons = null;
    @SerializedName("service")
    @Expose
    private List<Service> service = null;
    @SerializedName("promotion")
    @Expose
    private List<Promotion> promotion = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Salon> getSalons() {
        return salons;
    }

    public void setSalons(List<Salon> salons) {
        this.salons = salons;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public List<Promotion> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<Promotion> promotion) {
        this.promotion = promotion;
    }

}