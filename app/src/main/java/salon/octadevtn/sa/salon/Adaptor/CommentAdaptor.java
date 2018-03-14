package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Comment;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.UserProfileFragment;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class CommentAdaptor extends RecyclerView.Adapter<CommentAdaptor.ViewHolder> {

    Context context;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Comment> itemList;

    // Constructor of the class
    public CommentAdaptor(ArrayList<Comment> itemList, Context context) {
        this.context = context;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        if (itemList.get(listPosition).getTypeUser().equals("salon")) {
            holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
        } else {
            if ((itemList.get(listPosition).getGenre().equals("men"))) {
                holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));

            } else
                holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

        }
        Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + itemList.get(listPosition).getPhoto())
                .override(100, 100)
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
        holder.username.setText(itemList.get(listPosition).getUsername());
        Calendar calendar = Calendar.getInstance();
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = format.parse(itemList.get(listPosition).getCreatedAt() + "");
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(String.valueOf(calendar.getTimeInMillis())),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        holder.date.setText(timeAgo);
        holder.content.setText(itemList.get(listPosition).getContenu());
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(listPosition).getTypeUser().equals("salon"))

                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt(itemList.get(listPosition).getIdUser())));
                else
                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(itemList.get(listPosition).getIdUser())));
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(listPosition).getTypeUser().equals("salon"))
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(Integer.parseInt(itemList.get(listPosition).getIdUser())));
                else
                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(Integer.parseInt(itemList.get(listPosition).getIdUser())));
            }
        });

        holder.username.setTypeface(MyApplication.type_jf_medium);
        holder.date.setTypeface(MyApplication.type_jf_light);
        holder.content.setTypeface(MyApplication.type_jf_medium);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, date, content;
        CircleImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            date = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.content);
            imageView = (CircleImageView) itemView.findViewById(R.id.image);
        }

    }
}