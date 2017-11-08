package br.com.smartside.mybiorun.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.activity.AddParticipantsActivity;
import br.com.smartside.mybiorun.database.SessionManager;
import br.com.smartside.mybiorun.model.Participants;
import br.com.smartside.mybiorun.utils.CircleTransform;
import br.com.smartside.mybiorun.model.Item;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by smartside on 02/06/17.
 */

public class GroupsAddParticipantsAdapter extends RecyclerView.Adapter<GroupsAddParticipantsAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    public ArrayList<Item> usersList = new ArrayList<>();
    public ArrayList<Item> selected_usersList = new ArrayList<>();
    ArrayList<Participants> lstData;

    public GroupsAddParticipantsAdapter(Context context, ArrayList<Item> data, ArrayList<Item> selectedList){
        mContext = context;
        usersList = data;
        this.selected_usersList = selectedList;

        lstData = new ArrayList<>();
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.view_list_item, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        Item item = usersList.get(position);

        if(!item.getPicture().equals("")){
            Picasso.with(mContext).load(item.getPicture()).transform(new CircleTransform()).into(holder.img_participant);
        }


        holder.title.setText(item.getTitle());

        if(selected_usersList.contains(usersList.get(position))) {
            holder.item.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }else {
            holder.item.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder{

        ImageView img_participant;
        TextView title;
        RelativeLayout item;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            img_participant = (ImageView) itemView.findViewById(R.id.img_friend);
            title = (TextView) itemView.findViewById(R.id.title);
            item = (RelativeLayout) itemView.findViewById(R.id.item);
        }
    }
}
