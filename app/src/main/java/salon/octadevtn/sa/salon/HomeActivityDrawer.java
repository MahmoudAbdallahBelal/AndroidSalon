package salon.octadevtn.sa.salon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.jetradar.multibackstack.BackStackActivity;

import java.io.File;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.FragmentNavigationController;
import salon.octadevtn.sa.salon.Api.FavoriteAPI;
import salon.octadevtn.sa.salon.Login_signup.LoginActivity;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Promotion;
import salon.octadevtn.sa.salon.Reservation.ListeReservationFragment;
import salon.octadevtn.sa.salon.Reservation.ListeReservationSalon;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.AboutAs;
import salon.octadevtn.sa.salon.fragment.Contact_Us;
import salon.octadevtn.sa.salon.fragment.ExploreFragment;
import salon.octadevtn.sa.salon.fragment.FavoriteFragment;
import salon.octadevtn.sa.salon.fragment.HomeFragment;
import salon.octadevtn.sa.salon.fragment.ListePromotionFragment;
import salon.octadevtn.sa.salon.fragment.NotifFragmentment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.SlideshowDialogFragment;
import salon.octadevtn.sa.salon.fragment.UserProfileFragment;

import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

public class HomeActivityDrawer extends BackStackActivity {

    private static final String STATE_CURRENT_TAB_ID = "current_tab_id";
    private static final int MAIN_TAB_ID = 0;
    public static RelativeLayout menu;
    public static FragmentManager manager;
    public static DrawerLayout drawer;
    public static BackStackActivity context;
    static FragmentNavigationController navigationController;
    private static Fragment curFragment;
    private static int curTabId;
    @BindView(R.id.image_reservation)
    ImageView image_reservation;
    @BindView(R.id.text_reservation)
    TextView text_reservation;
    @BindView(R.id.reservation)
    LinearLayout reservation;
    @BindView(R.id.image_explore)
    ImageView image_explore;
    @BindView(R.id.text_explore)
    TextView text_explore;
    @BindView(R.id.explore)
    LinearLayout explore;
    @BindView(R.id.image_notification)
    ImageView image_notification;
    @BindView(R.id.text_notification)
    TextView text_notification;
    @BindView(R.id.notification)
    LinearLayout notification;
    @BindView(R.id.image_favorit)
    ImageView image_favorit;
    @BindView(R.id.text_favorite)
    TextView text_favorite;
    @BindView(R.id.favorite)
    LinearLayout favorite;
    @BindView(R.id.image_promotion)
    ImageView image_promotion;
    @BindView(R.id.text_promotion)
    TextView text_promotion;
    TextView logout;
    @BindView(R.id.promotion)
    LinearLayout promotion;
    NavigationView navigationView;
    Profile profile = null;
    CircleImageView profile_image;
    TextView username, location;

