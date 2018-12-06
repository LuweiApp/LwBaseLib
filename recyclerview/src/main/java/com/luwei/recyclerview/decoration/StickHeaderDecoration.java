package com.luwei.recyclerview.decoration;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Mr_Zeng
 *
 * 粘性头部
 *
 * @date 2018/10/15
 */
public class StickHeaderDecoration extends RecyclerView.ItemDecoration {

    //存储粘性头部的ViewHolder实例，复用机制减少性能损耗
    private SparseArray<RecyclerView.ViewHolder> mViewMap = new SparseArray<>();
    //StickHeader的提供者
    private StickProvider mProvider;

    public StickHeaderDecoration(StickProvider provider) {
        mProvider = provider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null || !(adapter instanceof StickProvider)) {
            return;
        }
        int itemCount = adapter.getItemCount();
        if (itemCount == 1) {
            return;
        }
        View firstItem = parent.getChildAt(0);
        if (firstItem == null) {
            return;
        }
        int firstItemPos = parent.getChildAdapterPosition(firstItem);
        //从第一个Child往前找，找到当前的StickHeader对应的position
        int currStickPos = currStickPos(firstItemPos);
        if (currStickPos == -1) {
            return;
        }
        c.save();
        int currStickType = adapter.getItemViewType(currStickPos);
        //当前显示的StickHeader相应的ViewHolder，先看有没有缓存
        RecyclerView.ViewHolder currHolder = mViewMap.get(currStickType);
        if (currHolder == null) {
            //没有缓存则新生成
            currHolder = adapter.createViewHolder(parent, currStickType);
            //主动测量并布局
            measure(currHolder.itemView, parent);
            mViewMap.put(currStickType, currHolder);
        }
        //寻找下一个StickHeader
        RecyclerView.ViewHolder nextStickHolder = nextStickHolder(parent);
        if (nextStickHolder != null) {
            int nextStickTop = nextStickHolder.itemView.getTop();
            //下一个StickHeader如果顶部碰到了当前StickHeader的屁股，那么将当前的向上推
            if (nextStickTop < currHolder.itemView.getHeight() && nextStickTop > 0) {
                c.translate(0, nextStickTop - currHolder.itemView.getHeight());
            }
        }
        adapter.bindViewHolder(currHolder, currStickPos);
        currHolder.itemView.draw(c);
        c.restore();
    }

    /**
     * 从开始位置向前查找相应的StickHeader的Adapter position
     * @param start 起始位置
     */
    private int currStickPos(int start) {
        for (int i = start; i >= 0; i--) {
            if (mProvider.isStick(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 下一个StickHeader的ViewHolder
     */
    private RecyclerView.ViewHolder nextStickHolder(RecyclerView parent) {
        int childCount = parent.getChildCount();
        if (childCount == 1) {
            return null;
        }
        for (int i = 1; i < childCount; i++) {
            //从RecyclerView第二个Child开始找
            View child = parent.getChildAt(i);
            int childPos = parent.getChildAdapterPosition(child);
            if (mProvider.isStick(childPos)) {
                return parent.getChildViewHolder(child);
            }
        }
        return null;
    }

    /**
     * 手动Measure并Layout
     */
    public void measure(View header, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) header.getLayoutParams();
        int heightSpec;
        int widthSpec;
        //测量高
        if (params.height == RecyclerView.LayoutParams.MATCH_PARENT) {
            int height = parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom();
            heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        } else if (params.height == RecyclerView.LayoutParams.WRAP_CONTENT) {
            heightSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        } else {
            int height = params.height;
            heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        }
        //测量宽
        if (params.width == RecyclerView.LayoutParams.MATCH_PARENT) {
            int width = parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight();
            widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        } else if (params.width == RecyclerView.LayoutParams.WRAP_CONTENT) {
            widthSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        } else {
            int width = params.width;
            widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        }
        header.measure(widthSpec, heightSpec);
        header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
    }

    public interface StickProvider {
        boolean isStick(int position);
    }

}
