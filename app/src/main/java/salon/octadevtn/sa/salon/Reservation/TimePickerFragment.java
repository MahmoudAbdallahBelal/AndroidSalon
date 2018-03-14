package salon.octadevtn.sa.salon.Reservation;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import salon.octadevtn.sa.salon.EditProfil;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;

import static salon.octadevtn.sa.salon.Utils.Static.reserv_time;

/**
 * Created by Octadev on 7/14/2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


// Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.DatePickerDialogTheme, this, hourOfDay, minute,
                true);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay % 12;
            if (hourOfDay < 10)
                time = "0" + hourOfDay + ":" + minute + " pm";
            else
                time = hourOfDay + ":" + minute + " pm";

        } else {
            if (hourOfDay < 10)
                time = "0" + hourOfDay + ":" + minute + " am";
            else
                time = hourOfDay + ":" + minute + " am";

        }
        if (Static.position >= 0 && Static.time.equals("start")) {
            EditProfil.WorkListe.get(Static.position).setStartHour(time);
            EditProfil.WorkListe.get(Static.position).setStatus(true);
            EditProfil.adapterWork.notifyItemRangeChanged(Static.position, EditProfil.WorkListe.size());
        }
        if (Static.position >= 0 && Static.time.equals("end")) {
            EditProfil.WorkListe.get(Static.position).setEndHours(time);
            EditProfil.WorkListe.get(Static.position).setStatus(true);
            EditProfil.adapterWork.notifyItemRangeChanged(Static.position, EditProfil.WorkListe.size());
        }
        if (reserv_time) {
            Reservation_home.time.setText(time);

            reserv_time = false;
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
            /*Configuration config = new Configuration();
            config.locale = locale;*/

        }
    }
}
