package com.theah64.gpix.ui.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.theah64.gpix.adapters.BaseRecyclerViewAdapter;
import com.theah64.gpix.util.APIResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shifar on 23/7/16.
 */
public abstract class BaseRecyclerViewActivity<T> extends BaseRefreshableActivity {

    //JSON keys
    protected static final String KEY_ID = "id";
    private final List<T> dataList = new ArrayList<>();

    private BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, T> adapter;

    @Override
    protected void handleAPIResponse(APIResponse apiResponse) throws JSONException {


        setDataList(parseData(apiResponse));


        if (!dataList.isEmpty()) {
            //Rendering
            if (adapter == null) {
                adapter = getNewAdapter(dataList);
                getRecyclerView().setAdapter(adapter);
            } else {
                adapter.setData(dataList);
                adapter.notifyDataSetChanged();
            }

            onSuccessCall();

        } else {
            Log.e("x", "No contacts found!");
            onError(getErrorOnEmptyData());
        }
    }

    protected final List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        
        if (!this.dataList.isEmpty()) {
            this.dataList.clear();
        }

        this.dataList.addAll(dataList);
    }

    protected abstract String getErrorOnEmptyData();

    protected abstract RecyclerView getRecyclerView();

    protected abstract BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, T> getNewAdapter(List<T> dataList);

    protected abstract List<T> parseData(APIResponse apiResponse) throws JSONException;

    public BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, T> getAdapter() {
        return adapter;
    }
}
