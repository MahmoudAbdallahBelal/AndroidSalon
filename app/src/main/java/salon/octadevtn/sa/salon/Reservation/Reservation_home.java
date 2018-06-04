package salon.octadevtn.sa.salon.Reservation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.valdesekamdem.library.mdtoast.MDToast;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Api.ApiSalonProfile;
import salon.octadevtn.sa.salon.Api.Promotion;
import salon.octadevtn.sa.salon.Api.Reservation.AddReservation;
import salon.octadevtn.sa.salon.Models.DayMounth;
import salon.octadevtn.sa.salon.Models.Event;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Promotiondate;
import salon.octadevtn.sa.salon.Models.Promotiondetail;
import salon.octadevtn.sa.salon.Models.User;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.ExpandableHeightGridView;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;


public class Reservation_home extends Fragment implements TimePickerDialog.OnTimeSetListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String dateTemplate = "MMMM yyyy";
    public static TextView date, time;
    public static String date_l;
    @SuppressWarnings("unused")
    @SuppressLint({"NewApi", "NewApi", "NewApi", "NewApi"})
    private final DateFormat dateFormatter = new DateFormat();
    salon.octadevtn.sa.salon.Models.SalonProfile salon;
    boolean selecteddate = false;
    Date selected = null;
    ArrayList<Event> event = new ArrayList<>();
    // List<Promotiondate> Promotiondateliste = new ArrayList<>();
    Calendar calendar;
    String dateseted = "", timeseted = "";
    Boolean setedtime = false;
    List<Promotiondate> Promotiondateliste = new ArrayList<>();
    Dialog dialog;
    @BindView(R.id.user_phone)
    EditText user_phone;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.promotion_name)
    EditText promotion_name;
    @BindView(R.id.salon_name)
    EditText salon_name;
    @BindView(R.id.back1)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.done)
    TextView done;
    @BindView(R.id.cancel)
    TextView cancel;
    View view;
    User user;
    String formattedDate;
    SweetAlertDialog pDialog;
    String time_text;
    private TextView currentMonth;
    private Button selectedDayMonthYearButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private ExpandableHeightGridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;


    public Reservation_home() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Reservation_home newInstance(int id, String name, String sname) {
        Reservation_home fragment = new Reservation_home();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, name);
        args.putString(ARG_PARAM3, sname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getInstance(), Static.shared_name);
        Profile pro = null;
        try {
            pro = (Profile) gsonSharedPrefs.getObject(new Profile());
            user = pro.getUser();
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        if (getArguments() != null) {
            mParam1 = String.valueOf(getArguments().getInt(ARG_PARAM1));
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = sdf.format(c.getTime());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        time_text = hourOfDay + ":" + minute;
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay % 12;
            if (hourOfDay < 10)
                time_text = "0" + hourOfDay + ":" + minute + "pm";
            else
                time_text = hourOfDay + ":" + minute + "pm";

        } else {
            if (hourOfDay < 10)
                time_text = "0" + hourOfDay + ":" + minute + "am";
            else
                time_text = hourOfDay + ":" + minute + "am";

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reservation_home, container, false);
        ButterKnife.bind(this, view);
        date = (TextView) view.findViewById(R.id.date);
        time = (TextView) view.findViewById(R.id.time);
        date.setTypeface(MyApplication.type_jf_regular);
        date.setTypeface(MyApplication.type_jf_regular);
        salon_name.setTypeface(MyApplication.type_jf_regular);
        promotion_name.setTypeface(MyApplication.type_jf_regular);
        user_name.setTypeface(MyApplication.type_jf_regular);
        user_phone.setTypeface(MyApplication.type_jf_regular);
        user_name.setTypeface(MyApplication.type_jf_regular);
        time.setTypeface(MyApplication.type_jf_regular);
        done.setTypeface(MyApplication.type_jf_regular);
        cancel.setTypeface(MyApplication.type_jf_regular);
        back.setTypeface(MyApplication.type_jf_regular);
        title.setTypeface(MyApplication.type_jf_medium);
        user_name.setText(user.getUsername());
        user_phone.setText(user.getPhone());
        promotion_name.setText(mParam2);
        salon_name.setText(mParam3);


         //user_name.setText("");

        /*
         pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.color2));
        pDialog.setTitleText(getResources().getString(R.string.loading));
        pDialog.setCancelable(false);
        */
        api(mParam1);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


/*

                DialogFragment timePickerDialog = new TimePickerFragment();
                timePickerDialog.show(getFragmentManager(), "timePicker");
                Static.reserv_time = true;
*/


                if (selecteddate == true) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                    Calendar c = Calendar.getInstance();
                    c.setTime(selected);
                    int dayweek = c.get(Calendar.DAY_OF_WEEK);
                    Date start_time = null, end_time = null;
                    try {
                        start_time = dateFormat.parse(salon.getSalon().getWork().get(dayweek - 1).getStartHour());
                        end_time = dateFormat.parse(salon.getSalon().getWork().get(dayweek - 1).getEndHours());

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                    }

                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            Reservation_home.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            true



                    );
                    tpd.setThemeDark(false);
                    tpd.vibrate(false);
                    tpd.dismissOnPause(true);
                    tpd.enableSeconds(false);

                    tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                   // tpd.setAccentColor(getResources().getColor(R.color.white));



                    if (true) {
                        tpd.setAccentColor(getResources().getColor(R.color.white));

                    }
                    if (false) {
                        tpd.setTitle("TimePicker Title");
                    }

                    tpd.setMaxTime(end_time.getHours(), end_time.getMinutes(), 0);
                    tpd.setMinTime(start_time.getHours(), start_time.getHours(), 0);
                    tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            Log.d("TimePicker", "Dialog was cancelled");
                        }
                    });
                    tpd.show(getActivity().getFragmentManager(), "ggg");
                } else {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(date);
                }
            }

        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
          DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
                Static.reserv_date = true;
