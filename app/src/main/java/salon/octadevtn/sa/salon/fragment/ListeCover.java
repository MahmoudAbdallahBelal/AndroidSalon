package salon.octadevtn.sa.salon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListeCover#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeCover extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ImageView[] dots;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;
    ArrayList<Cover> images;
    private ArrayList<Cover> ImagesArray = new ArrayList<Cover>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ListeCover() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListeCover.
     */
    // TODO: Rename and change types and number of parameters
    public static ListeCover newInstance(String param1, String param2) {
        ListeCover fragment = new ListeCover();
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
        View view = inflater.inflate(R.layout.fragment_liste_cover, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        images = (ArrayList<Cover>) getArguments().getSerializable("images");
        ((TextView) view.findViewById(R.id.back1)).setTypeface(MyApplication.type_jf_regular);


        init(images);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void init(ArrayList<Cover> x) {
        if (ImagesArray.size() == 0) {
            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    for (int i = 0; i < NUM_PAGES; i++) {
                        if (ListeCover.this.isVisible()) {
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
            for (int i = 0; i < x.size(); i++)
                ImagesArray.add(x.get(i));


            mPager.setAdapter(new MyPageAdapter(getChildFragmentManager(), getActivity(), ImagesArray));


            final float density = getResources().getDisplayMetrics().density;


            NUM_PAGES = x.size();
            setUiPageViewController(NUM_PAGES);

            // Auto start of viewpager
            final Handler handler = new Handler();
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            mPager.setCurrentItem(currentPage++, true);
            for (int i = 0; i < NUM_PAGES; i++) {
                if (ListeCover.this.isVisible()) {
                    if (i == mPager.getCurrentItem())
                        dots[mPager.getCurrentItem()].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot_white));
                    else
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot_white));
                }

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        ArrayList<Cover> imagesArray;

        public MyPageAdapter(FragmentManager fm, FragmentActivity activity, ArrayList<Cover> imagesArray) {
            super(fm);
            this.imagesArray = imagesArray;
        }


        @Override
        public int getCount() {
            return imagesArray.size();
        }

        @Override
        public Fragment getItem(int position) {

            return CoverItemFragment.newInstance(imagesArray.get(position).getId(), position, images);
        }

    }


}
