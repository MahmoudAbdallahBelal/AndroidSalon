package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import salon.octadevtn.sa.salon.Utils.MyApplication;

public class WordDayHour {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_ar")
    @Expose
    private String nameAr;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("start_hour")
    @Expose
    private String startHour;
    @SerializedName("end_hours")
    @Expose
    private String endHours;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
            return nameAr;
        else
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHours() {
        return endHours;
    }

    public void setEndHours(String endHours) {
        this.endHours = endHours;
    }

}
