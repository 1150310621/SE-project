package com.hit.wegoal.entityclass;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 26049_000 on 2017/12/9.
 */

public class remind extends DataSupport{
    private String goalname;
    private Date remindtime;

    public remind() {
        super();
    }

    public remind(String goalname, Date remindtime) {
        this.goalname = goalname;
        this.remindtime = remindtime;
    }

    public String getGoalname() {
        return goalname;
    }

    public void setGoalname(String goalname) {
        this.goalname = goalname;
    }

    public Date getRemindtime() {
        return remindtime;
    }

    public void setRemindtime(Date remindtime) {
        this.remindtime = remindtime;
    }
}
