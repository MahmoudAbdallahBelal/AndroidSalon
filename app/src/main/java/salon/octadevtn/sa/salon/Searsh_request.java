package salon.octadevtn.sa.salon;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import salon.octadevtn.sa.salon.Adaptor.AdapterWork;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCity;
import salon.octadevtn.sa.salon.Adaptor.AdaptorCountry;
import salon.octadevtn.sa.salon.Api.ListeCountry;
import salon.octadevtn.sa.salon.Models.City;
import salon.octadevtn.sa.salon.Models.Country;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Searsh;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.Searsh.LoadFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Searsh_request#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searsh_request extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static Searsh searsh;
    @BindView(R.id.Search)
    Button Search;
    @BindView(R.id.country0)
    TextView country0;
    @BindView(R.id.city0)
    TextView city0;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.genre_user)
    TextView genre_user;
    @BindView(R.id.genre_user0)
    TextView genre_user0;
    @BindView(R.id.titre)
    TextView titre;
    @BindView(R.id.country)
    TextView country;
    @BindView(R.id.sort0)
    TextView sort0;
    @BindView(R.id.sort)
    TextView sort;

    @BindView(R.id.trie)
    LinearLayout trie;
    @BindView(R.id.sex)
    LinearLayout sex;
    @BindView(R.id.ct)
    LinearLayout ct;
    @BindView(R.id.count)
    LinearLayout count;


    Dialog dialog;
    RecyclerView recyclerView;
    AdaptorCountry adaptorCountry;
    AdaptorCity adaptorCity;
    private List<Country> country1s = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Searsh_request() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searsh_request.
     */
    // TODO: Rename and change types and number of parameters
    public static Searsh_request newInstance(String param1, String param2) {
        Searsh_request fragment = new Searsh_request();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(MoveAnimation.LEFT, enter, 500L);
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
        View view = inflater.inflate(R.layout.fragment_searsh_request, container, false);
        ButterKnife.bind(this, view);
        adaptorCountry = new AdaptorCountry(country1s, getActivity());
        adaptorCity = new AdaptorCity(cities, getActivity());


        LinearLayout linearLayoutSortBy=view.findViewById(R.id.trie);

        linearLayoutSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu_sort(sort);

            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu_sort(sort);

            }
        });
        sort0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu_sort(sort);

            }
        });


        country0.setTypeface(MyApplication.type_jf_regular);
        sort.setTypeface(MyApplication.type_jf_regular);
        sort0.setTypeface(MyApplication.type_jf_regular);
        genre_user0.setTypeface(MyApplication.type_jf_regular);
        genre_user.setTypeface(MyApplication.type_jf_regular);
        city.setTypeface(MyApplication.type_jf_regular);
        titre.setTypeface(MyApplication.type_jf_medium);
        city0.setTypeface(MyApplication.type_jf_regular);
        country.setTypeface(MyApplication.type_jf_regular);
        count.setOnClickListener(new View.OnClickListener() {
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
                ListeCountry();
            }
        });
        ct.setOnClickListener(new View.OnClickListener() {
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

        adaptorCountry.setRecyclerClickListener(new AdapterWork.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
                    country.setText(country1s.get(position).getCountry().getNameEn());
                } else {
                    country.setText(country1s.get(position).getCountry().getNameAr());
                }
                city.setText("");
                dialog.dismiss();
                cities.clear();
                cities.addAll(country1s.get(position).getCity());
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
                dialog.dismiss();

            }
        });
        Search.setTypeface(MyApplication.type_jf_medium);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String countr = null, cit = null, genre = null, sort = null;
                if (!country.getText().toString().equals(""))
                    countr = country.getText().toString();
                if (!city0.getText().toString().equals(""))
                    cit = city.getText().toString();
                if (sort0.getText().toString().length() > 0)
                    sort = sort0.getText().toString();
                if (genre_user0.getText().toString().length() > 0)
                    genre = genre_user0.getText().toString();
                Searsh(countr, cit, genre, sort, Static.searsh);
            }
        });
        trie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu_sort(sort);
            }
        });
      /*  sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(genre_user);
            }
        });*/
        return view;

    }

    @OnClick(R.id.sex)
    public void setSex() {
        showPopupMenu(genre_user);
    }

    @OnClick(R.id.genre_user0)
    public void setSex1() {
        showPopupMenu(genre_user);
    }

    @OnClick(R.id.genre_user)
    public void setSex2() {
        showPopupMenu(genre_user);
    }

    public void Searsh(String country, String city, String genre, String sort, final String search) {
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        Profile profile = null;
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        new salon.octadevtn.sa.salon.Api.Searsh().Searsh(country, city, genre, sort, profile.getUserId() + "", profile.getType(), search
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            Gson gson = new Gson();
                            JSONObject responseObj = null;
                            try {
                                responseObj = new JSONObject(String.valueOf(result));
                                searsh = (gson.fromJson(String.valueOf(responseObj), Searsh.class));
                            } catch (JSONException e) {
                                Log.d("responseresult", e.toString());
                            }
                            LoadFragment(new Searsh_pager());
                            Static.frag = new Searsh_pager();
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void showPopupMenu(View view) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.gendre1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.findViewById(R.id.women).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genre_user.setText(getActivity().getResources().getString(R.string.women));
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genre_user.setText(getActivity().getResources().getString(R.string.man));
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genre_user.setText(getActivity().getResources().getString(R.string.man));
                dialog.dismiss();
            }
        });


    }

    private void showPopupMenu_sort(View view) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.sort);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort.setText(getActivity().getResources().getString(R.string.all));
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.salon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort.setText(getActivity().getResources().getString(R.string.salons));
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort.setText(getActivity().getResources().getString(R.string.user));
                dialog.dismiss();
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
                    recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
                    recyclerView.setItemViewCacheSize(999);
                    GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gl);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setDrawingCacheEnabled(true);
                    adaptorCountry.notifyDataSetChanged();
                    recyclerView.setAdapter(adaptorCountry);


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

}
