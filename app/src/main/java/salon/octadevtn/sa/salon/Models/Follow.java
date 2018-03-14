package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class Follow {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_flow")
    @Expose
    private String idFlow;
    @SerializedName("isFolow")
    @Expose
    private String isFolow;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getIdFlow() {
        return idFlow;
    }

    public void setIdFlow(String idFlow) {
        this.idFlow = idFlow;
    }

    public Boolean getIsFolow() {
        return isFolow.equals("true");
    }

    public void setIsFolow(String isFolow) {
        this.isFolow = isFolow;
    }

}
