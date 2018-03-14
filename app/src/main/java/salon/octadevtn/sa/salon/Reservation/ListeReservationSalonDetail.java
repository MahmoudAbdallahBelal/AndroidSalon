package salon.octadevtn.sa.salon.Reservation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Adaptor.AdapterReservationDetail;
import salon.octadevtn.sa.salon.Models.DayMounth;
import salon.octadevtn.sa.salon.Models.Event;
import salon.octadevtn.sa.salon.Models.Promotiondate;
import salon.octadevtn.sa.salon.Models.Reservation.Reservation;
import salon.octadevtn.sa.salon.Models.Reservation.Task;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.ExpandableHeightGridView;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeReservationSalonDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeReservationSalonDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String tag = "MyCalendarActivity";
    private static final String dateTemplate = "MMMM yyyy";
    public static FragmentManager manager;
    @SuppressWarnings("unused")
    @SuppressLint({"NewApi", "NewApi", "NewApi", "NewApi"})
    private final DateFormat dateFormatter = new DateFormat();
    Calendar calendar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nodata)
    LinearLayout nodata;
    @BindView(R.id.nodata2)
    LinearLayout nodata2;
    @BindView(R.id.data)
    NestedScrollView data;
    @BindView(R.id.add_reservation)
    TextView add_reservation;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Date date = null;
    ArrayList<Event> event = new ArrayList<>();
    List<Promotiondate> Promotiondateliste = new ArrayList<>();
    AdapterReservationDetail adapterReservation;
    ToolTipView mPurpleToolTipView;
    ToolTipRelativeLayout mToolTipFrameLayout;
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
    private OnFragmentInteractionListener mListener;

    public ListeReservationSalonDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searsh.
     */
    // TODO: Rename and change types and number of parameters
    public static ListeReservationSalonDetail newInstance(String param1) {
        ListeReservationSalonDetail fragment = new ListeReservationSalonDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_reservation_salon_detail, container, false);
        ButterKnife.bind(this, view);
        try {
            ((TextView) view.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);

            ((TextView) view.findViewById(R.id.nodatatxt2)).setTypeface(MyApplication.type_jf_medium);
        } catch (Exception e) {
        }


        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
                + year);
        mToolTipFrameLayout = (ToolTipRelativeLayout) view.findViewById(R.id.activity_main_tooltipframelayout);

        selectedDayMonthYearButton = (Button) view
                .findViewById(R.id.selectedDayMonthYear);
        selectedDayMonthYearButton.setText("Selected: ");

        prevMonth = (ImageView) view.findViewById(R.id.prevMonth);


        currentMonth = (TextView) view.findViewById(R.id.currentMonth);
        currentMonth.setTypeface(MyApplication.type_jf_medium);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            date = format.parse(mParam1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //  c.add(Calendar.DATE, 1);

        date = c.getTime();
        SimpleDateFormat format2 = new SimpleDateFormat("EEE, dd MMM yyyy");

        currentMonth.setText(format2.format(date.getTime()));
        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, -1);
                date = c.getTime();
                SimpleDateFormat format2 = new SimpleDateFormat("EEE, dd MMM yyyy");

                currentMonth.setText(format2.format(date.getTime()));
                mParam1 = format.format(date.getTime());
                ListeReservation(mParam1);


            }
        });
        nextMonth = (ImageView) view.findViewById(R.id.nextMonth);

        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
                SimpleDateFormat format2 = new SimpleDateFormat("EEE, dd MMM yyyy");

                currentMonth.setText(format2.format(date.getTime()));

                mParam1 = format.format(date.getTime());
                ListeReservation(mParam1);


            }
        });
        //  nextMonth.setOnClickListener(this);

        calendarView = (ExpandableHeightGridView) view.findViewById(R.id.calendar);

