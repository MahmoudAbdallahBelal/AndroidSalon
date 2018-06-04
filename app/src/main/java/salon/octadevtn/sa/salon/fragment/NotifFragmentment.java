package salon.octadevtn.sa.salon.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import salon.octadevtn.sa.salon.Adaptor.NotifAaptor;
import salon.octadevtn.sa.salon.Api.GetActivity;
import salon.octadevtn.sa.salon.Models.Activity.Activity;
import salon.octadevtn.sa.salon.Models.Activity.ListTimeline;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static salon.octadevtn.sa.salon.HomeActivityDrawer.runLayoutAnimation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotifFragmentment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifFragmentment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<ListTimeline> activityList;
    SwipeRefreshLayout swipeRefreshLayout;
    NotifAaptor adapter;
    RecyclerView recyclerView;
    LinearLayout lin;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public NotifFragmentment() {
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
    public static NotifFragmentment newInstance(String param1, String param2) {
        NotifFragmentment fragment = new NotifFragmentment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static boolean isInList(
            final List<ListTimeline> list, final ListTimeline candidate) {

        for (final ListTimeline item : list) {
            if (candidate.getId().equals(item.getId())) {
                return true;
            }
        }
        return false;
        //  ^-- or you may want to use .parallelStream() here instead
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
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        View v = inflater.inflate(R.layout.fragment_notif, container, false);
        ((TextView) v.findViewById(R.id.nodatatxt)).setTypeface(MyApplication.type_jf_medium);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        lin = (LinearLayout) v.findViewById(R.id.nodata);

        activityList = new ArrayList<>();
        if (savedInstanceState != null) {

            ArrayList<ListTimeline> x = savedInstanceState.getParcelableArrayList("array");
            activityList.addAll(x);
        }
        adapter = new NotifAaptor(getActivity(), activityList, recyclerView);

        ((TextView) v.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_medium);
        ((TextView) v.findViewById(R.id.back)).setTypeface(MyApplication.type_jf_regular);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        v.findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityList.clear();
                runLayoutAnimation(recyclerView);
                load(0);
            }

        });
      /*  recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Toast.makeText(getActivity(),page+ "", Toast.LENGTH_SHORT).show();
                load(page);
                int curSize = adapter.getItemCount();

                adapter.notifyItemRangeInserted(curSize, activityList.size() - 1);
            }
        });
*/
        swipeRefreshLayout.setColorSchemeResources(
                R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                load(0);
            }
        });


        return v;
    }

    public void load(int page) {
        lin.setVisibility(View.GONE);

        new GetActivity().GetNotif(page, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    //  activityList.clear();
                    for (int i = 0; i < ((Activity) result).getListTimeline().size(); i++) {
                        if (!(isInList(activityList, ((Activity) result).getListTimeline().get(i)))) {
                            activityList.add(((Activity) result).getListTimeline().get(i));
                            Log.d("arraysize", activityList.size() + "");
                        }

                    }
                    if (activityList.size() == 0)
                        lin.setVisibility(View.VISIBLE);
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
        swipeRefreshLayout.setRefreshing(false);
        int gravity = LEFT;
        if (Locale.getDefault().getLanguage() == "ar")
            gravity = RIGHT;
        if (NotifFragmentment.this.isVisible()) {
            Alerter a = Alerter.create(getActivity())
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
                            swipeRefreshLayout.setRefreshing(false);

                            Alerter.hide();
                        }

                    });
            a.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putParcelableArrayList("array", activityList);
        // etc.
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
