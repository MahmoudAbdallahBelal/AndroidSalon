package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Timeline_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_objet")
    @Expose
    private String idObjet;
    @SerializedName("type_object")
    @Expose
    private String typeObject;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("visible")
    @Expose
    private String visible;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("cover")
    @Expose
    private List<Cover> cover = null;
    @SerializedName("promotion")
    @Expose
    private List<Promotion> promotion = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdObjet() {
        return idObjet;
    }

    public void setIdObjet(String idObjet) {
        this.idObjet = idObjet;
    }

    public String getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(String typeObject) {
        this.typeObject = typeObject;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Cover> getCover() {
        return cover;
    }

    public void setCover(List<Cover> cover) {
        this.cover = cover;
    }

    public List<Promotion> getPromotion() {
        return promotion;
    }

    public void setPromotion(List<Promotion> promotion) {
        this.promotion = promotion;
    }

}
