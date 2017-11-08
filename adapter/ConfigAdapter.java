package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.activity.AddGroupActivity;
import br.com.smartside.mybiorun.activity.ConfigListActivity;
import br.com.smartside.mybiorun.model.Item;
import br.com.smartside.mybiorun.model.ItemConfig;
import br.com.smartside.mybiorun.utils.CircleTransform;

/**
 * Created by smartside on 02/06/17.
 */

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ConfigAudiosViewHolder> {

    Context mContext;
    ArrayList<ItemConfig> array_list;
    String type_speed;
    ItemConfig item;
    Integer mPosition;

    public ConfigAdapter(Context context, ArrayList<ItemConfig> array_list, String type_speed){
        mContext = context;
        this.array_list = array_list;
        this.type_speed = type_speed;
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

        if(!type_speed.equals("time")){
            if(type_speed.equals("km")){
                mPosition = array_list.get(position).getNumber();
                holder.mTitleAudio.setText(((mPosition == 0) ? "DESATIVADO" : array_list.get(position).getNumber()+" KM"));
            }else{
                mPosition = array_list.get(position).getNumber();
                holder.mTitleAudio.setText(((mPosition == 0) ? "DESATIVADO" : array_list.get(position).getNumber()+" MI"));
            }
        }else{
            mPosition = array_list.get(position).getNumber();
            holder.mTitleAudio.setText(((mPosition == 0) ? "DESATIVADO" : array_list.get(position).getNumber()+" MIN"));
        }

        holder.item_config_audio.setTag(position);
        holder.item_config_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int clickPosition = (int) v.getTag();
                item = array_list.get(clickPosition);

                array_list.remove(item);

                if(mContext instanceof ConfigListActivity){
                    ((ConfigListActivity)mContext).addToListAudios(item, type_speed);
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
