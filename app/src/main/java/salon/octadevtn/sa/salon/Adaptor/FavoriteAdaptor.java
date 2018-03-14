package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;
import com.tuyenmonkey.textdecorator.TextDecorator;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Favorite;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.Reservation_home;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.Salon_services;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

/**
 * Created by Marwen octadev on 7/8/2017.
 */

public class FavoriteAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    View popupView;
    salon.octadevtn.sa.salon.Models.Profile profile = null;
    private List<Favorite> moviesList;

    public FavoriteAdaptor(List<Favorite> moviesList, Context context, RecyclerView recyclerView) {
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
        if (viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_promotion, parent, false);
            return new MyViewHolderpromotion(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_service, parent, false);
            return new MyViewHolderservice(itemView);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null)
                    popupWindow.dismiss();
                return false;
            }
        });

        /////////*********************************************///////////////////////////
        if (holder instanceof MyViewHolderpromotion) {
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getSalon().getPhoto()).into(((MyViewHolderpromotion) holder).imageView);
            ((MyViewHolderpromotion) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));

            ((MyViewHolderpromotion) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                }
            });


            ((MyViewHolderpromotion) holder).salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                }
            });

            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getObject().getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((MyViewHolderpromotion) holder).time.setText(timeAgo);
            ((MyViewHolderpromotion) holder).time.setTypeface(MyApplication.type_jf_regular);

            //   ((MyViewHolderpromotion) holder).action.setText(MyApplication.getAppContext().getResources().getString(R.string.addpromotion));

            String comment = MyApplication.getAppContext().getResources().getString(R.string.addpromotion);

            final String name = moviesList.get(position).getSalon().getUsername();
            ((MyViewHolderpromotion) holder).salonname.setTypeface(MyApplication.type_jf_regular);
            if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
                TextDecorator
                        .decorate(
                                ((MyViewHolderpromotion) holder).salonname, "\u200F" + name + " " + comment)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, comment)
                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

                            }
                        }, false, name)
                        .setTextColor(R.color.gray_noir, name)
                        .build();
            else
                TextDecorator
                        .decorate(
                                ((MyViewHolderpromotion) holder).salonname, "\u200F" + name + " " + comment)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name)
                        .setAbsoluteSize(MyApplication.getAppContext().getResources().getInteger(R.integer.size), true, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)
                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(name)) {
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                                }

                            }
                        }, false, name)
                        .setTextColor(R.color.gray_noir, name)
                        .build();
            ((MyViewHolderpromotion) holder).salonname.setTypeface(MyApplication.type_jf_regular);


            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getObject().getImage())
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
                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().getId(), 1));
                }
            });
            (((MyViewHolderpromotion) holder).lincom).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().getId(), 0));
                }
            });


            if (profile.getType().equals("salon")) {
                ((MyViewHolderpromotion) holder).reserver.setVisibility(View.GONE);
            } else {
                ((MyViewHolderpromotion) holder).reserver.setVisibility(View.VISIBLE);
            }
            (((MyViewHolderpromotion) holder).reserver).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(Reservation_home.newInstance(moviesList.get(position).getObject().getId(), moviesList.get(position).getSalon().getUsername(), moviesList.get(position).getSalon().getSalonName()));
                }
            });
            final MyViewHolderpromotion viewholder = (MyViewHolderpromotion) holder;
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            viewholder.date.setTypeface(MyApplication.type_jf_regular);

            try {
                Date date1 = dt.parse(moviesList.get(position).getObject().getStartDate());
                Date date2 = dt.parse(moviesList.get(position).getObject().getEndDate());
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

            Double discount = Double.parseDouble(moviesList.get(position).getObject().getPrice()) - ((Double.parseDouble(moviesList.get(position).getObject().getPrice()) / 100) * (Double.parseDouble(moviesList.get(position).getObject().getDiscount())));
            viewholder.discount.setText("discount %" + moviesList.get(position).getObject().getDiscount() + " - " + moviesList.get(position).getObject().getCurrency() + " " + formatter.format(discount) + " instead of " + moviesList.get(position).getObject().getCurrency() + " " + moviesList.get(position).getObject().getPrice());

            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                viewholder.discount.setText("تخفيض %" + moviesList.get(position).getObject().getDiscount() + " - " + formatter.format(discount) + " بدلا من " + moviesList.get(position).getObject().getPrice());
            else
                viewholder.discount.setText("discount %" + moviesList.get(position).getObject().getDiscount() + " - " + formatter.format(discount) + " instead of " + moviesList.get(position).getObject().getPrice());

            viewholder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            viewholder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

            if (moviesList.get(position).getObject().getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color3));
                viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color3));
            }


            if (moviesList.get(position).getObject().getIsComment()) {
                viewholder.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            if (moviesList.get(position).getObject().getIsShare()) {
                viewholder.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            viewholder.textcomment.setText(moviesList.get(position).getObject().getCountComment() + "");
            viewholder.textcomment.setTypeface(MyApplication.type_jf_regular);

            viewholder.textlike.setText(moviesList.get(position).getObject().getCountLike() + "");
            viewholder.textlike.setTypeface(MyApplication.type_jf_regular);

            viewholder.textfav.setText(moviesList.get(position).getObject().getCountFavorit() + "");
            viewholder.textfav.setTypeface(MyApplication.type_jf_regular);

            viewholder.textshare.setText(moviesList.get(position).getObject().getShare() + "");
            viewholder.textshare.setTypeface(MyApplication.type_jf_regular);

            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));
                }
            });

            viewholder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = layoutInflater.inflate(R.layout.pop_up, null);
                    if (popupWindow == null) popupWindow = new PopupWindow(
                            popupView,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(
                            android.graphics.Color.TRANSPARENT));

                    LinearLayout delete = (LinearLayout) popupView.findViewById(R.id.delete);
                    LinearLayout hide = (LinearLayout) popupView.findViewById(R.id.hide);
                    ((TextView) popupView.findViewById(R.id.del)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.hid)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.edi)).setTypeface(MyApplication.type_jf_regular);

                    delete.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // chiheb
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

            viewholder.title.setText(moviesList.get(position).getObject().getTitle());
            viewholder.linshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = moviesList.get(position).getObject().getTitle();
                    if (moviesList.get(position).getObject().getTitle() != null && !moviesList.get(position).getObject().getTitle().equals("")) {
                        s = (moviesList.get(position).getObject().getTitle()).replaceAll(" ", "%20");
                    }
                    share("title=" + s
                            + "&img=" + moviesList.get(position).getObject().getImageUrl() +
                            "&type=promotion&id=" + moviesList.get(position).getObject().getId() +
                            "+&description=" + moviesList.get(position).getObject().getDescription());

                    Shared(String.valueOf(moviesList.get(position).getObject().getId()), "promotion");

                }
            });
            viewholder.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getObject().getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(moviesList.get(position).getObject().getId(), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setText(moviesList.get(position).getObject().getCountLike() - 1 + "");
                                    moviesList.get(position).getObject().setIsLike("false");
                                    moviesList.get(position).getObject().setCountLike(moviesList.get(position).getObject().getCountLike() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Like(moviesList.get(position).getObject().getId() + "", "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {

                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setText(moviesList.get(position).getObject().getCountLike() + 1 + "");
                                    moviesList.get(position).getObject().setIsLike("true");
                                    moviesList.get(position).getObject().setCountLike(moviesList.get(position).getObject().getCountLike() + 1);

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
                    new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(moviesList.get(position).getObject().getId(), "promotion", new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                moviesList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());

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

            });


        }

        /////////*********************************************///////////////////////////


        if (holder instanceof MyViewHolderservice) {
            MyViewHolderservice viewholder = (MyViewHolderservice) holder;


            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getSalon().getPhoto()).into(((MyViewHolderservice) holder).imageView);
            ((MyViewHolderservice) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));

            ((MyViewHolderservice) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                }
            });
            ((MyViewHolderservice) holder).salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                }
            });


            Calendar calendar = Calendar.getInstance();
            Date date;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(moviesList.get(position).getObject().getCreatedAt() + "");
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
            ((MyViewHolderservice) holder).time.setText(timeAgo);
            ((MyViewHolderservice) holder).time.setTypeface(MyApplication.type_jf_regular);


            String comment = MyApplication.getAppContext().getResources().getString(R.string.addservice);

            final String name = moviesList.get(position).getSalon().getUsername();
            ((MyViewHolderservice) holder).salonname.setTypeface(MyApplication.type_jf_regular);
            if (MyApplication.getInstance().getPrefManager().getLang().equals("0"))
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
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));
                                }

                            }
                        }, false, name)
                        .setTextColor(R.color.gray_noir, name)
                        .build();

            else TextDecorator
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
                                ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                            }

                        }
                    }, false, name)
                    .setTextColor(R.color.gray_noir, name)
                    .build();
            ((MyViewHolderservice) holder).salonname.setTypeface(MyApplication.type_jf_regular);

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getObject().getImageUrl())
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

                    .into(((MyViewHolderservice) holder).imageViewP);
            (((MyViewHolderservice) holder).imageViewP).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(moviesList.get(position).getObject().getId()));
                }
            });
            final MyViewHolderservice viewholder2 = (MyViewHolderservice) holder;


            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            viewholder2.title.setText(moviesList.get(position).getObject().getName());
            viewholder2.title.setTypeface(MyApplication.type_jf_medium);
            viewholder2.date.setText(moviesList.get(position).getObject().getCategoryname());

            viewholder2.title.setTypeface(MyApplication.type_jf_regular);
            viewholder2.date.setTypeface(MyApplication.type_jf_regular);

            viewholder2.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            viewholder2.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

            if (moviesList.get(position).getObject().getIsLike()) {
                viewholder2.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder2.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            if (moviesList.get(position).getObject().getIsComment()) {
                viewholder2.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder2.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }
            if (moviesList.get(position).getObject().getIsShare()) {
                viewholder2.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder2.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            }

            viewholder2.textcomment.setText(moviesList.get(position).getObject().getCountComment() + "");
            viewholder2.textcomment.setTypeface(MyApplication.type_jf_regular);
            viewholder2.textlike.setText(moviesList.get(position).getObject().getCountLike() + "");
            viewholder2.textlike.setTypeface(MyApplication.type_jf_regular);
            viewholder2.textfav.setText(moviesList.get(position).getObject().getCountFavorit() + "");
            viewholder2.textfav.setTypeface(MyApplication.type_jf_regular);
            viewholder2.textshare.setText(moviesList.get(position).getObject().getShare() + "");
            viewholder2.textshare.setTypeface(MyApplication.type_jf_regular);

            viewholder2.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));
                }
            });

            viewholder2.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = layoutInflater.inflate(R.layout.pop_up, null);

                    if (popupWindow == null) popupWindow = new PopupWindow(
                            popupView,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout delete = (LinearLayout) popupView.findViewById(R.id.delete);
                    LinearLayout hide = (LinearLayout) popupView.findViewById(R.id.hide);
                    ((TextView) popupView.findViewById(R.id.del)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.hid)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.edi)).setTypeface(MyApplication.type_jf_regular);
                    delete.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // chiheb
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


                    popupWindow.showAsDropDown(viewholder2.menu, -60, -60);

                }
            });

            viewholder.linshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = moviesList.get(position).getObject().getTitle();
                    if (moviesList.get(position).getObject().getTitle() != null && !moviesList.get(position).getObject().getTitle().equals("")) {

                        s = (moviesList.get(position).getObject().getTitle()).replaceAll(" ", "%20");
                    }
                    share("title=" + s
                            + "&img=" + moviesList.get(position).getObject().getImageUrl() +
                            "&type=service&id=" + moviesList.get(position).getObject().getId() +
                            "+&description=" + moviesList.get(position).getObject().getDescription());
                    Shared(String.valueOf(moviesList.get(position).getObject().getId()), "service");


                }
            });
            viewholder2.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getObject().getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(moviesList.get(position).getObject().getId(), "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder2.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder2.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder2.textlike.setText(moviesList.get(position).getObject().getCountLike() - 1 + "");
                                    viewholder2.textlike.setTypeface(MyApplication.type_jf_regular);
                                    moviesList.get(position).getObject().setIsLike("false");
                                    moviesList.get(position).getObject().setCountLike(moviesList.get(position).getObject().getCountLike() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Like(moviesList.get(position).getObject().getId() + "", "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder2.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder2.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

                                    viewholder2.textlike.setText(moviesList.get(position).getObject().getCountLike() + 1 + "");
                                    viewholder2.textlike.setTypeface(MyApplication.type_jf_regular);
                                    moviesList.get(position).getObject().setIsLike("true");
                                    moviesList.get(position).getObject().setCountLike(moviesList.get(position).getObject().getCountLike() + 1);

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
                    new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(moviesList.get(position).getObject().getId(), "service", new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                moviesList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());

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

            });


        }


    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1; //Default is 1
        if (moviesList.get(position).getObject().getType().equals("promotion"))
            viewType = 0; //if zero, it will be a header view
        return viewType;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
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
        @BindView(R.id.reserver)
        LinearLayout reserver;
        @BindView(R.id.lincom)
        LinearLayout lincom;
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

    public class MyViewHolderservice extends RecyclerView.ViewHolder {
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
        @BindView(R.id.imagecomment)

        ImageView imagecomment;
        @BindView(R.id.imageshare)

        ImageView imageshare;
        @BindView(R.id.textfav)
        TextView textfav;
        @BindView(R.id.imagelike)
        ImageView imagelike;
        @BindView(R.id.imagefav)
        ImageView imagefav;
        @BindView(R.id.lincom)
        LinearLayout lincom;

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
            discount = (TextView) view.findViewById(R.id.discount);
            rel = (RelativeLayout) view.findViewById(R.id.rel);
            menu = (ImageView) view.findViewById(R.id.menu);
            reservation = (LinearLayout) view.findViewById(R.id.reservation);

        }

    }


}
