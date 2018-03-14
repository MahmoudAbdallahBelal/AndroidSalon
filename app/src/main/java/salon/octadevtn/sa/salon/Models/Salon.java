package salon.octadevtn.sa.salon.Models;

import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;

public class Salon {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("apiKey")
    @Expose
    private String apiKey;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("salon_name")
    @Expose
    private String salonName;
    @SerializedName("username")
    @Expose
    private String Username;
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
    private double latitude;
    @SerializedName("longutide")
    @Expose
    private double longutide;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("cover")
    @Expose
    private List<Cover> cover;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("payment")
    @Expose
    private List<Payment_> payment = null;
    @SerializedName("work")
    @Expose
    private List<WordDayHour> work = null;
    @SerializedName("branches")
    @Expose
    private String branches;
    @SerializedName("isFlow")
    @Expose
    private String isFlow;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Cover> getCover() {
        return cover;
    }

    public void setCover(List<Cover> cover) {
        this.cover = cover;
    }

    public String getIsFlow() {
        return isFlow;
    }

    public void setIsFlow(String isFlow) {
        this.isFlow = isFlow;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String salonUsername) {
        this.Username = salonUsername;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongutide() {
        return longutide;
    }

    public void setLongutide(double longutide) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Payment_> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment_> payment) {
        this.payment = payment;
    }

    public List<WordDayHour> getWork() {
        return work;
    }

    public void setWork(List<WordDayHour> work) {
        this.work = work;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public Salon getSalonInfo() {
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getInstance(), Static.shared_name);
        Profile profile = null;
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        return profile.getSalon();


    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
