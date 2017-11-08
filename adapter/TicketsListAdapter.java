package br.com.smartside.mybiorun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.smartside.mybiorun.R;
import br.com.smartside.mybiorun.model.EventsResultVO;
import br.com.smartside.mybiorun.model.Tickets;
import br.com.smartside.mybiorun.utils.Intents;
import br.com.smartside.mybiorun.utils.Utils;

/**
 * Created by smartside on 02/06/17.
 */

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.GroupsItemInvitedsViewHolder> {

    Context mContext;
    ArrayList<Tickets> item;

    public TicketsListAdapter(Context context, ArrayList<Tickets> item){
        this.item = item;
        mContext = context;
    }

    @Override
    public GroupsItemInvitedsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.tickets_list_item, viewGroup, false);
        return new GroupsItemInvitedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupsItemInvitedsViewHolder holder, final int position) {

        holder.ticket_name.setText(item.get(position).getName());
        String price = "R$ "+item.get(position).getPrice().replace(".",",")+"0";
        holder.ticket_price.setText(price);
        holder.ticke_end_date.setText("Final das incrições: "+Utils.convertDate2(item.get(position).getEnd()));

        holder.ticket_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(Intents.goToPaymentTicket(mContext, item.get(position).getName(), item.get(position).getPrice(), item.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class GroupsItemInvitedsViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout ticket_item;
        TextView ticket_name, ticket_price, ticke_end_date;
        ImageView ticket_arrow;

        public GroupsItemInvitedsViewHolder(View itemView) {
            super(itemView);

            ticket_name = (TextView) itemView.findViewById(R.id.ticket_name);
            ticket_price = (TextView) itemView.findViewById(R.id.ticket_price);
            ticke_end_date = (TextView) itemView.findViewById(R.id.ticke_end_date);
            ticket_arrow = (ImageView) itemView.findViewById(R.id.ticket_arrow);

            ticket_item = (RelativeLayout) itemView.findViewById(R.id.ticket_item);
        }
    }
}
