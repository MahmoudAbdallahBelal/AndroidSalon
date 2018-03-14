package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import salon.octadevtn.sa.salon.Models.Profile;
import salon.octadevtn.sa.salon.Models.Timeline_;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.Static;

import static salon.octadevtn.sa.salon.Utils.UrlStatic.pathImag;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.ViewHolder> {

    Profile profile = null;
    Context context;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Timeline_> itemList;


    // Constructor of the class
    public UserActivityAdapter(List<Timeline_> itemList, Context context) {
        this.itemList = itemList;
        GSONSharedPreferences gsonSharedPrefs = new GSONSharedPreferences(MyApplication.getAppContext(), Static.shared_name);
        try {
            profile = (Profile) gsonSharedPrefs.getObject(new Profile());
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        this.context = context;


    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_activity, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context)
                .load(pathImag + profile.getUser().getPhoto())
                .centerCrop()
                .into(holder.cercleImage);
        Date date;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(itemList.get(position).getCreatedAt() + "");
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.time.setText(timeAgo);
        holder.time.setTypeface(MyApplication.type_jf_regular);

        if (itemList.get(position).getTypeObject().equals("promotion")) {
            String name = profile.getUser().getUsername();
            String comment = "";
            String object = itemList.get(position).getPromotion().get(0).getTitle();
            if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
                comment = itemList.get(position).getAction();
            } else {
                if (itemList.get(position).getAction().equals("like"))
                    comment = "اعجب ب";
                if (itemList.get(position).getAction().equals("comment"))
                    comment = "اعجب بعرض";
                if (itemList.get(position).getAction().equals("favorite"))
                    comment = "قام باضافة مفضلة";

            }
            holder.salonname.setTypeface(MyApplication.type_jf_regular);
            TextDecorator
                    .decorate(holder.salonname, name + " " + comment + " " + object)
                    .setTextColor(R.color.gray, comment)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, object)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {

                        }
                    }, false, name, object)
                    .setTextColor(R.color.noir, name, object)
                    .build();
        }

        if (itemList.get(position).getTypeObject().equals("following")) {
            String name = profile.getUser().getUsername();
            String comment = "";
            String object = "";//itemList.get(position).getPromotion().get(0).getTitle();
            if (MyApplication.getInstance().getPrefManager().getLang().equals("0")) {
                comment = itemList.get(position).getAction();
            } else {
                comment = "قام بمتابعة";
            }
            holder.salonname.setTypeface(MyApplication.type_jf_regular);

            TextDecorator
                    .decorate(holder.salonname, name + " " + comment + " " + object)
                    .setTextColor(R.color.gray, comment)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size), true, name, object)
                    .setAbsoluteSize(context.getResources().getInteger(R.integer.size1), true, comment)
                    .makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {


                        }
                    }, false, name, object)
                    .setTextColor(R.color.noir, name, object)
                    .build();
        }

    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cercleImage)
        CircleImageView cercleImage;
        @BindView(R.id.salonname)
        TextView salonname;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        @Override
        public void onClick(View view) {
        }
    }
}