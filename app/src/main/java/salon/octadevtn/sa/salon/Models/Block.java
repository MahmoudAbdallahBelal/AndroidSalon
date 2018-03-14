package salon.octadevtn.sa.salon.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Block {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("listblock")
    @Expose
    private List<Listblock> listblock = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Listblock> getListblock() {
        return listblock;
    }

    public void setListblock(List<Listblock> listblock) {
        this.listblock = listblock;
    }

}
