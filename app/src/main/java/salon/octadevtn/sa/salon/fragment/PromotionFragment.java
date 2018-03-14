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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Adaptor.CommentAdaptor;
import salon.octadevtn.sa.salon.Api.Promotion;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Comment;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Promotiondetail;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.Reservation_home;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PromotionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromotionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.imagenew_comment)
    CircleImageView imagenew_comment;
    @BindView(R.id.btn_comment)
    Button btn_comment;
    @BindView(R.id.comment)
    EditText comment;
    @BindView(R.id.tryagain)
    LinearLayout tryagain;
    @BindView(R.id.imagetry)
    ImageView imagetry;
    @BindView(R.id.linlike)
    LinearLayout linlike;
    @BindView(R.id.reserver)
    LinearLayout reserver;
    @BindView(R.id.lincomment)
    LinearLayout lincomment;
    @BindView(R.id.linfav)
    LinearLayout linfav;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.lincom)
    LinearLayout lincom;
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
    @BindView(R.id.reserv)
    TextView reserv;
    @BindView(R.id.imagelike)
    ImageView imagelike;
    @BindView(R.id.imagecomment)
    ImageView imagecomment;
    @BindView(R.id.imageshare)
    ImageView imageshare;

    @BindView(R.id.imagefav)
    ImageView imagefav;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.ns)
    NestedScrollView ns;
    Promotiondetail promotiondetail;
    CommentAdaptor adapter;
    View v;
    Profile profile = null;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    public PromotionFragment() {
        // Required empty public constructor
    }

    public static PromotionFragment newInstance(int id, int position) {
        PromotionFragment fragment = new PromotionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    public static String theMonth(int month) {

        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] monthNames_ar = {"جانفي", "فيفري", "مارس", "أفريل", "ماي", "جوان", "جويلية", "أوت", "سبتمبر", "أكنوبر", "نوفمبر", "ديسمبر"};
        if (Locale.getDefault().getLanguage() == "ar")
            return monthNames_ar[month];

        return monthNames[month];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_promotion, container, false);
        ButterKnife.bind(this, v);
        textfav.setTypeface(MyApplication.type_jf_regular);
        textcomment.setTypeface(MyApplication.type_jf_regular);
        textlike.setTypeface(MyApplication.type_jf_regular);
        textshare.setTypeface(MyApplication.type_jf_regular);

        lincom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ns.scrollTo(0, ns.getBottom());
            }
        });
        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);

        progressBar2.setVisibility(View.VISIBLE);
        lin.setVisibility(View.GONE);
        title.setTypeface(MyApplication.type_jf_medium);
        titlpromotiontitle.setTypeface(MyApplication.type_jf_medium);
        promotiondiscount.setTypeface(MyApplication.type_jf_regular);
        titlpromotiondate.setTypeface(MyApplication.type_jf_regular);
        btn_comment.setTypeface(MyApplication.type_jf_regular);
        reserv.setTypeface(MyApplication.type_jf_regular);
        description.setTypeface(MyApplication.type_jf_light);
        comment.setTypeface(MyApplication.type_jf_light);
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

        Zoomy.Builder builder = new Zoomy.Builder(getActivity())
                .target(image)
                .interpolator(new OvershootInterpolator());
        builder.register();

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
        if (profile.getType().equals("salon")) {
            reserver.setVisibility(View.GONE);
        } else {
            reserver.setVisibility(View.VISIBLE);
        }

        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(Reservation_home.newInstance(Integer.parseInt(promotiondetail.getPromotion().getId()), promotiondetail.getSalon().getUsername(), promotiondetail.getSalon().getSalonName()));
            }
        });

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
        tryagain.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);
        lin.setVisibility(View.GONE);
        lincomment.setVisibility(View.GONE);


        new Promotion().getpromotion(id
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null && PromotionFragment.this.isVisible()) {

                            progressBar2.setVisibility(View.GONE);
                            lin.setVisibility(View.VISIBLE);
                            lincomment.setVisibility(View.VISIBLE);
                            reserver.setVisibility(View.VISIBLE);
                            if (profile.getType().equals("salon"))
                                reserver.setVisibility(View.GONE);

                            promotiondetail = (Promotiondetail) result;
                            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + promotiondetail.getPromotion().getImage())
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
                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                            titlpromotiontitle.setText(promotiondetail.getPromotion().getTitle());
                            title.setText(promotiondetail.getSalon().getSalonName());
                            title.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((HomeActivityDrawer) getActivity()).setFragment(SalonProfile.newInstance(promotiondetail.getSalon().getId()));
                                }
                            });
                            try {
                                Date date1 = dt.parse(promotiondetail.getPromotion().getStartDate());
                                Date date2 = dt.parse(promotiondetail.getPromotion().getEndDate());
                                Calendar c = Calendar.getInstance();
                                c.setTime(date1);
                                Calendar c2 = Calendar.getInstance();
                                c2.setTime(date2);
                                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                                    titlpromotiondate.setText("من " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " إلى " +
                                            c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                                    );
                                else
                                    titlpromotiondate.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                                            c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                                    );


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            NumberFormat formatter = new DecimalFormat("#0.0");

                            Double discount = Double.parseDouble(promotiondetail.getPromotion().getPrice()) - ((Double.parseDouble(promotiondetail.getPromotion().getPrice()) / 100) * (Double.parseDouble(promotiondetail.getPromotion().getDiscount())));
                            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                                promotiondiscount.setText("تخفيض %" + promotiondetail.getPromotion().getDiscount() + " - " + formatter.format(discount) + " بدلا من " + promotiondetail.getPromotion().getPrice());
                            else
                                promotiondiscount.setText("discount %" + promotiondetail.getPromotion().getDiscount() + " - " + formatter.format(discount) + " instead of " + promotiondetail.getPromotion().getPrice());

                            description.setText(promotiondetail.getPromotion().getDescription());
                            if (promotiondetail.getIsFavorite()) {
                                imagefav.setColorFilter(getResources().getColor(R.color.yellow));
                                textfav.setTextColor(getResources().getColor(R.color.yellow));
                            }
                            if (promotiondetail.getIsLike()) {
                                imagelike.setColorFilter(getResources().getColor(R.color.color4));
                                textlike.setTextColor(getResources().getColor(R.color.color4));
                            }
                            if (promotiondetail.getIsComment()) {
                                imagecomment.setColorFilter(getResources().getColor(R.color.color4));
                                textcomment.setTextColor(getResources().getColor(R.color.color4));
                            }
                            if (promotiondetail.getIsShare()) {
                                imageshare.setColorFilter(getResources().getColor(R.color.color4));
                                textshare.setTextColor(getResources().getColor(R.color.color4));
                            }
                            textcomment.setText(promotiondetail.getCommentCount() + "");
                            textlike.setText(promotiondetail.getLikeCount() + "");
                            textfav.setText(promotiondetail.getFavoriteCount() + "");
                            textshare.setText(promotiondetail.getPromotion().getShare() + "");

                            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
                            activityList = new ArrayList<>();
                            activityList.addAll(promotiondetail.getComment());
                            adapter = new CommentAdaptor(activityList, getActivity());
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);

                            linshare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String s = promotiondetail.getPromotion().getTitle();
                                    if (promotiondetail.getPromotion().getTitle() != null && !promotiondetail.getPromotion().getTitle().equals("")) {
                                        s = (promotiondetail.getPromotion().getTitle()).replaceAll(" ", "%20");
                                    }
                                    share("title=" + s + "&img=" + promotiondetail.getPromotion().getImage() +
                                            "&type=promotion&id=" + promotiondetail.getPromotion().getId() +
                                            "+&description=" + promotiondetail.getPromotion().getDescription());
                                    Shared(promotiondetail.getPromotion().getId(), "promotion");

                                }
                            });

                            linlike.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (promotiondetail.getIsLike()) {
                                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(Integer.parseInt(promotiondetail.getPromotion().getId()), "promotion", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textlike.setText(promotiondetail.getLikeCount() - 1 + "");
                                                    promotiondetail.setIsLike("false");
                                                    promotiondetail.setLikeCount(promotiondetail.getLikeCount() - 1);

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
                                        new salon.octadevtn.sa.salon.Api.Comment().Like(promotiondetail.getPromotion().getId(), "promotion", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                                    textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                                    textlike.setText(promotiondetail.getLikeCount() + 1 + "");
                                                    promotiondetail.setIsLike("true");
                                                    promotiondetail.setLikeCount(promotiondetail.getLikeCount() + 1);

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


                            linfav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (promotiondetail.getIsFavorite()) {
                                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(Integer.parseInt(promotiondetail.getPromotion().getId()), "promotion", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                                    textfav.setText(promotiondetail.getFavoriteCount() - 1 + "");
                                                    promotiondetail.setIsFavorite("false");
                                                    promotiondetail.setFavoriteCount(promotiondetail.getFavoriteCount() - 1);

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
                                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(Integer.parseInt(promotiondetail.getPromotion().getId()), "promotion", new UniversalCallBack() {
                                            @Override
                                            public void onResponse(Object result) {
                                                if (result != null) {
                                                    imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                                    textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                                    textfav.setText(promotiondetail.getFavoriteCount() + 1 + "");
                                                    promotiondetail.setIsFavorite("true");
                                                    promotiondetail.setFavoriteCount(promotiondetail.getFavoriteCount() + 1);
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
                        showError2();
                    }
                }
        );
    }

    public void comment() {
        if (!comment.getText().toString().equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            btn_comment.setEnabled(false);
            new salon.octadevtn.sa.salon.Api.Comment().AddComment(promotiondetail.getPromotion().getId(), "promotion", comment.getText().toString(), new UniversalCallBack() {
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
                       /* MediaPlayer mPlayer2;
                        mPlayer2 = MediaPlayer.create(getActivity(), R.raw.notif);
                        mPlayer2.start();
                   */
                        v.findViewById(R.id.li).setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                        imagecomment.setColorFilter(getResources().getColor(R.color.color4));
                        textcomment.setTextColor(getResources().getColor(R.color.color4));

                        comment.setText("");
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
            });
        }

    }

    public void showError() {
        btn_comment.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        int gravity = LEFT;
        if (Locale.getDefault().getLanguage() == "ar")
            gravity = RIGHT;
        Alerter.create(getActivity())
                .setTitle(getResources().getString(R.string.erreur1))
                .setText(getResources().getString(R.string.erreur2))
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

    public void showError2() {
        if (PromotionFragment.this.isVisible()) {
            progressBar2.setVisibility(View.GONE);
            lin.setVisibility(View.VISIBLE);
            lincomment.setVisibility(View.GONE);

            int gravity = LEFT;
            if (Locale.getDefault().getLanguage() == "ar")
                gravity = RIGHT;
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
                    .setTitle(getResources().getString(R.string.erreur1))
                    .setText(getResources().getString(R.string.erreur2))
                    .setContentGravity(gravity)
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
