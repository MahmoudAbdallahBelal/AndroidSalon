package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {

    @SerializedName("country")
    @Expose
    private Country1 country;
    @SerializedName("city")
    @Expose
    private List<City> city = null;

    public Country1 getCountry() {
        return country;
    }

    public void setCountry(Country1 country) {
        this.country = country;
    }

    public List<City> getCity() {
        return city;
    }


    public void setCity(List<City> city) {
        this.city = city;
    }

}
