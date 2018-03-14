package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalonTypes {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("salon_types")
    @Expose
    private List<SalonType> salonTypes = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SalonType> getSalonTypes() {
        return salonTypes;
    }

    public void setSalonTypes(List<SalonType> salonTypes) {
        this.salonTypes = salonTypes;
    }

}