*/
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_datepiker);
                ButterKnife.bind(this, dialog);
                _calendar = Calendar.getInstance(Locale.getDefault());
                month = _calendar.get(Calendar.MONTH) + 1;
                year = _calendar.get(Calendar.YEAR);

                selectedDayMonthYearButton = (Button) dialog
                        .findViewById(R.id.selectedDayMonthYear);
                selectedDayMonthYearButton.setText("Selected: ");

                prevMonth = (ImageView) dialog.findViewById(R.id.prevMonth);

                currentMonth = (TextView) dialog.findViewById(R.id.currentMonth);
                currentMonth.setTypeface(MyApplication.type_jf_medium);
                currentMonth.setText(DateFormat.format(dateTemplate,
                        _calendar.getTime()));

                nextMonth = (ImageView) dialog.findViewById(R.id.nextMonth);
                nextMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (month > 11) {
                            month = 1;
                            year++;
                        } else {
                            month++;
                        }
                        setGridCellAdapterToDate(month, year);
                    }
                });
                prevMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (month <= 1) {
                            month = 12;
                            year--;
                        } else {
                            month--;
                        }
                        setGridCellAdapterToDate(month, year);
                    }
                });

                calendarView = (ExpandableHeightGridView) dialog.findViewById(R.id.calendar);

