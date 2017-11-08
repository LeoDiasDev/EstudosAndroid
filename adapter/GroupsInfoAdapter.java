package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.model.Participants;
import br.com.smartside.mybiorun.model.UserGroupsResultVO;
import br.com.smartside.mybiorun.utils.CircleTransform;

/**
 * Created by smartside on 02/06/17.
 */

public class GroupsInfoAdapter extends RecyclerView.Adapter<GroupsInfoAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    ArrayList<Participants> mUsers;

    public GroupsInfoAdapter(Context context, ArrayList<Participants> users){
        mContext = context;
        mUsers = users;
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_inviteds, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        if(!mUsers.get(position).getPicture().equals("")){
            Picasso.with(mContext).load(mUsers.get(position).getPicture()).transform(new CircleTransform()).into(holder.pic_invited);
        }

        holder.title.setText(mUsers.get(position).getName());

        if(mUsers.get(position).getIs_accept().equals("0")){
            holder.item_confirmed.setVisibility(View.GONE);
            holder.item_not_confirmed.setVisibility(View.VISIBLE);
        }
        else if(mUsers.get(position).getIs_accept().equals("1")){
            holder.item_confirmed.setVisibility(View.VISIBLE);
            holder.item_not_confirmed.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder  {

        ImageView pic_invited;
        TextView item_confirmed, item_not_confirmed, title;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            pic_invited = (ImageView) itemView.findViewById(R.id.img_invited);
            item_confirmed = (TextView) itemView.findViewById(R.id.item_confirmed);
            item_not_confirmed = (TextView) itemView.findViewById(R.id.item_not_confirmed);
            title = (TextView) itemView.findViewById(R.id.title);

        }

    }

}
