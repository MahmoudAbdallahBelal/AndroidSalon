package salon.octadevtn.sa.salon.Models.Activity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import salon.octadevtn.sa.salon.Models.Reservation.Task;
import salon.octadevtn.sa.salon.Models.Salon;

public class ListTimeline implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("message")
    @Expose

    private String message;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("reservation")
    @Expose
    private Task reservation;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("object")
    @Expose
    private List<Object> object = null;
    @SerializedName("salon")
    @Expose
    private Salon salon;
    @SerializedName("flow")
    @Expose
    private Flow flow;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Task getReservation() {
        return reservation;
    }

    public void setReservation(Task reservation) {
        this.reservation = reservation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Object> getObject() {
        return object;
    }

    public void setObject(List<Object> object) {
        this.object = object;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Flow getFlow() {
        return flow;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
