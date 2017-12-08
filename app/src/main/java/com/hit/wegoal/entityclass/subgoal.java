package com.hit.wegoal.entityclass;

import org.litepal.crud.DataSupport;
import java.util.Date;

/**
 * Created by 26049_000 on 2017/12/1.
 */

public class subgoal extends DataSupport {
    private String goalname;
    private String subgoal;
    private Date deadline;
    private int totalnum;
    private int actualnum;

    public subgoal() {
        super();
    }

    public subgoal(String goalname, String subgoal, Date deadline, int totalnum, int actualnum) {
        this.goalname = goalname;
        this.subgoal = subgoal;
        this.deadline = deadline;
        this.totalnum = totalnum;
        this.actualnum = actualnum;
    }

    public String getGoalname() {
        return goalname;
    }

    public void setGoalname(String goalname) {
        this.goalname = goalname;
    }

    public String getSubgoal() {
        return subgoal;
    }

    public void setSubgoal(String subgoal) {
        this.subgoal = subgoal;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public int getActualnum() {
        return actualnum;
    }

    public void setActualnum(int actualnum) {
        this.actualnum = actualnum;
    }
}
