package com.hit.wegoal.entityclass;

import org.litepal.crud.DataSupport;
import java.util.Date;

/**
 * Created by 26049_000 on 2017/12/1.
 */

public class goal extends DataSupport {
    private String goalname;
    private Date deadline;

    public goal(String goalname, Date deadline) {
        this.goalname = goalname;
        this.deadline = deadline;
    }

    public goal() {
        super();
    }

    public String getGoalname() {
        return goalname;
    }

    public void setGoalname(String goalname) {
        this.goalname = goalname;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
