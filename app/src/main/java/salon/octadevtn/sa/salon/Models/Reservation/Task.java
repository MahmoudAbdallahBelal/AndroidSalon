package salon.octadevtn.sa.salon.Models.Reservation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import salon.octadevtn.sa.salon.Models.Promotion;
import salon.octadevtn.sa.salon.Models.User;

public class Task {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("date")
    @Expose
    private String Date;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("etat")
    @Expose
    private String etat;
    @SerializedName("cause_salon")
    @Expose
    private String causeSalon;
    @SerializedName("cause_custemer")
    @Expose
    private String causeCustemer;
    @SerializedName("id_custemer")
    @Expose
    private String idCustemer;
    @SerializedName("id_salon")
    @Expose
    private String idSalon;
    @SerializedName("id_promotion")
    @Expose
    private String idPromotion;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("promotion")
    @Expose
    private Promotion promotion;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getCauseSalon() {
        return causeSalon;
    }

    public void setCauseSalon(String causeSalon) {
        this.causeSalon = causeSalon;
    }

    public String getCauseCustemer() {
        return causeCustemer;
    }

    public void setCauseCustemer(String causeCustemer) {
        this.causeCustemer = causeCustemer;
    }

    public String getIdCustemer() {
        return idCustemer;
    }

    public void setIdCustemer(String idCustemer) {
        this.idCustemer = idCustemer;
    }

    public String getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(String idSalon) {
        this.idSalon = idSalon;
    }

    public String getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(String idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

}
