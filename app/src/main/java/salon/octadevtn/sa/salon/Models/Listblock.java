package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Listblock {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_block")
    @Expose
    private String idBlock;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getIdBlock() {
        return idBlock;
    }

    public void setIdBlock(String idBlock) {
        this.idBlock = idBlock;
    }

}
