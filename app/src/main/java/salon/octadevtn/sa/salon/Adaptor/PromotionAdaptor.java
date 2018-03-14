package salon.octadevtn.sa.salon.Adaptor;

import android.app.Dialog;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Promotion;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.Reservation_home;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Api.Service;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;

/**
 * Created by Marwen octadev on 7/8/2017.
 */

public class PromotionAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    RecyclerView recyclerView;
    View popupView;
    Profile profile = null;
    private List<Promotion> moviesList;


    public PromotionAdaptor(List<Promotion> moviesList, Context context, RecyclerView recyclerView) {
        this.moviesList = moviesList;
        this.context = context;
        this.recyclerView = recyclerView;
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
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
            ((MyViewHolderpromotion) holder).lincom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(Integer.parseInt(moviesList.get(position).getId()), 0));
                }
            });
            ((MyViewHolderpromotion) holder).salonname.setTypeface(MyApplication.type_jf_medium);

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getSalon().getPhoto()).into(((MyViewHolderpromotion) holder).imageView);


            ((MyViewHolderpromotion) holder).imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
            ((MyViewHolderpromotion) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance((moviesList.get(position)).getSalon().getId()));
                }
            });
            String comment = MyApplication.getAppContext().getResources().getString(R.string.addpromotion);
            final String name = moviesList.get(position).getSalon().getUsername();
            TextDecorator
                    .decorate(
                            ((MyViewHolderpromotion) holder).salonname, name + " " + comment)
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
            ((MyViewHolderpromotion) holder).time.setText(timeAgo);
            ((MyViewHolderpromotion) holder).time.setTypeface(MyApplication.type_jf_medium);

            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + moviesList.get(position).getImage())
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
                    ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(Integer.parseInt(moviesList.get(position).getId()), 1));
                }
            });
            final MyViewHolderpromotion viewholder = (MyViewHolderpromotion) holder;
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date1 = dt.parse(moviesList.get(position).getStartDate());
                Date date2 = dt.parse(moviesList.get(position).getEndDate());
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

                viewholder.date.setTypeface(MyApplication.type_jf_regular);


            } catch (ParseException e) {
                e.printStackTrace();
            }

            NumberFormat formatter = new DecimalFormat("#0.0");

            Double discount = Double.parseDouble(moviesList.get(position).getPrice()) - ((Double.parseDouble(moviesList.get(position).getPrice()) / 100) * (Double.parseDouble(moviesList.get(position).getDiscount())));
            //  viewholder.discount.setText("discount %" + moviesList.get(position).getDiscount() + " - " + moviesList.get(position).getCurrency() + " " + formatter.format(discount) + " instead of " + moviesList.get(position).getCurrency() + " " + moviesList.get(position).getPrice());

            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                viewholder.discount.setText("تخفيض %" + moviesList.get(position).getDiscount() + " - " + formatter.format(discount) + " بدلا من " + moviesList.get(position).getPrice());
            else
                viewholder.discount.setText("discount %" + moviesList.get(position).getDiscount() + " - " + formatter.format(discount) + " instead of " + moviesList.get(position).getPrice());
            viewholder.discount.setTypeface(MyApplication.type_jf_regular);

            if (moviesList.get(position).getIsFavorite()) {
                viewholder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                viewholder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            }
            if (moviesList.get(position).getIsLike()) {
                viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getIsComment()) {
                viewholder.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            if (moviesList.get(position).getIsShare()) {
                viewholder.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                viewholder.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            }
            viewholder.textlike.setTypeface(MyApplication.type_jf_regular);

            viewholder.textcomment.setText(moviesList.get(position).getCountComment() + "");
            viewholder.textcomment.setTypeface(MyApplication.type_jf_regular);

            viewholder.textlike.setText(moviesList.get(position).getCountLike() + "");
            viewholder.textlike.setTypeface(MyApplication.type_jf_regular);

            viewholder.textfav.setText(moviesList.get(position).getCountFavorit() + "");
            viewholder.textfav.setTypeface(MyApplication.type_jf_regular);

            viewholder.textshare.setText(moviesList.get(position).getShare() + "");
            viewholder.textshare.setTypeface(MyApplication.type_jf_regular);

            viewholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt(moviesList.get(position).getIdSalon())));
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

                    ((TextView) popupView.findViewById(R.id.del)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.hid)).setTypeface(MyApplication.type_jf_regular);
                    ((TextView) popupView.findViewById(R.id.edi)).setTypeface(MyApplication.type_jf_regular);

                    LinearLayout delete = (LinearLayout) popupView.findViewById(R.id.delete);
                    LinearLayout hide = (LinearLayout) popupView.findViewById(R.id.hide);
                    delete.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                                DeletePromotion(Integer.parseInt(moviesList.get(position).getId()), position);
                                popupWindow.dismiss();

                        }
                    });
                    hide.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.report);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.show();
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            dialog.getWindow().setAttributes(lp);
                            dialog.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EditText report = dialog.findViewById(R.id.report);
                                    AddReport(Integer.parseInt(moviesList.get(position).getId()), "promotion", report.getText().toString(), dialog);
                                    dialog.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                                    dialog.findViewById(R.id.line).setVisibility(View.GONE);
                                }
                            });
                            dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                        }
                    });
                    popupWindow.showAsDropDown(viewholder.menu, -60, -60);
                }
            });

            viewholder.title.setText(moviesList.get(position).getTitle());
            viewholder.title.setTypeface(MyApplication.type_jf_medium);

            viewholder.linlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (moviesList.get(position).getIsLike()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(Integer.parseInt(moviesList.get(position).getId()), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    viewholder.textlike.setText(moviesList.get(position).getCountLike() - 1 + "");

                                    moviesList.get(position).setIsLike("false");
                                    moviesList.get(position).setCountLike(moviesList.get(position).getCountLike() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Like(moviesList.get(position).getId() + "", "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    viewholder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                    viewholder.textlike.setText(moviesList.get(position).getCountLike() + 1 + "");
                                    moviesList.get(position).setIsLike("true");
                                    moviesList.get(position).setCountLike(moviesList.get(position).getCountLike() + 1);

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
                    String s = moviesList.get(position).getTitle();
                    if (moviesList.get(position).getTitle() != null && !moviesList.get(position).getTitle().equals("")) {

                        s = (moviesList.get(position).getTitle()).replaceAll(" ", "%20");
                    }
                    share("title=" + s
                            + "&img=" + moviesList.get(position).getImage() +
                            "&type=service&id=" + moviesList.get(position).getId() +
                            "+&description=" + moviesList.get(position).getDescription());
                    Shared(moviesList.get(position).getId(), "service");

                }
            });
            viewholder.linfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moviesList.get(position).getIsFavorite()) {
                        new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(Integer.parseInt(moviesList.get(position).getId()), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderpromotion) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderpromotion) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                    ((MyViewHolderpromotion) holder).textfav.setText(moviesList.get(position).getCountFavorit() - 1 + "");
                                    moviesList.get(position).setIsFavorite("false");
                                    moviesList.get(position).setCountFavorit(moviesList.get(position).getCountFavorit() - 1);

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
                        new salon.octadevtn.sa.salon.Api.Comment().Favorite(Integer.parseInt(moviesList.get(position).getId()), "promotion", new UniversalCallBack() {
                            @Override
                            public void onResponse(Object result) {
                                if (result != null) {
                                    ((MyViewHolderpromotion) holder).imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderpromotion) holder).textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                    ((MyViewHolderpromotion) holder).textfav.setText(moviesList.get(position).getCountFavorit() + 1 + "");
                                    moviesList.get(position).setIsFavorite("true");
                                    moviesList.get(position).setCountFavorit(moviesList.get(position).getCountFavorit() + 1);
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
            ((MyViewHolderpromotion) holder).reserver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivityDrawer) context).setFragment(Reservation_home.newInstance(Integer.parseInt(moviesList.get(position).getId()), moviesList.get(position).getTitle(), moviesList.get(position).getSalon().getSalonName()));
                }
            });
        }


    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void DeletePromotion(int id, final int position) {
        new Service().DeletePromotion(id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                if (result != null) {
                    moviesList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, moviesList.size());
                    notifyDataSetChanged();

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
                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
            }
        });
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

    private void AddReport(int id, String salon, String reason, final Dialog dialog) {
        new salon.octadevtn.sa.salon.Api.DeleteBlock().AddReport(reason, String.valueOf(id), "promotion"
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";

                    }

                    @Override
                    public void OnError(String message) {

                    }

                    @Override
                    public void onFinish() {
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
