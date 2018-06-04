package salon.octadevtn.sa.salon.Adaptor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ramotion.foldingcell.FoldingCell;
import com.tuyenmonkey.textdecorator.TextDecorator;
import com.tuyenmonkey.textdecorator.callback.OnTextClickListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Api.Reservation.AcceptReservation;
import salon.octadevtn.sa.salon.Api.Reservation.CancelReservation;
import salon.octadevtn.sa.salon.Api.Reservation.RateReservation;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Reservation.Task;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Reservation.UpdateReservation;
import salon.octadevtn.sa.salon.Utils.Circlebutton.CircularProgressButton;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Reservation.ListeReservationFragment.ListeReservation;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;
import static salon.octadevtn.sa.salon.Utils.UrlStatic.pathImag;


public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.NewViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    Context mcontext;
    ArrayList<Task> mDataSet;
    View popupView;
    RecyclerView recyclerView;
    private int position;


    public AdapterReservation(ArrayList<Task> mDataSet, RecyclerView recyclerView, Context mcontect) {
        this.mDataSet = mDataSet;
        this.mcontext = mcontect;
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
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        NewViewHolder userViewHolder = new NewViewHolder(v);
        return userViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null)
                    popupWindow.dismiss();
                return false;
            }
        });
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 1) {
            //reservation Approve view detail
            holder.end.setVisibility(View.GONE);

        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == -1) {
            //reservation Approve view detail

        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 0) {
            //reservation Approve view detail
            holder.buton.setVisibility(View.GONE);
            holder.buton1.setVisibility(View.GONE
            );

        }

        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(true);
            }
        });
        holder.send_reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.send_reason.setIndeterminateProgressMode(true); // turn on indeterminate progress
                holder.send_reason.setProgress(50);
                holder.face.setVisibility(View.VISIBLE);
                CancelReservation(mDataSet.get(position).getId() + "", holder.reason.getText().toString(), "Customer", holder, null, position);

            }
        });
        holder.aprouve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.aprouve.setIndeterminateProgressMode(true); // turn on indeterminate progress
                holder.aprouve.setProgress(50);
                AcceptReservation(mDataSet.get(position).getId() + "", holder);

            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mDataSet.get(position).getEtat()) == 0 || Integer.parseInt(mDataSet.get(position).getEtat()) == 1 ||Integer.parseInt(mDataSet.get(position).getEtat()) == -1) {

                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = layoutInflater.inflate(R.layout.pop_up_reservation1, null);
                    ((TextView) popupView.findViewById(R.id.mod)).setTypeface(MyApplication.type_jf_light);
                    ((TextView) popupView.findViewById(R.id.up)).setTypeface(MyApplication.type_jf_light);
                    if (popupWindow == null)
                        popupWindow = new PopupWindow(
                                popupView,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout btnDismiss = (LinearLayout) popupView.findViewById(R.id.cancel);
                    LinearLayout update = (LinearLayout) popupView.findViewById(R.id.update);
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((HomeActivityDrawer) mcontext).setFragment(UpdateReservation.newInstance(Integer.parseInt(mDataSet.get(position).getId()),
                                    mDataSet.get(position).getPromotion().getTitle(),
                                    mDataSet.get(position).getPromotion().getId() + "",
                                    mDataSet.get(position).getDate(),
                                    mDataSet.get(position).getId()));

                        }
                    });
                    btnDismiss.setOnClickListener(new Button.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(mcontext);
                            dialog.setContentView(R.layout.dialog_cancel_reservation);
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.getWindow().setAttributes(lp);
                            ((MultiAutoCompleteTextView) dialog.findViewById(R.id.reason)).setTypeface(MyApplication.type_jf_regular);
                            ((TextView) dialog.findViewById(R.id.title)).setTypeface(MyApplication.type_jf_regular);
                            ((Button) dialog.findViewById(R.id.send_reason)).setTypeface(MyApplication.type_jf_regular);
                            dialog.show();
                            dialog.findViewById(R.id.send_reason).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                   if (((MultiAutoCompleteTextView) dialog.findViewById(R.id.reason)).getText().toString().length() ==0){

                                       ((MultiAutoCompleteTextView) dialog.findViewById(R.id.reason)).setError(""+mcontext.getString(R.string.write_reason));
                                       return;
                                    } else {
                                       ((CircularProgressButton) dialog.findViewById(R.id.send_reason)).setIndeterminateProgressMode(true); // turn on indeterminate progress
                                       ((CircularProgressButton) dialog.findViewById(R.id.send_reason)).setProgress(50);
                                       holder.face.setVisibility(View.VISIBLE);
                                       CancelReservation(mDataSet.get(position).getId() + "", ((MultiAutoCompleteTextView) dialog.findViewById(R.id.reason)).getText().toString(), "Customer", holder, dialog, position);
                                   }

                                }
                            });

                        }
                    });
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }

                    popupWindow.showAsDropDown(holder.menu, -60, -60);
                }
            }
        });
        //   holder.description.setText("");

        Calendar calendar = Calendar.getInstance();
        Date date;
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(mDataSet.get(position).getCreatedAt() + "");
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.left_time.setText(timeAgo + "");
        holder.left_time.setTypeface(MyApplication.type_jf_regular);
        holder.left_time1.setText(timeAgo + "");
        holder.left_time1.setTypeface(MyApplication.type_jf_regular);
        holder.titre.setTypeface(MyApplication.type_jf_regular);
        holder.decline.setTypeface(MyApplication.type_jf_regular);
        holder.aprouve.setTypeface(MyApplication.type_jf_regular);
        holder.send_reason.setTypeface(MyApplication.type_jf_regular);
        holder.titre.setTypeface(MyApplication.type_jf_regular);

       if (mDataSet.get(position).getUser().getPhoto()!=null)
       {  Glide.with(mcontext)
                    .load(pathImag + mDataSet.get(position).getUser().getPhoto())
                    .centerCrop()
               .override(150,150)
                    .into(holder.photo);
            Glide.with(mcontext)
                    .load(pathImag + mDataSet.get(position).getUser().getPhoto())
                    .centerCrop()
                    .override(150,150)
                    .into(holder.photo1);
        }
        String name = "";
        String comment = "";
        String obj = "";
        String dat = "";
        String service = "";
        String of = "";
        String beause;
        String cause;


        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 0) {
            holder.description.setVisibility(View.GONE);
            name = mDataSet.get(position).getUser().getUsername();
            comment = mcontext.getResources().getString(R.string.mes1);
            dat = mDataSet.get(position).getCreatedAt();
            service = mcontext.getResources().getString(R.string.mes2);
            obj = mDataSet.get(position).getPromotion().getTitle();

            final String finalObj3 = obj;
            final String finalName4 = name;
            TextDecorator
                    .decorate(holder.text, name + " " + comment + " " + dat + " " + of + " " + service + " " + obj)
                    .setTextColor(R.color.gray, comment, service)

                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, of, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, service)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName4))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalObj3))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name, of, obj, dat)
                    .build();
            TextDecorator
                    .decorate(holder.text1, name + " " + comment + " " + dat + " " + of + " " + service + " " + obj)
                    .setTextColor(R.color.gray, comment, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, of, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, service)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName4))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalObj3))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name, of, obj, dat)
                    .build();

        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == -1) {
            holder.description.setVisibility(View.GONE);
            name = mDataSet.get(position).getUser().getUsername();
            comment = mcontext.getResources().getString(R.string.mes3);
            dat = mDataSet.get(position).getCreatedAt();
            service = mcontext.getResources().getString(R.string.mes2);
            if(mDataSet.get(position).getPromotion() != null)
            obj = mDataSet.get(position).getPromotion().getTitle();
            beause = mcontext.getResources().getString(R.string.mes4);
            cause = mDataSet.get(position).getCauseSalon();

            final String finalObj2 = obj;
            final String finalName3 = name;
            TextDecorator
                    .decorate(holder.text, name + " " + comment + " " + dat + " " + of + " " + service + " " + obj + " " + beause + " " + cause)
                    .setTextColor(R.color.gray, comment, of, beause, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, dat, of, service, cause, obj)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, beause)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName3))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalObj2))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name, dat, cause, obj)
                    .build();
            TextDecorator
                    .decorate(holder.text1, name + " " + comment + " " + dat + " " + of + " " + service + " " + obj + " " + beause + " " + cause)
                    .setTextColor(R.color.gray, comment, of, beause, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, dat, of, service, cause, obj)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, beause)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName3))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalObj2))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name, dat, cause, obj)
                    .build();


        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 1) {
            holder.description.setVisibility(View.GONE);
            name = mDataSet.get(position).getUser().getUsername();
            comment = mcontext.getResources().getString(R.string.mes5);
            dat = mDataSet.get(position).getDate();
            service = mDataSet.get(position).getPromotion().getTitle();
            obj = mDataSet.get(position).getPromotion().getDescription();

            holder.description.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
            final String finalService = service;
            final String finalName = name;
            TextDecorator
                    .decorate(holder.text, comment + " " + name)
                    .setTextColor(R.color.gray, comment)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalService))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name)
                    .build();
            TextDecorator
                    .decorate(holder.text1, comment + " " + name)
                    .setTextColor(R.color.gray, comment)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalService))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name)
                    .build();

            TextDecorator
                    .decorate(holder.description, service + " - " + dat + "\n" + obj)
                    .setTextColor(R.color.gray, comment)
                    .setTextColor(R.color.holo_red_dark, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalService))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, service)
                    .build();
            TextDecorator
                    .decorate(holder.description1, service + " - " + dat + "\n" + obj)
                    .setTextColor(R.color.gray, comment)
                    .setTextColor(R.color.holo_red_dark, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalService))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, service)
                    .build();


        }

        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 2) {
            name = mDataSet.get(position).getUser().getUsername() + "";
            comment = mcontext.getResources().getString(R.string.mes11) + "";
            String punish = mcontext.getResources().getString(R.string.mess12) + "";
            String punish1 = mcontext.getResources().getString(R.string.mess13) + "";
            obj = mDataSet.get(position).getPromotion().getTitle() + "";
            final String finalObj = obj;
            final String finalName1 = name;
            TextDecorator
                    .decorate(holder.text, name + " " + comment + " " + punish + " " + punish1 + " " + obj)
                    .setTextColor(R.color.gray, comment, punish1)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, punish, obj)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment, punish1)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName1))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalObj))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name, punish, obj)
                    .build();

            holder.description.setVisibility(View.GONE);
        }

        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 3) {
            name = mDataSet.get(position).getUser().getUsername();
            comment = mcontext.getResources().getString(R.string.mess14);
            obj = mDataSet.get(position).getPromotion().getTitle();
            service = mcontext.getResources().getString(R.string.mes15);
            final String finalName2 = name;
            final String finalObj1 = obj;
            TextDecorator
                    .decorate(holder.text, name + " " + comment + " " + obj + " " + service)
                    .setTextColor(R.color.gray, comment, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, obj)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment, service)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName2))
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            if (text.equals(finalObj1))
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name, obj)
                    .build();
            Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImag + mDataSet.get(position).getPromotion().getImage())
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

                    .into(holder.image);
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            holder.promotion_name.setText(mDataSet.get(position).getPromotion().getTitle());

            try {
                Date date1 = dt.parse(mDataSet.get(position).getPromotion().getStartDate());
                Date date2 = dt.parse(mDataSet.get(position).getPromotion().getEndDate());
                Calendar c = Calendar.getInstance();
                c.setTime(date1);
                Calendar c2 = Calendar.getInstance();
                c2.setTime(date2);
                holder.text_from.setText("From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            NumberFormat formatter = new DecimalFormat("#0.0");

            Double discount = Double.parseDouble(mDataSet.get(position).getPromotion().getPrice()) - ((Double.parseDouble(mDataSet.get(position).getPromotion().getPrice()) / 100) * (Double.parseDouble(mDataSet.get(position).getPromotion().getDiscount())));
            holder.promotiondiscount.setText("discount %" + mDataSet.get(position).getPromotion().getDiscount() + " - " + formatter.format(discount) + " instead of " + mDataSet.get(position).getPromotion().getPrice());

            holder.description.setVisibility(View.GONE);
            holder.rat.setVisibility(View.VISIBLE);
            holder.rel.setVisibility(View.VISIBLE);

        }
        if(mDataSet.get(position).getPromotion() != null)
        holder.description.setText(mDataSet.get(position).getPromotion().getDescription());
        holder.description.setTypeface(MyApplication.type_jf_regular);
        holder.text.setTypeface(MyApplication.type_jf_medium);
        holder.text1.setTypeface(MyApplication.type_jf_medium);
        holder.myRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                RateReservation(mDataSet.get(position).getId() + "", rating + "", holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void CancelReservation(String id, String cause, String type, final NewViewHolder holder, final Dialog dialog, final int pos) {
        new CancelReservation().CancelReservation(id, cause, type
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {
                            if (dialog != null)
                                dialog.dismiss();
                            holder.fc.toggle(true);
                            mDataSet.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, mDataSet.size());
                            if (popupWindow != null)
                                popupWindow.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        Toast.makeText(mcontext, Error, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnError(String message) {
                        Toast.makeText(mcontext, "non", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    public void AcceptReservation(String id, final NewViewHolder holder) {
        new AcceptReservation().AcceptReservation(id
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {
                            holder.fc.toggle(false);
                            holder.end.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        Toast.makeText(mcontext, Error, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnError(String message) {
                        Toast.makeText(mcontext, "non", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    public void RateReservation(String id, String start, final NewViewHolder holder) {
        new RateReservation().RateReservation(id, start
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {
                            ListeReservation();
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";
                        Toast.makeText(mcontext, Error, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void OnError(String message) {
                        Toast.makeText(mcontext, "non", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.line)
        TextView line;
        @BindView(R.id.left_time)
        TextView left_time;
        @BindView(R.id.left_time1)
        TextView left_time1;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.description1)
        TextView description1;
        @BindView(R.id.send_reason)
        CircularProgressButton send_reason;
        @BindView(R.id.buton1)
        LinearLayout buton1;
        @BindView(R.id.buton)
        LinearLayout buton;
        @BindView(R.id.end)
        LinearLayout end;
        @BindView(R.id.aprouve)
        CircularProgressButton aprouve;
        @BindView(R.id.decline)
        Button decline;
        @BindView(R.id.reason)
        MultiAutoCompleteTextView reason;
        @BindView(R.id.menu)
        ImageView menu;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.etat1)
        LinearLayout etat1;
        @BindView(R.id.face)
        LinearLayout face;
        @BindView(R.id.folding_cell)
        FoldingCell fc;
        @BindView(R.id.photo)
        ImageView photo;
        @BindView(R.id.photo1)
        ImageView photo1;
        @BindView(R.id.rat)
        LinearLayout rat;
        @BindView(R.id.rel)
        RelativeLayout rel;
        @BindView(R.id.myRatingBar)
        RatingBar myRatingBar;
        @BindView(R.id.promotion_name)
        TextView promotion_name;
        @BindView(R.id.text_from)
        TextView text_from;
        @BindView(R.id.promotiondiscount)
        TextView promotiondiscount;
        @BindView(R.id.titre)
        TextView titre;
        @BindView(R.id.card)
        CardView card;


        NewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}