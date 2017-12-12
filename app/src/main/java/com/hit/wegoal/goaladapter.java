package com.hit.wegoal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.hit.wegoal.entityclass.goal;
import com.hit.wegoal.entityclass.remind;
import com.hit.wegoal.entityclass.subgoal;
import org.litepal.crud.DataSupport;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by 26049_000 on 2017/12/2.
 */

public class goaladapter extends RecyclerView.Adapter<goaladapter.ViewHolder> implements ItemTouchHelperAdapter{
    private List<goal> mygoal;
    private addInterface addsubgoal;   //点击添加按钮的添加子目标接口
    private remindInterfance addremindInterfance;   //添加提醒接口
    private OnItemClickListener mOnItemClickListener;   //点击目标框的展示子目标接口
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(mygoal,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }
    @Override
    public void onItemDissmiss(int position) {
        //移除数据
        DataSupport.deleteAll(remind.class,"goalname = ?",mygoal.get(position).getGoalname());
        DataSupport.deleteAll(goal.class,"goalname = ?",mygoal.get(position).getGoalname());
        DataSupport.deleteAll(subgoal.class,"goalname = ?",mygoal.get(position).getGoalname());
        mygoal.remove(position);
        notifyItemRemoved(position);
    }

    public void addremindSetOnclick(remindInterfance addremindInterfance){
        this.addremindInterfance=addremindInterfance;
    }
    public void addSetOnclick(addInterface addsubgoal){
        this.addsubgoal=addsubgoal;
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(String goalname);
    }

    public interface addInterface{
        void onclick(String goalname);
    }
    public interface remindInterfance{
        void onclick(String goalname);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View goalview;
        TextView goalname;
        TextView deadline;
        ImageButton addsubgoal;
        ImageButton addremind;
        public ViewHolder(View view){
            super(view);
            goalview=view;
            goalname=(TextView)view.findViewById(R.id.goalname);
            deadline=(TextView)view.findViewById(R.id.deadline);
            addsubgoal=(ImageButton)view.findViewById(R.id.addsubgoal);
            addremind=(ImageButton)view.findViewById(R.id.addremind);
        }
    }

    public goaladapter(List<goal> goals){
        this.mygoal=goals;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final goal agoal=mygoal.get(position);
        holder.goalname.setText(agoal.getGoalname());
        holder.deadline.setText(format.format(agoal.getDeadline()));
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(agoal.getGoalname());
                }
            });
        }
        holder.addremind.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(addremindInterfance!=null) {
                    addremindInterfance.onclick(agoal.getGoalname());
                }
            }
        });
        holder.addsubgoal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(addsubgoal!=null) {
                    addsubgoal.onclick(agoal.getGoalname());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mygoal.size();
    }
}