    public static void setTranslucentStatusBar(Window window) {
        if (window == null) return;
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.LOLLIPOP) {
            setTranslucentStatusBarLollipop(window);
        } else if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusBarKiKat(window);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setTranslucentStatusBarLollipop(Window window) {
        window.setStatusBarColor(
                window.getContext()
                        .getResources()
                        .getColor(android.R.color.transparent));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setTranslucentStatusBarKiKat(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static void KillFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.remove(fragment).commit();


    }

    public static void setFragmentCover(Bundle b) {
        FragmentTransaction ft = manager.beginTransaction();
        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
        newFragment.setArguments(b);

        // ft.commitAllowingStateLoss();

        newFragment.show(ft, "slideshow");

        curFragment = newFragment;

    }

    public static void opendrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else drawer.openDrawer(GravityCompat.START);
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.grid_layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void toolbarSetElevation(float elevation) {
        // setElevation() only works on Lollipop
        if ((android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            menu.setElevation(elevation);
        }
    }

    public static void toolbarAnimateShow() {
        menu.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbarSetElevation(35);
                    }
                });
    }

    public static void toolbarAnimateHide() {
        menu.animate()
                .translationY(menu.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //  window.setStatusBarColor(getResources().getColor(android.R.color.transparent));


        setContentView(R.layout.activity_home_drawer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().setNavigationBarColor(Color.parseColor("#f16565"));
        }
        menu = findViewById(R.id.menu);
        manager = getSupportFragmentManager();
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        // Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this, SplachScreen.class));

        findViewById(R.id.txt_count).setVisibility(View.GONE);
        image_reservation.setColorFilter(Color.parseColor("#969696"));
        text_reservation.setTextColor(Color.parseColor("#969696"));
        image_explore.setColorFilter(Color.parseColor("#969696"));
        text_explore.setTextColor(Color.parseColor("#969696"));
        image_notification.setColorFilter(Color.parseColor("#969696"));
        text_notification.setTextColor(Color.parseColor("#969696"));
        image_favorit.setColorFilter(Color.parseColor("#969696"));
        text_favorite.setTextColor(Color.parseColor("#969696"));
        image_promotion.setColorFilter(Color.parseColor("#969696"));
        text_promotion.setTextColor(Color.parseColor("#969696"));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getHeaderView(0).findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
                Profile profile = null;
                try {
                    profile = (Profile) gsonSharedPrefs.getObject(new Profile());
                } catch (ParsingException e) {
                    e.printStackTrace();
                }
                if (profile.getType().equals("salon"))
                    setFragment(SalonProfile.newInstance(profile.getSalon().getId()));
                else
                    setFragment(UserProfileFragment.newInstance(profile.getUserId()));
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.setting)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.searsh)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.contactus)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.about)).setTypeface(MyApplication.type_jf_regular);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.logout)).setTypeface(MyApplication.type_jf_regular);
        text_reservation.setTypeface(MyApplication.type_jf_medium);


        text_explore.setTypeface(MyApplication.type_jf_medium);
        text_notification.setTypeface(MyApplication.type_jf_medium);
        text_favorite.setTypeface(MyApplication.type_jf_medium);
        text_promotion.setTypeface(MyApplication.type_jf_medium);

        navigationView.getHeaderView(0).findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new Settings());
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.searsh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new Searsh());
                //  replaceFragment(new Searsh());
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.contactus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new Contact_Us());

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new AboutAs());

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.fragmentContainer).setPadding(0, 0, 0, 0);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //setFragment( TimeLineFragment().ne);

        profile_image = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        location = (TextView) navigationView.getHeaderView(0).findViewById(R.id.location);
        username.setTypeface(MyApplication.type_jf_medium);
        location.setTypeface(MyApplication.type_jf_regular);
        settionprofile();

        curTabId = 0;


        showFragment(new HomeFragment(), true);
        reservation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                rival(1);
                buttonmenu(0);


            }
        });
        explore.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                rival(2);
                buttonmenu(1);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                rival(3);
                //   setFragment(new NotifFragmentment());
                buttonmenu(2);
            }
        });
        navigationView.getHeaderView(0).findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearApplicationData();
                startActivity(new Intent(HomeActivityDrawer.this, LoginActivity.class));

                finish();

            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                rival(4);
                buttonmenu(3);
                // setFragment(new FavoriteFragment());
            }
        });
        promotion.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                rival(5);
                buttonmenu(4);
                ((TextView) findViewById(R.id.txt_count)).setVisibility(View.GONE);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        new FavoriteAPI().getlistepromotion(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    findViewById(R.id.txt_count).setVisibility(View.VISIBLE);
                    Promotion[] ob = (Promotion[]) result;
                    if (ob.length > 0)
                        ((TextView) findViewById(R.id.txt_count)).setText(ob.length + "");
                    else ((TextView) findViewById(R.id.txt_count)).setVisibility(View.GONE);
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

                Dialog dialog = new Dialog(HomeActivityDrawer.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_serveur_erreur);
                ((TextView) dialog.findViewById(R.id.text)).setTypeface(MyApplication.type_jf_medium);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                // dialog.show();
                dialog.getWindow().setAttributes(lp);
                SweetAlertDialog s = new SweetAlertDialog(HomeActivityDrawer.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("")

                        .setContentText(getResources().getString(R.string.serveur_metenance))
                        .setCustomImage(R.drawable.forget_password)
                        .setConfirmText(getResources().getString(R.string.ok));
                s.setCancelable(false);
                s.show();
            }
        });


    }

    public void settionprofile() {
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        if (profile.getType().equals("salon")) {
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + profile.getSalon().getPhoto()).into(profile_image);
            profile_image.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            username.setText(profile.getSalon().getSalonName());
            location.setText(profile.getSalon().getCountry() + " " + profile.getSalon().getCity());
        } else {
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + profile.getUser().getPhoto()).into(profile_image);
            username.setText(profile.getUser().getName());
            location.setText(profile.getUser().getCountry() + " " + profile.getUser().getCity());

            if (profile.getUser().getGenre().equals("men"))
                profile_image.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                profile_image.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
        }

    }

    /*  @Override
      public void onBackPressed() {
          DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
          if (drawer.isDrawerOpen(GravityCompat.START)) {
              drawer.closeDrawer(GravityCompat.START);
          } else {
              int count = getFragmentManager().getBackStackEntryCount();
              getSupportFragmentManager().popBackStack();


          }
      }
  */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void rival(int x) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final View myView = findViewById(R.id.revealiew);
            int[] colors = {R.color.color1, R.color.color2, R.color.color3};
            Random rand = new Random();
            int nombreAleatoire = rand.nextInt(3);

            myView.setBackgroundColor(getResources().getColor(colors[nombreAleatoire]));
