package salon.octadevtn.sa.salon.Models;

/**
 * Created by Marwen octadev on 7/5/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("salon")
    @Expose
    private Salon salon;
    @SerializedName("Type")
    @Expose
    private String type;

    public Salon getSalon() {
        return salon;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSalon() {
        return type.equals("salon");
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}