// Initialised


        back.setTypeface(MyApplication.type_jf_regular);
        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        add_reservation.setTypeface(MyApplication.type_jf_medium);
        title.setTypeface(MyApplication.type_jf_medium);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(999);
        GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gl);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setDrawingCacheEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (adapterReservation != null)
                    adapterReservation.notifyDataSetChanged();
                ListeReservation(mParam1);
            }

        });
        swipeRefreshLayout.setColorSchemeResources(
                R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                ListeReservation(mParam1);

            }
        });


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    public void ListeReservation(String date) {
        nodata.setVisibility(View.GONE);
        data.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);

        new salon.octadevtn.sa.salon.Api.Reservation.ListeReservationSalon().ListeReservationdate(date, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {

                if (result != null) {
                    Reservation reservation = (Reservation) result;
                    if (reservation.getTasks().size() == 0) {
                        nodata2.setVisibility(View.VISIBLE);

                    } else {
                        nodata2.setVisibility(View.GONE);


                    }
                    ArrayList<Task> tasks = new ArrayList<Task>();
                    tasks.addAll(reservation.getTasks());
                    adapterReservation = new AdapterReservationDetail(tasks, recyclerView, getActivity());
                    recyclerView.setAdapter(adapterReservation);
                    runLayoutAnimation(recyclerView);
                    swipeRefreshLayout.setRefreshing(false);
                    ///update
                }

            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";
                //  Toast.makeText(getActivity(), Error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void OnError(String message) {

            }

            @Override
            public void onFinish() {
            }
        });
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "Destroying View …");
        super.onDestroy();
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class GridCellAdapter extends BaseAdapter implements View.OnClickListener, ToolTipView.OnToolTipViewClickedListener {
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
        private TextView num_events_per_day;

        // Days in Current Month
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
                        new DayMounth(i, currentMonth, yy, "GREY", true, 0)

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
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

// Get a reference to the Day gridcell
            gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
            txt_count = (TextView) row.findViewById(R.id.txt_count);
            texttop = (TextView) row.findViewById(R.id.texttop);

            textbuttom = (TextView) row.findViewById(R.id.textbuttom);
            textleft = (TextView) row.findViewById(R.id.textleft);
            textright = (TextView) row.findViewById(R.id.textright);


            gridcell.setOnClickListener(this);
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


                        int x = promotionin(new Date(year, month, day), new Date(year2, month2, day2), new Date(day_color.getYy(), day_color.getMm() + 1, day_color.getDd()));
                        if (x == 1) {
                            textright.setVisibility(View.GONE);

                            if ((position + 1) % 7 == 0)
                                textright.setVisibility(View.VISIBLE);

                            textleft.setVisibility(View.VISIBLE);
                            texttop.setVisibility(View.VISIBLE);
                            textbuttom.setVisibility(View.VISIBLE);

                        } else {
                            if (x == 2) {
                                textleft.setVisibility(View.GONE);
                                if (position % 7 == 0)
                                    textleft.setVisibility(View.VISIBLE);

                                textright.setVisibility(View.VISIBLE);
                                texttop.setVisibility(View.VISIBLE);
                                textbuttom.setVisibility(View.VISIBLE);

                            } else {
                                if (x == 3) {
                                    textleft.setVisibility(View.GONE);
                                    textright.setVisibility(View.GONE);
                                    if (position % 7 == 0)
                                        textleft.setVisibility(View.VISIBLE);
                                    if (position + 1 % 7 == 0)
                                        textright.setVisibility(View.VISIBLE);

                                    texttop.setVisibility(View.VISIBLE);
                                    textbuttom.setVisibility(View.VISIBLE);

                                } else {
                                    textleft.setVisibility(View.GONE);
                                    textright.setVisibility(View.GONE);
                                    texttop.setVisibility(View.GONE);
                                    textbuttom.setVisibility(View.GONE);
                                }
                            }

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


                        int x = promotionin(new Date(year, month, day), new Date(year2, month2, day2), new Date(day_color.getYy(), day_color.getMm() + 1, day_color.getDd()));
                        if (x == 1) {
                            textright.setVisibility(View.GONE);

                            if ((position + 1) % 7 == 0)
                                textright.setVisibility(View.VISIBLE);

                            textleft.setVisibility(View.VISIBLE);
                            texttop.setVisibility(View.VISIBLE);
                            textbuttom.setVisibility(View.VISIBLE);

                        } else {
                            if (x == 2) {
                                textleft.setVisibility(View.GONE);
                                if (position % 7 == 0)
                                    textleft.setVisibility(View.VISIBLE);

                                textright.setVisibility(View.VISIBLE);
                                texttop.setVisibility(View.VISIBLE);
                                textbuttom.setVisibility(View.VISIBLE);

                            } else {
                                if (x == 3) {
                                    textleft.setVisibility(View.GONE);
                                    textright.setVisibility(View.GONE);
                                    if (position % 7 == 0)
                                        textleft.setVisibility(View.VISIBLE);
                                    if (position + 1 % 7 == 0)
                                        textright.setVisibility(View.VISIBLE);

                                    texttop.setVisibility(View.VISIBLE);
                                    textbuttom.setVisibility(View.VISIBLE);

                                } else {
                                    textleft.setVisibility(View.GONE);
                                    textright.setVisibility(View.GONE);
                                    texttop.setVisibility(View.GONE);
                                    textbuttom.setVisibility(View.GONE);
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            return row;
        }

        private void addPurpleToolTipView(View view) {
            ToolTip toolTip = new ToolTip()
                    .withContentView(LayoutInflater.from(getActivity()).inflate(R.layout.tooltip_custom, null))
                    .withColor(getResources().getColor(R.color.color2))
                    .withShadow()

                    .withAnimationType(ToolTip.AnimationType.NONE);

            mPurpleToolTipView = mToolTipFrameLayout.showToolTipForView(toolTip, view.findViewById(R.id.calendar_day_gridcell));
            mPurpleToolTipView.setOnToolTipViewClickedListener(this);
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

        @Override
        public void onToolTipViewClicked(ToolTipView toolTipView) {
            if (mPurpleToolTipView == toolTipView) {
                mPurpleToolTipView = null;
            }


        }
    }


}