// get the center for the clipping circle
            int cx = myView.getWidth() / 5;
            int cy = myView.getHeight() / 2;

// get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

// create the animator for this view (the start radius is zero)

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx * x, cy, 0, finalRadius);

// make the view visible and start the animation
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    myView.setVisibility(View.INVISIBLE);

                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    myView.setVisibility(View.VISIBLE);

                    super.onAnimationStart(animation);
                }
            });

            anim.start();
        }
    }

    public void buttonmenu(int seted) {
        image_reservation.setColorFilter(Color.parseColor("#969696"));
        text_reservation.setTextColor(Color.parseColor("#969696"));


        image_explore.setColorFilter(Color.parseColor("#969696"));
        text_explore.setTextColor(Color.parseColor("#969696"));

        image_notification.setColorFilter(Color.parseColor("#969696"));
        text_notification.setTextColor(Color.parseColor("#969696"));

        image_favorit.setColorFilter(Color.parseColor("#969696"));
        text_favorite.setTextColor(Color.parseColor("#969696"));

        image_promotion.setColorFilter(Color.parseColor("#969696"));
        text_promotion.setTextColor(Color.parseColor("#969696"));

        if (seted != -1) {
            if (seted == 0) {
                image_reservation.setColorFilter(getResources().getColor(R.color.color4));
                text_reservation.setTextColor(getResources().getColor(R.color.color4));
                if (curFragment != null) {
                    pushFragmentToBackStack(0, curFragment);
                }
                curTabId = 1;
                Fragment fragment = popFragmentFromBackStack(curTabId);
                if (fragment == null) {
                    if (profile.getType().equals("salon")) {
                        fragment = new ListeReservationSalon();
                    } else {
                        fragment = new ListeReservationFragment();

                    }
                }
                replaceFragment(fragment);
            }
            if (seted == 1) {
                image_explore.setColorFilter(getResources().getColor(R.color.color4));
                text_explore.setTextColor(getResources().getColor(R.color.color4));
                //setFragment(new ExploreFragment());
                // showFragment(new ExploreFragment(), true);
                if (curFragment != null) {
                    pushFragmentToBackStack(0, curFragment);
                }
                curTabId = 2;
                Fragment fragment = popFragmentFromBackStack(curTabId);
                if (fragment == null) {
                    fragment = new ExploreFragment();
                }
                replaceFragment(fragment);

            }
            if (seted == 2) {
                image_notification.setColorFilter(getResources().getColor(R.color.color4));
                text_notification.setTextColor(getResources().getColor(R.color.color4));
                // showFragment(new NotifFragmentment(), true);
                if (curFragment != null) {
                    pushFragmentToBackStack(0, curFragment);
                }
                curTabId = 3;
                Fragment fragment = popFragmentFromBackStack(curTabId);
                if (fragment == null) {
                    fragment = new NotifFragmentment();
                }
                replaceFragment(fragment);

            }
            if (seted == 3) {
                image_favorit.setColorFilter(getResources().getColor(R.color.color4));
                text_favorite.setTextColor(getResources().getColor(R.color.color4));
                if (curFragment != null) {
                    pushFragmentToBackStack(0, curFragment);
                }
                curTabId = 4;
                Fragment fragment = popFragmentFromBackStack(curTabId);
                if (fragment == null) {
                    fragment = new FavoriteFragment();
                }
                replaceFragment(fragment);


            }
            if (seted == 4) {
                image_promotion.setColorFilter(getResources().getColor(R.color.color4));
                text_promotion.setTextColor(getResources().getColor(R.color.color4));
                //  showFragment(new ListePromotionFragment(), true);
                if (curFragment != null) {
                    pushFragmentToBackStack(0, curFragment);
                }
                curTabId = 5;
                Fragment fragment = popFragmentFromBackStack(curTabId);
                if (fragment == null) {
                    fragment = new ListePromotionFragment();
                }
                replaceFragment(fragment);

            }
        }
    }

    public void setFragment(Fragment fragment) {
        showFragment(fragment, true);
        if (curFragment != null && true ) {
           pushFragmentToBackStack(curTabId, curFragment);
        }
        replaceFragment(fragment);

    }

    public void setFragment(Fragment fragment, Bundle b) {

        if (curFragment != null) {
            pushFragmentToBackStack(curTabId, curFragment);
        }

        fragment.setArguments(b);
        replaceFragment(fragment);


    }



    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        curFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        curTabId = savedInstanceState.getInt(STATE_CURRENT_TAB_ID);
        // bottomNavBar.selectTab(curTabId, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_CURRENT_TAB_ID, curTabId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (Static.frag != null) {
            Static.frag = null;
            FragmentTransaction tr = manager.beginTransaction();
            tr.replace(R.id.fragmentContainer, new Searsh());
            tr.commitAllowingStateLoss();
        } else {
            Pair<Integer, Fragment> pair = popFragmentFromBackStack();
            if (pair != null) {
                if (isRootTabFragment(curFragment, curTabId))
                    backToRoot();
                else
                    backTo(pair.first, pair.second);
            } else {
                super.onBackPressed();
            }
        }
    }

    public void showFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        if (curFragment != null && addToBackStack) {
            pushFragmentToBackStack(curTabId, curFragment);
        }
        replaceFragment(fragment);
    }

    private void backTo(int tabId, @NonNull Fragment fragment) {
        if (tabId != curTabId) {
            curTabId = tabId;
            //     bottomNavBar.selectTab(curTabId, false);
        }
        replaceFragment(fragment);
        getSupportFragmentManager().executePendingTransactions();
    }

    private void backToRoot() {
        if (!isRootTabFragment(curFragment, curTabId)) {
            return;
        }
        resetBackStackToRoot(0);
        Fragment rootFragment = popFragmentFromBackStack(0);
        image_reservation.setColorFilter(Color.parseColor("#969696"));
        text_reservation.setTextColor(Color.parseColor("#969696"));
        image_explore.setColorFilter(Color.parseColor("#969696"));
        text_explore.setTextColor(Color.parseColor("#969696"));
        image_notification.setColorFilter(Color.parseColor("#969696"));
        text_notification.setTextColor(Color.parseColor("#969696"));
        image_favorit.setColorFilter(Color.parseColor("#969696"));
        text_favorite.setTextColor(Color.parseColor("#969696"));
        image_promotion.setColorFilter(Color.parseColor("#969696"));
        text_promotion.setTextColor(Color.parseColor("#969696"));
        backTo(0, new HomeFragment());
        pushFragmentToBackStack(0, rootFragment);

    }

    private boolean isRootTabFragment(@NonNull Fragment fragment, int tabId) {
        return rootTabFragment(fragment);
    }

    public Boolean rootTabFragment(Fragment f) {
        if (f.getClass() == new ExploreFragment().getClass()) {
            return true;
        } else if (f.getClass() == new NotifFragmentment().getClass()) {
            return true;
        } else if (f.getClass() == new FavoriteFragment().getClass()) {
            return true;
        } else if (f.getClass() == new ListePromotionFragment().getClass()) {
            return true;
        } else if (f.getClass() == new ListeReservationSalon().getClass()) {
            return true;
        } else if (f.getClass() == new ListeReservationFragment().getClass()) {
            return true;
        }
        return false;
    }

    public void replaceFragment(@NonNull Fragment fragment) {
        FragmentTransaction tr = manager.beginTransaction();
        tr.replace(R.id.fragmentContainer, fragment);
        tr.commitAllowingStateLoss();
        curFragment = fragment;
        if (popupWindow != null)
            popupWindow.dismiss();
    }


}
