package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.activity.ConfigListActivity;
import br.com.smartside.mybiorun.activity.CountDownListActivity;
import br.com.smartside.mybiorun.model.ItemConfig;

/**
 * Created by smartside on 02/06/17.
 */

public class CountDownAdapter extends RecyclerView.Adapter<CountDownAdapter.ConfigAudiosViewHolder> {

    Context mContext;
    ArrayList<ItemConfig> array_list;
    ItemConfig item;
    Integer mPosition;

    public CountDownAdapter(Context context, ArrayList<ItemConfig> array_list){
        this.array_list = array_list;
        mContext = context;
    }

    @Override
    public ConfigAudiosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_config_audios, viewGroup, false);
        return new ConfigAudiosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConfigAudiosViewHolder holder, final int position) {

        if(array_list.get(position).getNumber() == 0){
            holder.mTitleAudio.setText("DESATIVADO");
        }

        mPosition = array_list.get(position).getNumber();
        holder.mTitleAudio.setText(((mPosition == 0) ? "DESATIVADO" : array_list.get(position).getNumber()+""));

        holder.item_config_audio.setTag(position);
        holder.item_config_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int clickPosition = (int) v.getTag();
                item = array_list.get(clickPosition);

                array_list.remove(item);

                if(mContext instanceof CountDownListActivity){
                    ((CountDownListActivity)mContext).addToListCountDown(item);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return array_list.size();
    }

    class ConfigAudiosViewHolder extends RecyclerView.ViewHolder  {

        LinearLayout item_config_audio;
        TextView mTitleAudio;

        public ConfigAudiosViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            mTitleAudio = (TextView) itemView.findViewById(R.id.title_audio);
            item_config_audio = (LinearLayout) itemView.findViewById(R.id.item_config_audio);
        }

    }

}
