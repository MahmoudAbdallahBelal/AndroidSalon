package salon.octadevtn.sa.salon.Models;

/**
 * Created by Octadev on 7/27/2017.
 */

public class Photo {
    String url, type;
    int id;

    public Photo(String url, String type, Integer id) {
        this.url = url;
        this.type = type;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
