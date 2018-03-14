package salon.octadevtn.sa.salon.Reservation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import salon.octadevtn.sa.salon.EditProfil;
import salon.octadevtn.sa.salon.Login_signup.Sign_up_salon;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

import static salon.octadevtn.sa.salon.Login_signup.Sign_up_salon.is_sign_up;
import static salon.octadevtn.sa.salon.Utils.Static.reserv_date;
import static salon.octadevtn.sa.salon.Utils.Static.sincefrom;

/**
 * Created by Octadev on 7/14/2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme, this, year, month, day);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext().getResources().updateConfiguration(config,
                    getContext().getResources().getDisplayMetrics());
        } else {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext().getResources().updateConfiguration(config,
                    getContext().getResources().getDisplayMetrics());

        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        if (is_sign_up) {
            Sign_up_salon.since_l = c.getTimeInMillis();
            Sign_up_salon.sincefrom.setText(formattedDate);

        }
        if (sincefrom) {
            EditProfil.OpenSince.setText(formattedDate);
            EditProfil.since_from = c.getTimeInMillis() + "";

        }
        if (reserv_date) {
            Reservation_home.date.setText(formattedDate);
            Reservation_home.date_l = formattedDate;
            reserv_date = false;
        }

        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext().getResources().updateConfiguration(config,
                    getContext().getResources().getDisplayMetrics());
        } else {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext().getResources().updateConfiguration(config,
                    getContext().getResources().getDisplayMetrics());

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context); // this statement is missing
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        /*Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;

        }
    }
}
