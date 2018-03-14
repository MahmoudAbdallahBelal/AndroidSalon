package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import salon.octadevtn.sa.salon.HomeActivityDrawer;
import salon.octadevtn.sa.salon.Models.Photo;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.Salon_services;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorService_Category1 extends RecyclerView.Adapter<AdaptorService_Category1.ViewHolder> {

    Context mcontext;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Photo> photos;

    // Constructor of the class
    public AdaptorService_Category1(List<Photo> photos, Context context) {
        this.photos = photos;
        mcontext = context;
    }


    // get the size of the list
    @Override
    public int getItemCount() {
        return photos.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_category_liste, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Glide.with(MyApplication.getAppContext()).load(UrlStatic.pathImag + photos.get(listPosition).getUrl()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivityDrawer) mcontext).setFragment(new Salon_services());
            }
        });


    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView text;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }

        @Override
        public void onClick(View view) {
        }
    }
}