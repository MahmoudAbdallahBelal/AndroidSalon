package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import salon.octadevtn.sa.salon.Models.SalonType;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorServicefor extends RecyclerView.Adapter<AdaptorServicefor.ViewHolder> {

    Context mcontext;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<SalonType> mdata;
    private AdapterWork.RecyclerClickListener recyclerClickListener;

    // Constructor of the class
    public AdaptorServicefor(List<SalonType> mdata, Context context) {
        this.mdata = mdata;
        mcontext = context;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        ViewHolder userViewHolder = new ViewHolder(v);
        return userViewHolder;

    }

    public void setRecyclerClickListener(AdapterWork.RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (MyApplication.getInstance().getPrefManager().getLang().equals("1"))

            holder.name.setText(mdata.get(position).getName());
        else holder.name.setText(mdata.get(position).getName_en());


        holder.name.setTypeface(MyApplication.type_jf_regular);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(holder.getAdapterPosition());
                }
            }
        });

    }


    public interface RecyclerClickListener {
        void onClick(int position);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView close;
        TextView name;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            close = (ImageView) itemView.findViewById(R.id.close);
            name = (TextView) itemView.findViewById(R.id.name);

        }

        @Override
        public void onClick(View view) {
        }
    }
}