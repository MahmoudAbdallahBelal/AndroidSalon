package salon.octadevtn.sa.salon.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.tapadoo.alerter.Alerter;

import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import salon.octadevtn.sa.salon.Adaptor.ActivityAaptor;
import salon.octadevtn.sa.salon.Api.GetActivity;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Activity.Activity;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.EndlessRecyclerViewScrollListener;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ActivityAaptor adapter;
    TextView title;
    Profile profile = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        title = (TextView) v.findViewById(R.id.title);
        title.setTypeface(MyApplication.type_jf_medium);


        v.findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (profile.getType().equals("salon"))
                    ((HomeActivityDrawer) getActivity()).setFragment(SalonProfile.newInstance(profile.getSalon().getId()));
                else
                    ((HomeActivityDrawer) getActivity()).setFragment(UserProfileFragment.newInstance(profile.getUserId()));
                HomeActivityDrawer.drawer.closeDrawer(GravityCompat.START);

            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        adapter = new ActivityAaptor(getActivity(), Static.activityList, recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setInterpolator(new OvershootInterpolator(1f));
        alphaAdapter.setFirstOnly(false);
        recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));

        v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivityDrawer.opendrawer();
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                runLayoutAnimation(recyclerView);
                Static.activityList.clear();
                load(0);
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
                Static.activityList.clear();
                load(0);
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                load(page);
                int curSize = adapter.getItemCount();

                adapter.notifyItemRangeInserted(curSize, Static.activityList.size() - 1);
            }
        });


        return v;
    }

    public void load(final int page) {
        new GetActivity().GetActivity(page, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    //  activityList.clear();
                    for (int i = 0; i < ((Activity) result).getListTimeline().size(); i++) {
                        if (!Static.activityList.contains(((Activity) result).getListTimeline().get(i))) {
                            Static.activityList.addAll(((Activity) result).getListTimeline());
                        }
                    }

                    runLayoutAnimation(recyclerView);

                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    showError();
                }


            }

            @Override
            public void onFailure(Object result) {
                ResponseErrors responseError = (ResponseErrors) result;
                String Error = "Failure";
                swipeRefreshLayout.setRefreshing(false);
                showError();

            }

            @Override
            public void OnError(String message) {
                swipeRefreshLayout.setRefreshing(false);

                showError();

            }

            @Override
            public void onFinish() {
                swipeRefreshLayout.setRefreshing(false);


            }
        });
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 300L);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void showError() {
        int gravity = LEFT;
        if (Locale.getDefault().getLanguage() == "ar")
            gravity = RIGHT;
        if (HomeFragment.this.isVisible())
            Alerter.create(getActivity())
                    .setTitle(getResources().getString(R.string.erreur1))
                    .setText(getResources().getString(R.string.erreur2))
                    .setContentGravity(gravity)

                    .setIcon(getResources().getDrawable(R.drawable.wifisi))
                    .setDuration(2000)
                    .enableSwipeToDismiss()
                    .setBackgroundColorRes(R.color.color2)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swipeRefreshLayout.setRefreshing(true);
                            Alerter.hide();
                        }

                    })
                    .show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


}
