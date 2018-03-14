package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment_ {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
