package salon.octadevtn.sa.salon.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Octadev on 7/17/2017.
 */

public class MyPreferenceManager {

    private static final String PREF_NAME = "Salon";
    private static final String Work = "Work";
    private static final String payment = "payment";
    private static final String Hidden_male = "Hidden_male";
    private static final String Hidden_Female = "Hidden_Female";
    private static final String sound_notification = "sound_notification";
    private static final String lang = "lang";
    private static final String Currency = "Currency";
    private static final String maps_searsh = "maps_searsh";
    Context _context;
    SharedPreferences pref;
    int PRIVATE_MODE = 0;
    SharedPreferences.Editor editor;


    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public String getWork() {
        return this.pref.getString(Work, "");

    }

    public void setWork(String work) {
        this.editor.putString(Work, work);
        this.editor.commit();

    }

    public String getPayment() {
        return this.pref.getString(payment, "");

    }

    public void setPayment(String pay) {
        this.editor.putString(payment, pay);
        this.editor.commit();

    }

    public String getLang() {
        return this.pref.getString(lang, "8");
    }

    public void setLang(int Lan) {
        this.editor.putString(lang, String.valueOf(Lan));
        this.editor.commit();

    }

    public String getCurrency() {
        return this.pref.getString("Currency", "0");
    }

    public void setCurrency(int Curren) {
        this.editor.putString(Currency, String.valueOf(Curren));
        this.editor.commit();

    }

    public void setmaps_searsh(String maps_sear) {
        this.editor.putString(maps_searsh, maps_sear);
        this.editor.commit();

    }

    public String getmaps_searsh() {
        return this.pref.getString("maps_searsh", "10");
    }

    public Boolean getHidden_Female() {
        return Boolean.valueOf(this.pref.getString("Hidden_Female", "false"));
    }

    public void setHidden_Female(boolean Hidden_Femal) {
        this.editor.putString(Hidden_Female, String.valueOf(Hidden_Femal));
        this.editor.commit();

    }

    public Boolean getHidden_male() {
        return Boolean.valueOf(this.pref.getString(Hidden_male, "false"));
    }

    public void setHidden_male(boolean Hidden_mal) {
        this.editor.putString(Hidden_male, String.valueOf(Hidden_mal));
        this.editor.commit();

    }

    public boolean isSound_notification() {
        return Boolean.parseBoolean(this.pref.getString(sound_notification, "false"));

    }

    public void setSound_notification(boolean sound_notificati) {
        this.editor.putString(sound_notification, String.valueOf(sound_notificati));
        this.editor.commit();

    }

}
