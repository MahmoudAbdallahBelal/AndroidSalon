package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Salons {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("salon_name")
    @Expose
    private String salonName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("salon_type")
    @Expose
    private String salonType;
    @SerializedName("since_from")
    @Expose
    private String sinceFrom;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longutide")
    @Expose
    private Integer longutide;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("work")
    @Expose
    private Work work;
    @SerializedName("branches")
    @Expose
    private String branches;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalonType() {
        return salonType;
    }

    public void setSalonType(String salonType) {
        this.salonType = salonType;
    }

    public String getSinceFrom() {
        return sinceFrom;
    }

    public void setSinceFrom(String sinceFrom) {
        this.sinceFrom = sinceFrom;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongutide() {
        return longutide;
    }

    public void setLongutide(Integer longutide) {
        this.longutide = longutide;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

}
