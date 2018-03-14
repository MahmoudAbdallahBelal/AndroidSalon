package salon.octadevtn.sa.salon.Models;

/**
 * Created by Marwen octadev on 8/1/2017.
 */

public class Event {
    int dd, mm, yy, nbbadge;

    public Event(int dd, int mm, int yy, int nbbadge) {
        this.dd = dd;
        this.mm = mm;
        this.yy = yy;
        this.nbbadge = nbbadge;
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

}
