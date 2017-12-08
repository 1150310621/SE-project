package com.hit.wegoal;

/**
 * Created by 26049_000 on 2017/12/7.
 */

public interface ItemTouchHelperAdapter {
        //数据交换
        void onItemMove(int fromPosition,int toPosition);
        //数据删除
        void onItemDissmiss(int position);
}
