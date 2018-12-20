package com.luwei.lwbaselib.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luwei.lwbaselib.R;
import com.luwei.lwbaselib.module.dialog.CustomDialog;
import com.luwei.lwbaselib.module.dialog.LoadingDialog;
import com.luwei.lwbaselib.module.dialog.ProgressDialog;
import com.luwei.ui.dialog.ConfirmListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * dialog 的使用示例
 */
public class DialogActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        List<String> listStr = new ArrayList<>();
        listStr.add("CustomDialog 顶部显示");
        listStr.add("CustomDialog 中部显示");
        listStr.add("CustomDialog 底部显示");
        listStr.add("LoadingDialog");
        listStr.add("ProgressDialog");
        adapter = new ArrayAdapter(this, listStr);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    Disposable disposable;

    private void onItemClick(int pos) {
        switch (pos) {
            case 0:
                showTopCustomDialog();
                break;
            case 1:
                showCenterCustomDialog();
                break;
            case 2:
                showBottomCustomDialog();
                break;
            case 3:
                showLoadingDialog();
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                    }
                }, 3000);
                break;
            case 4:
                if (disposable != null && !disposable.isDisposed()) {
                    return;
                }
                disposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (aLong > 10) {
                                    disposable.dispose();
                                    hideProgress();
                                } else {
                                    showProgress(Integer.parseInt(String.valueOf(aLong)), 10);
                                }
                            }
                        });
                break;
            default:
                break;
        }
    }

    private ProgressDialog progressDialog;

    private void showProgress(int progress, int maxProgress) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.newInstance();
        }
        progressDialog.setMaxProgress(maxProgress)
                .setProgress(progress)
                .setCanCancel(false)
                .showDialog(this);
    }

    private void hideProgress() {
        if (progressDialog != null
                && progressDialog.isVisible()) {
            progressDialog.dismiss();
        }
    }

    private LoadingDialog loadingDialog;

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.newInstance();
        }
        loadingDialog
                .setCanCancel(true)
                .showDialog(this);
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null
                && loadingDialog.isVisible()) {
            loadingDialog.dismiss();
        }
    }

    private void showTopCustomDialog() {
        CustomDialog.newInstance()
                .setContent("这是没有标题的对话框")
                .setCancel("取消")
                .setConfirm("确认")
                .setConfirmColor(Color.BLUE)
                .setCanCancel(true)
                .setTransparent(true)
                .setGravity(Gravity.TOP)
                .setConfirmLitener(new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        Toast.makeText(DialogActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .showDialog(DialogActivity.this);
    }

    private void showCenterCustomDialog() {
        CustomDialog.newInstance()
                .setTitle("我是标题")
                .setContent("这是有标题的对话框")
                .setCancel("取消")
                .setConfirm("确认")
                .setConfirmColor(Color.BLUE)
                .setCanCancel(false)
                .setTransparent(false)
                .setGravity(Gravity.CENTER)
                .setConfirmLitener(new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        Toast.makeText(DialogActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .showDialog(DialogActivity.this);
    }

    private void showBottomCustomDialog() {
        CustomDialog.newInstance()
                .setTitle("我是标题")
                .setContent("这是有标题的对话框")
                .setCancel("取消")
                .setConfirm("确认")
                .setConfirmColor(Color.BLUE)
                .setCanCancel(false)
                .setTransparent(true)
                .setGravity(Gravity.BOTTOM)
                .setConfirmLitener(new ConfirmListener() {
                    @Override
                    public void onClickConfirm() {
                        Toast.makeText(DialogActivity.this, "确认", Toast.LENGTH_SHORT).show();
                    }
                })
                .showDialog(DialogActivity.this);
    }

    public class ArrayAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Context context;
        private List<String> dataList;

        public ArrayAdapter(Context context, List<String> list) {
            this.context = context;
            this.dataList = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_array, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.text.setTag(position);
            holder.text.setText(dataList.get(position));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick((Integer) v.getTag());
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
