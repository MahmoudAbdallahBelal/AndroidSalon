package salon.octadevtn.sa.salon.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import salon.octadevtn.sa.salon.Models.WordDayHour;
import salon.octadevtn.sa.salon.R;
import salon.octadevtn.sa.salon.Utils.MyApplication;


public class AdapterWork extends RecyclerView.Adapter<AdapterWork.NewViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    Context mcontext;
    List<WordDayHour> mDataSet;
    private RecyclerClickListener recyclerClickListener;
    private RecyclerClickListener recyclerClickListener1;
    private int position;


    public AdapterWork(List<WordDayHour> mDataSet, Context mcontect) {
        this.mDataSet = mDataSet;
        this.mcontext = mcontect;
    }

    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work, parent, false);
        NewViewHolder userViewHolder = new NewViewHolder(v);
        return userViewHolder;
    }

    public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

    }

    public void setRecyclerClickListener1(RecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener1 = recyclerClickListener;

    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public void onBindViewHolder(final NewViewHolder holder, final int position) {

        holder.time1.setTypeface(MyApplication.type_jf_regular);
        holder.time2.setTypeface(MyApplication.type_jf_regular);
        holder.day.setTypeface(MyApplication.type_jf_regular);
        if (Integer.parseInt(MyApplication.getInstance().getPrefManager().getLang()) == 1) {
            holder.day.setText(mDataSet.get(position).getNameAr());
        } else {
            holder.day.setText(mDataSet.get(position).getName().substring(0, 3));
        }

        holder.time1.setText(mDataSet.get(position).getStartHour());
        holder.time2.setText(mDataSet.get(position).getEndHours());
        if (mDataSet.get(position).getStatus() == true) {
            holder.toggleButton1.setChecked(true);
            holder.time.setVisibility(View.VISIBLE);
            holder.Closed.setVisibility(View.GONE);
        } else {
            holder.toggleButton1.setChecked(false);
            holder.time.setVisibility(View.GONE);
            holder.Closed.setVisibility(View.VISIBLE);
        }
        holder.toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.toggleButton1.isChecked()) {
                    mDataSet.get(position).setStatus(true);
                    holder.time.setVisibility(View.VISIBLE);
                    holder.Closed.setVisibility(View.GONE);
                } else {
                    mDataSet.get(position).setStatus(false);
                    holder.time.setVisibility(View.GONE);
                    holder.Closed.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onClick(holder.getAdapterPosition());
                }
            }
        });
        holder.time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerClickListener1 != null) {
                    recyclerClickListener1.onClick(holder.getAdapterPosition());
                }
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

    public interface RecyclerClickListener {
        void onClick(int position);
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.toggleButton1)
        ToggleButton toggleButton1;

        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.time)
        LinearLayout time;
        @BindView(R.id.time1)
        TextView time1;
        @BindView(R.id.time2)
        TextView time2;
        @BindView(R.id.Closed)
        TextView Closed;


        NewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }


}