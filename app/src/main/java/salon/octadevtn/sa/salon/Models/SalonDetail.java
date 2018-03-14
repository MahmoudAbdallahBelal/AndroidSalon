package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalonDetail {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("salons")
    @Expose
    private Salons salons;
    @SerializedName("Services")
    @Expose
    private List<Service> services = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("promotion")
    @Expose
    private List<PromotionProfileDetail> promotion = null;
    @SerializedName("folowing")
    @Expose
    private Integer folowing;
    @SerializedName("folowingBySalon")
    @Expose
    private Integer folowingBySalon;
    @SerializedName("rating")
    @Expose
    private Double rating;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Salons getSalons() {
        return salons;
    }

    public void setSalons(Salons salons) {
        this.salons = salons;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<PromotionProfileDetail> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<PromotionProfileDetail> promotion) {
        this.promotion = promotion;
    }

    public Integer getFolowing() {
        return folowing;
    }

    public void setFolowing(Integer folowing) {
        this.folowing = folowing;
    }

    public Integer getFolowingBySalon() {
        return folowingBySalon;
    }

    public void setFolowingBySalon(Integer folowingBySalon) {
        this.folowingBySalon = folowingBySalon;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
