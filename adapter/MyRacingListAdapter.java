package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.database.Location;
import br.com.smartside.mybiorun.database.SessionManager;
import br.com.smartside.mybiorun.database.Tracking;
import br.com.smartside.mybiorun.model.Racing;
import br.com.smartside.mybiorun.model.UserConfigData;
import br.com.smartside.mybiorun.utils.Intents;
import br.com.smartside.mybiorun.utils.Utils;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by smartside on 02/06/17.
 */

public class MyRacingListAdapter extends RecyclerView.Adapter<MyRacingListAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    public ArrayList<Tracking> racing = new ArrayList<>();
    private UserConfigData userConfigData;
    private int value_type_speed;
    String typeSpeed, distance;

    public MyRacingListAdapter(Context context, ArrayList<Tracking> racing){
        this.racing = racing;
        mContext = context;
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.racing_list_item, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        userConfigData = SessionManager.instance().getUserConfigData(mContext);
        if(userConfigData != null){
            if (userConfigData.type_speed.equals("km")) {
                typeSpeed = "KM";
                value_type_speed = 1000;
            }else if(userConfigData.type_speed.equals("miles")){
                value_type_speed = 1609;
                typeSpeed = "MI";
            }
        }

        final Tracking racingItem = racing.get(position);

        holder.title.setText(Utils.convertNameDate(racingItem.date)+" CORRIDA");
        holder.date_racing.setText(Utils.convertFormatDate(racingItem.date));

        if(racingItem.distance != null){
            distance = String.format("%.2f", (racingItem.distance/value_type_speed));
        }

        float secondsPerDistance = ((float)racingItem.time / (racingItem.distance / value_type_speed));
        if(!Float.isInfinite(secondsPerDistance)){

            if (!Double.isNaN(secondsPerDistance)) {
                holder.info_racing.setText(Html.fromHtml(distance+typeSpeed+" | "+ Utils.getTimeWithSeconds(secondsPerDistance)+"&ldquo;"));
            }else{
                holder.info_racing.setText("");
            }
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(Intents.goToMyTracking(mContext, racingItem._id, false));
            }
        });
    }

    @Override
    public int getItemCount() {
        return racing.size();
    }

    public static class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout item;
        TextView title, date_racing, info_racing;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_racing);
            date_racing = (TextView) itemView.findViewById(R.id.date_racing);
            info_racing = (TextView) itemView.findViewById(R.id.info_racing);

            item = (RelativeLayout) itemView.findViewById(R.id.item);
        }
    }
}
