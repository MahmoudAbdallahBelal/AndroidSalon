package salon.octadevtn.sa.salon.Models;

import android.graphics.Bitmap;

/**
 * Created by Octadev on 8/8/2017.
 */

public class CoverModel {
    boolean status;
    Bitmap bitmap;
    Cover cover;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}
