package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("count")
    @Expose
    private String count;

    public String getStartDate() {
        return date;
    }

    public void setStartDate(String startDate) {
        this.date = startDate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
