package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import salon.octadevtn.sa.salon.Models.Activity.ListTimeline;

public class Timeline {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("listTimeline")
    @Expose
    private List<ListTimeline> timeline = null;
    @SerializedName("countfollowing")
    @Expose
    private Integer countfollowing;
    @SerializedName("countfollowers")
    @Expose
    private Integer countfollowers;
    @SerializedName("isFolow")
    @Expose
    private String isFolow;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ListTimeline> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<ListTimeline> timeline) {
        this.timeline = timeline;
    }

    public Integer getCountfollowing() {
        return countfollowing;
    }

    public void setCountfollowing(Integer countfollowing) {
        this.countfollowing = countfollowing;
    }

    public Integer getCountfollowers() {
        return countfollowers;
    }

    public void setCountfollowers(Integer countfollowers) {
        this.countfollowers = countfollowers;
    }

    public String getIsFolow() {
        return isFolow;
    }

    public void setIsFolow(String isFolow) {
        this.isFolow = isFolow;
    }

}
