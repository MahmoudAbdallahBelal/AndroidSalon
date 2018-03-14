package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopular {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("mostpopulair")
    @Expose
    private List<Mostpopulair> mostpopulair = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Mostpopulair> getMostpopulair() {
        return mostpopulair;
    }

    public void setMostpopulair(List<Mostpopulair> mostpopulair) {
        this.mostpopulair = mostpopulair;
    }

}
