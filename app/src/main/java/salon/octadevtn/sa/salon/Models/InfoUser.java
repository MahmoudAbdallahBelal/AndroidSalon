package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Octadev on 7/17/2017.
 */

public class InfoUser {
    @SerializedName("First_Run")
    @Expose
    boolean First_Run;

    @SerializedName("Hidden_male")
    @Expose
    boolean Hidden_Male;
    @SerializedName("Hidden_Female")
    @Expose
    boolean Hidden_Female;

    @SerializedName("sound_notification")
    @Expose
    boolean sound_notification;

    @SerializedName("lang")
    @Expose
    int lang;
    @SerializedName("currency")
    @Expose
    int currency;
    @SerializedName("maps_searsh")
    @Expose
    double maps_searsh;

    public InfoUser() {
        this.First_Run = true;
        this.currency = 0;
        this.lang = 0;
        this.maps_searsh = 10;
        this.sound_notification = true;
        this.Hidden_Female = false;
        this.Hidden_Male = false;
    }

    public boolean isFirst_Run() {
        return First_Run;
    }

    public void setFirst_Run(boolean first_Run) {
        First_Run = first_Run;
    }

    public boolean isSound_notification() {
        return sound_notification;
    }

    public void setSound_notification(boolean sound_notification) {
        this.sound_notification = sound_notification;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public double getMaps_searsh() {
        return maps_searsh;
    }

    public void setMaps_searsh(double maps_searsh) {
        this.maps_searsh = maps_searsh;
    }

    public boolean isHidden_Male() {
        return Hidden_Male;
    }

    public void setHidden_Male(boolean hidden_Male) {
        Hidden_Male = hidden_Male;
    }

    public boolean isHidden_Female() {
        return Hidden_Female;
    }

    public void setHidden_Female(boolean hidden_Female) {
        Hidden_Female = hidden_Female;
    }
}
