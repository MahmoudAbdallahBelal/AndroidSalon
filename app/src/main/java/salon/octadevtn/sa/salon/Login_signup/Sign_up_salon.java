package salon.octadevtn.sa.salon.Login_signup;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Adaptor.AdapterWork;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCity;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCountry;
import salon.octadevtn.sa.salon.Adaptor.AdaptorServicefor;
import salon.octadevtn.sa.salon.Api.ApiSalonProfile;
import salon.octadevtn.sa.salon.Api.ListeCountry;
import salon.octadevtn.sa.salon.Api.VerifUserName;
import salon.octadevtn.sa.salon.Models.City;
import salon.octadevtn.sa.salon.Models.Country;
import salon.octadevtn.sa.salon.Models.Salon;
import salon.octadevtn.sa.salon.Models.SalonType;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.DatePickerFragment;
import salon.octadevtn.sa.salon.Utils.Circlebutton.CircularProgressButton;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sign_up_salon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sign_up_salon extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static TextView sincefrom;
    public static String since;
    public static long since_l = 0;
    public static boolean is_sign_up = false;
    String token;
    String genre;
    @BindView(R.id.name_user)
    EditText name_user;
    @BindView(R.id.worke)
    LinearLayout worke;
    @BindView(R.id.login_user)
    EditText login_user;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.city0)
    TextView city0;
    @BindView(R.id.phone_user)
    EditText phone_user;
    @BindView(R.id.email_user)
    EditText email_user;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.terme)
    CheckBox terme;
    @BindView(R.id.login)
    CircularProgressButton login;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.country0)
    TextView country0;
    @BindView(R.id.genre_user)
    TextView genre_user;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.service)
    TextView service;
    @BindView(R.id.usernameverif)
    ImageView usernameverif;
    @BindView(R.id.usernameprogress)
    ProgressBar usernameprogress;
    @BindView(R.id.servicefor)
    TextView servicefor;
    @BindView(R.id.condi)
    TextView condi;
    @BindView(R.id.prograess)
    AVLoadingIndicatorView progress;

    Boolean pass = true;
    boolean username_verifed = false;
    String work_s = "salon";
    View view;
    AdaptorCountry adaptorCountry;
    AdaptorCity adaptorCity;
    AdaptorServicefor adaptorServicefor;
    Dialog dialog;
    RecyclerView recyclerView;
    ArrayAdapter spinnerArrayAdapter;
    SalonType[] SalonType;
    List<SalonType> salonTypesListe;

    String service_for;
    Dialog dialog1;
    String citys, countrys;
    private List<Country> country1s = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Sign_up_salon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sign_up_salon.
     */
    // TODO: Rename and change types and number of parameters
    public static Sign_up_salon newInstance(String param1, String param2) {
        Sign_up_salon fragment = new Sign_up_salon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up_salon, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        salonTypesListe = new ArrayList<>();
        token = FirebaseInstanceId.getInstance().getToken();
        sincefrom = (TextView) view.findViewById(R.id.sincefrom);
        email_user.setTypeface(MyApplication.type_jf_regular);
        servicefor.setTypeface(MyApplication.type_jf_regular);
        phone_user.setTypeface(MyApplication.type_jf_regular);
        country0.setTypeface(MyApplication.type_jf_regular);
        country.setTypeface(MyApplication.type_jf_regular);
        city.setTypeface(MyApplication.type_jf_regular);
        city0.setTypeface(MyApplication.type_jf_regular);
        email_user.setTypeface(MyApplication.type_jf_regular);
        password.setTypeface(MyApplication.type_jf_regular);
        terme.setTypeface(MyApplication.type_jf_regular);
        login.setTypeface(MyApplication.type_jf_regular);
        name_user.setTypeface(MyApplication.type_jf_regular);
        login_user.setTypeface(MyApplication.type_jf_regular);
        sincefrom.setTypeface(MyApplication.type_jf_regular);
        work.setTypeface(MyApplication.type_jf_regular);
        service.setTypeface(MyApplication.type_jf_regular);
        view.findViewById(R.id.countrys).setOnClickListener(new View.OnClickListener() {
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

            }
        });
        view.findViewById(R.id.citys).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!country.getText().toString().equals("")) {
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

        sincefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
                is_sign_up = true;
            }
        });
        login_user.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                new VerifUserName().Costumer(login_user.getText().toString()
                        , new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                Boolean verif = (Boolean) result;
                                if (verif) {
                                    verifusername(100);
                                    username_verifed = true;
                                } else {
                                    verifusername(-1);
                                    username_verifed = false;
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

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                verifusername(50);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = true;
                if (name_user.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(name_user);
                    pass = false;
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_name), Toast.LENGTH_SHORT).show();
                } else if (!username_verifed) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(login_user);
                    pass = false;


                } else if (work_s.equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(work);
                    pass = false;

                }
                if (name_user.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(name_user);
                    pass = false;
                } else if (country.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(country0);
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_country), Toast.LENGTH_SHORT).show();
                    pass = false;
                } else if (since_l == 0) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(sincefrom);
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_since), Toast.LENGTH_SHORT).show();

                    pass = false;
                } else if (city.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(city0);
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_city), Toast.LENGTH_SHORT).show();

                    pass = false;
                }
                else if (email_user.getText().toString().equals("") || !isEmailValid(email_user.getText().toString()))
                {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(email_user);
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_mail), Toast.LENGTH_SHORT).show();
                    pass = false;
                } else if (genre_user.getText().toString().equals("")) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(service);
                    pass = false;
                } else if (password.getText().toString().equals("") || password.getText().toString().length() < 6) {
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(password);
                    Toasty.warning(getActivity(), getActivity().getResources().getString(R.string.w_password), Toast.LENGTH_SHORT).show();
                    pass = false;


                } else if (!terme.isChecked()) {
                    pass = false;
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .repeat(1)
                            .playOn(terme);

                } else if (pass) {
                    Salon salon = new Salon();
                    login.setVisibility(View.GONE);
                    progress.setVisibility(View.VISIBLE);

                    new salon.octadevtn.sa.salon.Api.Sign_up_salon().salon(email_user.getText().toString(),
                            password.getText().toString(),
                            name_user.getText().toString()
                            , login_user.getText().toString(),
                            service_for,
                            since_l + "",
                            phone_user.getText().toString(),
                            citys,
                            countrys,
                            genre, token
                            , new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (result != null) {
                                        Dialog dialog = new Dialog(getActivity());
                                        dialog.setContentView(R.layout.alert);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        dialog.show();
                                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                        lp.copyFrom(dialog.getWindow().getAttributes());
                                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                        dialog.getWindow().setAttributes(lp);
                                        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                getActivity().finish();
                                            }
                                        });
                                        ((TextView) dialog.findViewById(R.id.content)).setText(getActivity().getResources().getString(R.string.evailvalid));

                                       // login.setProgress(100);
                                    } else{
                                        login.setVisibility(View.VISIBLE);
                                        progress.setVisibility(View.GONE);

                                    }
                                        //login.setProgress(-1);

                                }

                                @Override
                                public void onFailure(Object result) {
                                    ResponseErrors responseError = (ResponseErrors) result;
                                    String Error = "Failure";
                                   //// login.setProgress(-1);
                                    login.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);


                                }

                                @Override
                                public void OnError(String message) {
                                  //  login.setProgress(-1);
                                    login.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.GONE);


                                }

                                @Override
                                public void onFinish() {
                                }
                            });
                }
            }
        });
        RelativeLayout mainLayout = (RelativeLayout) view.findViewById(R.id.relative); // You must use the layout root
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);

        adaptorCountry = new AdaptorCountry(country1s, getActivity());


        adaptorCity = new AdaptorCity(cities, getActivity());
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
        ListeCountry();
        worke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ListeCountry(dialog1);
                dialog1.show();
                // ;
            }
        });
        view.findViewById(R.id.services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(genre_user);
            }
        });
        adaptorCountry.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {

                if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
                    country.setText(country1s.get(position).getCountry().getNameEn());
                } else {
                    country.setText(country1s.get(position).getCountry().getNameAr());
                }
                countrys = country1s.get(position).getCountry().getNameEn();


                city.setText("");
                cities.clear();
                cities.addAll(country1s.get(position).getCity());
                dialog.dismiss();
            }
        });
        adaptorCity.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
                    city.setText(cities.get(position).getNameEn());
                } else {
                    city.setText(cities.get(position).getNameAr());
                }
                citys = cities.get(position).getNameEn();
                dialog.dismiss();

            }
        });
        adaptorServicefor.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                    servicefor.setText(salonTypesListe.get(position).getName());
                else servicefor.setText(salonTypesListe.get(position).getName_en());

                service_for = salonTypesListe.get(position).getId() + "";
                dialog1.dismiss();
            }
        });
        condi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ConditionPolicy.class));
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_left);
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

    public void verifusername(int key) {
        switch (key) {
            case -1:
                usernameprogress.setVisibility(View.GONE);
                usernameverif.setVisibility(View.VISIBLE);
                usernameverif.setImageDrawable(getResources().getDrawable(R.drawable.error_verif));
                break;
            case 50:
                usernameprogress.setVisibility(View.VISIBLE);
                usernameverif.setVisibility(View.GONE);
                break;
            case 100:
                usernameprogress.setVisibility(View.GONE);
                usernameverif.setVisibility(View.VISIBLE);
                usernameverif.setImageDrawable(getResources().getDrawable(R.drawable.success_verif));
                break;
        }
    }

    public void showPopupMenu(View view) {
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
                genre = "men";
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.women).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre_user.setText(((TextView) dialog.findViewById(R.id.women)).getText().toString());
                genre = "women";
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre_user.setText(((TextView) dialog.findViewById(R.id.all)).getText().toString());
                genre = "all";
                dialog.dismiss();
            }
        });

    }

    public void ListeCountry() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.color2));
        pDialog.setTitleText(getResources().getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();
        new ListeCountry().ListeCountry(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) throws JSONException {
                if (result != null) {
                    pDialog.dismiss();
                    country1s.clear();
                    Country[] country = (Country[]) result;
                    List<City> cities1 = new ArrayList<City>();
                    for (int i = 0; i < country.length; i++) {
                        country1s.add(country[i]);
                    }

                    SalonTypes();
                } else {
                    pDialog.dismiss();
                    ListeCountry();
                }

            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";

                pDialog.dismiss();
                ListeCountry();
            }

            @Override
            public void OnError(String message) {
                pDialog.dismiss();
                ListeCountry();
            }

            @Override
            public void onFinish() {
            }
        });

    }

    public void SalonTypes() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.color2));
        pDialog.setTitleText(getResources().getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();

        new ApiSalonProfile().SalonTypes(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) throws JSONException {
                if (result != null) {
                    pDialog.dismiss();
                    SalonType = (salon.octadevtn.sa.salon.Models.SalonType[]) result;
                    for (int i = 0; i < SalonType.length; i++) {
                        salonTypesListe.add(SalonType[i]);

                        adaptorServicefor.notifyDataSetChanged();
                    }

                } else {
                    pDialog.dismiss();

                    SalonTypes();
                }
            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";
                pDialog.dismiss();

                SalonTypes();

            }

            @Override
            public void OnError(String message) {
                pDialog.dismiss();

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
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