// Initialised

                calendarView.setExpanded(true);
                //botoCalendarView.setRobotoCalendarListener( getActivity());
                calendar = Calendar.getInstance();
                Random random = new Random(System.currentTimeMillis());
                int style = random.nextInt(2);
                int daySelected = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, daySelected);

                adapter = new GridCellAdapter(getActivity(),
                        R.id.calendar_day_gridcell, month, year, event);
                calendarView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
             /*   FragmentManager fm = getFragmentManager();
                Customdialog dialogFragment = new Customdialog ();
                dialogFragment.show(getFragmentManager(),"this");
*/

            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReservation();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }

    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getActivity(),
                R.id.calendar_day_gridcell, month, year, event);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    public void AddReservation() {
        if (selecteddate && setedtime) {
            //>>pDialog.show();
            new AddReservation().AddReservation(user.getId() + "", salon.getSalon().getId() + "", mParam1, date.getText().toString(), dateseted, timeseted,
                    new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                             //   pDialog.dismiss();


                                MDToast mdToast;
                                mdToast = MDToast.makeText(getActivity(),getString(R.string.youmakenewres) , 8,MDToast.TYPE_SUCCESS );
                                mdToast.show();

                                getActivity().onBackPressed();

//                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText(getContext().getString(R.string.new_res))
//                                        .setConfirmText(getContext().getString(R.string.ok))
//                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                                sweetAlertDialog.dismissWithAnimation();
//
//                                                getActivity().onBackPressed();
//                                            }
//                                        })
//                                        .setContentText(getContext().getString(R.string.youmakenewres))
//                                        .show();
//
//                                getActivity().getSupportFragmentManager().popBackStack();



                            } else {
                                //pDialog.dismiss();
//                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                                        .setTitleText(getResources().getString(R.string.erreurreservation))
//                                        .setContentText(getResources().getString(R.string.tryagain))
//                                        .setConfirmText(getResources().getString(R.string.ok))
//                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sDialog) {
//                                                sDialog.dismissWithAnimation();
//                                            }
//                                        })
//                                        .show();
                                MDToast mdToast;
                                mdToast = MDToast.makeText(getActivity(),getString(R.string.erreurreservation) , 8,MDToast.TYPE_ERROR );
                                mdToast.show();

                            }
                        }

                        @Override
                        public void onFailure(Object result) {
                            ResponseErrors responseError = (ResponseErrors) result;
                            String Error = "Failure";
                            //pDialog.dismiss();
//                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//                                    .setTitleText(getResources().getString(R.string.erreurreservation))
//                                    .setContentText(getResources().getString(R.string.tryagain))
//                                    .setConfirmText(getResources().getString(R.string.ok))
//                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                        @Override
//                                        public void onClick(SweetAlertDialog sDialog) {
//                                            sDialog.dismissWithAnimation();
//                                        }
//                                    })
//                                    .show();
                            MDToast mdToast;
                            mdToast = MDToast.makeText(getActivity(),getString(R.string.erreurreservation) , 8,MDToast.TYPE_ERROR );
                            mdToast.show();
                        }

                        @Override
                        public void OnError(String message) {
                           // pDialog.dismiss();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.erreurreservation))
                                    .setContentText(getResources().getString(R.string.tryagain))
                                    .setConfirmText(getResources().getString(R.string.ok))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void onFinish() {
                        }
                    });

        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        timeseted = hourOfDay + ":" + minute + ":" + second;
        setedtime = true;
        String time2 = "";
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay % 12;
            if (hourOfDay < 10)
                time2 = "0" + hourOfDay + ":" + minute + "pm";
            else
                time2 = hourOfDay + ":" + minute + "pm";

        } else {
            if (hourOfDay < 10)
                time2 = "0" + hourOfDay + ":" + minute + "am";
            else
                time2 = hourOfDay + ":" + minute + "am";
        }
        time.setText(time2);
    }

    public int getEventPosition(int dd, int mm, int yy, ArrayList<Event> event) {

        for (int i = 0; i < event.size(); i++) {
            int dayOfMounth = event.get(i).getDd();
            int Mounth = event.get(i).getMm() - 1;
            int year = event.get(i).getYy();


            if (dd == dayOfMounth && mm == Mounth && year == yy) {

                return i;
            }
        }
        return -1;
    }

    public int promotionin(Date d1, Date d2, Date d3) {
        if (d3.compareTo(d1) < 0)
            return -1;
        else if (d3.compareTo(d2) > 0)
            return -1;
        else if (d3.compareTo(d1) == 0) {
            return 1;
        } else if (d3.compareTo(d2) == 0)
            return 2;
        else return 3;
    }

    public void api(String id) {

        //pDialog.show();


        new Promotion().getpromotion(Integer.parseInt(id)
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null && Reservation_home.this.isVisible()) {


                            Promotiondetail promotiondetail = (Promotiondetail) result;
                            Promotiondateliste.add(new Promotiondate(promotiondetail.getPromotion().getStartDate(), promotiondetail.getPromotion().getEndDate()));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date start = null;
                            try {
                                start = sdf.parse(promotiondetail.getPromotion().getEndDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date d = new Date(System.currentTimeMillis());
                            if (d.after(start)) {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.reservation_espired))
                                        .setContentText(getResources().getString(R.string.otherpromo))
                                        .setConfirmText(getResources().getString(R.string.ok))
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                getActivity().onBackPressed();
                                            }
                                        })
                                        .show();

                            } else {
                                api2(promotiondetail.getSalon().getId());
                            }
                        } else {
                            showError();
                        }
                    }

                    @Override
                    public void onFailure(Object result) {
                        showError();
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void OnError(String message) {
                        showError();
                    }
                }
        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (pDialog != null)
          //  pDialog.dismiss();
    }

    public void api2(int id) {
        new ApiSalonProfile().salon(id
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null && Reservation_home.this.isVisible()) {
                            salon = (salon.octadevtn.sa.salon.Models.SalonProfile) result;
                          //  pDialog.dismiss();
                        } else {
                            showError();

                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        showError();
                    }

                    @Override
                    public void OnError(String message) {
                        showError();
                    }

                    @Override
                    public void onFinish() {

                    }
                });


    }

    public void showError() {
       // pDialog.dismiss();
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.erreur1))
                .setContentText(getResources().getString(R.string.tryagain))
                .setConfirmText(getResources().getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        getActivity().onBackPressed();
                    }
                })
                .show();

    }

    public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {
        private static final String tag = "GridCellAdapter";
        private static final int DAY_OFFSET = 1;
        private final Context _context;
        private final List<DayMounth> list;
        private final String[] weekdays = new String[]{"Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat"};
        private final String[] months = {"January", "February", "March",
                "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
                31, 30, 31};
        private final HashMap<String, Integer> eventsPerMonthMap;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd-MMM-yyyy");
        ArrayList<Event> event;
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private TextView gridcell, texttop, textbuttom, textleft, textright, txt_count;
        private TextView badge;
        private LinearLayout closed;
        private TextView num_events_per_day;


        public GridCellAdapter(Context context, int textViewResourceId,
                               int month, int year, ArrayList<Event> event) {
            super();
            this._context = context;
            this.event = event;
            this.list = new ArrayList<DayMounth>();
            Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
                    + "Year: " + year);
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
            Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
            Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
            Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

// Print Month
            printMonth(month, year);

// Find Number of Events
            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public DayMounth getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Prints Month
         *
         * @param mm
         * @param yy
         */
        private void printMonth(int mm, int yy) {
            Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);

            Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
                    + daysInMonth + " days.");

            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
            Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
                Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
                        + prevMonth + " NextMonth: " + nextMonth
                        + " NextYear: " + nextYear);
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
                Log.d(tag, "**-> PrevYear: " + prevYear + " PrevMonth:"
                        + prevMonth + " NextMonth: " + nextMonth
                        + " NextYear: " + nextYear);
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                Log.d(tag, "***—> PrevYear: " + prevYear + " PrevMonth:"
                        + prevMonth + " NextMonth: " + nextMonth
                        + " NextYear: " + nextYear);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            Log.d(tag, "Week Day:" + currentWeekDay + " is "
                    + getWeekDayAsString(currentWeekDay));
            Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
            Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

