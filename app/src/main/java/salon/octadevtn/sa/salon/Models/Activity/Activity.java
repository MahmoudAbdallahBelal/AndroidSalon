package salon.octadevtn.sa.salon.Models.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Activity {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("listTimeline")
    @Expose
    private List<ListTimeline> listTimeline = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ListTimeline> getListTimeline() {
        return listTimeline;
    }

    public void setListTimeline(List<ListTimeline> listTimeline) {
        this.listTimeline = listTimeline;
    }

}
