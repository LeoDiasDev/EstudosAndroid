package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.model.EventsResultVO;
import br.com.smartside.mybiorun.utils.Intents;
import br.com.smartside.mybiorun.utils.Utils;

/**
 * Created by smartside on 02/06/17.
 */

public class MyEventsListAdapter extends RecyclerView.Adapter<MyEventsListAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    private EventsResultVO.ResultInfo[] item;

    public MyEventsListAdapter(Context context, EventsResultVO.ResultInfo[] item){
        this.item = item;
        mContext = context;
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.events_list_item, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        holder.distance.setText(item[position].distance+"K");
        holder.date.setText(Utils.convertDate2(item[position].date));
        holder.name.setText(item[position].title);
        holder.address.setText(Utils.capitalize(item[position].address_start));

        int color = mContext.getResources().getColor(R.color.blue);
        int colorButton = R.drawable.bg_button_blue;

        if(item[position].distance == 10){
            color = mContext.getResources().getColor(R.color.blue);
            colorButton = R.drawable.bg_button_blue;
        }else if(item[position].distance == 5){
            color = mContext.getResources().getColor(R.color.orange);
            colorButton = R.drawable.bg_button_orange;
        }

        holder.name.setTextColor(color);
        holder.distance.setTextColor(color);
        holder.bt_run_info.setBackgroundResource(colorButton);

        if(item[position].team){
            holder.bt_run_info.setText("REVEZAMENTO");
        } else{
            holder.bt_run_info.setText("INDIVIDUAL");
        }

        final EventsResultVO.ResultInfo eventItem = item[position];

        holder.bt_run_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(Intents.goToEventInfo(mContext, eventItem.id, eventItem.title, eventItem.description, eventItem.address_start, eventItem.distance, eventItem.date, eventItem.team, eventItem.is_subscribe));
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.length;
    }

    public static class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout item;
        TextView distance, date, name, address;
        Button bt_run_info;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            distance = (TextView) itemView.findViewById(R.id.distance);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            bt_run_info = (Button) itemView.findViewById(R.id.bt_run_info);

            item = (RelativeLayout) itemView.findViewById(R.id.event_item);
        }
    }
}
