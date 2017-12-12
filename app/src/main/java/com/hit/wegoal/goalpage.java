package com.hit.wegoal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import com.hit.wegoal.entityclass.goal;
import com.hit.wegoal.entityclass.remind;
import com.hit.wegoal.entityclass.subgoal;
import org.feezu.liuli.timeselector.TimeSelector;
import org.litepal.crud.DataSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class goalpage extends AppCompatActivity{
    private FloatingActionButton addgoal;
    private RecyclerView mygoal;
    private EditText yougoalname;
    private DatePicker thedeadline;
    private EditText subgoalname;
    private EditText subgoalnum;
    private DatePicker subgoaldeadline;
<<<<<<< HEAD
    private home_lancher lancher_page;
=======
    private Toolbar goaltoolbar;
>>>>>>> 356e004cd7f36cfa3e4eabe5152af4a631ecf9fe
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
                Intent intent2=new Intent(goalpage.this,subgoalpage.class);
                intent2.putExtra("goalname",goalname);
                startActivity(intent2);
            }
        });
        goaladapter.addremindSetOnclick(new goaladapter.remindInterfance(){

            @Override
            public void onclick(final String goalname) {
                //这里编写添加提醒的界面
                final SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date curDate =  new Date(System.currentTimeMillis());
                String now=formatter.format(curDate);
                TimeSelector timeSelector = new TimeSelector(goalpage.this, new TimeSelector.ResultHandler() {
                        @Override
                    public void handle(String time) {
                            Date date = null;
                            try {
                                date = formatter.parse(time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            remind a=new remind();
                            a.setRemindtime(date);
                            a.setGoalname(goalname);
                            a.save();
                            AlarmManager alarmManager=alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(goalpage.this, alarmreceiver.class);
                            intent.putExtra("goalname",goalname);
                            int num=DataSupport.findAll(remind.class).size();
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(goalpage.this,num, intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,date.getTime(), pendingIntent);
                        }
                },now, "2999-12-31 23:59:59");
                timeSelector.show();
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
                        if(isNumeric(subgoalnum.getText().toString())&&!subgoalnum.getText().toString().equals(""))
                        {
                            input.setTotalnum(Integer.parseInt(subgoalnum.getText().toString()));
                            input.setDeadline(new Date(subgoaldeadline.getYear() - 1900, subgoaldeadline.getMonth(), subgoaldeadline.getDayOfMonth()));
                            input.save();
                            dialog.dismiss();
                        }
                    }
                });
                Dialog.show();
            }
        });

        mygoal.setAdapter(goaladapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(goalpage.this);
        mygoal.setLayoutManager(layoutManager);
        mygoal.setHasFixedSize(true);
        mygoal.addItemDecoration(new SpacesItemDecoration(18));

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

        goaltoolbar=(Toolbar)findViewById(R.id.goaltoolbar);
        goaltoolbar.setTitle("welcome to wegoal");
        setSupportActionBar(goaltoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.goaltoolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.remind:
                Intent intent=new Intent(goalpage.this,remindpage.class);
                startActivity(intent);
                break;
            case R.id.more:
                break;
            default:
        }
        return true;
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
