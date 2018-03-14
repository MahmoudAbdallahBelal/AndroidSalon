package salon.octadevtn.sa.salon.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SalonProfile implements Serializable, Parcelable {

    public static final Creator<SalonProfile> CREATOR = new Creator<SalonProfile>() {
        @Override
        public SalonProfile createFromParcel(Parcel in) {
            return new SalonProfile(in);
        }

        @Override
        public SalonProfile[] newArray(int size) {
            return new SalonProfile[size];
        }
    };
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("salon")
    @Expose
    private Salon salon;
    @SerializedName("ServicesCategory")
    @Expose
    private List<ServicesCategory> servicesCategory = null;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("promotion")
    @Expose
    private List<PromotionProfileDetail> promotion = null;
    @SerializedName("folowing")
    @Expose
    private Integer folowing;
    @SerializedName("folowers")
    @Expose
    private Integer folowers;
    @SerializedName("isFolow")
    @Expose
    private String isFolow;
    @SerializedName("rating")
    @Expose
    private String rating;

    protected SalonProfile(Parcel in) {
        error = in.readString();
        isFolow = in.readString();
        rating = in.readString();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
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

    public Integer getFolowers() {
        return folowers;
    }

    public void setFolowers(Integer folowers) {
        this.folowers = folowers;
    }

    public Boolean getIsFolow() {
        return isFolow.equals("true");
    }

    public void setIsFolow(String isFolow) {
        this.isFolow = isFolow;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<ServicesCategory> getServicesCategory() {
        return servicesCategory;
    }

    public void setServicesCategory(List<ServicesCategory> servicesCategory) {
        this.servicesCategory = servicesCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
        dest.writeString(isFolow);
        dest.writeString(rating);
    }
}
