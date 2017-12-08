package com.hit.wegoal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.wegoal.entityclass.subgoal;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by 26049_000 on 2017/12/8.
 */

public class subgoaladapter extends RecyclerView.Adapter<subgoaladapter.ViewHolder> implements ItemTouchHelperAdapter{
    private List<subgoal> mysubgoal;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    static class ViewHolder extends RecyclerView.ViewHolder{
        View subgoalview;
        TextView showsubgoalname;
        TextView showdeadline;
        SeekBar schedule;
        TextView scheduleinnum;
        public ViewHolder(View view){
            super(view);
            subgoalview=view;
            showsubgoalname=(TextView)view.findViewById(R.id.showsubgoalname);
            showdeadline=(TextView)view.findViewById(R.id.showdeadline);
            schedule=(SeekBar)view.findViewById(R.id.schedule);
            scheduleinnum=(TextView)view.findViewById(R.id.scheduleinnum);
        }
    }

    public subgoaladapter(List<subgoal> mysubgoal){
        this.mysubgoal=mysubgoal;
    }

    @Override
    public subgoaladapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subgoal_item,parent,false);
        subgoaladapter.ViewHolder holder=new subgoaladapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final subgoaladapter.ViewHolder holder, int position) {
        final subgoal subgoal=mysubgoal.get(position);
        holder.showsubgoalname.setText(subgoal.getSubgoal());
        holder.showdeadline.setText(format.format(subgoal.getDeadline()));
        holder.schedule.setMax(subgoal.getTotalnum());
        holder.schedule.setProgress(subgoal.getActualnum());
        holder.scheduleinnum.setText(subgoal.getActualnum()+"/"+subgoal.getTotalnum());
        holder.schedule.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.scheduleinnum.setText(progress+"/"+subgoal.getTotalnum());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                holder.scheduleinnum.setText(seekBar.getProgress()+"/"+subgoal.getTotalnum());
                subgoal.setActualnum(seekBar.getProgress());
                subgoal.save();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mysubgoal.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mysubgoal,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        //这里还需要添加数据库删除操作
        DataSupport.deleteAll(subgoal.class,"subgoal = ?",mysubgoal.get(position).getSubgoal());
        mysubgoal.remove(position);
        notifyItemRemoved(position);
    }
}
