package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.activity.AddGroupActivity;
import br.com.smartside.mybiorun.model.Item;
import br.com.smartside.mybiorun.utils.CircleTransform;
import br.com.smartside.mybiorun.utils.Utils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by smartside on 02/06/17.
 */

public class GroupsInvitedsAdapter extends RecyclerView.Adapter<GroupsInvitedsAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    List<Item> mAllParticipants;
    Item tema;

    public GroupsInvitedsAdapter(Context context, List<Item> allParticipants){
        mContext = context;
        mAllParticipants = allParticipants;
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_inviteds_add, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        if(!mAllParticipants.get(position).getPicture().equals("")){
            Picasso.with(mContext).load(mAllParticipants.get(position).getPicture()).transform(new CircleTransform()).into(holder.pic_invited);
        }

        holder.title.setText(mAllParticipants.get(position).getTitle());

        holder.item_delete.setTag(position);
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int clickPosition = (int) v.getTag();
                tema = mAllParticipants.get(clickPosition);

                mAllParticipants.remove(tema);

                if(mContext instanceof AddGroupActivity){
                    ((AddGroupActivity)mContext).addToList(mAllParticipants);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mAllParticipants.size();
    }

    class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder  {

        ImageView pic_invited, item_delete;
        TextView title;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            title = (TextView) itemView.findViewById(R.id.title);
            pic_invited = (ImageView) itemView.findViewById(R.id.img_invited);
            item_delete = (ImageView) itemView.findViewById(R.id.item_delete);

        }

    }

}
