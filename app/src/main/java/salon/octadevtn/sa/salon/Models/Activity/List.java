package salon.octadevtn.sa.salon.Models.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import salon.octadevtn.sa.salon.Models.Salon;

public class List {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("object")
    @Expose
    private java.util.List<Object> object = null;
    @SerializedName("salon")
    @Expose
    private Salon salon;


    @SerializedName("flow")
    @Expose
    private User userFlow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.util.List<Object> getObject() {
        return object;
    }

    public void setObject(java.util.List<Object> object) {
        this.object = object;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public User getUserFlow() {
        return userFlow;
    }

    public void setUserFlow(User userFlow) {
        this.userFlow = userFlow;
    }

}
