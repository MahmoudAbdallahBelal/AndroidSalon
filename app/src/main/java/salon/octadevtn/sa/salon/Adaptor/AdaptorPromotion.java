package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.hynra.gsonsharedpreferences.GSONSharedPreferences;
import com.github.hynra.gsonsharedpreferences.ParsingException;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.PromotionProfileDetail;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.Reservation_home;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorPromotion extends RecyclerView.Adapter<AdaptorPromotion.ViewHolder> {

    private static final int REQUEST_SHARE_RESULT = 100;
    Profile profile = null;
    Context context;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<PromotionProfileDetail> itemList;

    // Constructor of the class
    public AdaptorPromotion(ArrayList<PromotionProfileDetail> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
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

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion2, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + itemList.get(listPosition).getImage())
                .override(400, 400)
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

                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(Integer.parseInt(itemList.get(listPosition).getId()), 1));
            }
        });
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        holder.promotionname.setText(itemList.get(listPosition).getTitle());

        try {
            Date date1 = dt.parse(itemList.get(listPosition).getStartDate());
            Date date2 = dt.parse(itemList.get(listPosition).getEndDate());
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date2);
            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))

                holder.date.setText("من " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " إلى " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                );
            else
                holder.date.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                );

            //    holder.date.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
            //            c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
            //     );


        } catch (ParseException e) {
            e.printStackTrace();
        }
        NumberFormat formatter = new DecimalFormat("#0.0");

        Double discount = Double.parseDouble(itemList.get(listPosition).getPrice()) - ((Double.parseDouble(itemList.get(listPosition).getPrice()) / 100) * (Double.parseDouble(itemList.get(listPosition).getDiscount())));
        if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
            holder.discount.setText("تخفيض %" + itemList.get(listPosition).getDiscount() + " - " + formatter.format(discount) + " بدلا من " + itemList.get(listPosition).getPrice());
        else
            holder.discount.setText("discount %" + itemList.get(listPosition).getDiscount() + " - " + formatter.format(discount) + " instead of " + itemList.get(listPosition).getPrice());


        // holder.discount.setText("discount %" + itemList.get(listPosition).getDiscount() + " - " + itemList.get(listPosition).getCurrency() + " " + formatter.format(discount) + " instead of " + itemList.get(listPosition).getCurrency() + " " + itemList.get(listPosition).getPrice());
        if (itemList.get(listPosition).getIsFavorite()) {
            holder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
            holder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
        }
        if (itemList.get(listPosition).getIsLike()) {
            holder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            holder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

        }
        if (itemList.get(listPosition).getIsShare()) {
            holder.imageshare.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            holder.textshare.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

        }
        if (itemList.get(listPosition).getIsComment()) {
            holder.imagecomment.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
            holder.textcomment.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

        }
        holder.textcomment.setText(itemList.get(listPosition).getCountcomment() + "");
        holder.textcomment.setTypeface(MyApplication.type_jf_regular);
        holder.textlike.setText(itemList.get(listPosition).getCountlike() + "");
        holder.textlike.setTypeface(MyApplication.type_jf_regular);
        holder.textfav.setText(itemList.get(listPosition).getCountfavorite() + "");
        holder.textfav.setTypeface(MyApplication.type_jf_regular);
        holder.textshare.setText(itemList.get(listPosition).getShare() + "");
        holder.textshare.setTypeface(MyApplication.type_jf_regular);


        holder.linlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(listPosition).getIsLike()) {
                    new salon.octadevtn.sa.salon.Api.Comment().DeleteLike(Integer.parseInt(itemList.get(listPosition).getId()), "promotion", new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                holder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                holder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));

                                holder.textlike.setText(itemList.get(listPosition).getCountlike() - 1 + "");
                                itemList.get(listPosition).setIsLike("false");
                                itemList.get(listPosition).setCountlike(itemList.get(listPosition).getCountlike() - 1);

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
                    new salon.octadevtn.sa.salon.Api.Comment().Like(itemList.get(listPosition).getId(), "promotion", new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                holder.imagelike.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.color4));
                                holder.textlike.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

                                holder.textlike.setText(itemList.get(listPosition).getCountlike() + 1 + "");
                                itemList.get(listPosition).setIsLike("true");
                                itemList.get(listPosition).setCountlike(itemList.get(listPosition).getCountlike() + 1);

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
            holder.reserver.setVisibility(View.GONE);
        } else {
            holder.reserver.setVisibility(View.VISIBLE);
        }
        holder.reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) context).setFragment(Reservation_home.newInstance(Integer.parseInt(itemList.get(listPosition).getId()), itemList.get(listPosition).getTitle(), ""));
            }
        });

        holder.linfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(listPosition).getIsFavorite()) {
                    new salon.octadevtn.sa.salon.Api.Comment().DeleteFavorite(Integer.parseInt(itemList.get(listPosition).getId()), "promotion", new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                holder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.gray));
                                holder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.gray));

                                holder.textfav.setText(itemList.get(listPosition).getCountfavorite() - 1 + "");
                                itemList.get(listPosition).setIsFavorite("false");
                                itemList.get(listPosition).setCountfavorite(itemList.get(listPosition).getCountfavorite() - 1);

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
                    new salon.octadevtn.sa.salon.Api.Comment().Favorite(Integer.parseInt(itemList.get(listPosition).getId()), "promotion", new UniversalCallBack() {
                        @Override
                        public void onResponse(Object result) {
                            if (result != null) {
                                holder.imagefav.setColorFilter(MyApplication.getAppContext().getResources().getColor(R.color.yellow));
                                holder.textfav.setTextColor(MyApplication.getAppContext().getResources().getColor(R.color.yellow));

                                holder.textfav.setText(itemList.get(listPosition).getCountfavorite() + 1 + "");
                                itemList.get(listPosition).setIsFavorite("true");
                                itemList.get(listPosition).setCountfavorite(itemList.get(listPosition).getCountfavorite() + 1);
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
        holder.linshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = itemList.get(listPosition).getTitle();
                if (itemList.get(listPosition).getTitle() != null && !itemList.get(listPosition).getTitle().equals("")) {

                    s = (itemList.get(listPosition).getTitle()).replaceAll(" ", "%20");
                }

                share("title=" + s
                        + "&img=" + itemList.get(listPosition).getImage() +
                        "&type=promotion&id=" + itemList.get(listPosition).getId() +
                        "+&description=" + itemList.get(listPosition).getDescription());
                Shared(itemList.get(listPosition).getId(), "promotion");

            }
        });
        holder.lincom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) context).setFragment(PromotionFragment.newInstance(Integer.parseInt(itemList.get(listPosition).getId()), 1));
            }
        });

    }

    private void shareBitmap(Bitmap bitmap, String fileName, String image) {
        try {
            File file = new File(context.getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "www.salon.com.sa");

            intent.setType("image/png");
            context.startActivity(Intent.createChooser(intent, context.getResources().getText(R.string.share_via)));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView promotionname, date, discount;
        @BindView(R.id.linlike)
        LinearLayout linlike;
        @BindView(R.id.reserver)
        LinearLayout reserver;
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
        @BindView(R.id.rel)
        RelativeLayout rel;
        @BindView(R.id.lincom)
        LinearLayout lincom;
        @BindView(R.id.imagecomment)
        ImageView imagecomment;
        @BindView(R.id.imageshare)
        ImageView imageshare;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imagepromotion);
            promotionname = (TextView) itemView.findViewById(R.id.promotionname);
            promotionname.setTypeface(MyApplication.type_jf_medium);
            date = (TextView) itemView.findViewById(R.id.date);
            date.setTypeface(MyApplication.type_jf_regular);
            discount = (TextView) itemView.findViewById(R.id.discount);
            discount.setTypeface(MyApplication.type_jf_regular);

        }

        @Override
        public void onClick(View view) {
        }
    }

}