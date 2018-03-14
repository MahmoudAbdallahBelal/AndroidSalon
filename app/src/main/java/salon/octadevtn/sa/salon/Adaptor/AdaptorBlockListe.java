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

import salon.octadevtn.sa.salon.Models.Listblock;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;
import salon.octadevtn.sa.salon.Utils.ResponseErrors;
import salon.octadevtn.sa.salon.Utils.UniversalCallBack;

/**
 * Created by Marwen octadev on 7/15/2017.
 */

public class AdaptorBlockListe extends RecyclerView.Adapter<AdaptorBlockListe.ViewHolder> {

    Context mcontext;
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Listblock> listblocks;

    // Constructor of the class
    public AdaptorBlockListe(List<Listblock> listblocks, Context context) {
        this.listblocks = listblocks;
        mcontext = context;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return listblocks.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_block, parent, false);
        ViewHolder userViewHolder = new ViewHolder(v);
        return userViewHolder;

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(listblocks.get(position).getUsername());
        holder.name.setTypeface(MyApplication.type_jf_regular);
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(listblocks.get(position).getIdBlock(), position);
            }
        });

    }

    public void delete(String s, final int s1) {
        new salon.octadevtn.sa.salon.Api.DeleteBlock().DeleteBlock(s
                , new UniversalCallBack() {
                    @Override
                    public void onResponse(Object result) {
                        if (result != null) {
                            listblocks.remove(s1);
                            notifyItemRemoved(s1);
                        }

                    }

                    @Override
                    public void onFailure(Object result) {
                        ResponseErrors responseError = (ResponseErrors) result;
                        String Error = "Failure";

                    }

                    @Override
                    public void OnError(String message) {

                    }

                    @Override
                    public void onFinish() {
                    }
                });
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