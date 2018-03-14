package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Follow;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.SalonProfile;
import salon.octadevtn.sa.salon.fragment.UserProfileFragment;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorFlow extends RecyclerView.Adapter<AdaptorFlow.ViewHolder> {

    Context context;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Follow> itemList;

    // Constructor of the class
    public AdaptorFlow(ArrayList<Follow> itemList, Context c) {
        context = c;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImage + itemList.get(listPosition).getImage()).into(holder.imageView);
        if (itemList.get(listPosition).getType().equals("salon"))
            holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color1));
        else if (itemList.get(listPosition).getGenre().equals("men"))
            holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color2));
        else
            holder.imageView.setBorderColor(MyApplication.getAppContext().getResources().getColor(R.color.color4));

        holder.text.setText(itemList.get(listPosition).getUsername());
        if (!itemList.get(listPosition).getIsFolow()) {
            holder.btn.setText(MyApplication.getAppContext().getResources().getString(R.string.start_follow));
        } else
            holder.btn.setText(MyApplication.getAppContext().getResources().getString(R.string.unfollow));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(listPosition).getType().equals("salon"))

                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(itemList.get(listPosition).getIdUser()));
                else
                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(itemList.get(listPosition).getIdUser()));
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemList.get(listPosition).getType().equals("salon"))
                    ((HomeActivityDrawer) context).setFragment(SalonProfile.newInstance(itemList.get(listPosition).getIdUser()));
                else
                    ((HomeActivityDrawer) context).setFragment(UserProfileFragment.newInstance(itemList.get(listPosition).getIdUser()));
            }
        });


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemList.get(listPosition).getIsFolow()) {
                    new salon.octadevtn.sa.salon.Api.Follow().AddFollow(itemList.get(listPosition).getIdUser(), itemList.get(listPosition).getType()
                            , new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (((Boolean) result) == true) {
                                        holder.btn.setText(MyApplication.getAppContext().getResources().getString(R.string.unfollow));
                                        itemList.get(listPosition).setIsFolow("true");
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
                    new salon.octadevtn.sa.salon.Api.Follow().DeleteFollow(itemList.get(listPosition).getIdUser(), itemList.get(listPosition).getType()
                            , new UniversalCallBack() {
                                @Override
                                public void onResponse(Object result) {
                                    if (((Boolean) result) == true) {
                                        holder.btn.setText(MyApplication.getAppContext().getResources().getString(R.string.start_follow));
                                        itemList.get(listPosition).setIsFolow("false");
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
        holder.btn.setTypeface(MyApplication.type_jf_regular);
        holder.text.setTypeface(MyApplication.type_jf_medium);

    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView text;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.cercleImage);
            text = (TextView) itemView.findViewById(R.id.username);
            btn = (Button) itemView.findViewById(R.id.btn_follow);

        }

        @Override
        public void onClick(View view) {
        }
    }
}