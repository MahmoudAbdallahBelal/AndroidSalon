package salon.octadevtn.sa.salon.Utils;

import android.widget.PopupWindow;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.Models.Activity.ListTimeline;
import salon.octadevtn.sa.salon.Models.CoverModel;
import salon.octadevtn.sa.salon.Models.Favorite;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Searsh_pager;

/**
 * Created by Marwen octadev on 7/5/2017.
 */

public class Static {

    public static String shared_name = "salon_app";
    public static int[] screen = new int[]{R.drawable.screen5, R.drawable.screen4, R.drawable.screen3, R.drawable.screen2, R.drawable.logo};
    public static int position = -1;
    public static String time;
    public static String searsh = null;
    public static boolean time1 = false;
    public static boolean date1 = false;
    public static Favorite favorite;
    public static boolean sincefrom = false;

    public static CoverModel sa;
    public static int slidingimage;
    public static boolean reserv_time;
    public static boolean reserv_date;
    public static java.util.List<ListTimeline> activityList = new ArrayList<>();

    public static PopupWindow popupWindow;
    public static Searsh_pager frag = null;
}
