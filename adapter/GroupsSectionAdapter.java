package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.model.Participants;
import br.com.smartside.mybiorun.model.UserGroupsResultVO;
import br.com.smartside.mybiorun.utils.CircleTransform;
import br.com.smartside.mybiorun.utils.Intents;
import br.com.smartside.mybiorun.utils.Utils;

/**
 * Created by smartside on 26/05/17.
 */

public class GroupsSectionAdapter extends SimpleSectionedAdapter<GroupsSectionAdapter.GroupsItemViewHolder> {

    Context mContext;
    List<List<UserGroupsResultVO.ResultInfo>> mGroups;
    UserGroupsResultVO.ResultInfo tema;
    String is_accepted;

    public GroupsSectionAdapter(Context context, List<List<UserGroupsResultVO.ResultInfo>> groups){
        mGroups = groups;
        mContext = context;
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        return section == 0 ? "FUTUROS" : "PASSADOS";
    }

    @Override
    protected int getSectionCount() {
        return 2;
    }

    @Override
    protected int getItemCountForSection(int section) {
        return mGroups.get(section).size();
    }

    @Override
    public GroupsItemViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.view_list_item_groups, viewGroup, false);

        return new GroupsItemViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(GroupsItemViewHolder holder, final int section, final int position) {

        tema = mGroups.get(section).get(position);

        if(section == 0){
            holder.textView.setText(tema.name);
            holder.date.setText(Utils.convertGroupDate(tema.date));

            if(!tema.picture.equals("")){
                Picasso.with(mContext).load(tema.picture).transform(new CircleTransform()).into(holder.pic_friend);
            }
        }else{
            holder.textView.setText(tema.name);
            holder.date.setText(Utils.convertGroupDate(tema.date));

            if(!tema.picture.equals("")){
                Picasso.with(mContext).load(tema.picture).transform(new CircleTransform()).into(holder.pic_friend);
            }
        }

        holder.item_group.setTag(position);
        holder.item_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int clickPosition = (int) v.getTag();
                tema = mGroups.get(section).get(clickPosition);

                ArrayList<Participants> participants = new ArrayList<>();

                for (int i = 0; i < tema.users.length; i++) {

                    if(tema.users[i].accept){
                        is_accepted = "true";
                    }else{
                        is_accepted = "false";
                    }

                    participants.add(new Participants(tema.users[i].name+" "+tema.users[i].last_name, tema.users[i].picture, is_accepted));
                }

                mContext.startActivity(Intents.goToGroupInfo(mContext, tema.id, tema.name, tema.distance, tema.picture, tema.date, tema.is_accept, participants));
            }
        });


    }

    class GroupsItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView, date;
        View friend_online;
        ImageView pic_friend;
        RelativeLayout item_group;

        public GroupsItemViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();
            textView = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            friend_online = itemView.findViewById(R.id.friend_online);
            pic_friend = (ImageView) itemView.findViewById(R.id.img_friend);
            item_group = (RelativeLayout) itemView.findViewById(R.id.item_group);

        }

    }

}
