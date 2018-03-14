package salon.octadevtn.sa.salon;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.lypeer.fcpermission.FcPermissionsB;
import com.lypeer.fcpermission.impl.OnPermissionsDeniedListener;
import com.lypeer.fcpermission.impl.OnPermissionsGrantedListener;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Adaptor.AdapterWork;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCity;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCountry;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCover;
import salon.octadevtn.sa.salon.Adaptor.AdaptorServicefor;
import salon.octadevtn.sa.salon.Api.ApiSalonProfile;
import salon.octadevtn.sa.salon.Api.ListeCountry;
import salon.octadevtn.sa.salon.Api.UpdateProfilSalon;
import salon.octadevtn.sa.salon.Models.City;
import salon.octadevtn.sa.salon.Models.Country;
import salon.octadevtn.sa.salon.Models.CoverModel;
import salon.octadevtn.sa.salon.Models.Payment_;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Salon;
import salon.octadevtn.sa.salon.Models.SalonType;
import salon.octadevtn.sa.salon.Models.WordDayHour;
import salon.octadevtn.sa.salon.Reservation.DatePickerFragment;
import salon.octadevtn.sa.salon.Reservation.TimePickerFragment;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

import static android.app.Activity.RESULT_OK;
import static salon.octadevtn.sa.salon.HomeActivityDrawer.context;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditProfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int READ_STORAGE_CODE = 1001;
    private static final int WRITE_STORAGE_CODE = 1002;
    public static int PICK_PHOTO_FOR_AVATAR = 102;
    public static TextView OpenSince;
    public static String since_from;
    public static AdapterWork adapterWork;
    public static List<WordDayHour> WorkListe = new ArrayList<>();
    salon.octadevtn.sa.salon.Models.SalonType[] SalonType;
    List<SalonType> salonTypesListe;
    AdaptorServicefor adaptorServicefor;
    Dialog dialog1;
    String work_type;
    Salon salon = null;
    boolean con = false;
    @BindView(R.id.recycler_work)
    RecyclerView recycler_work;
    @BindView(R.id.recycler_image)
    RecyclerView recycler_image;
    @BindView(R.id.toggleButton1)
    ToggleButton toggleButton1;
    @BindView(R.id.toggleButton2)
    ToggleButton toggleButton2;
    @BindView(R.id.toggleButton3)
    ToggleButton toggleButton3;
    @BindView(R.id.toggleButton4)
    ToggleButton toggleButton4;
    @BindView(R.id.map)
    LinearLayout map;
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.t0)
    TextView t0;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.map_location)
    TextView map_location;
    @BindView(R.id.genre_user)
    TextView genre_user;
    @BindView(R.id.payment)
    TextView payment;
    @BindView(R.id.country0)
    TextView country0;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.salon_name)
    EditText salon_name;
    @BindView(R.id.login_user)
    EditText login_user;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.adresse)
    EditText adresse;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.phone_user)
    EditText phone_user;
    @BindView(R.id.email_user)
    EditText email_user;
    @BindView(R.id.Branches)
    TextView Branches;
    @BindView(R.id.branchedetail)
    MultiAutoCompleteTextView branchedetail;
    @BindView(R.id.done)
    ImageView done;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.cover)
    LinearLayout cover;
    @BindView(R.id.usernameverif)
    ImageView mapverif;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.prog)
    ProgressBar progr;
    @BindView(R.id.worktype)
    TextView worktype;
    Bitmap selectedImage, selectedImage1;
    ArrayList<String> imagesEncodedList;
    AdaptorCover adaptorCover;
    ArrayList<CoverModel> mArrayUri = new ArrayList<>();
    String email, branches, _name, username, salon_type,
            phone_number, city_s, contry, work_s,
            payment_s, gender, latitude, longitude, album;
    List<Payment_> payment_list = new ArrayList<>();
    Dialog dialog;
    RecyclerView recyclerView;
    Profile profile = null;
    AdaptorCountry adaptorCountry;
    AdaptorCity adaptorCity;
    String citys, contrys;
    Bitmap bitmap;
    private List<Country> country1s = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditProfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfil.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfil newInstance(String param1, String param2) {
        EditProfil fragment = new EditProfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);

        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        salon = new Salon();
        FcPermissionsB mFcPermissionsB = new FcPermissionsB.Builder(getActivity())
                .onGrantedListener(new OnPermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(int requestCode, List<String> perms) {
                        if (ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(getActivity(),
                                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }

                    }
                })
                .onDeniedListener(new OnPermissionsDeniedListener() {
                    @Override
                    public void onPermissionsDenied(int requestCode, List<String> perms) {

                    }
                })
                .positiveBtn4ReqPer(android.R.string.ok)
                .negativeBtn4ReqPer(R.string.cancel)
                .positiveBtn4NeverAskAgain(R.string.setting)
                .negativeBtn4NeverAskAgain(R.string.cancel)
                .rationale4ReqPer(getString(R.string.prompt_request_camara))//必需
                .rationale4NeverAskAgain(getString(R.string.prompt_we_need_camera))//必需
                .requestCode(200)
                .build();
        mFcPermissionsB.requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION);//request permissions

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profil, container, false);
        ButterKnife.bind(this, view);
        salonTypesListe = new ArrayList<>();

        adaptorCountry = new AdaptorCountry(country1s, getActivity());
        adaptorCity = new AdaptorCity(cities, getActivity());
        ((TextView) view.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) view.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);

        OpenSince = (TextView) view.findViewById(R.id.OpenSince);
        t0.setTypeface(MyApplication.type_jf_regular);
        payment.setTypeface(MyApplication.type_jf_regular);
        t1.setTypeface(MyApplication.type_jf_regular);
        t1.setTypeface(MyApplication.type_jf_regular);
        work.setTypeface(MyApplication.type_jf_regular);
        map_location.setTypeface(MyApplication.type_jf_regular);
        phone_user.setTypeface(MyApplication.type_jf_regular);
        country0.setTypeface(MyApplication.type_jf_regular);
        genre_user.setTypeface(MyApplication.type_jf_regular);
        salon_name.setTypeface(MyApplication.type_jf_regular);
        login_user.setTypeface(MyApplication.type_jf_regular);
        city.setTypeface(MyApplication.type_jf_regular);
        adresse.setTypeface(MyApplication.type_jf_regular);
        email_user.setTypeface(MyApplication.type_jf_regular);
        OpenSince.setTypeface(MyApplication.type_jf_regular);
        branchedetail.setTypeface(MyApplication.type_jf_regular);
        Branches.setTypeface(MyApplication.type_jf_regular);
        back.setTypeface(MyApplication.type_jf_regular);
        worktype.setTypeface(MyApplication.type_jf_regular);

        email = salon.getSalonInfo().getEmail();
        branches = salon.getSalonInfo().getBranches();
        _name = salon.getSalonInfo().getSalonName();
        username = salon.getSalonInfo().getUsername();
        salon_type = salon.getSalonInfo().getSalonType();
        since_from = salon.getSalonInfo().getSinceFrom();
        phone_number = salon.getSalonInfo().getPhoneNumber();
        city_s = salon.getSalonInfo().getCity();
        contry = salon.getSalonInfo().getCountry();
        work_s = MyApplication.getInstance().getPrefManager().getWork();
        payment_s = MyApplication.getInstance().getPrefManager().getPayment();
        gender = salon.getSalonInfo().getGender();
        latitude = String.valueOf(salon.getSalonInfo().getLatitude());
        longitude = String.valueOf(salon.getSalonInfo().getLongutide());
        album = salon.getSalonInfo().getAlbum();
        recycler_work.setHasFixedSize(true);

        GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recycler_work.setLayoutManager(gl);
        recycler_work.setItemAnimator(new DefaultItemAnimator());
        recycler_work.setDrawingCacheEnabled(true);
        String json = new Gson().toJson(MyApplication.getInstance().getPrefManager().getWork());
        Gson gson = new Gson();
        WorkListe.clear();
        WordDayHour[] dayHour = gson.fromJson(MyApplication.getInstance().getPrefManager().getWork(), WordDayHour[].class);
        for (int i = 0; i < dayHour.length; i++) {
            WorkListe.add(dayHour[i]);
        }
        adapterWork = new AdapterWork(WorkListe, getActivity());
        recycler_work.setAdapter(adapterWork);
        adapterWork.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                Static.position = position;
                Static.time = "start";
                DialogFragment timePickerDialog = new TimePickerFragment();
                timePickerDialog.show(getFragmentManager(), "timePicker");
            }
        });
        adapterWork.setRecyclerClickListener1(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                Static.position = position;
                Static.time = "end";
                DialogFragment timePickerDialog = new TimePickerFragment();
                timePickerDialog.show(getFragmentManager(), "timePicker");
            }
        });

        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_list.get(0).setStatus(toggleButton1.isChecked());
            }
        });
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_list.get(2).setStatus(toggleButton2.isChecked());
            }
        });
        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_list.get(1).setStatus(toggleButton3.isChecked());
            }
        });
        toggleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_list.get(3).setStatus(toggleButton4.isChecked());
            }
        });
        map_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), MapsActivity.class), 200);
            }
        });
        /*Glide.with(getActivity()).load(UrlStatic.pathImag + profile.getSalon().getPhoto())
                .thumbnail(0.5f)
                .crossFade()
                .override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);*/

        ListeCountry();

        profile_image.setBorderColor(getResources().getColor(R.color.noir));
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 125);
            }
        });
        genre_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.gendre_s);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                dialog.findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genre_user.setText(((TextView) dialog.findViewById(R.id.man)).getText().toString());
                        gender = "men";
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.women).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genre_user.setText(((TextView) dialog.findViewById(R.id.women)).getText().toString());
                        gender = "women";
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genre_user.setText(((TextView) dialog.findViewById(R.id.all)).getText().toString());
                        gender = "all";
                        dialog.dismiss();
                    }
                });

            }
        });
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePickerIntent();
            }
        });
        Verif();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                work_s = new Gson().toJson(WorkListe);
                payment_s = new Gson().toJson(payment_list);
                MyApplication.getInstance().getPrefManager().setWork(work_s);
                MyApplication.getInstance().getPrefManager().setPayment(payment_s);
                work_s = work_s.replaceAll("\"", "'");
                payment_s = payment_s.replaceAll("\"", "'");
                UpdateSalon(email, branches, _name, username, salon_type,
                        since_from, phone_number, citys, contrys, work_s,
                        payment_s, gender, latitude, longitude, album, selectedImage1, work_type, adresse.getText().toString());
                progr.setVisibility(View.VISIBLE);
                done.setVisibility(View.GONE);
            }
        });
        OpenSince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
                Static.sincefrom = true;
            }
        });

        adaptorCountry.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {

                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    country0.setText(country1s.get(position).getCountry().getNameAr());
                } else {
                    country0.setText(country1s.get(position).getCountry().getNameEn());
                }
                city.setText("");
                cities.clear();
                cities.addAll(country1s.get(position).getCity());
                dialog.dismiss();
                contrys = country1s.get(position).getCountry().getNameEn();

            }
        });
        adaptorCity.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {

                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    city.setText(cities.get(position).getNameAr());
                } else {
                    city.setText(cities.get(position).getNameEn());
                }
                citys = cities.get(position).getNameEn();
                dialog.dismiss();

            }
        });
        country0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.block_list);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
                recyclerView.setItemViewCacheSize(999);
                GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gl);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setDrawingCacheEnabled(true);
                adaptorCountry.notifyDataSetChanged();
                recyclerView.setAdapter(adaptorCountry);
                con = true;

            }
        });
        worktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adaptorServicefor = new AdaptorServicefor(salonTypesListe, getActivity());
                dialog1 = new Dialog(getActivity());
                dialog1.setContentView(R.layout.block_list);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog1.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog1.getWindow().setAttributes(lp);
                recyclerView = (RecyclerView) dialog1.findViewById(R.id.recycler);
                recyclerView.setItemViewCacheSize(999);
                GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gl);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setAdapter(adaptorServicefor);
                dialog1.show();
                adaptorServicefor.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                            worktype.setText(salonTypesListe.get(position).getName());
                        else worktype.setText(salonTypesListe.get(position).getName_en());

                        work_type = salonTypesListe.get(position).getId() + "";
                        dialog1.dismiss();
                    }
                });
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!country0.getText().toString().equals("") && con) {
                    dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.block_list);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
                    recyclerView.setItemViewCacheSize(999);
                    GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gl);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setDrawingCacheEnabled(true);
                    adaptorCity.notifyDataSetChanged();
                    recyclerView.setAdapter(adaptorCity);
                } else {
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.notice), Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        recycler_image.setHasFixedSize(true);
        recycler_image.setItemViewCacheSize(999);
        GridLayoutManager gl1 = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
        recycler_image.setLayoutManager(gl1);
        recycler_image.setItemAnimator(new DefaultItemAnimator());
        recycler_image.setDrawingCacheEnabled(true);

        adaptorCover = new AdaptorCover(mArrayUri, getActivity());
        recycler_image.setAdapter(adaptorCover);

        return view;
    }

    private void UpdateSalon(String email, String branches, String _name, String username, String salon_type, String since_from,
                             String phone_number, String city_s, String contry, String work_s, String payment_s, String gender,
                             String latitude, String longitude, String album, Bitmap selectedImage, String work_type, String adress) {
        new UpdateProfilSalon().UpdateProfilSalon(email, branches, _name, username, salon_type, since_from,
                phone_number, city_s, contry, work_s, payment_s, gender,
                latitude, longitude, album, selectedImage, work_type, adress
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        done.setVisibility(View.VISIBLE);
                        progr.setVisibility(View.GONE);

                        if (result != null) {
                            done.setVisibility(View.VISIBLE);
                            progr.setVisibility(View.GONE);
                            Toasty.success(getActivity(), getResources().getString(R.string.succes), Toast.LENGTH_SHORT, true).show();
                            api2();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        done.setVisibility(View.VISIBLE);
                        progr.setVisibility(View.GONE);


                    }

                    @Override
                    public void OnError(String message) {
                        done.setVisibility(View.VISIBLE);
                        progr.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFinish() {
                        done.setVisibility(View.VISIBLE);
                        progr.setVisibility(View.GONE);

                    }
                });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 200:
                if (resultCode == RESULT_OK) {

                    latitude = imageReturnedIntent.getStringExtra("lat");
                    longitude = imageReturnedIntent.getStringExtra("lon");
                    mapverif.setVisibility(View.VISIBLE);

                    mapverif.setImageDrawable(getResources().getDrawable(R.drawable.success_verif));



                    try {
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getActivity(), Locale.getDefault());

                        double lat=Double.parseDouble(latitude);
                        double lon=Double.parseDouble(longitude);

                        addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                       if(!country.equals(null) || !city.equals(null))
                        map_location.setText(country+ " , "+city);
                    }
                    catch (Exception e)
                    {
                      e.printStackTrace();
                    }
                } else {
                    mapverif.setVisibility(View.VISIBLE);
                    mapverif.setImageDrawable(getResources().getDrawable(R.drawable.error_verif));
                }
                break;
            case 125:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        selectedImage1 = BitmapFactory.decodeStream(imageStream);
                        profile_image.setImageBitmap(selectedImage1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 120:
                try {
                    if (resultCode == RESULT_OK) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        imagesEncodedList = new ArrayList<String>();
                        String imageEncoded;
                        if (imageReturnedIntent.getData() != null) {
                            Uri mImageUri = imageReturnedIntent.getData();

                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                                    filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                            CoverModel coverModel = new CoverModel();
                            coverModel.setStatus(false);
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(mImageUri);
                            selectedImage = BitmapFactory.decodeStream(imageStream);

                            coverModel.setBitmap(selectedImage);
                            mArrayUri.add(coverModel);
                            adaptorCover.notifyDataSetChanged();


                            imageEncoded = cursor.getString(columnIndex);
                            cursor.close();
                            imagesEncodedList.add(imageEncoded);

                        } else {
                            if (imageReturnedIntent.getClipData() != null) {
                                ClipData mClipData = imageReturnedIntent.getClipData();
                                for (int i = 0; i < mClipData.getItemCount(); i++) {
                                    ClipData.Item item = mClipData.getItemAt(i);
                                    Uri uri = item.getUri();
                                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                                    selectedImage = BitmapFactory.decodeStream(imageStream);
                                    CoverModel coverModel = new CoverModel();
                                    coverModel.setStatus(false);
                                    coverModel.setBitmap(selectedImage);
                                    mArrayUri.add(coverModel);
                                    adaptorCover.notifyDataSetChanged();
                                    // Get the cursor
                                    Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                                    // Move to first row
                                    cursor.moveToFirst();
                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    imageEncoded = cursor.getString(columnIndex);
                                    imagesEncodedList.add(imageEncoded);
                                    cursor.close();
                                }
                                Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }


        }
    }

    public void Verif() {
        salon_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _name = salon_name.getText().toString();

            }
        });
        login_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                username = login_user.getText().toString();

            }
        });
        genre_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                gender = genre_user.getText().toString();

            }
        });
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                city_s = city.getText().toString();
            }
        });
        country0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                contry = country0.getText().toString();
            }
        });
        email_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                email = email_user.getText().toString();
            }
        });
        phone_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone_number = phone_user.getText().toString();

            }
        });
        branchedetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                branches = branchedetail.getText().toString();

            }
        });
    }

    public void ListeCountry() {
        new ListeCountry().ListeCountry(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    country1s.clear();
                    Country[] country = (Country[]) result;
                    List<City> cities1 = new ArrayList<City>();
                    for (int i = 0; i < country.length; i++) {
                        country1s.add(country[i]);
                    }

                    api();
                    SalonTypes();
                }


            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";
                ListeCountry();

            }

            @Override
            public void OnError(String message) {
                ListeCountry();
            }

            @Override
            public void onFinish() {
            }
        });

    }

    private void openImagePickerIntent() {

        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 120);

        } else {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_CODE);
        }

    }

    private boolean isPermissionGranted(String permission) {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getActivity(), permission);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestPermission(String permission, int code) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, code);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == READ_STORAGE_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openImagePickerIntent();

            }
        } else if (requestCode == WRITE_STORAGE_CODE) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    public void api() {
        new ApiSalonProfile().salon(profile.getSalon().getId()
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            progressBar.setVisibility(View.GONE);
                            scrollView.setAlpha(1);

                            final salon.octadevtn.sa.salon.Models.SalonProfile salon = (salon.octadevtn.sa.salon.Models.SalonProfile) result;
                            Glide.with(getActivity()).load(UrlStatic.pathImag + salon.getSalon().getPhoto())
                                    .thumbnail(0.5f)
                                    .crossFade()
                                    .override(100, 100)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(profile_image);
                            for (int i = 0; i < salon.getSalon().getCover().size(); i++) {
                                CoverModel coverModel = new CoverModel();
                                coverModel.setStatus(true);
                                coverModel.setCover(salon.getSalon().getCover().get(i));
                                mArrayUri.add(coverModel);
                                adaptorCover.notifyDataSetChanged();
                            }
                            salon_name.setText(salon.getSalon().getSalonName());
                            login_user.setText(salon.getSalon().getUsername());
                            email_user.setText(salon.getSalon().getEmail());
                            phone_user.setText(salon.getSalon().getPhoneNumber());
                            city.setText(salon.getSalon().getCity());
                            adresse.setText(salon.getSalon().getAdress());
                            country0.setText(salon.getSalon().getCountry());
                            worktype.setText(salon.getSalon().getSalonType());
                            genre_user.setText(salon.getSalon().getGender());
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis((Long.parseLong(salon.getSalon().getSalonInfo().getSinceFrom())));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = sdf.format(c.getTime());
                            OpenSince.setText(formattedDate);
                            branchedetail.setText(salon.getSalon().getBranches());
                            cities.clear();
                            con = true;
                            for (int i = 0; i < country1s.size(); i++)
                                if (country1s.get(i).getCountry().getNameEn().equals(salon.getSalon().getCountry()))
                                    cities.addAll(country1s.get(i).getCity());


                            for (int i = 0; i < salon.getSalon().getPayment().size(); i++) {
                                payment_list.add(salon.getSalon().getPayment().get(i));
                            }
                            try {
                                toggleButton1.setChecked(payment_list.get(0).getStatus());
                                toggleButton2.setChecked(payment_list.get(2).getStatus());
                                toggleButton3.setChecked(payment_list.get(1).getStatus());
                                toggleButton4.setChecked(payment_list.get(3).getStatus());
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        Toasty.error(getActivity(), Error, Toast.LENGTH_SHORT, true).show();
                        api();
                    }

                    @Override
                    public void OnError(String message) {
                        Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();
                        api();
                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }

    public void api2() {
        done.setVisibility(View.GONE);

        progr.setVisibility(View.VISIBLE);
        new ApiSalonProfile().salon(profile.getSalon().getId()
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            progressBar.setVisibility(View.GONE);
                            final salon.octadevtn.sa.salon.Models.SalonProfile salon = (salon.octadevtn.sa.salon.Models.SalonProfile) result;
                            profile.setSalon(salon.getSalon());
                            GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
                            gsonSharedPrefs.saveObject(profile);
                            done.setVisibility(View.VISIBLE);
                            progr.setVisibility(View.GONE);
                            ((HomeActivityDrawer) getActivity()).settionprofile();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        Toasty.error(getActivity(), Error, Toast.LENGTH_SHORT, true).show();
                    }

                    @Override
                    public void OnError(String message) {
                        Toasty.error(getActivity(), message, Toast.LENGTH_SHORT, true).show();
                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }

    public void SalonTypes() {


        new ApiSalonProfile().SalonTypes(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) throws JSONException {
                if (result != null) {
                    SalonType = (salon.octadevtn.sa.salon.Models.SalonType[]) result;
                    for (int i = 0; i < SalonType.length; i++) {
                        salonTypesListe.add(SalonType[i]);

                    }

                } else {

                    SalonTypes();
                }
            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";

                SalonTypes();

            }

            @Override
            public void OnError(String message) {

                SalonTypes();
            }

            @Override
            public void onFinish() {
            }
        });

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
        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            Locale locale = new Locale("ar");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
        }
    }

}
