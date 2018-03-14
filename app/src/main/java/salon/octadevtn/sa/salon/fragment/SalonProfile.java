package salon.octadevtn.sa.salon.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Adaptor.SlidingImage_Adapter;
import salon.octadevtn.sa.salon.Api.ApiSalonProfile;
import salon.octadevtn.sa.salon.EditSalon;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.Models.Payment_;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.CustomTabLayout;
import salon.octadevtn.sa.salon.Utils.MagicViewPager;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SalonProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalonProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ImageView[] dots;
    int id;
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.container)
    MagicViewPager viewpager;
    @BindView(R.id.follow)
    LinearLayout follow;
    @BindView(R.id.followers)
    LinearLayout followers;
    @BindView(R.id.tryagain)
    LinearLayout tryagain;
    @BindView(R.id.imagetry)
    ImageView imagetry;
    @BindView(R.id.cercleImage)
    CircleImageView circleImageView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.nb_flow)
    TextView nb_flow;
    @BindView(R.id.nb_followers)
    TextView nb_followers;
    @BindView(R.id.btn_follow)
    Button btn_follow;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.googlemap)
    TextView googlemap;
    @BindView(R.id.branche)
    TextView branche;
    @BindView(R.id.workhours)
    TextView workhours;
    @BindView(R.id.opensince)
    TextView opensince;
    @BindView(R.id.myRatingBar)
    RatingBar ratingbar;
    @BindView(R.id.visa)
    ImageView visa;
    @BindView(R.id.mastercard)
    ImageView mastercard;
    @BindView(R.id.debit)
    ImageView debit;
    @BindView(R.id.cash)
    ImageView cash;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.text6)
    TextView text6;
    @BindView(R.id.text7)
    TextView text7;
    @BindView(R.id.textlight)
    TextView textlight;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.layout_content)
    LinearLayout layout_content;
    @BindView(R.id.layout_folowing)
    LinearLayout layout_folowing;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;
    View view;
    ViewPagerAdapter adapter;
    salon.octadevtn.sa.salon.Models.SalonProfile salon;
    private ArrayList<Cover> ImagesArray = new ArrayList<Cover>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SalonProfile() {
        // Required empty public constructor
    }

    public static SalonProfile newInstance(int id) {
        SalonProfile fragment = new SalonProfile();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @OnTouch(R.id.page)
    public boolean Page() {
        if (popupWindow != null)
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        return false;
    }

    @OnTouch(R.id.page2)
    public boolean Page2() {
        if (popupWindow != null)
            if (popupWindow.isShowing())
                popupWindow.dismiss();
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view = inflater.inflate(R.layout.fragment_salon_profile, container, false);
        ((TextView) view.findViewById(R.id.back1)).setTypeface(MyApplication.type_jf_regular);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        name.setTypeface(MyApplication.type_jf_regular);
        username.setTypeface(MyApplication.type_jf_regular);
        nb_followers.setTypeface(MyApplication.type_jf_regular);
        nb_flow.setTypeface(MyApplication.type_jf_regular);
        btn_follow.setTypeface(MyApplication.type_jf_regular);
        text1.setTypeface(MyApplication.type_jf_medium);
        text2.setTypeface(MyApplication.type_jf_medium);
        text3.setTypeface(MyApplication.type_jf_medium);
        text4.setTypeface(MyApplication.type_jf_medium);
        text5.setTypeface(MyApplication.type_jf_medium);
        text6.setTypeface(MyApplication.type_jf_medium);
        text7.setTypeface(MyApplication.type_jf_medium);
        textlight.setTypeface(MyApplication.type_jf_light);
        googlemap.setTypeface(MyApplication.type_jf_light);
        location.setTypeface(MyApplication.type_jf_light);
        type.setTypeface(MyApplication.type_jf_light);
        opensince.setTypeface(MyApplication.type_jf_light);
        workhours.setTypeface(MyApplication.type_jf_light);
        branche.setTypeface(MyApplication.type_jf_light);
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        Profile profile = null;
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupMenu1(view.findViewById(R.id.setting));
            }
        });
        if (profile.getType().equals("salon"))
            if (id == profile.getSalon().getId())
                view.findViewById(R.id.setting).setVisibility(View.GONE);


        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(ListeFlow.newInstance(id, "salon", "FL"));
            }
        });
        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(ListeFlow.newInstance(id, "salon", "FR"));
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        if (savedInstanceState != null) {

            inisializer((salon.octadevtn.sa.salon.Models.SalonProfile) savedInstanceState.getParcelable("salon"));
        }

        api();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void AddBlock(int id, String salon) {
        new salon.octadevtn.sa.salon.Api.DeleteBlock().AddBlock(String.valueOf(id), salon
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            getActivity().onBackPressed();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";

                    }

                    @Override
                    public void OnError(String message) {

                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.block1, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.block:
                        AddBlock(id, "salon");
                        return true;
                    case R.id.report:
                        //AddReport(id, "salon");
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.report);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.show();
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.getWindow().setAttributes(lp);

                        dialog.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText report = dialog.findViewById(R.id.report);
                                AddReport(id, "salon", report.getText().toString(), dialog);
                                dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                                dialog.findViewById(R.id.line).setVisibility(View.GONE);

                            }
                        });
                        dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                return;

                            }
                        });
                        // return true;
                }
                return true;
            }
        });
        popup.show();
    }

    public void ShowPopupMenu1(View view) {
        LayoutInflater layoutInflater
                = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.pop_up1, null);
        if (popupWindow != null)
            if (popupWindow.isShowing())
                popupWindow.dismiss();

        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ((TextView) popupView.findViewById(R.id.del)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) popupView.findViewById(R.id.hid)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) popupView.findViewById(R.id.edi)).setTypeface(MyApplication.type_jf_regular);

        LinearLayout delete = (LinearLayout) popupView.findViewById(R.id.delete);
        LinearLayout hide = (LinearLayout) popupView.findViewById(R.id.hide);
        delete.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddBlock(id, "salon");
            }
        });
        hide.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.report);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                dialog.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText report = dialog.findViewById(R.id.report);
                        AddReport(id, "salon", report.getText().toString(), dialog);
                        dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                        dialog.findViewById(R.id.line).setVisibility(View.GONE);

                    }
                });
                dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        return;

                    }
                });
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(view, -60, -60);

    }

    private void AddReport(int id, String salon, String reason, final Dialog dialog) {
        new salon.octadevtn.sa.salon.Api.DeleteBlock().AddReport(reason, String.valueOf(id), "salon"
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";

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
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.UP, enter, 500L);
    }

    public void api() {
        tryagain.setVisibility(View.GONE);
        new ApiSalonProfile().salon(id
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null && SalonProfile.this.isVisible()) {
                            progressBar.setVisibility(View.GONE);
                            layout_content.setVisibility(View.VISIBLE);
                            layout_folowing.setVisibility(View.VISIBLE);

                            salon = (salon.octadevtn.sa.salon.Models.SalonProfile) result;
                            inisializer(salon);

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

    private void inisializer(final salon.octadevtn.sa.salon.Models.SalonProfile salon) {
        progressBar.setVisibility(View.GONE);
        layout_content.setVisibility(View.VISIBLE);
        layout_folowing.setVisibility(View.VISIBLE);

        adapter.addFragment(listeServiceFragment.newInstance(salon), getResources().getString(R.string.sercices));
        adapter.addFragment(Salon_price_list.newInstance(salon), getResources().getString(R.string.prices));
        adapter.addFragment(Liste_Promotion_Salon_Profile.newInstance(salon), getResources().getString(R.string.promotion));

        if (salon != null)
            init(salon.getSalon().getCover());

        viewpager.setAdapter(adapter);
        changeTabsFont();
        tabs.setupWithViewPager(viewpager);
        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        Profile profile = null;
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        if (profile.getType().equals("salon") && id == profile.getSalon().getId()) {
            btn_follow.setText(getResources().getString(R.string.edit_profile));
        }
        final Profile finalProfile = profile;

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalProfile.getType().equals("salon") && id == finalProfile.getSalon().getId()) {
                    ((HomeActivityDrawer) getActivity()).setFragment(new EditSalon());
                } else if (!salon.getIsFolow()) {
                    new salon.octadevtn.sa.salon.Api.Follow().AddFollow(salon.getSalon().getId(), "salon"
                            , new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (((Boolean) result) == true) {
                                        btn_follow.setText(MyApplication.getAppContext().getResources().getString(R.string.unfollow));
                                        salon.setIsFolow("true");

                                    }
                                }

                                @Override
                                public void onFailure(Object result) {

                                }

                                @Override
                                public void onFinish() {

                                }

                                @Override
                                public void OnError(String message) {

                                }
                            });
                } else {
                    new salon.octadevtn.sa.salon.Api.Follow().DeleteFollow(salon.getSalon().getId(), "salon"
                            , new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (((Boolean) result) == true) {
                                        btn_follow.setText(MyApplication.getAppContext().getResources().getString(R.string.start_follow));
                                        salon.setIsFolow("false");
                                    }
                                }

                                @Override
                                public void onFailure(Object result) {

                                }

                                @Override
                                public void onFinish() {

                                }

                                @Override
                                public void OnError(String message) {

                                }
                            });
                }
            }
        });

        Glide.with(getActivity()).load(UrlStatic.pathImage + salon.getSalon().getPhoto()).into(circleImageView);
        nb_flow.setText(salon.getFolowing() + "");
        nb_followers.setText(salon.getFolowers() + "");
        name.setText(salon.getSalon().getSalonName());
        username.setText(salon.getSalon().getUsername());
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        if (profile.getType().equals("salon")) {
            if (profile.getSalon().getId() == salon.getSalon().getId()) {
                btn_follow.setText(getResources().getString(R.string.edit_profile));
            } else {

                if (salon.getIsFolow())
                    btn_follow.setText(getResources().getString(R.string.unfollow));
                else
                    btn_follow.setText(getResources().getString(R.string.start_follow));
            }

        } else {
            if (salon.getIsFolow())
                btn_follow.setText(getResources().getString(R.string.unfollow));
            else
                btn_follow.setText(getResources().getString(R.string.start_follow));
        }
        type.setText(salon.getSalon().getSalonType());
        if (salon.getSalon().getAdress() + "\n" + salon.getSalon().getCountry() != null)
            location.setText(
                    salon.getSalon().getAdress() + "\n" + salon.getSalon().getCountry() + ", " + salon.getSalon().getCity());
        else
            location.setText(
                    salon.getSalon().getCountry() + ", " + salon.getSalon().getCity());

        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:" + salon.getSalon().getLatitude() + "," +
                        salon.getSalon().getLongutide());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        if (salon.getRating() != null) {
            ratingbar.setRating(Float.parseFloat(salon.getRating()));
        } else
            ratingbar.setRating(0.0f);
        ratingbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        branche.setText(salon.getSalon().getBranches());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((Long.parseLong(salon.getSalon().getSinceFrom())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        opensince.setText(formattedDate);

        //   opensince.setText(salon.getSalon().getSinceFrom() + "");
        List<Payment_> pay = salon.getSalon().getPayment();
        if (pay.size() > 0) {
            for (int i = 0; i < pay.size(); i++) {
                if (pay.get(i).getName().contains("visa") && pay.get(i).getStatus() == true) {
                    visa.setVisibility(View.VISIBLE);
                } else if (pay.get(i).getName().contains("master") && pay.get(i).getStatus() == true) {
                    mastercard.setVisibility(View.VISIBLE);
                } else if (pay.get(i).getName().contains("cash") && pay.get(i).getStatus() == true) {
                    cash.setVisibility(View.VISIBLE);
                } else if (pay.get(i).getName().contains("debit card") && pay.get(i).getStatus() == true) {
                    debit.setVisibility(View.VISIBLE);
                }
            }
        }
        String work = "";
        String first = salon.getSalon().getWork().get(0).getName();
        String houropen = salon.getSalon().getWork().get(0).getStartHour();
        String hourend = salon.getSalon().getWork().get(0).getEndHours();
        boolean status = salon.getSalon().getWork().get(0).getStatus();
        for (int i = 0; i < salon.getSalon().getWork().size(); i++) {
            if (salon.getSalon().getWork().get(i).getStatus() != status) {
                if (i == salon.getSalon().getWork().size() - 1) {
                    if (status) {
                        if (salon.getSalon().getWork().get(i).getName().equals(first)) {
                            work += first + " " + houropen + " - " + hourend;

                        } else {
                            work += first + " - " + salon.getSalon().getWork().get(i).getName() + " " + houropen + " - " + hourend;
                        }
                        status = salon.getSalon().getWork().get(i).getStatus();
                        first = salon.getSalon().getWork().get(i).getName();

                    } else {
                        if (salon.getSalon().getWork().get(i).getName().equals(first)) {

                            work += first + " " + getActivity().getResources().getString(R.string.closed);

                        } else
                            work += first + " - " + salon.getSalon().getWork().get(i).getName() + " " + getActivity().getResources().getString(R.string.closed);
                        status = salon.getSalon().getWork().get(i).getStatus();
                        first = salon.getSalon().getWork().get(i).getName();
                    }
                }
                if (status) {
                    if (salon.getSalon().getWork().get(i - 1).getName().equals(first)) {
                        work += first + " " + houropen + " - " + hourend + "\n";
                    } else
                        work += first + " - " + salon.getSalon().getWork().get(i - 1).getName() + " " + houropen + " - " + hourend + "\n";

                    status = salon.getSalon().getWork().get(i).getStatus();
                    first = salon.getSalon().getWork().get(i).getName();
                } else {

                    if (salon.getSalon().getWork().get(i - 1).getName().equals(first)) {
                        work += first + getActivity().getResources().getString(R.string.closed) + "\n";
                    } else
                        work += first + " - " + salon.getSalon().getWork().get(i - 1).getName() + " " + getActivity().getResources().getString(R.string.closed) + "\n";
                    status = salon.getSalon().getWork().get(i).getStatus();
                    first = salon.getSalon().getWork().get(i).getName();
                }


            }

            workhours.setText(work);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context); // this statement is missing
        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            return add;


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabs.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(MyApplication.type_jf_medium);
                }
            }
        }
    }

    private void init(List<Cover> Image) {
        if (Image.size() > 0) {
            if (ImagesArray.size() == 0) {
                mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        for (int i = 0; i < NUM_PAGES; i++) {
                            if (SalonProfile.this.isVisible()) {
                                if (i == mPager.getCurrentItem())
                                    dots[mPager.getCurrentItem()].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot_white));
                                else
                                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot_white));
                            }

                        }

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                for (int i = 0; i < Image.size(); i++)
                    ImagesArray.add(Image.get(i));


                mPager.setAdapter(new SlidingImage_Adapter(getActivity(), ImagesArray));


                final float density = getResources().getDisplayMetrics().density;


                NUM_PAGES = Image.size();
                setUiPageViewController(NUM_PAGES);

                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                        for (int i = 0; i < NUM_PAGES; i++) {
                            if (SalonProfile.this.isVisible()) {
                                if (i == mPager.getCurrentItem())
                                    dots[mPager.getCurrentItem()].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot_white));
                                else
                                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot_white));
                            }

                        }

                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 4000, 4000);

                // Pager listener over indicator
            }

        }
    }

    private void setUiPageViewController(int NUMBER_OF_FRAGMENTS) {

        dots = new ImageView[NUMBER_OF_FRAGMENTS];

        for (int i = 0; i < NUMBER_OF_FRAGMENTS; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot_white));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(5, 0, 5, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot_white));
    }

    public void showError() {
        if (SalonProfile.this.isVisible()) {
            progressBar.setVisibility(View.GONE);
            layout_content.setVisibility(View.GONE);
            layout_folowing.setVisibility(View.GONE);


            RotateAnimation rotate = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);

            rotate.setDuration(2000);
            rotate.setRepeatCount(Animation.INFINITE);
            imagetry.setAnimation(rotate);
            tryagain.setVisibility(View.VISIBLE);
            tryagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ((HomeActivityDrawer)getActivity()).setFragment(new EditSalon());
                    api();
                }
            });
            int gravity = LEFT;
            if (Locale.getDefault().getLanguage() == "ar")
                gravity = RIGHT;
            Alerter.create(getActivity())
                    .setTitle(getResources().getString(R.string.erreur1))
                    .setText(getResources().getString(R.string.erreur2))
                    .setIcon(getResources().getDrawable(R.drawable.wifisi))
                    .setContentGravity(gravity)

                    .setDuration(2000)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Alerter.hide();
                        }

                    })
                    .show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putParcelable("salon", salon);
        // etc.
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            if (mFragmentList.size() < 3) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
