package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.SalonProfile;
import salon.octadevtn.sa.salon.Models.ServicesCategory;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.Service_Category;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorServices_Liste extends RecyclerView.Adapter<AdaptorServices_Liste.ViewHolder> {

    SalonProfile salon;
    Context context;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<ServicesCategory> itemList;

    // Constructor of the class
    public AdaptorServices_Liste(Context context, ArrayList<ServicesCategory> itemList, SalonProfile salon) {
        this.itemList = itemList;
        this.salon = salon;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_liste_salon, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        holder.textView.setText(itemList.get(listPosition).getName());
        Glide.with(context).load(UrlStatic.pathImage + itemList.get(listPosition).getImageUrl())
                .override(500, 500)
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })

                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) context).setFragment(Service_Category.newInstance(salon, Integer.parseInt(itemList.get(listPosition).getId())));
            }
        });
        holder.textView.setTypeface(MyApplication.type_jf_medium);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.title);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);

        }

    }
}