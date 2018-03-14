package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.google.gson.Gson;
import com.tuyenmonkey.textdecorator.TextDecorator;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.Api.GetActivity;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Activity.ListTimeline;
import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.Models.Payment_;
import salon.octadevtn.sa.salon.Models.WordDayHour;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.Reservation_home;
import salon.octadevtn.sa.salon.Utils.Convertiseurlag;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.ListeCover;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.Salon_services;
import salon.octadevtn.sa.salon.fragment.UserProfileFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

/**
 * Created by Marwen octadev on 7/14/2017.
 */

public class ActivityAaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final public int MULTI_LIKE_PROMOTION = 0;
    final public int FOLLOW = 1;
    Context context;
    RecyclerView recyclerView;
    Bitmap bitmap;
    salon.octadevtn.sa.salon.Models.Profile profile = null;
    private List<ListTimeline> moviesList;

    public ActivityAaptor(Context context, List<ListTimeline> moviesList, RecyclerView recyclerView) {
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
        if (viewType == 0) {
            // menu finish
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_photo, parent, false);
            return new MultiPromotion(itemView);

        } else if (viewType == 1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flowed, parent, false);
            return new FOLLOW(itemView);

        } else if (viewType == 5) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flowed, parent, false);
            return new Profile(itemView);

        } else if (viewType == 3) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_photo, parent, false);
            return new MultiService(itemView);

        } else if (viewType == 7) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_service, parent, false);
            return new MyViewHolderCover(itemView);

        } else if (viewType == 8) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_photo, parent, false);
            return new MultiCover(itemView);

        } else if (viewType == 9) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_flowed, parent, false);
            return new Price(itemView);

        } else if (viewType == 2) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_promotion, parent, false);
            return new MyViewHolderpromotion(itemView);
        } else {
            if (viewType == 4) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_service, parent, false);
                return new MyViewHolderservice(itemView);
            } else if (viewType == 6) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_flowed, parent, false);
                return new RESERVATION(itemView);
            }

        }
        return null;
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


        if (holder instanceof MyViewHolderpromotion) {
            (((MyViewHolderpromotion) holder).lincom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 0));
                }
            });

          /*  if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((MyViewHolderpromotion) holder).menu.setVisibility(View.GONE);
            else*/
            ((MyViewHolderpromotion) holder).menu.setVisibility(View.VISIBLE);

            ((MyViewHolderpromotion) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((MyViewHolderpromotion) holder).menu, -60, -60);

                }
            });
            ((MyViewHolderpromotion) holder).salonname.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderpromotion) holder).action.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderpromotion) holder).time.setTypeface(MyApplication.type_jf_regular);
            ((MyViewHolderpromotion) holder).title.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderpromotion) holder).date.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderpromotion) holder).discount.setTypeface(MyApplication.type_jf_medium);
            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
                    .into(((MyViewHolderpromotion) holder).imageView);
            ((MyViewHolderpromotion) holder).linshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = moviesList.get(position).getObject().get(0).getTitle();
                    if (moviesList.get(position).getObject().get(0).getTitle() != null && !moviesList.get(position).getObject().get(0).getTitle().equals("")) {
                        s = (moviesList.get(position).getObject().get(0).getTitle()).replaceAll(" ", "%20");
                    }
                    share("title=" + s + "&img=" + moviesList.get(position).getObject().get(0).getImage() +
                            "&type=promotion&id=" + moviesList.get(position).getObject().get(0).getId() +
                            "+&description=" + moviesList.get(position).getObject().get(0).getDescription());
                    Shared(String.valueOf(moviesList.get(position).getObject().get(0).getId()), "service");

                }
            });

            if (moviesList.get(position).getUser().getType().equals("salon")) {
                ((MyViewHolderpromotion) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            } else {
                if (moviesList.get(position).getUser().getGenre().equals("men")) {
                    ((MyViewHolderpromotion) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));

                } else
                    ((MyViewHolderpromotion) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            ((MyViewHolderpromotion) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
            String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " +
                    Convertiseurlag.getType(moviesList.get(position).getType());

            final String name = moviesList.get(position).getUser().getUsername();
            ((MyViewHolderpromotion) holder).salonname.setTypeface(MyApplication.type_jf_regular);
            TextDecorator
                    .decorate(
                            ((MyViewHolderpromotion) holder).salonname, "\u200F" + name + " " + comment)
                    .setTextColor(R.color.gray, comment)
                    .setTextStyle(Typeface.BOLD, name)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)

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
                    }, false, name)

                    .setTextColor(R.color.gray_noir, name)
                    .build();


            // ((MyViewHolderpromotion) holder).salonname.setText(moviesList.get(position).getUser().getUsername());


            ((MyViewHolderpromotion) holder).salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });

            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getObject().get(0).getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((MyViewHolderpromotion) holder).time.setText(timeAgo);

            //   ((MyViewHolderpromotion) holder).action.setText(moviesList .get(position).getAction()+" "+moviesList.get(position).getType());
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getObject().get(0).getImage())
                    .override(500, 500)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(((MyViewHolderpromotion) holder).imageViewP);
            (((MyViewHolderpromotion) holder).imageViewP).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));
                }
            });
            final MyViewHolderpromotion viewholder = (MyViewHolderpromotion) holder;
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date1 = dt.parse(moviesList.get(position).getObject().get(0).getStartDate());
                Date date2 = dt.parse(moviesList.get(position).getObject().get(0).getEndDate());
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(date2);

                if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                    viewholder.date.setText("من " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " إلى " +
                            c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                    );
                else
                    viewholder.date.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                            c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                    );


            } catch (ParseException e) {
                e.printStackTrace();
            }

            NumberFormat formatter = new DecimalFormat("#0.0");

            Double discount = Double.parseDouble(moviesList.get(position).getObject().get(0).getPrice()) - ((Double.parseDouble(moviesList.get(position).getObject().get(0).getPrice()) / 100) * (Double.parseDouble(moviesList.get(position).getObject().get(0).getDiscount())));

            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                viewholder.discount.setText("تخفيض %" + moviesList.get(position).getObject().get(0).getDiscount() + " - " + formatter.format(discount) + " بدلا من " + moviesList.get(position).getObject().get(0).getPrice());
            else
                viewholder.discount.setText("discount %" + moviesList.get(position).getObject().get(0).getDiscount() + " - " + formatter.format(discount) + " instead of " + moviesList.get(position).getObject().get(0).getPrice());


            //  viewholder.discount.setText("discount %" + moviesList.get(position).getObject().get(0).getDiscount() + " - " + moviesList.get(position).getObject().get(0).getCurrency() + " " + formatter.format(discount) + " instead of " + moviesList.get(position).getObject().get(0).getCurrency() + " " + moviesList.get(position).getObject().get(0).getPrice());
            if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                viewholder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                viewholder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            }
            if (moviesList.get(position).getObject().get(0).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getIsComment()) {
                viewholder.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getIsShare()) {
                viewholder.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getCountComment() == null)
                viewholder.textcomment.setText("0");

            else
                viewholder.textcomment.setText(moviesList.get(position).getObject().get(0).getCountComment() + "");

            viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + "");
            viewholder.textlike.setTypeface(MyApplication.type_jf_regular);
            viewholder.textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + "");
            viewholder.textfav.setTypeface(MyApplication.type_jf_regular);
            viewholder.textshare.setText(moviesList.get(position).getObject().get(0).getShare() + "");
            viewholder.textshare.setTypeface(MyApplication.type_jf_regular);
            viewholder.textcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //chiheb
                }
            });
         /* viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(1));
                }
            });*/


            viewholder.title.setText(moviesList.get(position).getObject().get(0).getTitle());

            viewholder.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getObject().get(0).getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(moviesList.get(position).getObject().get(0).getId(), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() - 1 + "");
                                    viewholder.textlike.setTypeface(MyApplication.type_jf_regular);
                                    moviesList.get(position).getObject().get(0).setIsLike("false");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Like(moviesList.get(position).getObject().get(0).getId() + "", "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsLike("true");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike() + 1);

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
            });


            viewholder.linfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(moviesList.get(position).getObject().get(0).getId(), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {

                                    ((MyViewHolderpromotion) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderpromotion) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderpromotion) holder).textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() - 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsFavorite("false");
                                    moviesList.get(position).getObject().get(0).setCountfavorite(moviesList.get(position).getObject().get(0).getCountfavorite() - 1);

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

                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(moviesList.get(position).getObject().get(0).getId(), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {

                                    ((MyViewHolderpromotion) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderpromotion) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderpromotion) holder).textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsFavorite("true");
                                    moviesList.get(position).getObject().get(0).setCountfavorite(moviesList.get(position).getObject().get(0).getCountfavorite() + 1);
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
            });
            if (profile.getType().equals("salon")) {
                ((MyViewHolderpromotion) holder).reserver.setVisibility(View.GONE);
            } else {
                ((MyViewHolderpromotion) holder).reserver.setVisibility(View.VISIBLE);
            }
            if (moviesList.get(position).getSalon() != null) {

                ((MyViewHolderpromotion) holder).reserver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((HomeActivityDrawer) context).setFragment(Reservation_home.newInstance(Integer.parseInt(moviesList.get(position).getId()), moviesList.get(position).getSalon().getUsername(), moviesList.get(position).getSalon().getSalonName()));

                    }
                });
            }
        }


        /****************************************   *****************************************/


        if (holder instanceof MyViewHolderCover) {
            /*if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((MyViewHolderCover) holder).menu.setVisibility(View.GONE);
            else*/
            ((MyViewHolderCover) holder).menu.setVisibility(View.VISIBLE);

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150,150)
                    .into(((MyViewHolderCover) holder).imageView);


            if (moviesList.get(position).getUser().getType().equals("salon")) {
                ((MyViewHolderCover) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            } else {
                if (moviesList.get(position).getUser().getGenre().equals("men")) {
                    ((MyViewHolderCover) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));

                } else
                    ((MyViewHolderCover) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            ((MyViewHolderCover) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

                }
            });
            String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " +
                    Convertiseurlag.getType(moviesList.get(position).getType());
            String in = context.getResources().getString(R.string.in);
            if (moviesList.get(position).getUser() != null && moviesList.get(position).getSalon() != null) {

                final String name = moviesList.get(position).getUser().getUsername();
                final String salon = moviesList.get(position).getSalon().getUsername();
                ((MyViewHolderCover) holder).salonname.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(
                                ((MyViewHolderCover) holder).salonname, "\u200F" + name + " " + comment + " " + in + " " + salon)
                        .setTextColor(R.color.gray, comment, in)
                        .setTextStyle(Typeface.BOLD, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, in)

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

                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

                            }
                        }, false, name, salon)

                        .setTextColor(R.color.gray_noir, name, salon)
                        .build();

            }
            // ((MyViewHolderpromotion) holder).salonname.setText(moviesList.get(position).getUser().getUsername());


            ((MyViewHolderCover) holder).salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });

            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getObject().get(0).getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((MyViewHolderCover) holder).time.setText(timeAgo);

            //   ((MyViewHolderpromotion) holder).action.setText(moviesList .get(position).getAction()+" "+moviesList.get(position).getType());
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getObject().get(0).getImageUrl())
                    .override(500, 500)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(((MyViewHolderCover) holder).imageViewP);
            (((MyViewHolderCover) holder).imageViewP).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    ArrayList<Cover> IMAGES = new ArrayList<>();
                    Cover cover = new Cover();
                    cover.setId(moviesList.get(position).getObject().get(0).getId());
                    cover.setCreatedAt(moviesList.get(position).getObject().get(0).getCreatedAt());
                    cover.setImageUrl(moviesList.get(position).getObject().get(0).getImageUrl());
                    cover.setIdSalon(moviesList.get(position).getObject().get(0).getIdSalon());

                    IMAGES.add(cover);
                    bundle.putSerializable("images", IMAGES);
                    bundle.putInt("position", 0);
                    bundle.putString("salonname", "jj");
                    ((HomeActivityDrawer) context).setFragment(new ListeCover(), bundle);

                    //    ((HomeActivityDrawer)context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId()));
                }
            });
            final MyViewHolderCover viewholder = (MyViewHolderCover) holder;
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                viewholder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                viewholder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            }
            if (moviesList.get(position).getObject().get(0).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getCountComment() == null)
                viewholder.textcomment.setText("0");
            if (moviesList.get(position).getObject().get(0).getIsComment()) {
                viewholder.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getIsShare()) {
                viewholder.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            } else {
                viewholder.textcomment.setText(moviesList.get(position).getObject().get(0).getCountComment() + "");
            }

            viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + "");
            viewholder.textlike.setTypeface(MyApplication.type_jf_regular);
            viewholder.textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + "");
            viewholder.textfav.setTypeface(MyApplication.type_jf_regular);
            viewholder.textshare.setText(moviesList.get(position).getObject().get(0).getShare() + "");
            viewholder.textshare.setTypeface(MyApplication.type_jf_regular);

            viewholder.menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(viewholder.menu, -60, -60);

                }
            });


            viewholder.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getObject().get(0).getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(moviesList.get(position).getObject().get(0).getId(), "cover", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() - 1 + "");
                                    viewholder.textlike.setTypeface(MyApplication.type_jf_regular);
                                    moviesList.get(position).getObject().get(0).setIsLike("false");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Like(moviesList.get(position).getObject().get(0).getId() + "", "cover", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + 1 + "");
                                    viewholder.textlike.setTypeface(MyApplication.type_jf_regular);
                                    moviesList.get(position).getObject().get(0).setIsLike("true");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike() + 1);

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
            });

            viewholder.linshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = moviesList.get(position).getObject().get(0).getName();
                    if (moviesList.get(position).getObject().get(0).getName() != null && !moviesList.get(position).getObject().get(0).getName().equals("")) {
                        s = (moviesList.get(position).getObject().get(0).getName()).replaceAll(" ", "%20");
                    }

                    share("title=cover&img=" + moviesList.get(position).getObject().get(0).getImageUrl() +
                            "&type=promotion&id=S-" + moviesList.get(position).getObject().get(0).getId() +
                            "+&description=" + "cover");


                    Shared(String.valueOf(moviesList.get(position).getObject().get(0).getId()), "cover");

                }
            });


            viewholder.linfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(moviesList.get(position).getObject().get(0).getId(), "cover", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderCover) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderCover) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderCover) holder).textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() - 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsFavorite("false");
                                    moviesList.get(position).getObject().get(0).setCountfavorite(moviesList.get(position).getObject().get(0).getCountfavorite() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(moviesList.get(position).getObject().get(0).getId(), "cover", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderCover) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderCover) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderCover) holder).textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsFavorite("true");
                                    moviesList.get(position).getObject().get(0).setCountfavorite(moviesList.get(position).getObject().get(0).getCountfavorite() + 1);
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
            });


        }


        /*********************************************************************************/


        if (holder instanceof MyViewHolderservice) {
           /* if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((MyViewHolderservice) holder).menu.setVisibility(View.GONE);
            else*/
            ((MyViewHolderservice) holder).menu.setVisibility(View.VISIBLE);

            ((MyViewHolderservice) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((MyViewHolderservice) holder).menu, -60, -60);

                }
            });
            (((MyViewHolderservice) holder).lincom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(moviesList.get(position).getObject().get(0).getId(), 0));
                }
            });

            MyViewHolderservice viewholder = (MyViewHolderservice) holder;
            ((MyViewHolderservice) viewholder).salonname.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderservice) viewholder).action.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderservice) viewholder).time.setTypeface(MyApplication.type_jf_regular);
            ((MyViewHolderservice) viewholder).title.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderservice) viewholder).date.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderservice) viewholder).discount.setTypeface(MyApplication.type_jf_medium);
            ((MyViewHolderservice) viewholder).linshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = moviesList.get(position).getObject().get(0).getTitle();
                    if (moviesList.get(position).getObject().get(0).getTitle() != null && !moviesList.get(position).getObject().get(0).getTitle().equals("")) {
                        s = (moviesList.get(position).getObject().get(0).getTitle()).replaceAll(" ", "%20");
                    }
                    share("title=" + s
                            + "&img=" + moviesList.get(position).getObject().get(0).getImageUrl() +
                            "&type=service&id=" + moviesList.get(position).getObject().get(0).getId() +
                            "+&description=" + moviesList.get(position).getObject().get(0).getDescription());
                    Shared(String.valueOf(moviesList.get(position).getObject().get(0).getId()), "service");
                }
            });

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150,150)
                    .into(((MyViewHolderservice) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon")) {
                viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            } else {
                if (moviesList.get(position).getUser().getGenre().equals("men")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //        viewholder.imageView.setBorderColor(MyApplication.getAppContext().getColor(R.color.color2));
                        viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));

                    } else {
                        viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2)
                        );
                    }

                } else
                    viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
            viewholder.salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });

            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getObject().get(0).getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            viewholder.time.setText(timeAgo);

            String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + Convertiseurlag.getType(moviesList.get(position).getType());
            if (moviesList.get(position).getUser() != null) {

                final String name = moviesList.get(position).getUser().getUsername();
                ((MyViewHolderservice) holder).salonname.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(
                                ((MyViewHolderservice) holder).salonname, "\u200F" + name + " " + comment)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)

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
                        }, false, name)
                        .setTextColor(R.color.gray_noir, name)
                        .build();
            }
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getObject().get(0).getImageUrl())
                    .override(500, 500)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            //   holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(viewholder.imageViewP);
            (viewholder.imageViewP).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));
                }
            });
            final MyViewHolderservice viewholder2 = (MyViewHolderservice) holder;


            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            viewholder2.title.setText(moviesList.get(position).getObject().get(0).getName());

            viewholder2.date.setText(moviesList.get(position).getObject().get(0).getCategoryname());


            if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                viewholder2.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                viewholder2.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            } else {
                viewholder2.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                viewholder2.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
            }

            if (moviesList.get(position).getObject().get(0).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            } else {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
            }
            if (moviesList.get(position).getObject().get(0).getIsComment()) {
                viewholder.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getIsShare()) {
                viewholder.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getObject().get(0).getCountComment() == null)
                viewholder2.textcomment.setText("0");
            else
                viewholder2.textcomment.setText(moviesList.get(position).getObject().get(0).getCountComment() + "");
            viewholder2.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + "");
            viewholder2.textlike.setTypeface(MyApplication.type_jf_regular);
            viewholder2.textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + "");
            viewholder2.textfav.setTypeface(MyApplication.type_jf_regular);
            viewholder2.textshare.setText(moviesList.get(position).getObject().get(0).getShare() + "");
            viewholder2.textshare.setTypeface(MyApplication.type_jf_regular);

          /*  viewholder2.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(1));
                }
            });*/

            viewholder2.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getObject().get(0).getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(moviesList.get(position).getObject().get(0).getId(), "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder2.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder2.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder2.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() - 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsLike("false");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Like(moviesList.get(position).getObject().get(0).getId() + "", "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder2.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder2.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder2.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsLike("true");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike() + 1);

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
            });


            viewholder2.linfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(moviesList.get(position).getObject().get(0).getId(), "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderservice) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderservice) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderservice) holder).textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() - 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsFavorite("false");
                                    moviesList.get(position).getObject().get(0).setCountfavorite(moviesList.get(position).getObject().get(0).getCountfavorite() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(moviesList.get(position).getObject().get(0).getId(), "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderservice) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderservice) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderservice) holder).textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsFavorite("true");
                                    moviesList.get(position).getObject().get(0).setCountfavorite(moviesList.get(position).getObject().get(0).getCountfavorite() + 1);
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

            });


        }


        if (holder instanceof MultiPromotion) {
      /*      if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((MultiPromotion) holder).menu.setVisibility(View.GONE);
            else*/
            ((MultiPromotion) holder).menu.setVisibility(View.VISIBLE);

            ((MultiPromotion) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((MultiPromotion) holder).menu, -60, -60);

                }
            });

            MultiPromotion viewholder = (MultiPromotion) holder;
            List<salon.octadevtn.sa.salon.Models.Activity.Object> activityList = new ArrayList<>();
            activityList.addAll(moviesList.get(position).getObject());
            GalleryPromotionAdapter adapter = new GalleryPromotionAdapter(context, activityList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 4);
            viewholder.recycler_image.setLayoutManager(mLayoutManager);
            if (moviesList.get(position).getUser().getType().equals("salon")) {
                viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            } else {
                if (moviesList.get(position).getUser().getGenre().equals("men")) {
                    viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));

                } else
                    viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }

            Glide.with(MyApplication.getAppContext()).
                    load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
                    .into(((MultiPromotion) holder).imageView);

            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });

            viewholder.recycler_image.setAdapter(adapter);
            String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + activityList.size() + " " + Convertiseurlag.getType(moviesList.get(position).getType());
            if (moviesList.get(position).getUser() != null && moviesList.get(position).getSalon() != null) {
                viewholder.text.setText(moviesList.get(position).getUser().getUsername());
                final String name = moviesList.get(position).getUser().getUsername();
                final String in = context.getResources().getString(R.string.in);
                final String salon = moviesList.get(position).getSalon().getUsername();
                ((MultiPromotion) holder).text.setTypeface(MyApplication.type_jf_regular);
                TextDecorator
                        .decorate(
                                ((MultiPromotion) holder).text, "\u200F" + name + " " + comment + " " + in + " " + salon)
                        .setTextColor(R.color.gray, comment, salon)
                        .setTextStyle(Typeface.BOLD, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, in)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

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
            ((MultiPromotion) holder).time.setText(timeAgo);

        }


        if (holder instanceof MultiCover) {
           /* if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((MultiCover) holder).menu.setVisibility(View.GONE);
            else*/
            ((MultiCover) holder).menu.setVisibility(View.VISIBLE);

            ((MultiCover) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((MultiCover) holder).menu, -60, -60);

                }
            });

            MultiCover viewholder = (MultiCover) holder;
            List<salon.octadevtn.sa.salon.Models.Activity.Object> activityList = new ArrayList<>();
            activityList.addAll(moviesList.get(position).getObject());
            GalleryCoverAdapter adapter = new GalleryCoverAdapter(context, activityList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 4);
            viewholder.recycler_image.setLayoutManager(mLayoutManager);
            if (moviesList.get(position).getUser().getType().equals("salon")) {
                viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            } else {
                if (moviesList.get(position).getUser().getGenre().equals("men")) {
                    viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));

                } else
                    viewholder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
                    .into(((MultiCover) holder).imageView);

            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getUser().getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getUser().getIdUser()));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
            viewholder.text.setText(moviesList.get(position).getUser().getUsername());

            viewholder.recycler_image.setAdapter(adapter);
            String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + activityList.size() + " " + Convertiseurlag.getType(moviesList.get(position).getType());
            if (moviesList.get(position).getUser() != null && moviesList.get(position).getSalon() != null) {

                final String name = moviesList.get(position).getUser().getUsername() + " ";
                final String in = context.getResources().getString(R.string.in);
                final String salon = " " + moviesList.get(position).getSalon().getUsername();
                ((MultiCover) holder).text.setTypeface(MyApplication.type_jf_regular);
                TextDecorator
                        .decorate(
                                ((MultiCover) holder).text, "\u200F" + name + " " + comment + " " + in + " " + salon)
                        .setTextColor(R.color.gray, comment, salon)
                        .setTextStyle(Typeface.BOLD, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, in)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

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
            ((MultiCover) holder).time.setText(timeAgo);


        }


        if (holder instanceof MultiService) {
            /*if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((MultiService) holder).menu.setVisibility(View.GONE);
            else*/
            ((MultiService) holder).menu.setVisibility(View.VISIBLE);

            ((MultiService) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((MultiService) holder).menu, -60, -60);

                }
            });

            MultiService viewholder = (MultiService) holder;
            List<salon.octadevtn.sa.salon.Models.Activity.Object> activityList = new ArrayList<>();
            activityList.addAll(moviesList.get(position).getObject());
            GalleryServiceAdapter adapter = new GalleryServiceAdapter(context, activityList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 4);
            viewholder.recycler_image.setLayoutManager(mLayoutManager);

            viewholder.recycler_image.setAdapter(adapter);


            String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + activityList.size() + " " + Convertiseurlag.getType(moviesList.get(position).getType());
            if (moviesList.get(position).getUser() != null && moviesList.get(position).getSalon() != null) {

                final String name = moviesList.get(position).getUser().getUsername();
                final String in = context.getResources().getString(R.string.in);
                final String salon = moviesList.get(position).getSalon().getUsername();
                ((MultiService) holder).text.setTypeface(MyApplication.type_jf_regular);
                TextDecorator
                        .decorate(
                                ((MultiService) holder).text, "\u200F" + name + " " + comment + " " + in + " " + salon)
                        .setTextColor(R.color.gray, comment, salon)
                        .setTextStyle(Typeface.BOLD, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, in)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

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
            ((MultiService) holder).time.setText(timeAgo);

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
                    .into(((MultiService) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((MultiService) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((MultiService) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((MultiService) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((MultiService) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });


        }


        if (holder instanceof FOLLOW) {
           /* if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((FOLLOW) holder).menu.setVisibility(View.GONE);
            else*/
            ((FOLLOW) holder).menu.setVisibility(View.VISIBLE);
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
                                if (moviesList.get(position).getUser().getType().equals("salon")) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    return;
                                } else
                                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                return;
                            }
                            if (text.equals(of)) {
                                if (moviesList.get(position).getFlow().getType().equals("salon")) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getFlow().getIdUser()));
                                    return;
                                } else {
                                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getFlow().getIdUser()));
                                    return;
                                }
                            }
                        }
                    }, false, name, of)
                    .setTextColor(R.color.gray_noir, name, of)
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
            ((FOLLOW) holder).time.setText(timeAgo);
            ((FOLLOW) holder).time.setTypeface(MyApplication.type_jf_regular);

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
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
                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
        }


        if (holder instanceof Price) {
           /* if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((Price) holder).menu.setVisibility(View.GONE);
            else*/
            ((Price) holder).menu.setVisibility(View.VISIBLE);

            ((Price) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((Price) holder).menu, -60, -60);

                }
            });
            if (moviesList.get(position).getUser() != null && moviesList.get(position).getSalon() != null) {

                String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + Convertiseurlag.getType(moviesList.get(position).getType());

                final String name = moviesList.get(position).getUser().getUsername();
                final String salon = moviesList.get(position).getSalon().getUsername();
                ((Price) holder).text.setTypeface(MyApplication.type_jf_regular);
                TextDecorator
                        .decorate(
                                ((Price) holder).text, "\u200F" + name + " " + comment + " " + salon)
                        .setTextColor(R.color.gray, comment, salon)
                        .setTextStyle(Typeface.BOLD, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)

                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

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
            ((Price) holder).time.setText(timeAgo);

            Glide.with(MyApplication.getAppContext()).
                    load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
                    .into(((Price) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((Price) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((Price) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((Price) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((Price) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
        }


        if (holder instanceof RESERVATION) {
         /*   if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((RESERVATION) holder).menu.setVisibility(View.GONE);
            else*/
            ((RESERVATION) holder).menu.setVisibility(View.VISIBLE);
            ((RESERVATION) holder).menu.setOnClickListener(new View.OnClickListener() {
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

                    popupWindow.showAsDropDown(((RESERVATION) holder).menu, -60, -60);

                }
            });
            if (moviesList.get(position).getUser() != null && moviesList.get(position).getSalon() != null) {

                final String name = moviesList.get(position).getUser().getUsername();
                String comment = context.getResources().getString(R.string.make_reservation);
                String in = context.getResources().getString(R.string.in) + " ";
                final String salon = moviesList.get(position).getSalon().getUsername();
                String date = moviesList.get(position).getReservation().getDate();
                ((RESERVATION) holder).text.setTypeface(MyApplication.type_jf_regular);
                final SimpleDateFormat formatd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
                try {
                    Date d = formatd.parse(date);
                    SimpleDateFormat format2 = new SimpleDateFormat("EEE, dd MMM");
                    date = format2.format(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TextDecorator
                        .decorate(((RESERVATION) holder).text, "\u200F" + name + " " + comment + " " + salon + " " + in + " " + date)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, salon, date)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon, date)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, in)
                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                                    } else
                                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                                }
                                if (text.equals(salon)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }
                            }
                        }, false, name, salon)
                        .setTextColor(R.color.gray_noir, name, salon, date)
                        .build();
            }
            Calendar calendar = Calendar.getInstance();
            Date date2;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date2 = format.parse(moviesList.get(position).getCreatedAt() + "");
                calendar.setTime(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((RESERVATION) holder).time.setText(timeAgo);
            ((RESERVATION) holder).time.setTypeface(MyApplication.type_jf_regular);

            Glide.with(MyApplication.getAppContext())
                    .load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage())
                    .override(150, 150)
                    .into(((RESERVATION) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((RESERVATION) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((RESERVATION) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((RESERVATION) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((RESERVATION) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("salon")) {
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });
        }


        if (holder instanceof Profile) {


        /*    if (!moviesList.get(position).getUser().getIdUser().equals(profile.getUserId()))
                ((Profile) holder).menu.setVisibility(View.GONE);
            else*/
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

            String of = Convertiseurlag.getProfile(moviesList.get(position).getAction());
            message = moviesList.get(position).getMessage();
            if (moviesList.get(position).getAction().equals("location")) {
                to = moviesList.get(position).getMessage();
                message = "";
            } else {
                to = context.getResources().getString(R.string.to);
            }
            if (moviesList.get(position).getAction().equals("since_from")) {
                if (!moviesList.get(position).getMessage().contains("-"))
                    message = new SimpleDateFormat("MM/dd/yyyy").format(Long.parseLong(moviesList.get(position).getMessage()));
                else {
                    message = moviesList.get(position).getMessage();
                }
                to = context.getResources().getString(R.string.to);
            }
            ((Profile) holder).text.setTypeface(MyApplication.type_jf_regular);
            if (moviesList.get(position).getAction().equals("branche")) {
                of = context.getResources().getString(R.string.branche);

            }
            if (moviesList.get(position).getAction().equals("payment")) {
                of = context.getResources().getString(R.string.payment);
                Gson gson = new Gson();
                Payment_[] payments = gson.fromJson(message, Payment_[].class);
                List<Payment_> pay = new ArrayList<>();
                for (int j = 0; j < payments.length; j++)
                    pay.add(payments[j]);

                if (pay.size() > 0) {
                    message = "";
                    for (int i = 0; i < pay.size(); i++) {
                        if (pay.get(i).getName().contains("visa") && pay.get(i).getStatus() == true) {
                            message = message + " visa card";
                        }
                        if (pay.get(i).getName().contains("master") && pay.get(i).getStatus() == true) {
                            message = message + ", master card";
                        }
                        if (pay.get(i).getName().contains("cash") && pay.get(i).getStatus() == true) {
                            message = message + ", cash";
                        }
                        if (pay.get(i).getName().contains("debit card") && pay.get(i).getStatus() == true) {
                            message = message + ", debit card";
                        }
                    }
                }

            }

            if (moviesList.get(position).getAction().equals("work") && !message.equals("")) {
                String work = "";
                Gson gson = new Gson();
                of = context.getResources().getString(R.string.workhours);
                WordDayHour[] dayHour = gson.fromJson(message, WordDayHour[].class);
                String first = dayHour[0].getName();
                String houropen = dayHour[0].getStartHour();
                String hourend = dayHour[0].getEndHours();
                boolean status = dayHour[0].getStatus();
                for (int i = 0; i < dayHour.length; i++) {
                    if (dayHour[i].getStatus() != status) {
                        if (i == dayHour.length - 1) {
                            if (status) {
                                if (dayHour[i].getName().equals(first)) {
                                    work += first + " " + houropen + " - " + hourend;

                                } else {
                                    work += first + " - " + dayHour[i].getName() + " " + houropen + " - " + hourend;
                                }
                                status = dayHour[i].getStatus();
                                first = dayHour[i].getName();

                            } else {
                                if (dayHour[i].getName().equals(first)) {

                                    work += first + " " + context.getResources().getString(R.string.closed);

                                } else
                                    work += first + " - " + dayHour[i].getName() + " " + context.getResources().getString(R.string.closed);
                                status = dayHour[i].getStatus();
                                first = dayHour[i].getName();
                            }
                        }
                        if (status) {
                            if (dayHour[i - 1].getName().equals(first)) {
                                work += first + " " + houropen + " - " + hourend + "\n";
                            } else
                                work += first + " - " + dayHour[i - 1].getName() + " " + houropen + " - " + hourend + "\n";

                            status = dayHour[i].getStatus();
                            first = dayHour[i].getName();
                        } else {
                            if (dayHour[i - 1].getName().equals(first)) {
                                work += first + context.getResources().getString(R.string.closed) + "\n";
                            } else
                                work += first + " - " + dayHour[i - 1].getName() + " " + context.getResources().getString(R.string.closed) + "\n";
                            status = dayHour[i].getStatus();
                            first = dayHour[i].getName();
                        }
                    }
                }
                message = work;
            }
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
                    .override(150, 150)
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

            return 6;
        }
        if (moviesList.get(position).getType().equals("promotion")) {
            if (moviesList.get(position).getObject() != null)

                if (moviesList.get(position).getObject().size() > 1)
                    return 0;
                else if (moviesList.get(position).getObject().size() == 1)
                    return 2;
        }
        if (moviesList.get(position).getType().equals("following")) {
            return 1;

        }
        if (moviesList.get(position).getType().equals("price")) {
            return 9;

        }
        if (moviesList.get(position).getType().equals("cover")) {
            if (moviesList.get(position).getObject() != null)

                if (moviesList.get(position).getObject().size() > 1)
                    return 8;
                else if (moviesList.get(position).getObject().size() == 1)
                    return 7;
        } else {
            if (moviesList.get(position).getType().equals("service")) {
                if (moviesList.get(position).getObject() != null)

                    if (moviesList.get(position).getObject().size() > 1)
                        return 3;
                    else if (moviesList.get(position).getObject().size() == 1)
                        return 4;
            }
            if (moviesList.get(position).getType().equals("profile")) {

                return 5;
            }

        }
        return 0;
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
                    moviesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, moviesList.size());
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    public void share(String s) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, "http://www.salon.com.sa/partage.php?" + s);
        context.startActivity(Intent.createChooser(share, "Share using!"));
    }

    public void Shared(String id, final String type) {
        new salon.octadevtn.sa.salon.Api.Promotion().Share(id, type, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {

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
        @BindView(R.id.reserver)
        LinearLayout reserver;
        @BindView(R.id.linfav)
        LinearLayout linfav;
        @BindView(R.id.lincom)
        LinearLayout lincom;
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
        @BindView(R.id.imagecomment)
        ImageView imagecomment;
        @BindView(R.id.imageshare)
        ImageView imageshare;

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
        @BindView(R.id.imagecomment)
        ImageView imagecomment;
        @BindView(R.id.imageshare)
        ImageView imageshare;

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
        @BindView(R.id.discount)
        TextView discount;
        @BindView(R.id.imagelike)
        ImageView imagelike;
        @BindView(R.id.lincom)
        LinearLayout lincom;
        @BindView(R.id.imagefav)
        ImageView imagefav;
        @BindView(R.id.imagecomment)
        ImageView imagecomment;
        @BindView(R.id.imageshare)
        ImageView imageshare;

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
        ImageView menu;

        public MultiPromotion(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);

            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            menu = (ImageView) view.findViewById(R.id.menu);
            recycler_image = (RecyclerView) view.findViewById(R.id.recycler_image);
        }
    }

    public class MultiCover extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        RecyclerView recycler_image;
        ImageView menu;

        public MultiCover(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            menu = (ImageView) view.findViewById(R.id.menu);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            recycler_image = (RecyclerView) view.findViewById(R.id.recycler_image);
        }
    }

    public class MultiService extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        RecyclerView recycler_image;
        ImageView menu;

        public MultiService(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            menu = (ImageView) view.findViewById(R.id.menu);
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

    public class Price extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView text, time;
        ImageView menu;

        public Price(View view) {
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

            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            menu = (ImageView) view.findViewById(R.id.menu);
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
