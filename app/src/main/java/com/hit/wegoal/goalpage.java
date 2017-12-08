package com.hit.wegoal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.hit.wegoal.entityclass.goal;
import com.hit.wegoal.entityclass.subgoal;

import org.litepal.crud.DataSupport;
import java.util.Date;
import java.util.List;

public class goalpage extends AppCompatActivity {
    private FloatingActionButton addgoal;
    private RecyclerView mygoal;
    private EditText yougoalname;
    private DatePicker thedeadline;
    private EditText subgoalname;
    private EditText subgoalnum;
    private DatePicker subgoaldeadline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        addgoal=(FloatingActionButton)findViewById(R.id.addgoal);
        mygoal=(RecyclerView)findViewById(R.id.mygoal);

        List<goal> goals= DataSupport.order("deadline asc").find(goal.class);
        final goaladapter goaladapter=new goaladapter(goals);

        goaladapter.setOnItemClickListener(new goaladapter.OnItemClickListener(){
            @Override
            public void onItemClick(String goalname) {
                //这里跳转到对应子目标页面
                Toast.makeText(goalpage.this,"展示子目标"+goalname,Toast.LENGTH_SHORT).show();


            }
        });
        goaladapter.addSetOnclick(new goaladapter.addInterface(){
            @Override
            public void onclick(final String goalname) {
                //这里编写添加子目标的界面
                final AlertDialog.Builder Dialog = new AlertDialog.Builder(goalpage.this);
                final View dialogView = LayoutInflater.from(goalpage.this).inflate(R.layout.inputsubgoal,null);
                Dialog.setTitle("add subgoal");
                Dialog.setIcon(R.mipmap.ic_launcher);
                Dialog.setView(dialogView);
                Dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subgoalname = (EditText) dialogView.findViewById(R.id.subgoalname);
                        subgoalnum=(EditText) dialogView.findViewById(R.id.subgoalnum);
                        subgoaldeadline=(DatePicker) dialogView.findViewById(R.id.subgoaldeadline);
                        subgoal input=new subgoal();
                        input.setGoalname(goalname);
                        input.setSubgoal(subgoalname.getText().toString());
                        input.setActualnum(0);
                        Log.d("goalpage","0");
                        input.setTotalnum(Integer.parseInt(subgoalnum.getText().toString()));
                        Log.d("goalpage","1");
                        input.setDeadline(new Date(subgoaldeadline.getYear()-1900,subgoaldeadline.getMonth(),subgoaldeadline.getDayOfMonth()));
                        Log.d("goalpage","2");
                        input.save();
                        Log.d("goalpage","3");
                        Log.d("goalpage","inputsuccess");
                        dialog.dismiss();
                    }
                });
                Dialog.show();
            }
        });

        mygoal.setAdapter(goaladapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(goalpage.this);
        mygoal.setLayoutManager(layoutManager);
        mygoal.setHasFixedSize(true);
        mygoal.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mygoal.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) goaladapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mygoal);

        addgoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //添加目标
                final AlertDialog.Builder Dialog = new AlertDialog.Builder(goalpage.this);
                final View dialogView = LayoutInflater.from(goalpage.this).inflate(R.layout.inputgoal,null);
                Dialog.setTitle("add goal");
                Dialog.setIcon(R.mipmap.ic_launcher);
                Dialog.setView(dialogView);
                Dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yougoalname = (EditText) dialogView.findViewById(R.id.yougoalname);
                                thedeadline =(DatePicker)dialogView.findViewById(R.id.thedeadline);
                                goal input=new goal();
                                input.setGoalname(yougoalname.getText().toString());
                                input.setDeadline(new Date(thedeadline.getYear()-1900,thedeadline.getMonth(),thedeadline.getDayOfMonth()));
                                input.save();
                                dialog.dismiss();
                                finish();
                                Intent intent=new Intent(goalpage.this,goalpage.class);
                                startActivity(intent);
                            }
                        });
                Dialog.show();
            }
        });
    }
}
