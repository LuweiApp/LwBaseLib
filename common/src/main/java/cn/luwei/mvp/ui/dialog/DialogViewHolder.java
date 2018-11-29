package cn.luwei.mvp.ui.dialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by runla on 2018/8/1.
 * 文件描述：
 */

public class DialogViewHolder {
    private SparseArray<View> views;
    private View convertView;

    private DialogViewHolder(View view) {
        this.convertView = view;
        this.views = new SparseArray();
    }

    public static DialogViewHolder create(View view) {
        return new DialogViewHolder(view);
    }

    public <T extends View> T getView(int viewId) {
        View view = (View)this.views.get(viewId);
        if(view == null) {
            view = this.convertView.findViewById(viewId);
            this.views.put(viewId, view);
        }

        return (T) view;
    }

    public View getConvertView() {
        return this.convertView;
    }

    public void setVisibility(int viewId,boolean isShow){
        View view = this.getView(viewId);
        view.setVisibility(isShow?View.VISIBLE:View.GONE);
    }
    public void setText(int viewId, String text) {
        TextView textView = (TextView)this.getView(viewId);
        textView.setText(text);
    }

    public void setText(int viewId, int textId) {
        TextView textView = (TextView)this.getView(viewId);
        textView.setText(textId);
    }

    public void setTextColor(int viewId, int colorId) {
        TextView textView = (TextView)this.getView(viewId);
        textView.setTextColor(colorId);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = this.getView(viewId);
        view.setOnClickListener(clickListener);
    }

    public void setBackgroundResource(int viewId, int resId) {
        View view = this.getView(viewId);
        view.setBackgroundResource(resId);
    }

    public void setBackgroundColor(int viewId, int colorId) {
        View view = this.getView(viewId);
        view.setBackgroundColor(colorId);
    }
}
