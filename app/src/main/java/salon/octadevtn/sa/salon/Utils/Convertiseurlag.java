package salon.octadevtn.sa.salon.Utils;

/**
 * Created by Marwen octadev on 8/25/2017.
 */

public class Convertiseurlag {
    public static String getAction(String s) {
        if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
            if (s.equals("favorite")) {
                return "أضاف مفضلة";
            }
            if (s.equals("like")) {
                return "أعجب";
            }
            if (s.equals("share")) {
                return "قام بمشاركة";
            }
            if (s.equals("comment")) {
                return "أضاف تعليق";
            }
            if (s.equals("add")) {
                return "أضاف";
            }
        }
        return s;
    }

    public static String getType(String s) {
        if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
            if (s.equals("promotion")) {
                return "عرض";
            }
            if (s.equals("service")) {
                return "خدمة";
            }
            if (s.equals("price")) {
                return "سعر";
            }
            if (s.equals("reservation")) {
                return "قام بالحجز";
            }
            if (s.equals("cover")) {
                return "صور";
            }
        }
        return s;
    }

    public static String getProfile(String s) {
        if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
            if (s.equals("name")) {
                return "الاسم";
            }
            if (s.equals("bio")) {
                return "الوصف";
            }
            if (s.equals("country")) {
                return "البلد";
            }
            if (s.equals("city")) {
                return "المدينة";
            }
            if (s.equals("since_from")) {
                return "تاريخ الافتتاح";
            }
            if (s.equals("location")) {
                return "المكان";
            }
        }
        return s.replace("_", " ");
    }

}
