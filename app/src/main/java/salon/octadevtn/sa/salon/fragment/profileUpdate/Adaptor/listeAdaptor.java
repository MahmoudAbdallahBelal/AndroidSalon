package salon.octadevtn.sa.salon.fragment.profileUpdate.Adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.UrlStatic;
import salon.octadevtn.sa.salon.fragment.profileUpdate.Models.Category;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class listeAdaptor extends RecyclerView.Adapter<listeAdaptor.ViewHolder> {
    OnItemClickListener myAdapterListener;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Category> itemList;

    // Constructor of the class
    public listeAdaptor(ArrayList<Category> itemList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listecategory, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    public void setMyAdapterListener(OnItemClickListener myAdapterListener) {
        this.myAdapterListener = myAdapterListener;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Glide.with(MyApplication.getAppContext())
                .load(UrlStatic.pathImage + itemList.get(listPosition).getImageUrl())
                .override(150,150)
                .into(holder.imageView);
        holder.text.setText(itemList.get(listItemLayout).getName());
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapterListener.onItemClick(listPosition);
            }
        });
        holder.text.setTypeface(MyApplication.type_jf_medium);

    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text;
        Button btn;
        LinearLayout lin;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.cercleImage);
            lin = (LinearLayout) itemView.findViewById(R.id.lin);
            text = (TextView) itemView.findViewById(R.id.username);

        }

    }
}