package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
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
import com.tuyenmonkey.textdecorator.TextDecorator;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import salon.octadevtn.sa.salon.Models.Cover;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.Convertiseurlag;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.ListeCover;
import salon.octadevtn.sa.salon.fragment.NotifFragmentment;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.Salon_services;
import salon.octadevtn.sa.salon.fragment.UserProfileFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

/**
 * Created by Marwen octadev on 7/14/2017.
 */

public class NotifAaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final public int MULTI_LIKE_PROMOTION = 0;
    final public int FOLLOW = 1;
    Context context;
    RecyclerView recyclerView;
    private List<ListTimeline> moviesList;

    public NotifAaptor(Context context, List<ListTimeline> moviesList, RecyclerView recyclerView) {
        this.moviesList = moviesList;
        this.context = context;
        this.recyclerView = recyclerView;


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




        //-------


        //--------
        if (holder instanceof MyViewHolderpromotion) {
            ((MyViewHolderpromotion) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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


            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((MyViewHolderpromotion) holder).imageView);
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

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

                }
            });
            String comment = moviesList.get(position).getAction() + " " + moviesList.get(position).getType();

            final String name = moviesList.get(position).getUser().getUsername();
            ((MyViewHolderpromotion) holder).salonname.setTypeface(MyApplication.type_jf_regular);
            TextDecorator
                    .decorate(
                            ((MyViewHolderpromotion) holder).salonname, name + " " + comment)
                    .setTextColor(R.color.gray, comment)
                    .setTextStyle(Typeface.BOLD, name)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
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

                        }
                    }, false, name)

                    .setTextColor(R.color.gray_noir, name)
                    .build();


            // ((MyViewHolderpromotion) holder).salonname.setText(moviesList.get(position).getUser().getUsername());


            ((MyViewHolderpromotion) holder).salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

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
            ((MyViewHolderpromotion) holder).time.setTypeface(MyApplication.type_jf_regular);


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
                viewholder.date.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                );


            } catch (ParseException e) {
                e.printStackTrace();
            }

            NumberFormat formatter = new DecimalFormat("#0.0");

            Double discount = Double.parseDouble(moviesList.get(position).getObject().get(0).getPrice()) - ((Double.parseDouble(moviesList.get(position).getObject().get(0).getPrice()) / 100) * (Double.parseDouble(moviesList.get(position).getObject().get(0).getDiscount())));
            viewholder.discount.setText("discount %" + moviesList.get(position).getObject().get(0).getDiscount() + " - " + moviesList.get(position).getObject().get(0).getCurrency() + " " + formatter.format(discount) + " instead of " + moviesList.get(position).getObject().get(0).getCurrency() + " " + moviesList.get(position).getObject().get(0).getPrice());
            if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                viewholder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            }
            if (moviesList.get(position).getObject().get(0).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color3));
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
            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(1));
                }
            });
            viewholder.rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(new PromotionFragment());
                }
            });
            viewholder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() - 1 + "");
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
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsLike("true");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike());

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


        }


        /*********************************************************************************/


        if (holder instanceof MyViewHolderCover) {
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((MyViewHolderCover) holder).imageView);


            ((MyViewHolderCover) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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

                    popupWindow.showAsDropDown(((MyViewHolderCover) holder).menu, -60, -60);

                }
            });


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
            String comment = moviesList.get(position).getAction() + " " + moviesList.get(position).getType();
            String in = context.getResources().getString(R.string.in);

            final String name = moviesList.get(position).getUser().getUsername();
            final String salon = moviesList.get(position).getSalon().getUsername();
            ((MyViewHolderCover) holder).salonname.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(
                            ((MyViewHolderCover) holder).salonname, name + " " + comment + " " + in + " " + salon)
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


            // ((MyViewHolderpromotion) holder).salonname.setText(moviesList.get(position).getUser().getUsername());


            ((MyViewHolderCover) holder).salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

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
            ((MyViewHolderCover) holder).time.setTypeface(MyApplication.type_jf_regular);

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

                    //    HomeActivityDrawer.setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId()));
                }
            });
            final MyViewHolderCover viewholder = (MyViewHolderCover) holder;
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                viewholder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            }
            if (moviesList.get(position).getObject().get(0).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color3));
            }
            if (moviesList.get(position).getObject().get(0).getCountComment() == null)
                viewholder.textcomment.setText("0");

            else
                viewholder.textcomment.setText(moviesList.get(position).getObject().get(0).getCountComment() + "");

            viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + "");
            viewholder.textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + "");
            viewholder.textshare.setText(moviesList.get(position).getObject().get(0).getShare() + "");
            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(1));
                }
            });
            viewholder.rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(new PromotionFragment());
                }
            });
            viewholder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() - 1 + "");
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
                                    viewholder.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsLike("true");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike());

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
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(moviesList.get(position).getObject().get(0).getId(), "cover", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderCover) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
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
            final MyViewHolderservice viewholder = (MyViewHolderservice) holder;


            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((MyViewHolderservice) holder).imageView);
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

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

                }
            });
            viewholder.salonname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

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
            viewholder.time.setTypeface(MyApplication.type_jf_regular);

            String comment = moviesList.get(position).getAction() + " " + moviesList.get(position).getType();

            final String name = moviesList.get(position).getUser().getUsername();
            ((MyViewHolderservice) holder).salonname.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(
                            ((MyViewHolderservice) holder).salonname, name + " " + comment)
                    .setTextColor(R.color.gray, comment)
                    .setTextStyle(Typeface.BOLD, name)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
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

                        }
                    }, false, name)
                    .setTextColor(R.color.gray_noir, name)
                    .build();
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
                    ((HomeActivityDrawer) context).setFragment(Salon_services.newInstance(moviesList.get(position).getObject().get(0).getId()));
                }
            });
            final MyViewHolderservice viewholder2 = (MyViewHolderservice) holder;


            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            viewholder2.title.setText(moviesList.get(position).getObject().get(0).getName());

            viewholder2.date.setText(moviesList.get(position).getObject().get(0).getCategoryname());


            if (moviesList.get(position).getObject().get(0).getIsFavorite()) {
                viewholder2.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            } else
                viewholder2.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));

            if (moviesList.get(position).getObject().get(0).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color3));
            } else
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));

            if (moviesList.get(position).getObject().get(0).getCountComment() == null)
                viewholder2.textcomment.setText("0");
            else
                viewholder2.textcomment.setText(moviesList.get(position).getObject().get(0).getCountComment() + "");
            viewholder2.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + "");
            viewholder2.textfav.setText(moviesList.get(position).getObject().get(0).getCountfavorite() + "");
            viewholder2.textshare.setText(moviesList.get(position).getObject().get(0).getShare() + "");
            viewholder2.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(1));
                }
            });
            viewholder2.rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(new PromotionFragment());
                }
            });
            viewholder2.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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


                    popupWindow.showAsDropDown(viewholder2.menu, -60, -60);

                }
            });


            viewholder2.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getObject().get(0).getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(moviesList.get(position).getObject().get(0).getId(), "service", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder2.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
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
                                    viewholder2.textlike.setText(moviesList.get(position).getObject().get(0).getCountlike() + 1 + "");
                                    moviesList.get(position).getObject().get(0).setIsLike("true");
                                    moviesList.get(position).getObject().get(0).setCountlike(moviesList.get(position).getObject().get(0).getCountlike());

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
                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(moviesList.get(position).getObject().get(0).getId(), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderservice) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
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

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((MultiPromotion) holder).imageView);

            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

                }
            });
            viewholder.text.setText(moviesList.get(position).getUser().getUsername());

            viewholder.recycler_image.setAdapter(adapter);
            String comment = moviesList.get(position).getAction() + " " + activityList.size() + " " + moviesList.get(position).getType();

            final String name = moviesList.get(position).getUser().getUsername();
            final String in = context.getResources().getString(R.string.in);
            final String salon = moviesList.get(position).getSalon().getUsername();
            ((MultiPromotion) holder).text.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(
                            ((MultiPromotion) holder).text, name + " " + comment + " " + in + " " + salon)
                    .setTextColor(R.color.gray, comment, salon)
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
            ((MultiPromotion) holder).time.setTypeface(MyApplication.type_jf_regular);

            ((MultiPromotion) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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
        }


        if (holder instanceof MultiCover) {

            final MultiCover viewholder = (MultiCover) holder;
            viewholder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);

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

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((MultiCover) holder).imageView);

            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (moviesList.get(position).getType().equals("salon"))
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt((moviesList.get(position)).getId())));
                    else
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(moviesList.get(position).getId())));

                }
            });
            viewholder.text.setText(moviesList.get(position).getUser().getUsername());

            viewholder.recycler_image.setAdapter(adapter);
            String comment = moviesList.get(position).getAction() + " " + activityList.size() + " " + moviesList.get(position).getType();

            final String name = moviesList.get(position).getUser().getUsername();
            final String in = context.getResources().getString(R.string.in);
            final String salon = moviesList.get(position).getSalon().getUsername();
            ((MultiCover) holder).text.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(
                            ((MultiCover) holder).text, name + " " + comment + " " + in + " " + salon)
                    .setTextColor(R.color.gray, comment, salon)
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
            ((MultiCover) holder).time.setTypeface(MyApplication.type_jf_regular);


        }

        if (holder instanceof MultiService) {
            final MultiService viewholder = (MultiService) holder;

            viewholder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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


            List<salon.octadevtn.sa.salon.Models.Activity.Object> activityList = new ArrayList<>();
            activityList.addAll(moviesList.get(position).getObject());
            GalleryServiceAdapter adapter = new GalleryServiceAdapter(context, activityList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 4);
            viewholder.recycler_image.setLayoutManager(mLayoutManager);

            viewholder.recycler_image.setAdapter(adapter);


            String comment = moviesList.get(position).getAction() + " " + activityList.size() + " " + moviesList.get(position).getType();

            final String name = moviesList.get(position).getUser().getUsername();
            final String in = context.getResources().getString(R.string.in);
            final String salon = moviesList.get(position).getSalon().getUsername();
            ((MultiService) holder).text.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(
                            ((MultiService) holder).text, name + " " + comment + " " + in + " " + salon)
                    .setTextColor(R.color.gray, comment, salon)
                    .setTextStyle(Typeface.BOLD, name, salon)
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
                            if (text.equals(salon)) {
                                ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getSalon().getId()));

                            }

                        }
                    }, false, name, salon)
                    .setTextColor(R.color.gray_noir, name, salon)
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
            ((MultiService) holder).time.setText(timeAgo);
            ((MultiService) holder).time.setTypeface(MyApplication.type_jf_regular);

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((MultiService) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((MultiService) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((MultiService) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((MultiService) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((MultiService) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });


        }
        if (holder instanceof FOLLOW) {
            if (moviesList.get(position).getType().equals("following")) {

                ((FOLLOW) holder). menu.setVisibility(View.VISIBLE);
                ((FOLLOW) holder). menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null)
                            if (popupWindow.isShowing())
                                popupWindow.dismiss();
                        LayoutInflater layoutInflater
                                = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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



                final String name = " " + moviesList.get(position).getUser().getUsername() + " ";
                //final String of = moviesList.get(position).getFlow().getUsername();
                String comment = "";

                comment = "\u200F" + name + " " + context.getResources().getString(R.string.start_follow_profile);
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(((FOLLOW) holder).text, "\u200F" + comment)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
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

                            }
                        }, false, name)
                        .setTextColor(R.color.gray_noir, name)
                        .build();
            }
            if (moviesList.get(position).getType().equals("promotion")) {

                final String name = " " + moviesList.get(position).getUser().getUsername() + " ";
                final String promotion = moviesList.get(position).getObject().get(0).getTitle() + " ";
                String comment = "";
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    comment = "\u200F" + name + " " + Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + context.getResources().getString(R.string.toyour) + Convertiseurlag.getType(moviesList.get(position).getType()) + " " + promotion;

                } else
                    comment = name + " " + Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + context.getResources().getString(R.string.toyour) + Convertiseurlag.getType(moviesList.get(position).getType()) + " " + promotion;
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(
                                ((FOLLOW) holder).text, comment)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, promotion)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, promotion)
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
                final String date = moviesList.get(position).getReservation().getStartDate();
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + salon + " " + in + " " + date)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, salon, date)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon, date)
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
                                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));

                                }
                            }
                        }, false, name, salon)
                        .setTextColor(R.color.gray_noir, name, salon, date)
                        .build();
            }

            if (moviesList.get(position).getType().equals("reservation_tomorrow")) {
                final String name = moviesList.get(position).getUser().getUsername();
                String comment = context.getResources().getString(R.string.next_reservation);
                String in = context.getResources().getString(R.string.youres);
                //final String salon = moviesList.get(position).getObject().get(0).getTitle();
                final String salon = context.getResources().getString(R.string.day);
                final String date = moviesList.get(position).getReservation().getStartDate();
                final String object = moviesList.get(position).getObject().get(0).getTitle();
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + salon + " " + in + " " + object)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, salon, object)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon, object)
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
                                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));

                                }
                            }
                        }, false, name, salon)
                        .setTextColor(R.color.gray_noir, name, salon, date)
                        .build();
            }
            if (moviesList.get(position).getType().equals("reservation_day")) {
                final String name = moviesList.get(position).getUser().getUsername();
                String comment = context.getResources().getString(R.string.next_reservation);
                String in = context.getResources().getString(R.string.youres);
                //final String salon = moviesList.get(position).getObject().get(0).getTitle();
                final String salon = context.getResources().getString(R.string.day1);
                final String date = moviesList.get(position).getReservation().getStartDate();
                final String object = moviesList.get(position).getObject().get(0).getTitle();
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + salon + " " + in + " " + object)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, salon, object)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, salon, object)
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
                                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(moviesList.get(position).getObject().get(0).getId(), 1));

                                }
                            }
                        }, false, name, salon)
                        .setTextColor(R.color.gray_noir, name, salon, date)
                        .build();
            }


            if (moviesList.get(position).getType().equals("service")) {

                // String comment = moviesList.get(position).getAction() + " " + context.getResources().getString(R.string.toyour) + " " + moviesList.get(position).getType();
                final String service = " " + moviesList.get(position).getObject().get(0).getName() + " ";

                final String name = " " + moviesList.get(position).getUser().getUsername() + " ";

                String comment = "";
                if (MyApplication.getInstance().getPrefManager().getLang().equals("1")) {
                    comment = "\u200F" + name + Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + context.getResources().getString(R.string.toyour) + Convertiseurlag.getType(moviesList.get(position).getType()) + " " + service;
                } else
                    comment = "\u200F" + name + " " + Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + context.getResources().getString(R.string.toyour) + Convertiseurlag.getType(moviesList.get(position).getType()) + " " + service;

                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(
                                ((FOLLOW) holder).text, comment)
                        .setTextColor(R.color.gray, comment)
                        .setTextStyle(Typeface.BOLD, name, service)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, service, name)
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
                String comment = Convertiseurlag.getAction(moviesList.get(position).getAction()) + " " + context.getResources().getString(R.string.toyour) + " " +
                        Convertiseurlag.getType(moviesList.get(position).getType());
                String in = "";//context.getResources().getString(R.string.in);

                final String name = moviesList.get(position).getUser().getUsername();
                //   final String salon = moviesList.get(position).getSalon().getUsername();
                ((FOLLOW) holder).text.setTypeface(MyApplication.type_jf_regular);

                TextDecorator
                        .decorate(
                                ((FOLLOW) holder).text, "\u200F" + name + " " + comment + " " + in + " ")
                        .setTextColor(R.color.gray, comment, in)
                        .setTextStyle(Typeface.BOLD, name)
                        .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name)
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


                            }
                        }, false, name)

                        .setTextColor(R.color.gray_noir, name)
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

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((FOLLOW) holder).imageView);
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


            ((FOLLOW) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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

        }

        if (holder instanceof RESERVATION) {

            final String name = moviesList.get(position).getUser().getUsername();
            String comment = context.getResources().getString(R.string.make_reservation);
            String in = context.getResources().getString(R.string.in);
            final String salon = moviesList.get(position).getSalon().getUsername();
            final String date = moviesList.get(position).getReservation().getStartDate();
            ((RESERVATION) holder).text.setTypeface(MyApplication.type_jf_regular);

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
                    .setTextColor(R.color.gray_noir, name, salon, date)
                    .build();
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

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((RESERVATION) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((RESERVATION) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((RESERVATION) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((RESERVATION) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((RESERVATION) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

                }
            });


            ((FOLLOW) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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

        }


        if (holder instanceof Profile) {


            ((Profile) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null)
                        if (popupWindow.isShowing())
                            popupWindow.dismiss();
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.pop_up, null);
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

            final String of = moviesList.get(position).getAction();
            message = moviesList.get(position).getMessage();
            if (moviesList.get(position).getAction().equals("location")) {
                to = moviesList.get(position).getMessage();
                message = "";
            } else {
                to = context.getResources().getString(R.string.to);
            }
            ((Profile) holder).text.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(((Profile) holder).text, name + " " + comment + " " + of + " " + to + " " + message)
                    .setTextColor(R.color.gray, comment, to)
                    .setTextStyle(Typeface.BOLD, name, of, message)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, of, message)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment, to)

                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(name)) {
                                if (moviesList.get(position).getUser().getType().equals("customer")) {
                                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                                } else
                                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

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

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getUser().getImage()).into(((Profile) holder).imageView);
            if (moviesList.get(position).getUser().getType().equals("salon"))
                ((Profile) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            else if (moviesList.get(position).getUser().getGenre().equals("men"))
                ((Profile) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
            else
                ((Profile) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

            ((Profile) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getUser().getType().equals("customer")) {
                        ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(moviesList.get(position).getUser().getIdUser()));
                    } else
                        ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(moviesList.get(position).getUser().getIdUser()));

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
        if (moviesList.get(position).getType().contains("reservation_day")) {

            return 1;
        }
        if (moviesList.get(position).getType().contains("reservation_tomorrow")) {

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

                return 1;
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
                    //   Toasty.success(context, context.getResources().getString(R.string.confimation_delete), Toast.LENGTH_SHORT).show();
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
        ImageView menu;

        public MultiPromotion(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.cercleImage);
            menu = (ImageView) view.findViewById(R.id.menu);
            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
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

            text = (TextView) view.findViewById(R.id.text);
            time = (TextView) view.findViewById(R.id.time);
            menu = (ImageView) view.findViewById(R.id.menu);

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
            menu = (ImageView) view.findViewById(R.id.menu);
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
