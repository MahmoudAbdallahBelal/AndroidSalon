package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marwen octadev on 7/8/2017.
 */

public class Favorite {
    @SerializedName("object")
    @Expose
    private salon.octadevtn.sa.salon.Models.Object object;
    @SerializedName("salon")
    @Expose
    private Salon salon;

    public salon.octadevtn.sa.salon.Models.Object getObject() {
        return object;
    }

    public void setObject(salon.octadevtn.sa.salon.Models.Object object) {
        this.object = object;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

}
