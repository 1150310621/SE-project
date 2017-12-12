package com.hit.wegoal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hit.wegoal.entityclass.remind;
import org.litepal.crud.DataSupport;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by 26049_000 on 2017/12/10.
 */

public class remindadapter extends RecyclerView.Adapter<remindadapter.ViewHolder> implements ItemTouchHelperAdapter{
    private List<remind> reminds;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(reminds,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }
    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        DataSupport.deleteAll(remind.class,"goalname = ?",reminds.get(position).getGoalname());
        reminds.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View goalview;
        TextView remindgoalname;
        TextView remindtime;
        public ViewHolder(View view){
            super(view);
            goalview=view;
            remindgoalname=(TextView)view.findViewById(R.id.remindgoalname);
            remindtime=(TextView)view.findViewById(R.id.remindtime);
        }
    }

    public remindadapter(List<remind> reminds){
        this.reminds=reminds;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final remind remindme=reminds.get(position);
        holder.remindgoalname.setText(remindme.getGoalname());
        holder.remindtime.setText(format.format(remindme.getRemindtime()));

    }

    @Override
    public int getItemCount() {
        return reminds.size();
    }
}