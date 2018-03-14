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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import salon.octadevtn.sa.salon.Api.Reservation.GetReservation;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Reservation.Task;
import salon.octadevtn.sa.salon.Models.Salon;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.Circlebutton.CircularProgressButton;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.fragment.PromotionFragment;
import salon.octadevtn.sa.salon.fragment.SalonProfile;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static salon.octadevtn.sa.salon.Reservation.ListeReservationSalon.ListeReservation;
import static salon.octadevtn.sa.salon.Utils.Static.popupWindow;
import static salon.octadevtn.sa.salon.Utils.UrlStatic.pathImag;


public class AdapterReservationSalon extends RecyclerView.Adapter<AdapterReservationSalon.NewViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    Context mcontext;
    ArrayList<Task> mDataSet;
    View popupView;
    RecyclerView recyclerView;
    boolean verif = false;
    private int position;


    public AdapterReservationSalon(ArrayList<Task> mDataSet, RecyclerView recyclerView, Context mcontext) {
        this.mDataSet = mDataSet;
        this.mcontext = mcontext;
        this.recyclerView = recyclerView;
    }

    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
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
            holder.buton1.setVisibility(View.GONE);

        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 0) {
            //reservation Approve view detail
            holder.buton.setVisibility(View.VISIBLE);
            holder.buton1.setVisibility(View.VISIBLE);

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
                CancelReservation(mDataSet.get(position).getId() + "", holder.reason.getText().toString(), "salon", position, holder, null);


            }
        });
        holder.aprouve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.aprouve.setIndeterminateProgressMode(true); // turn on indeterminate progress
                holder.aprouve.setProgress(50);
                AcceptReservation(mDataSet.get(position).getId() + "", holder, position);

            }
        });


        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(mDataSet.get(position).getEtat()) == 0 || Integer.parseInt(mDataSet.get(position).getEtat()) == 1) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = layoutInflater.inflate(R.layout.pop_up_reservation, null);

                    if (popupWindow == null)
                        popupWindow = new PopupWindow(
                                popupView,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout btnDismiss = (LinearLayout) popupView.findViewById(R.id.cancel);
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
                                    ((CircularProgressButton) dialog.findViewById(R.id.send_reason)).setIndeterminateProgressMode(true); // turn on indeterminate progress
                                    ((CircularProgressButton) dialog.findViewById(R.id.send_reason)).setProgress(50);
                                    holder.face.setVisibility(View.VISIBLE);
                                    CancelReservation(mDataSet.get(position).getId() + "", ((MultiAutoCompleteTextView) dialog.findViewById(R.id.reason)).getText().toString(), "salon", position, holder, dialog);

                                }
                            });

                        }
                    });
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }

                    popupWindow.showAsDropDown(holder.menu, -60, -60);
                } else if (Integer.parseInt(mDataSet.get(position).getEtat()) == 0 || Integer.parseInt(mDataSet.get(position).getEtat()) == 1) {
                    LayoutInflater layoutInflater
                            = (LayoutInflater) MyApplication.getAppContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = layoutInflater.inflate(R.layout.pop_up_reservation, null);

                    if (popupWindow == null)
                        popupWindow = new PopupWindow(
                                popupView,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout btnDismiss = (LinearLayout) popupView.findViewById(R.id.cancel);
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
                                    ((CircularProgressButton) dialog.findViewById(R.id.send_reason)).setIndeterminateProgressMode(true); // turn on indeterminate progress
                                    ((CircularProgressButton) dialog.findViewById(R.id.send_reason)).setProgress(50);
                                    holder.face.setVisibility(View.VISIBLE);
                                    CancelReservation(mDataSet.get(position).getId() + "", ((MultiAutoCompleteTextView) dialog.findViewById(R.id.reason)).getText().toString(), "salon", position, holder, dialog);

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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
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

        if (mDataSet.get(position).getUser().getPhoto() != null) {
            Glide.with(mcontext)
                    .load(pathImag + mDataSet.get(position).getUser().getPhoto())
                    .centerCrop()
                    .into(holder.photo);
            Glide.with(mcontext)
                    .load(pathImag + mDataSet.get(position).getUser().getPhoto())
                    .centerCrop()
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
            comment = mcontext.getResources().getString(R.string.mes6);
            dat = mDataSet.get(position).getCreatedAt();
            service = mcontext.getResources().getString(R.string.mes7);
            obj = mDataSet.get(position).getPromotion().getTitle();
            final String finalName5 = name;
            final String finalObj = obj;
            TextDecorator
                    .decorate(holder.text, name + " " + comment + " " + obj + " " + of + " " + service + " " + dat)
                    .setTextColor(R.color.gray, comment, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, of, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, service)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName5)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
                            if (text.equals(finalObj)) {
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                            }
                        }
                    }, false, name, of, obj)
                    .setTextColor(R.color.noir, name, of, obj, dat)
                    .build();
            TextDecorator
                    .decorate(holder.text1, name + " " + comment + " " + obj + " " + of + " " + service + " " + dat)
                    .setTextColor(R.color.gray, comment, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, of, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, service)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName5)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
                            if (text.equals(finalObj)) {
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                            }
                        }
                    }, false, name, of, obj)
                    .setTextColor(R.color.noir, name, of, obj, dat)
                    .build();
            holder.description.setText(mDataSet.get(position).getPromotion().getDescription());
            holder.description1.setText(mDataSet.get(position).getPromotion().getDescription());
        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == -1) {
            holder.description.setVisibility(View.GONE);
            name = mDataSet.get(position).getUser().getUsername();
            comment = mcontext.getResources().getString(R.string.mes3);
            dat = mDataSet.get(position).getCreatedAt();
            service = mcontext.getResources().getString(R.string.mes2);
            obj = mDataSet.get(position).getPromotion().getTitle();
            beause = mcontext.getResources().getString(R.string.mes4);
            cause = mDataSet.get(position).getCauseSalon();
            final String finalName4 = name;
            final String finalObj1 = obj;
            TextDecorator
                    .decorate(holder.text, name + " " + comment + " " + dat + " " + of + " " + service + " " + obj + " " + beause + " " + cause)
                    .setTextColor(R.color.gray, comment, of, beause, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, dat, of, service, cause, obj)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, beause)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName4)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
                            if (text.equals(finalObj1)) {
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                            }

                        }
                    }, false, name, of, obj)
                    .setTextColor(R.color.noir, name, dat, cause, obj)
                    .build();

            final String finalName3 = name;
            final String finalObj2 = obj;
            TextDecorator
                    .decorate(holder.text1, name + " " + comment + " " + dat + " " + of + " " + service + " " + obj + " " + beause + " " + cause)
                    .setTextColor(R.color.gray, comment, of, beause, service)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, dat, of, service, cause, obj)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, obj, comment, beause)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName3)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
                            if (text.equals(finalObj2)) {
                                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
                            }
                        }
                    }, false, name, of, obj)
                    .setTextColor(R.color.noir, name, dat, cause, obj)
                    .build();


        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 1) {
            holder.description.setVisibility(View.GONE);
            name = mDataSet.get(position).getUser().getUsername();
            comment = mcontext.getResources().getString(R.string.mes8);
            dat = mDataSet.get(position).getDate();
            service = mDataSet.get(position).getPromotion().getTitle();
            obj = mDataSet.get(position).getPromotion().getDescription();

            holder.description.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
            final String finalName2 = name;
            TextDecorator
                    .decorate(holder.text, comment + " " + name)
                    .setTextColor(R.color.gray, comment)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName2)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
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
                            Toast.makeText(mcontext, text, Toast.LENGTH_SHORT).show();
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, service)
                    .build();


            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar1 = Calendar.getInstance();

            Date date1;
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                date1 = format1.parse(mDataSet.get(position).getEndDate() + "");
                calendar2.setTime(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (calendar1.getTimeInMillis() > calendar2.getTimeInMillis()) {
                holder.buton1.setVisibility(View.VISIBLE);
                holder.buton2.setVisibility(View.VISIBLE);
                holder.description.setVisibility(View.GONE);
                name = mDataSet.get(position).getUser().getUsername();
                comment = mcontext.getResources().getString(R.string.mes9);
                String username = mDataSet.get(position).getUser().getUsername();
                obj = mcontext.getResources().getString(R.string.mes10);
                service = mDataSet.get(position).getPromotion().getTitle() + "?";
                final String finalName1 = name;
                TextDecorator
                        .decorate(holder.text, name + " " + comment + " " + username + " " + obj + " " + service)
                        .setTextColor(R.color.gray, comment, obj)
                        .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, username, service)
                        .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment, obj)
                        .makeTextClickable(new OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                if (text.equals(finalName1)) {
                                    ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                                }
                            }
                        }, false, name, of)
                        .setTextColor(R.color.noir, name, username, service)
                        .build();
            }
        }
        if (Integer.parseInt(mDataSet.get(position).getEtat()) == 2) {
            holder.description.setVisibility(View.GONE);
            name = new Salon().getSalonInfo().getSalonName();
            comment = mcontext.getResources().getString(R.string.mes5);
            dat = mDataSet.get(position).getStartDate();
            service = mDataSet.get(position).getPromotion().getTitle();
            obj = mDataSet.get(position).getPromotion().getDescription();

            holder.description.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
            final String finalName = name;
            TextDecorator
                    .decorate(holder.text, comment + " " + name)
                    .setTextColor(R.color.gray, comment)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, comment)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            if (text.equals(finalName)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
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
                            if (text.equals(finalName)) {
                                ((HomeActivityDrawer) mcontext).setFragment(SalonProfile.newInstance(Integer.parseInt((mDataSet.get(position).getIdSalon()))));
                            }
                        }
                    }, false, name, of)
                    .setTextColor(R.color.noir, name)
                    .build();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

            Date date1 = null;
            Date date2 = null;
            String da = "", dis = "";
            try {
                date1 = dt.parse(mDataSet.get(position).getPromotion().getStartDate());
                date2 = dt.parse(mDataSet.get(position).getPromotion().getEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date2);
            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))

                da = "من " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " إلى " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR);

            else
                da = "From " + c.get(Calendar.DAY_OF_MONTH) + " " + theMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR) + " to " +
                        c2.get(Calendar.DAY_OF_MONTH) + " " + theMonth(date2.getMonth()) + " " + c2.get(Calendar.YEAR)
                        ;
            NumberFormat formatter = new DecimalFormat("#0.0");

            Double discount = Double.parseDouble(mDataSet.get(position).getPromotion().getPrice()) - ((Double.parseDouble(mDataSet.get(position).getPromotion().getPrice()) / 100) * (Double.parseDouble(mDataSet.get(position).getPromotion().getDiscount())));
            if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))
                dis = ("تخفيض %" + mDataSet.get(position).getPromotion().getDiscount() + " - " + formatter.format(discount) + " بدلا من " + mDataSet.get(position).getPromotion().getPrice());
            else
                dis = ("discount %" + mDataSet.get(position).getPromotion().getDiscount() + " - " + formatter.format(discount) + " instead of " + mDataSet.get(position).getPromotion().getPrice());

            if(dat != null)
            TextDecorator
                    .decorate(holder.description, service + " - " + dat + "\n" + da + "\n" + dis + "\n" + obj)
                    .setTextColor(R.color.gray, comment)
                    .setTextColor(R.color.gray_noir, service, da, dis)
                    .setTextColor(R.color.holo_red_dark, dat)
                    .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size), true, name, da, dis)
                    // .setAbsoluteSize(mcontext.getResources().getInteger(R.integer.size1), true, da)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                        }
                    }, false, name, of)
                    .build();


        }
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) mcontext).setFragment(PromotionFragment.newInstance(Integer.parseInt((mDataSet.get(position).getPromotion().getId())), 1));
            }
        });


        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetReservation(mDataSet.get(position).getId() + "", "false", holder, position);
            }
        });
        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetReservation(mDataSet.get(position).getId() + "", "true", holder, position);
            }
        });

        holder.description.setTypeface(MyApplication.type_jf_regular);
        holder.description1.setTypeface(MyApplication.type_jf_regular);

        holder.text.setTypeface(MyApplication.type_jf_medium);
        holder.text1.setTypeface(MyApplication.type_jf_medium);

        holder.no.setTypeface(MyApplication.type_jf_regular);
        holder.yes.setTypeface(MyApplication.type_jf_regular);
        holder.send_reason.setTypeface(MyApplication.type_jf_regular);
        holder.titre.setTypeface(MyApplication.type_jf_regular);
        holder.decline.setTypeface(MyApplication.type_jf_regular);
        holder.aprouve.setTypeface(MyApplication.type_jf_regular);

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

    public void CancelReservation(String id, String cause, String type, final int pos, final NewViewHolder holder, final Dialog dialog) {

        new CancelReservation().CancelReservation(id, cause, type
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {
                            if (dialog != null)
                                dialog.dismiss();
                            mDataSet.remove(pos);
                            notifyItemRemoved(pos);
                            notifyItemRangeChanged(pos, mDataSet.size());
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

    public void AcceptReservation(String id, final NewViewHolder holder, final int pos) {
        new AcceptReservation().AcceptReservation(id
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {

                        if (result != null) {
                            holder.fc.toggle(false);
                            holder.end.setVisibility(View.GONE);
                            mDataSet.get(pos).setEtat("1");
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

    public void GetReservation(String id, String cause, final NewViewHolder holder, final int pos) {
        new GetReservation().GetReservation(id, cause
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            mDataSet.get(pos).setEtat("3");
                            //  notifyItemRangeChanged(pos,mDataSet.size());
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
        @BindView(R.id.titre)
        TextView titre;
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
        @BindView(R.id.buton2)
        LinearLayout buton2;
        @BindView(R.id.buton)
        LinearLayout buton;
        @BindView(R.id.end)
        LinearLayout end;
        @BindView(R.id.aprouve)
        CircularProgressButton aprouve;
        @BindView(R.id.no)
        Button no;
        @BindView(R.id.yes)
        Button yes;
        @BindView(R.id.decline)
        Button decline;
        @BindView(R.id.reason)
        MultiAutoCompleteTextView reason;
        @BindView(R.id.menu)
        ImageView menu;
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
        @BindView(R.id.card)
        CardView card;


        NewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}