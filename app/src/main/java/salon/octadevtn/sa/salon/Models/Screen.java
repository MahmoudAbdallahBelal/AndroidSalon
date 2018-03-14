package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Octadev on 7/17/2017.
 */

public class Screen {

    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("title_ar")
    @Expose
    private String titleAr;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("description_ar")
    @Expose
    private String descriptionAr;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

}


