package salon.octadevtn.sa.salon;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.flippablestackview.FlippableStackView;
import com.bartoszlipinski.flippablestackview.StackPageTransformer;
import com.bartoszlipinski.flippablestackview.utilities.ValueInterpolator;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Login_signup.LoginActivity;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.SwipeEvents;
import salon.octadevtn.sa.salon.fragment.ColorFragment;

public class MainActivity extends AppCompatActivity {
    private static final int NUMBER_OF_FRAGMENTS = 5;
    ImageView[] dots;
    LinearLayout pager_indicator;
    @BindView(R.id.skipt)
    TextView skipt;
    private FlippableStackView mFlippableStack;
    private ColorFragmentAdapter mPageAdapter;
    private List<Fragment> mViewPagerFragments;
    private int min_distance = 100;
    private float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ButterKnife.bind(MainActivity.this);
        skipt.setTypeface(MyApplication.type_jf_regular);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        try {
            createViewPagerFragments();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPageAdapter = new ColorFragmentAdapter(getSupportFragmentManager(), mViewPagerFragments);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        setUiPageViewController();
        boolean portrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        mFlippableStack = (FlippableStackView) findViewById(R.id.flippable_stack_view);
        mFlippableStack.initStack(4, portrait ?
                StackPageTransformer.Orientation.VERTICAL :
                StackPageTransformer.Orientation.VERTICAL);
        mFlippableStack.setAdapter(mPageAdapter);
        LinearLayout yourButton = (LinearLayout) findViewById(R.id.skip);
        yourButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        mFlippableStack.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < NUMBER_OF_FRAGMENTS; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }
                dots[NUMBER_OF_FRAGMENTS - position - 1].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

                if (position + 1 == NUMBER_OF_FRAGMENTS) {

                } else {
                    //btnFinish.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SwipeEvents.detect(findViewById(R.id.flippable_stack_view), new SwipeEvents.SwipeCallback() {
            @Override
            public void onSwipeTop() {
            }

            @Override
            public void onSwipeRight() {
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    if (mFlippableStack.getCurrentItem() >= 1)
                        mFlippableStack.setCurrentItem(mFlippableStack.getCurrentItem() - 1);

                } else {
                    if (mFlippableStack.getCurrentItem() < mViewPagerFragments.size())
                        mFlippableStack.setCurrentItem(mFlippableStack.getCurrentItem() + 1);
                }
            }

            @Override
            public void onSwipeBottom() {
            }

            @Override
            public void onSwipeLeft() {
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    if (mFlippableStack.getCurrentItem() < mViewPagerFragments.size())
                        mFlippableStack.setCurrentItem(mFlippableStack.getCurrentItem() + 1);

                } else {
                    if (mFlippableStack.getCurrentItem() >= 1)
                        mFlippableStack.setCurrentItem(mFlippableStack.getCurrentItem() - 1);


                }
            }
        });

    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void createViewPagerFragments() throws IOException, JSONException {
        mViewPagerFragments = new ArrayList<>();

        int startColor = getResources().getColor(android.R.color.white);
        int startR = Color.red(startColor);
        int startG = Color.green(startColor);
        int startB = Color.blue(startColor);

        int endColor = getResources().getColor(android.R.color.white);
        int endR = Color.red(endColor);
        int endG = Color.green(endColor);
        int endB = Color.blue(endColor);

        ValueInterpolator interpolatorR = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endR, startR);
        ValueInterpolator interpolatorG = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endG, startG);
        ValueInterpolator interpolatorB = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endB, startB);

        for (int i = 0; i < NUMBER_OF_FRAGMENTS; ++i) {
            mViewPagerFragments.add(ColorFragment.newInstance(Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i)), i));
        }
    }

    private void setUiPageViewController() {

        dots = new ImageView[NUMBER_OF_FRAGMENTS];

        for (int i = 0; i < NUMBER_OF_FRAGMENTS; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(10, 0, 10, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    private class ColorFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public ColorFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = this.fragments.get(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

}
