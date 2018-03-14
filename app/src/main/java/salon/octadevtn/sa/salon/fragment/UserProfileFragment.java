package salon.octadevtn.sa.salon.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Adaptor.UserActionAaptor;
import salon.octadevtn.sa.salon.Api.DetailUser;
import salon.octadevtn.sa.salon.EditProfilUser;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Timeline;
import salon.octadevtn.sa.salon.Models.Timeline_;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.pathImag;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t11)
    TextView t11;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t21)
    TextView t21;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.location_text)
    TextView location_text;
    @BindView(R.id.bio_text)
    TextView bio_text;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.edit_profil)
    Button edit_profil;
    ImageView cover;
    @BindView(R.id.photo)
    CircleImageView photo;
    @BindView(R.id.info)
    LinearLayout info;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.following)
    LinearLayout following;
    @BindView(R.id.followers)
    LinearLayout followers;

    Profile profile = null;
    int id;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(int id) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);

        return fragment;
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

        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
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


    private void AddReport(int id, String salon, String reason, final Dialog dialog) {
        new salon.octadevtn.sa.salon.Api.DeleteBlock().AddReport(reason, String.valueOf(id), "user"
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


    public void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(getActivity(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.block1, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.block:
                        AddBlock(id, "customer");
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
                                AddReport(id, "user", report.getText().toString(), dialog);
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
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_user_profile, container, false);
        ButterKnife.bind(this, v);
        ((TextView) v.findViewById(R.id.back1)).setTypeface(MyApplication.type_jf_regular);

        cover = (ImageView) v.findViewById(R.id.cover);
        t1.setTypeface(MyApplication.type_jf_medium);
        t2.setTypeface(MyApplication.type_jf_medium);
        t11.setTypeface(MyApplication.type_jf_regular);
        t21.setTypeface(MyApplication.type_jf_regular);
        bio.setTypeface(MyApplication.type_jf_medium);
        location.setTypeface(MyApplication.type_jf_medium);
        bio_text.setTypeface(MyApplication.type_jf_light);
        location_text.setTypeface(MyApplication.type_jf_light);
        username.setTypeface(MyApplication.type_jf_light);
        name.setTypeface(MyApplication.type_jf_light);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        Detail();
        v.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v.findViewById(R.id.setting));
            }
        });
        if (profile.getType().equals("customer"))
            if (id == profile.getUser().getId())
                v.findViewById(R.id.setting).setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(999);
        GridLayoutManager gl = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gl);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(ListeFlow.newInstance(id, "customer", "FL"));
            }
        });
        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) getActivity()).setFragment(ListeFlow.newInstance(id, "customer", "FR"));
            }
        });


        return v;
    }

    private void Detail() {
        new DetailUser().DetailUser(id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    Gson gson = new Gson();
                    try {
                        info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        JSONObject json = new JSONObject(String.valueOf(result));
                        final Timeline timeline = gson.fromJson(String.valueOf(json), Timeline.class);
                        name.setText(timeline.getUser().getName());
                        username.setText(timeline.getUser().getUsername());
                        bio_text.setText(timeline.getUser().getBio());
                        Glide.with(MyApplication.getInstance())
                                .load(pathImag + timeline.getUser().getCover())
                                .centerCrop()
                                .override(150,150)
                                .into(cover);
                        Glide.with(MyApplication.getInstance())
                                .load(pathImag + timeline.getUser().getPhoto())
                                .centerCrop()
                                .override(150,150)
                                .error(R.drawable.logoimage)
                                .into(photo);
                        if (timeline.getCountfollowers() > 1000) {
                            t2.setText(timeline.getCountfollowers() / 1000 + "." + timeline.getCountfollowers() % 1000 + " k");
                        } else {
                            t2.setText(timeline.getCountfollowers() + "");
                        }
                        if (timeline.getCountfollowing() > 1000) {
                            t1.setText(timeline.getCountfollowing() / 1000 + "." + timeline.getCountfollowing() % 1000 + " k");
                        } else {
                            t1.setText(timeline.getCountfollowing() + "");
                        }
                        if (timeline.getUser().getGenre().equals("men"))
                            photo.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
                        else
                            photo.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                        if (timeline.getIsFolow().equals("true")) {
                            edit_profil.setText(MyApplication.getInstance().getResources().getString(R.string.start_follow));
                        } else
                            edit_profil.setText(MyApplication.getInstance().getResources().getString(R.string.unfollow));
                        if (id == profile.getUserId()) {
                            edit_profil.setText(MyApplication.getInstance().getResources().getString(R.string.edit_profile));
                        }
                        edit_profil.setVisibility(View.VISIBLE);
                        location_text.setText(timeline.getUser().getCountry() + " , " + timeline.getUser().getCity());
                        edit_profil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (id == profile.getUserId()) {
                                    Gson gson = new Gson();
                                    String user = gson.toJson(timeline.getUser());

                                    ((HomeActivityDrawer) getActivity()).setFragment(EditProfilUser.newInstance(user));
                                } else {
                                    if (timeline.getIsFolow().equals("true")) {
                                        new salon.octadevtn.sa.salon.Api.Follow().AddFollow(id, "customer"
                                                , new UniversalCallBack() {
                                                    @Override
                                                    public void onResponse(Object result) {
                                                        if (((Boolean) result) == true) {
                                                            edit_profil.setText(MyApplication.getAppContext().getResources().getString(R.string.unfollow));
                                                            timeline.setIsFolow("false");
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

                                        new salon.octadevtn.sa.salon.Api.Follow().DeleteFollow(id, "customer"
                                                , new UniversalCallBack() {
                                                    @Override
                                                    public void onResponse(Object result) {
                                                        if (((Boolean) result) == true) {
                                                            edit_profil.setText(MyApplication.getAppContext().getResources().getString(R.string.start_follow));
                                                            timeline.setIsFolow("true");
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
                            }
                        });
                        List<Timeline_> timeline_s = new ArrayList<Timeline_>();
                        UserActionAaptor userActivityAdapter = new UserActionAaptor(getActivity(), timeline.getTimeline(), recyclerView);

                        recyclerView.setAdapter(userActivityAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
