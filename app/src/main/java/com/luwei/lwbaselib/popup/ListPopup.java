package com.luwei.lwbaselib.popup;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luwei.lwbaselib.R;
import com.luwei.ui.popup.BasePopup;

/**
 * Created by LiCheng
 * Date：2018/12/10
 */
public class ListPopup extends BasePopup<ListPopup> {

    public static ListPopup newInstance(){
        return new ListPopup();
    }

    public static ListPopup newInstance(Context context){
        return new ListPopup(context);
    }

    public ListPopup() {
    }
    public ListPopup(Context context) {
        setContext(context);
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_list);
    }

    @Override
    protected void initViews(View view, ListPopup popup) {
        RecyclerView recyclerView = view.findViewById(R.id.rlv_popup_list);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        PopupListAdapter adapter = new PopupListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
