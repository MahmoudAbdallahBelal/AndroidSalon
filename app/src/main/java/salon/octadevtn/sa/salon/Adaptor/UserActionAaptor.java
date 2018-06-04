package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.tuyenmonkey.textdecorator.TextDecorator;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Api.GetActivity;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Activity.ListTimeline;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.Convertiseurlag;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.Salon_services;
import salon.octadevtn.sa.salon.fragment.UserProfileFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

/**
 * Created by Marwen octadev on 7/14/2017.
 */

public class UserActionAaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final public int MULTI_LIKE_PROMOTION = 0;
    final public int FOLLOW = 1;
    Context context;
    RecyclerView recyclerView;
    salon.octadevtn.sa.salon.Models.Profile profile = null;
    private List<ListTimeline> moviesList;


    public UserActionAaptor(Context context, List<ListTimeline> moviesList, RecyclerView recyclerView) {

        this.moviesList = moviesList;
        this.context = context;
        this.recyclerView = recyclerView;
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (salon.octadevtn.sa.salon.Models.Profile) gsonSharedPrefs.getObject(new salon.octadevtn.sa.salon.Models.Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }

    public static String theMonth(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] monthNamesAr = {"يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو",
                "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"};
        if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
            return monthNames[month];
        else
            return monthNamesAr[month];

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            return new ProgressViewHolder(v);

        }
        if (viewType == 2) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flowed, parent, false);
            return new Profile(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flowed, parent, false);
            return new FOLLOW(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null)
                    popupWindow.dismiss();
                return false;
            }
        });

        /*********************************************************************************/


        if (holder instanceof FOLLOW) {

            ((FOLLOW) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);

                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    popupWindow = new PopupWindow(
                            popupView,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    ((TextView) popupView.findViewById(R.id.del)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.hid)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.edi)).setTypeface(MyApplication.type_jf_regular);

                    LinearLayout delete = (LinearLayout) popupView.findViewById(R.id.delete);
                    LinearLayout hide = (LinearLayout) popupView.findViewById(R.id.hide);
                    delete.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            DeleteActivity(moviesList.get(position).getId(), position);
                            popupWindow.dismiss();
                        }
                    });
                    hide.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            moviesList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, moviesList.size());

                            popupWindow.dismiss();
                        }
                    });

                    popupWindow.showAsDropDown(((FOLLOW) holder).menu, -60, -60);

                }
            });


            if (moviesList.get(position).getType().equals("following")) {
                final String name = moviesList.get(position).getUser().getUsername();
                String comment = context.getResources().getString(R.string.start_follow);
                final String of = moviesList.get(position).getFlow().getUsername();
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + of)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, of)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, of)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)
                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(of)) {
                                    if (moviesList.get(position).getFlow().getType().equals("customer")) {
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getFlow().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getFlow().getIdUser()));

                                }
                            }
                        }, false, name, of)
                        .setTextColor(R.color.gray_noir, name, of)
                        .build();
            }
            if (moviesList.get(position).getType().equals("promotion")) {
                String comment = moviesList.get(position).getAction() + " " + moviesList.get(position).getType();
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    if (moviesList.get(position).getAction().equals("like")) {
                        comment = "اعجب بعرض";
                    } else if (moviesList.get(position).getAction().equals("comment")) {
                        comment = "علق على";
                    } else if (moviesList.get(position).getAction().equals("share")) {
                        comment = "قام بمشاركة";
                    } else if (moviesList.get(position).getAction().equals("favorite")) {
                        comment = "أضاف الى مفضلته";
                    }
                }
                final String name = moviesList.get(position).getUser().getUsername();
                final String promotion = moviesList.get(position).getObject().get(0).getTitle() + " ";
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(
                                ((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + promotion)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, promotion)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, promotion)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(promotion)) {
                                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));

                                }
                            }
                        }, false, name, promotion)

                        .setTextColor(R.color.gray_noir, name, promotion)
                        .build();

            }
            if (moviesList.get(position).getType().equals("reservation")) {

                final String name = moviesList.get(position).getUser().getUsername();
                String comment = context.getResources().getString(R.string.make_reservation);
                String in = context.getResources().getString(R.string.in);
                final String salon = moviesList.get(position).getObject().get(0).getTitle();
                final String date = moviesList.get(position).getReservation().getDate();

                TextDecorator
                        .decorate(((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + salon + " " + in + " " + date)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, salon, date)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon, date)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, in)
                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon)) {
                                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));

                                }
                            }
                        }, false, name, salon)
                        .setTextColor(R.color.gray_noir, name, salon, date)
                        .build();
            }
            if (moviesList.get(position).getType().equals("service")) {

                String comment = moviesList.get(position).getAction() + " " + moviesList.get(position).getType();
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {

                    if (moviesList.get(position).getAction().equals("like")) {
                        comment = "اعجب بخدمة";
                    } else if (moviesList.get(position).getAction().equals("comment")) {
                        comment = "علق على";
                    } else if (moviesList.get(position).getAction().equals("share")) {
                        comment = "قام بمشاركة";
                    } else if (moviesList.get(position).getAction().equals("favorite")) {
                        comment = "أضاف الى مفضلته";
                    } else if (moviesList.get(position).getAction().equals("add")) {
                        comment = "قام باضافة خدمة";
                    }
                }
                final String service = moviesList.get(position).getObject().get(0).getName();
                final String name = moviesList.get(position).getUser().getUsername();
                TextDecorator
                        .decorate(
                                ((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + service)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, service)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, service)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(service)) {
                                    ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(moviesList.get(position).getObject().get(0).getId()));

                                }
                            }
                        }, false, name, service)
                        .setTextColor(R.color.gray_noir, name, service)
                        .build();

            }
            if (moviesList.get(position).getType().equals("cover")) {
                String comment = moviesList.get(position).getAction() + " " + moviesList.get(position).getType();
                String in = context.getResources().getString(R.string.in);
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {

                    if (moviesList.get(position).getAction().equals("like")) {
                        comment = "اعجب بصورة";
                    } else if (moviesList.get(position).getAction().equals("comment")) {
                        comment = "علق على صورة";
                    } else if (moviesList.get(position).getAction().equals("share")) {
                        comment = "قام بمشاركة";
                    } else if (moviesList.get(position).getAction().equals("favorite")) {
                        comment = "أضاف الى صورتك مفضلته";
                    } else if (moviesList.get(position).getAction().equals("add")) {
                        comment = "قام باضافة صورة";
                    }
                }
                final String name = moviesList.get(position).getUser().getUsername();
                final String salon = moviesList.get(position).getSalon().getUsername();
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);
                TextDecorator
                        .decorate(
                                ((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + in + " " + salon)
                        .setTextColor(R.color.gray, comment, in)
                        .setTextStyle(Typeface.BOLD, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, in)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon))
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));


                            }
                        }, false, name, salon)

                        .setTextColor(R.color.gray_noir, name, salon)
                        .build();
            }
            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((FOLLOW) holder).time.setText(timeAgo);
            ((FOLLOW) holder).time.setTypeface(MyApplication.type_jf_regular);

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150,150)
                    .into(((FOLLOW) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((FOLLOW) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((FOLLOW) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((FOLLOW) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((FOLLOW) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
        }
/*******************************************************************************************/
        if (holder instanceof Profile ) {


            ((Profile) holder).menu.setVisibility(View.VISIBLE);
            if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((Profile) holder).menu.setVisibility(View.VISIBLE);
            //>>
            else
                ((Profile) holder).menu.setVisibility(View.VISIBLE);

            ((Profile) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();

                    popupWindow = new PopupWindow(
                            popupView,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    ((TextView) popupView.findViewById(R.id.del)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.hid)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.edi)).setTypeface(MyApplication.type_jf_regular);

                    LinearLayout delete = (LinearLayout) popupView.findViewById(R.id.delete);
                    LinearLayout hide = (LinearLayout) popupView.findViewById(R.id.hide);
                    delete.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            DeleteActivity(moviesList.get(position).getId(), position);
                            popupWindow.dismiss();
                        }
                    });
                    hide.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            moviesList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, moviesList.size());

                            popupWindow.dismiss();
                        }
                    });

                    popupWindow.showAsDropDown(((Profile) holder).menu, -60, -60);

                }
            });

            String comment = "";
            String to = "";
            String message;
            final String name = moviesList.get(position).getUser().getUsername();
            if (moviesList.get(position).getAction().equals("location")) {
                comment = context.getResources().getString(R.string.add);
            } else comment = context.getResources().getString(R.string.edit);

            final String of = Convertiseurlag.getProfile(moviesList.get(position).getAction());
            message = moviesList.get(position).getMessage();
            if (moviesList.get(position).getAction().equals("location")) {
                to = moviesList.get(position).getMessage();
                message = "";
            } else {
                to = context.getResources().getString(R.string.to);
            }
            if (moviesList.get(position).getAction().equals("since_from")) {
                message = new SimpleDateFormat("MM/dd/yyyy").format(Long.parseLong(moviesList.get(position).getMessage()));
                to = context.getResources().getString(R.string.to);
            }
            ((Profile) holder).text.setTypeface(MyApplication.type_jf_regular);


            TextDecorator
                    .decorate(((Profile) holder).text, "\u200F" + name + " " + comment + " " + of + " " + to + " " + message)
                    .setTextColor(R.color.gray, comment, to)
                    .setTextStyle(Typeface.BOLD, name, of, message)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, of, message)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, to)

                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(name)) {
                                if (moviesList.get(position).getUser().getType().equals("salon")) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                } else
                                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                            }

                        }
                    }, false, name, of)
                    .setTextColor(R.color.gray_noir, name, of, message)
                    .build();
            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((Profile) holder).time.setText(timeAgo);
            ((Profile) holder).time.setTypeface(MyApplication.type_jf_regular);

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150,150)
                    .into(((Profile) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((Profile) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((Profile) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((Profile) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((Profile) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (moviesList.get(position) == null)
            return -1;
        if (moviesList.get(position).getType().equals("reservation")) {

            return 1;
        }
        if (moviesList.get(position).getType().equals("promotion")) {
            if (moviesList.get(position).getObject() != null)

                if (moviesList.get(position).getObject().size() > 1)
                    return 1;
                else if (moviesList.get(position).getObject().size() == 1)
                    return 1;
        }
        if (moviesList.get(position).getType().equals("following")) {
            return 1;

        }
        if (moviesList.get(position).getType().equals("cover")) {
            if (moviesList.get(position).getObject() != null)

                if (moviesList.get(position).getObject().size() > 1)
                    return 1;
                else if (moviesList.get(position).getObject().size() == 1)
                    return 1;
        } else {
            if (moviesList.get(position).getType().equals("service")) {
                if (moviesList.get(position).getObject() != null)

                    if (moviesList.get(position).getObject().size() > 1)
                        return 1;
                    else if (moviesList.get(position).getObject().size() == 1)
                        return 1;
            }
            if (moviesList.get(position).getType().equals("profile")) {

                return 2;
            }

        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void DeleteActivity(String id, final int position) {
        new GetActivity().DeleteActivity(id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    //   Toasty.success(context, context.getResources().getString(R.string.confimation_delete), Toast.LENGTH_SHORT).show();
                    moviesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, moviesList.size());
                }
            }

            @Override
            public void onFailure(Object result) {
                Toast.makeText(context, ""+result.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void OnError(String message) {
            }
        });
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public class MyViewHolderserviceMultiple extends RecyclerView.ViewHolder {


        public MyViewHolderserviceMultiple(View view) {
            super(view);


        }
    }

    public class MyViewHolderpromotion extends RecyclerView.ViewHolder {
        public TextView title, date, discount, time, action, salonname;
        CircleImageView imageView;
        RelativeLayout rel;
        ImageView imageViewP;
        ImageView menu;
        LinearLayout reservation;
        @BindView(R.id.linlike)
        LinearLayout linlike;
        @BindView(R.id.linfav)
        LinearLayout linfav;
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
        @BindView(R.id.imagelike)
        ImageView imagelike;
        @BindView(R.id.imagefav)
        ImageView imagefav;

        public MyViewHolderpromotion(View view) {
            super(view);
            ButterKnife.bind(this, view);

            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            action = (TextView) view.findViewById(R.id.action);
            salonname = (TextView) view.findViewById(R.id.salonname);
            imageViewP = (ImageView) view.findViewById(R.id.image);
            discount = (TextView) view.findViewById(R.id.discount);
            rel = (RelativeLayout) view.findViewById(R.id.rel);
            menu = (ImageView) view.findViewById(R.id.menu);
            reservation = (LinearLayout) view.findViewById(R.id.reservation);

        }


    }

    public class MyViewHolderCover extends RecyclerView.ViewHolder {
        public TextView title, date, discount, time, action, salonname;
        CircleImageView imageView;
        RelativeLayout rel;
        ImageView imageViewP;
        ImageView menu;
        LinearLayout reservation;
        @BindView(R.id.linlike)
        LinearLayout linlike;
        @BindView(R.id.linfav)
        LinearLayout linfav;
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
        @BindView(R.id.imagelike)
        ImageView imagelike;
        @BindView(R.id.imagefav)
        ImageView imagefav;

        public MyViewHolderCover(View view) {
            super(view);
            ButterKnife.bind(this, view);

            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            action = (TextView) view.findViewById(R.id.action);
            salonname = (TextView) view.findViewById(R.id.salonname);
            imageViewP = (ImageView) view.findViewById(R.id.image);
            rel = (RelativeLayout) view.findViewById(R.id.rel);
            menu = (ImageView) view.findViewById(R.id.menu);

        }

    }

    public class MyViewHolderservice extends RecyclerView.ViewHolder {
        public TextView title, date, time, action, salonname;
        CircleImageView imageView;
        RelativeLayout rel;
        ImageView imageViewP;
        ImageView menu;
        LinearLayout reservation;
        @BindView(R.id.linlike)
        LinearLayout linlike;
        @BindView(R.id.linfav)
        LinearLayout linfav;
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
        @BindView(R.id.imagelike)
        ImageView imagelike;
        @BindView(R.id.imagefav)
        ImageView imagefav;

        public MyViewHolderservice(View view) {
            super(view);
            ButterKnife.bind(this, view);

            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            time = (TextView) view.findViewById(R.id.time);
            action = (TextView) view.findViewById(R.id.action);
            salonname = (TextView) view.findViewById(R.id.salonname);
            imageViewP = (ImageView) view.findViewById(R.id.image);
            rel = (RelativeLayout) view.findViewById(R.id.rel);
            menu = (ImageView) view.findViewById(R.id.menu);
            reservation = (LinearLayout) view.findViewById(R.id.reservation);

        }

    }

    public class MultiPromotion extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        RecyclerView recycler_image;

        public MultiPromotion(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);

            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            recycler_image = (RecyclerView) view.findViewById(R.id.recycler_image);
        }
    }

    public class MultiCover extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        RecyclerView recycler_image;

        public MultiCover(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);

            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            recycler_image = (RecyclerView) view.findViewById(R.id.recycler_image);
        }
    }

    public class MultiService extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        RecyclerView recycler_image;

        public MultiService(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);

            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            recycler_image = (RecyclerView) view.findViewById(R.id.recycler_image);
        }
    }

    public class FOLLOW extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        ImageView menu;

        public FOLLOW(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            menu = (ImageView) view.findViewById(R.id.menu);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public class Profile extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        ImageView menu;

        public Profile(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            menu = (ImageView) view.findViewById(R.id.menu);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public class RESERVATION extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        ImageView menu;

        public RESERVATION(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            menu = (ImageView) view.findViewById(R.id.menu);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

}
