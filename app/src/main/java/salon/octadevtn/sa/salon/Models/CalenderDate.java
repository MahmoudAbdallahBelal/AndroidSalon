package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CalenderDate {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("day")
    @Expose
    private List<Day> day = null;
    @SerializedName("promotiondate")
    @Expose
    private List<Promotiondate> promotiondate = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Day> getDay() {
        return day;
    }

    public void setDay(List<Day> day) {
        this.day = day;
    }

    public List<Promotiondate> getPromotiondate() {
        return promotiondate;
    }

    public void setPromotiondate(List<Promotiondate> promotiondate) {
        this.promotiondate = promotiondate;
    }

}
