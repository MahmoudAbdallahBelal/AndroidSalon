package salon.octadevtn.sa.salon.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.tapadoo.alerter.Alerter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Adaptor.CommentAdaptor;
import salon.octadevtn.sa.salon.Api.Service;
import salon.octadevtn.sa.salon.Models.Comment;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.ServiceDetail;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Salon_services#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Salon_services extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    int id, position;
    ArrayList<Comment> activityList;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.promotiontitle)
    TextView titlpromotiontitle;
    @BindView(R.id.promotiondate)
    TextView titlpromotiondate;
    @BindView(R.id.promotiondiscount)
    TextView promotiondiscount;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.imagenew_comment)
    CircleImageView imagenew_comment;
    @BindView(R.id.btn_comment)
    Button btn_comment;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.linlike)
    LinearLayout linlike;
    @BindView(R.id.linfav)
    LinearLayout linfav;
    @BindView(R.id.linshare)
    LinearLayout linshare;
    @BindView(R.id.textshare)
    TextView textshare;
    @BindView(R.id.textlike)
    TextView textlike;
    @BindView(R.id.textcomment)
    TextView textcomment;
    @BindView(R.id.textfav)
    TextView textfav;
    @BindView(R.id.imagelike)
    ImageView imagelike;
    @BindView(R.id.imagefav)
    ImageView imagefav;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    ServiceDetail serviceDetail;
    @BindView(R.id.tryagain)
    LinearLayout tryagain;
    @BindView(R.id.imagetry)
    ImageView imagetry;
    @BindView(R.id.imagecomment)
    ImageView imagecomment;
    @BindView(R.id.imageshare)
    ImageView imageshare;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.ns)
    NestedScrollView ns;
    @BindView(R.id.lincom)
    LinearLayout lincom;
    View v;
    CommentAdaptor adapter;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Salon_services() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Salon_services newInstance(int id, int position) {
        Salon_services fragment = new Salon_services();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    public static Salon_services newInstance(int id) {
        Salon_services fragment = new Salon_services();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static String theMonth(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            if (getArguments().containsKey(ARG_PARAM2))
                position = getArguments().getInt(ARG_PARAM2);
            else position = 1;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_salon_services, container, false);
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);
        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ButterKnife.bind(this, v);
        lincom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ns.scrollTo(0, ns.getBottom());
            }
        });
        title.setTypeface(MyApplication.type_jf_medium);
        titlpromotiontitle.setTypeface(MyApplication.type_jf_medium);
        promotiondiscount.setTypeface(MyApplication.type_jf_regular);
        titlpromotiondate.setTypeface(MyApplication.type_jf_regular);
        btn_comment.setTypeface(MyApplication.type_jf_regular);
        description.setTypeface(MyApplication.type_jf_light);
        comment.setTypeface(MyApplication.type_jf_light);
        textfav.setTypeface(MyApplication.type_jf_regular);
        textcomment.setTypeface(MyApplication.type_jf_regular);
        textlike.setTypeface(MyApplication.type_jf_regular);
        textshare.setTypeface(MyApplication.type_jf_regular);

        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        Profile profile = null;
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

        if (profile.getType().equals("salon")) {
            imagenew_comment.setBorderColor(getResources().getColor(R.color.color1));
        } else {
            if (profile.getUser().getGenre().equals("men")) {
                imagenew_comment.setBorderColor(getResources().getColor(R.color.color2));

            } else imagenew_comment.setBorderColor(getResources().getColor(R.color.color4));

        }


        if (profile.getType().equals("salon")) {
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + profile.getSalon().getPhoto())
                    .override(200, 200)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(imagenew_comment);
        } else {
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + profile.getUser().getPhoto())
                    .override(200, 200)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(imagenew_comment);
        }


        api();

        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                if (position == 0) {
                    ns.scrollTo(0, ns.getBottom());
                }
            }
        }, 1 * 1000);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void api() {


        new Service().getService(id
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {

                            tryagain.setVisibility(View.GONE);
                            lin.setVisibility(View.VISIBLE);

                            serviceDetail = (ServiceDetail) result;
                            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + serviceDetail.getService().getImageUrl())
                                    .override(500, 500)
                                    .centerCrop()
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            //   holder.progressBar.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            //   holder.progressBar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })

                                    .into(image);
                            Zoomy.Builder builder = new Zoomy.Builder(getActivity())
                                    .target(image)
                                    .interpolator(new OvershootInterpolator());
                            builder.register();
                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                            titlpromotiontitle.setText(serviceDetail.getService().getName());
                            title.setText(serviceDetail.getService().getName());

                            titlpromotiondate.setText(serviceDetail.getCategoryname());


                            description.setText(serviceDetail.getService().getDescription());
                            if (serviceDetail.getIsFavorite()) {
                                imagefav.setColorFilter(getResources().getColor(R.color.yellow));
                                textfav.setTextColor(getResources().getColor(R.color.yellow));
                            }
                            if (serviceDetail.getIsLike()) {
                                imagelike.setColorFilter(getResources().getColor(R.color.color4));
                                textlike.setTextColor(getResources().getColor(R.color.color4));
                            }
                            if (serviceDetail.getIsComment()) {
                                imagecomment.setColorFilter(getResources().getColor(R.color.color4));
                                textcomment.setTextColor(getResources().getColor(R.color.color4));
                            }
                            if (serviceDetail.getIsShare()) {
                                imageshare.setColorFilter(getResources().getColor(R.color.color4));
                                textshare.setTextColor(getResources().getColor(R.color.color4));
                            }

                            textcomment.setText(serviceDetail.getCommentCount() + "");
                            textlike.setText(serviceDetail.getLikeCount() + "");
                            textfav.setText(serviceDetail.getFavoriteCount() + "");
                            textshare.setText(serviceDetail.getService().getShare() + "");

                            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
                            activityList = new ArrayList<>();
                            activityList.addAll(serviceDetail.getComment());
                            adapter = new CommentAdaptor(activityList, getActivity());

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);


                            linlike.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (serviceDetail.getIsLike()) {
                                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(Integer.parseInt(serviceDetail.getService().getId()), "service", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textlike.setText(serviceDetail.getLikeCount() - 1 + "");
                                                    serviceDetail.setIsLike("false");
                                                    serviceDetail.setLikeCount(serviceDetail.getLikeCount() - 1);

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
                                        new salon.octadevtn.sa.salon.Api.Comment().Like(serviceDetail.getService().getId(), "service", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                                    textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                                    textlike.setText(serviceDetail.getLikeCount() + 1 + "");
                                                    serviceDetail.setIsLike("true");
                                                    serviceDetail.setLikeCount(serviceDetail.getLikeCount() + 1);

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

                            linshare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String s = serviceDetail.getService().getName();
                                    if (serviceDetail.getService().getName() != null && !serviceDetail.getService().getName().equals("")) {
                                        s = (serviceDetail.getService().getName()).replaceAll(" ", "%20");
                                    }
                                    share("title=" + s
                                            + "&img=" + serviceDetail.getService().getImageUrl() +
                                            "&type=service&id=" + serviceDetail.getService().getId() +
                                            "+&description=" + serviceDetail.getService().getDescription());
                                    Shared(serviceDetail.getService().getId(), "service");
                                }
                            });
                            linfav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (serviceDetail.getIsFavorite()) {
                                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(Integer.parseInt(serviceDetail.getService().getId()), "service", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textfav.setText(serviceDetail.getFavoriteCount() - 1 + "");
                                                    serviceDetail.setIsFavorite("false");
                                                    serviceDetail.setFavoriteCount(serviceDetail.getFavoriteCount() - 1);

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
                                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(Integer.parseInt(serviceDetail.getService().getId()), "service", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                                    textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                                    textfav.setText(serviceDetail.getFavoriteCount() + 1 + "");
                                                    serviceDetail.setIsFavorite("true");
                                                    serviceDetail.setFavoriteCount(serviceDetail.getFavoriteCount() + 1);
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


                            btn_comment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    comment();
                                }
                            });
                        } else showError2();
                    }

                    @Override
                    public void onFailure(Object result) {
                        showError2();

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void OnError(String message) {
                        // showError2();
                        Alerter.hide();
                    }
                }
        );


    }

    public void comment() {
        if (!comment.getText().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            btn_comment.setEnabled(false);
            new salon.octadevtn.sa.salon.Api.Comment().AddComment(serviceDetail.getService().getId(), "service", comment.getText().toString(), new UniversalCallBack() {
                @Override
                public void onResponse(Object result) {
                    if (result != null) {
                        btn_comment.setEnabled(true);
                        progressBar.setVisibility(View.GONE);

                        Comment com = new Comment();
                        com.setContenu(comment.getText().toString());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        com.setCreatedAt(format.format(new Date(System.currentTimeMillis())) + "");
                        String typeuser = "";
                        String iduser = "";
                        String image = "";
                        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
                        Profile profile = null;
                        try {
                            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
                        } catch (ParsingException e) {
                            e.printStackTrace();
                        }

                        if (profile.getType().equals("salon")) {
                            typeuser = "salon";
                            iduser = profile.getSalon().getId() + "";
                            image = profile.getSalon().getPhoto();
                            com.setUsername(profile.getSalon().getUsername());

                        } else {
                            typeuser = "customer";
                            iduser = profile.getUser().getId() + "";
                            com.setGenre(profile.getUser().getGenre());
                            image = profile.getUser().getPhoto();
                            com.setUsername(profile.getUser().getUsername());

                        }
                        com.setIdUser(iduser);
                        com.setTypeUser(typeuser);
                        com.setPhoto(image);
                        activityList.add(com);
                     /*   MediaPlayer mPlayer2;
                        mPlayer2 = MediaPlayer.create(getActivity(), R.raw.notif);
                        mPlayer2.start();
                   */
                        imagecomment.setColorFilter(getResources().getColor(R.color.color4));
                        textcomment.setTextColor(getResources().getColor(R.color.color4));

                        adapter.notifyDataSetChanged();
                        comment.setText("");
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

    public void showError() {
        btn_comment.setEnabled(true);
        progressBar.setVisibility(View.GONE);

        Alerter.create(getActivity())
                .setTitle("Can't load page No internet connection.")
                .setText("Make sure Wi-Fi or cellular data is turned on, then try again.")
                .setDuration(3000)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        comment();
                    }

                })
                .show();
    }

    public void showError2() {
        progressBar2.setVisibility(View.GONE);
        lin.setVisibility(View.VISIBLE);
        // lincomment.setVisibility(View.GONE);


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
                api();
            }
        });
        Alerter.create(getActivity())
                .setTitle("Can't load page No internet connection.")
                .setText("Make sure Wi-Fi or cellular data is turned on, then try again.")
                .setIcon(getResources().getDrawable(R.drawable.wifisi))
                .setDuration(2000)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        api();
                    }

                })
                .show();
    }

    public void share(String s) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, "http://www.salon.com.sa/partage.php?" + s);
        startActivity(Intent.createChooser(share, "Share using!"));
    }

    public void Shared(String id, final String type) {
        new salon.octadevtn.sa.salon.Api.Promotion().Share(id, type, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {

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
