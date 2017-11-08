package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import java.io.IOException;
import java.util.List;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.model.FriendListVO;
import br.com.smartside.mybiorun.model.FriendsOffline;
import br.com.smartside.mybiorun.model.FriendsOnline;
import br.com.smartside.mybiorun.utils.CircleTransform;
import br.com.smartside.mybiorun.utils.Utils;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by smartside on 26/05/17.
 */

public class FriendsSectionAdapter extends SimpleSectionedAdapter<FriendsSectionAdapter.FriendsItemViewHolder> {

    Context mContext;
    List<List<FriendListVO.ResultInfo>> mFriends;
    FriendListVO.ResultInfo tema;

    public FriendsSectionAdapter(Context context, List<List<FriendListVO.ResultInfo>> friends){
        mContext = context;
        mFriends = friends;
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        return section == 0 ? "AMIGOS ONLINE" : "AMIGOS";
    }

    @Override
    protected int getSectionCount() {
        return 2;
    }

    @Override
    protected int getItemCountForSection(int section) {
        if (!mFriends.get(section).isEmpty()) {
            return mFriends.get(section).size();
        }else{
            return 0;
        }
    }

    @Override
    public FriendsItemViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.view_list_item, viewGroup, false);

        return new FriendsItemViewHolder(itemView);
    }

    @Override
    protected void onBindItemViewHolder(FriendsItemViewHolder holder, int section, int position) {

        tema = mFriends.get(section).get(position);

        if(section == 0){
            holder.friend_online.setVisibility(View.VISIBLE);

            if(tema.name != null){

                holder.textView.setText(tema.name);
            }

            if(!tema.picture.equals("") && holder.pic_friend != null){
                Utils.putImageView(mContext, tema.picture, holder.pic_friend);

            }




        }else{
            holder.friend_online.setVisibility(View.GONE);

            if(tema.name != null){
                holder.textView.setText(tema.name);
            }



            if(!tema.picture.equals("") && holder.pic_friend != null){
                Utils.putImageView(mContext, tema.picture, holder.pic_friend);
            }


        }

    }



    class FriendsItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        View friend_online;
        ImageView pic_friend;

        public FriendsItemViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();
            textView = (TextView) itemView.findViewById(R.id.title);
            friend_online = itemView.findViewById(R.id.friend_online);
            pic_friend = (ImageView) itemView.findViewById(R.id.img_friend);

        }

    }

}
