package salon.octadevtn.sa.salon.fragment.profileUpdate.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategorySalon {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