// Trailing Month days
// Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                Log.d(tag,
                        "PREV MONTH:= "
                                + prevMonth
                                + " => "
                                + getMonthAsString(prevMonth)
                                + " "
                                + String.valueOf((daysInPrevMonth
                                - trailingSpaces + DAY_OFFSET)
                                + i));
                list.add(new DayMounth(((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
                        , prevMonth, yy, "GREY", false, 0)
                );
            }

// Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {
                Log.d("xxxxxxxxxxxx" + currentMonthName, String.valueOf(i) + " "
                        + getMonthAsString(currentMonth) + " " + yy + "    " + (_calendar.get(Calendar.MONTH) - 1) + "    " + currentMonth);
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                int month1 = calendar.get(Calendar.MONTH);
                ;

                if (i == getCurrentDayOfMonth() && currentMonth == month1) {
                    int pos = getEventPosition(getCurrentDayOfMonth(), currentMonth, yy, event);
                    if (pos == -1)

                        list.add(new DayMounth(i, currentMonth, yy, "BLUE", false, 0));
                    else
                        list.add(new DayMounth(i, currentMonth, yy, "BLUE", true, event.get(pos).getNbbadge()));

                } else {
                    int pos = getEventPosition(i, currentMonth, yy, event);
                    if (
                            pos == -1)
                        list.add(
                                new DayMounth(i, currentMonth, yy, "WHITE", false, 0)
                        );
                    else
                        list.add(
                                new DayMounth(i, currentMonth, yy, "WHITE", true, event.get(pos).getNbbadge())
                        );

                }
            }

// Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
                list.add(
                        new DayMounth(i + 1, currentMonth, yy, "GREY", true, 0)

                );
            }
        }

        /**
         * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
         * ALL entries from a SQLite database for that month. Iterate over the
         * List of All entries, and get the dateCreated, which is converted into
         * day.
         *
         * @param year
         * @param month
         * @return
         */
        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                    int month) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();

            return map;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

