package com.hit.wegoal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import com.hit.wegoal.entityclass.remind;
import org.litepal.crud.DataSupport;
import java.util.List;

public class remindpage extends AppCompatActivity {
    private Toolbar remindtoolbar;
    private RecyclerView allremind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showremind);
        remindtoolbar=(Toolbar)findViewById(R.id.remindtoolbar);
        allremind=(RecyclerView)findViewById(R.id.remind);

        remindtoolbar.setTitle("你的提醒");
        setSupportActionBar(remindtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("goalpage","enter");
        remindtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<remind>reminds= DataSupport.findAll(remind.class);
        remindadapter remindadapt=new remindadapter(reminds);
        allremind.setAdapter(remindadapt);
        LinearLayoutManager layoutManager=new LinearLayoutManager(remindpage.this);
        allremind.setLayoutManager(layoutManager);
        allremind.setHasFixedSize(true);
        allremind.addItemDecoration(new SpacesItemDecoration(20));
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter) remindadapt);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(allremind);
    }
}
