package com.hit.wegoal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hit.wegoal.entityclass.subgoal;

import org.litepal.crud.DataSupport;

import java.util.List;

public class subgoalpage extends AppCompatActivity {
    private String goalname;
    private RecyclerView subgoal;
    private Toolbar subgoaltoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgoal);
        Intent intent3=getIntent();
        goalname=intent3.getStringExtra("goalname");
        subgoal=(RecyclerView)findViewById(R.id.subgoal);
        List<subgoal> allsubgoal= DataSupport.where("goalname=?",goalname).order("deadline asc").find(subgoal.class);
        subgoaladapter subgoaladapter=new subgoaladapter(allsubgoal);

        subgoal.setAdapter(subgoaladapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(subgoalpage.this);
        subgoal.setLayoutManager(layoutManager);
        subgoal.setHasFixedSize(true);
        subgoal.addItemDecoration(new SpacesItemDecoration(20));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) subgoaladapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(subgoal);

        subgoaltoolbar=(Toolbar)findViewById(R.id.subgoaltoolbar);
        subgoaltoolbar.setTitle("目标："+goalname);
        setSupportActionBar(subgoaltoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subgoaltoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subgoaltoolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.share:
                //这里添加子目标进度分享的代码
                break;
            case R.id.more:
                break;
            default:
        }
        return true;
    }
}