// Get a reference to the Day gridcell
            gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
            closed = (LinearLayout) row.findViewById(R.id.colsed);
            txt_count = (TextView) row.findViewById(R.id.txt_count);
            texttop = (TextView) row.findViewById(R.id.texttop);

            textbuttom = (TextView) row.findViewById(R.id.textbuttom);
            textleft = (TextView) row.findViewById(R.id.textleft);
            textright = (TextView) row.findViewById(R.id.textright);


            final View finalRow = row;
            gridcell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date date1 = null, date2 = null, date3 = null;
                    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    try {
                        date1 = format.parse(Promotiondateliste.get(0).getStartDate());
                        date2 = format.parse(Promotiondateliste.get(0).getEndDate());
                        date3 = format.parse(list.get(position).getYy() + "-" + (list.get(position).getMm() + 1) + "-" + list.get(position).getDd());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int x = promotionin(date1, date2, date3);
                    if (x < 1) {
                        //addPurpleToolTipView(v);
                        String d = getWeekDayAsString(currentWeekDay);
                        BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_sample_popup, null);
                        PopupWindow popupWindow = BubblePopupHelper.create(getActivity(), bubbleLayout);
                        final Random random = new Random();

                        int[] location = new int[2];
                        v.getLocationInWindow(location);

                        bubbleLayout.setArrowDirection(ArrowDirection.BOTTOM);
                        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - (v.getWidth()) - (v.getWidth() / 2), location[1] - (v.getHeight() / 2));


                    } else {
                        Date date4 = null;
                        try {
                            date4 = format.parse(list.get(position).getYy() + "-" + (list.get(position).getMm() + 1) + "-" + list.get(position).getDd());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(date4);
                        int dayweek = c.get(Calendar.DAY_OF_WEEK) - 1;

                        if (!salon.getSalon().getWork().get(dayweek).getStatus()) {
                            BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_sample_popup_closed, null);
                            PopupWindow popupWindow = BubblePopupHelper.create(getActivity(), bubbleLayout);
                            final Random random = new Random();

                            int[] location = new int[2];
                            v.getLocationInWindow(location);

                            bubbleLayout.setArrowDirection(ArrowDirection.BOTTOM);
                            popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0] - (v.getWidth()), location[1] - (v.getHeight() / 2));
                        } else {
                            SimpleDateFormat format2 = new SimpleDateFormat(" EEE , dd MMM");
                            date.setText(format2.format(date4.getTime()));
                            try {
                                selected = format.parse(list.get(position).getYy() + "-" + (list.get(position).getMm() + 1) + "-" + list.get(position).getDd());
                                selecteddate = true;
                                dateseted = list.get(position).getYy() + "-" + (list.get(position).getMm() + 1) + "-" + list.get(position).getDd();
                                dialog.dismiss();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }
            });
            badge = (TextView) row.findViewById(R.id.txt_count);


// ACCOUNT FOR SPACING

            Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
            DayMounth day_color = list.get(position);
            String theday = day_color.getDd() + "";
            String themonth = day_color.getMm() + "";
            String theyear = day_color.getYy() + "";
            if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
                if (eventsPerMonthMap.containsKey(theday)) {
                    num_events_per_day = (TextView) row
                            .findViewById(R.id.num_events_per_day);
                    Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                    num_events_per_day.setText(numEvents.toString());
                }
            }

// Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);
            Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
                    + theyear);
            if (day_color.getType().equals("GREY")) {
                gridcell.setTextColor(getResources()
                        .getColor(R.color.lightgray02));
                badge.setVisibility(View.GONE);
                textleft.setVisibility(View.GONE);
                textright.setVisibility(View.GONE);
                texttop.setVisibility(View.GONE);
                textbuttom.setVisibility(View.GONE);


            }
            if (day_color.getType().equals("WHITE")) {
                gridcell.setTextColor(getResources().getColor(
                        R.color.colorBlack));
                if (day_color.getBadge()) {
                    {
                        badge.setVisibility(View.VISIBLE);
                        badge.setText(day_color.getNbbadge() + "");
                    }
                } else badge.setVisibility(View.GONE);
                Date date3 = null;

                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                try {
                    date3 = format.parse(list.get(position).getYy() + "-" + (list.get(position).getMm() + 1) + "-" + list.get(position).getDd());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(date3);
                int dayweek = c.get(Calendar.DAY_OF_WEEK) - 1;
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");


                for (int i = 0; i < Promotiondateliste.size(); i++) {
                    try {
                        Date date = dt.parse(Promotiondateliste.get(i).getStartDate());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH) + 1;
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        Date date2 = dt.parse(Promotiondateliste.get(i).getEndDate());
                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(date2);
                        int year2 = cal2.get(Calendar.YEAR);
                        int month2 = cal2.get(Calendar.MONTH) + 1;
                        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
                        Date celldate = new Date(list.get(position).getYy(), list.get(position).getMm() + 1, list.get(position).getDd());

                        int x = promotionin(new Date(year, month, day), new Date(year2, month2, day2), celldate);
                        if (x >= 1) {

                            gridcell.setTextColor(getResources().getColor(R.color.color2));
                            if (selecteddate) {
                                if (celldate.getDay() == selected.getDay() && celldate.getMonth() == selected.getMonth()) {
                                    gridcell.setTextColor(getResources().getColor(android.R.color.white));
                                    closed.setVisibility(View.VISIBLE);

                                }
                            }
                            if (!salon.getSalon().getWork().get(dayweek).getStatus()) {
//closed.setVisibility(View.VISIBLE);
                                gridcell.setTextColor(getResources().getColor(R.color.color3));

                            }

                        } else {

                            gridcell.setTextColor(getResources().getColor(R.color.colorBlack));
                            textleft.setVisibility(View.GONE);
                            textright.setVisibility(View.GONE);
                            texttop.setVisibility(View.GONE);
                            textbuttom.setVisibility(View.GONE);


                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
            if (day_color.getType().equals("BLUE")) {

                gridcell.setTextColor(getResources().getColor(R.color.orrange));
                if (day_color.getBadge()) {
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(day_color.getNbbadge() + "");

                } else badge.setVisibility(View.GONE);
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");


                for (int i = 0; i < Promotiondateliste.size(); i++) {
                    Date date3 = null;
                    final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    try {
                        date3 = format.parse(list.get(position).getYy() + "-" + (list.get(position).getMm() + 1) + "-" + list.get(position).getDd());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar c = Calendar.getInstance();
                    c.setTime(date3);
                    int dayweek = c.get(Calendar.DAY_OF_WEEK) - 1;

                    try {
                        Date date = dt.parse(Promotiondateliste.get(i).getStartDate());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH) + 1;
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        Date date2 = dt.parse(Promotiondateliste.get(i).getEndDate());
                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(date2);
                        int year2 = cal2.get(Calendar.YEAR);
                        int month2 = cal2.get(Calendar.MONTH) + 1;
                        int day2 = cal2.get(Calendar.DAY_OF_MONTH);

                        Date celldate = new Date(list.get(position).getYy(), list.get(position).getMm() + 1, list.get(position).getDd());

                        int x = promotionin(new Date(year, month, day), new Date(year2, month2, day2), new Date(day_color.getYy(), day_color.getMm() + 1, day_color.getDd()));
                        if (x >= 1) {
                            gridcell.setTextColor(getResources().getColor(R.color.color2));
                            if (selecteddate) {
                                if (celldate.getDay() == selected.getDay() && celldate.getMonth() == selected.getMonth()) {
                                    gridcell.setTextColor(getResources().getColor(android.R.color.white));
                                    closed.setVisibility(View.VISIBLE);

                                }
                            }
                            if (!salon.getSalon().getWork().get(dayweek).getStatus()) {
                                //     closed.setVisibility(View.VISIBLE);
                                gridcell.setTextColor(getResources().getColor(R.color.color3));

                            }
                        } else {

                            gridcell.setTextColor(getResources().getColor(R.color.colorBlack));
                            textleft.setVisibility(View.GONE);
                            textright.setVisibility(View.GONE);
                            texttop.setVisibility(View.GONE);
                            textbuttom.setVisibility(View.GONE);


                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            return row;
        }


        @Override
        public void onClick(View view) {
            String date_month_year = (String) view.getTag();
            Log.e("Selected date", date_month_year);
            try {
                Date parsedDate = dateFormatter.parse(date_month_year);
                Log.d(tag, "Parsed Date: " + parsedDate.toString());
                //    HomeActivityDrawer.setFragment(DayRecervationFragment.newInstance(parsedDate.toString()));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }


    }
}
