package salon.octadevtn.sa.salon.Models;

/**
 * Created by Marwen octadev on 8/1/2017.
 */

public class DayMounth {
    int dd, mm, yy, nbbadge;
    String type;
    Boolean Badge;

    public DayMounth(int dd, int mm, int yy, String type, Boolean badge, int nbbadge) {
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
        this.nbbadge = nbbadge;
        this.type = type;
        Badge = badge;
    }

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getYy() {
        return yy;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }

    public int getNbbadge() {
        return nbbadge;
    }

    public void setNbbadge(int nbbadge) {
        this.nbbadge = nbbadge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getBadge() {
        return Badge;
    }

    public void setBadge(Boolean badge) {
        Badge = badge;
    }
}
