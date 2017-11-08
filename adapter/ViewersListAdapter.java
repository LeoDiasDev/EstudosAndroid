package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.activity.HomeActivity;
import br.com.smartside.mybiorun.activity.LoginActivity;
import br.com.smartside.mybiorun.database.SessionManager;
import br.com.smartside.mybiorun.model.DeleteViewerResultVO;
import br.com.smartside.mybiorun.model.Tickets;
import br.com.smartside.mybiorun.model.UserData;
import br.com.smartside.mybiorun.model.Viewers;
import br.com.smartside.mybiorun.rest.RestClient;
import br.com.smartside.mybiorun.utils.Intents;
import br.com.smartside.mybiorun.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by smartside on 02/06/17.
 */

public class ViewersListAdapter extends RecyclerView.Adapter<ViewersListAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    ArrayList<Viewers> item;
    UserData userData;
    String id_event;
    RelativeLayout progressRegister;

    public ViewersListAdapter(Context context, ArrayList<Viewers> item, String id_event, RelativeLayout progressRegister){
        this.item = item;
        mContext = context;
        this.id_event = id_event;
        this.progressRegister = progressRegister;
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_inviteds_viewers, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        userData = SessionManager.instance().getUserData(mContext);

        holder.title.setText(item.get(position).getName());
        Picasso.with(mContext).load(item.get(position).getPicture()).into(holder.img_invited);

        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("id invite", item.get(position).getId());

                progressRegister.setVisibility(View.VISIBLE);

                Call<DeleteViewerResultVO> call = RestClient.instance().getService().deleteViewer(Utils.getToken(), item.get(position).getEventId(), item.get(position).getId());
                call.enqueue(new Callback<DeleteViewerResultVO>() {
                    @Override
                    public void onResponse(Call<DeleteViewerResultVO> call, Response<DeleteViewerResultVO> response) {

                        progressRegister.setVisibility(View.GONE);

                        if(response.body() != null){
                            Utils.showToast(mContext, response.body().reason);

                            item.remove(position);
                            notifyDataSetChanged();

                        }

                        else if(response.errorBody() != null){
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Utils.showToast(mContext, jObjError.getString("reason"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<DeleteViewerResultVO> call, Throwable t) {
                        progressRegister.setVisibility(View.GONE);
                        Log.d("failed", "delete viewer failed");
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder{

        LinearLayout item_invite;
        TextView title;
        ImageView img_invited, item_delete;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            img_invited = (ImageView) itemView.findViewById(R.id.img_invited);
            item_delete = (ImageView) itemView.findViewById(R.id.item_delete);
            item_invite = (LinearLayout) itemView.findViewById(R.id.item_invite);
        }
    }
}
