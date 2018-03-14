package salon.octadevtn.sa.salon;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabClickListener;

public class HomeActivity extends AppCompatActivity {
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setFixedInactiveIconColor(ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.setActiveTabColor(ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.setMaxFixedTabs(5);
        mBottomBar.setItems(
                new BottomBarTab(R.drawable.ic_reservation_icon, getResources().getString(R.string.reservation)),
                new BottomBarTab(R.drawable.ic_explore_icon, getResources().getString(R.string.explore)),
                new BottomBarTab(R.drawable.ic_notification_icon, getResources().getString(R.string.notication)),
                new BottomBarTab(R.drawable.ic_favorite_icon, getResources().getString(R.string.favorit)),
                new BottomBarTab(R.drawable.ic_promotion_icon, getResources().getString(R.string.promotion))

        );

        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                // The user selected a tab at the specified position
            }

            @Override
            public void onTabReSelected(int position) {
                // The user reselected a tab at the specified position!
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, android.R.color.white));
        mBottomBar.mapColorForTab(4, ContextCompat.getColor(this, android.R.color.white));
        BottomBarBadge unreadMessages = mBottomBar.makeBadgeForTabAt(2, "#FF0000", 13);

        unreadMessages.setAutoShowAfterUnSelection(true);

    }

}
