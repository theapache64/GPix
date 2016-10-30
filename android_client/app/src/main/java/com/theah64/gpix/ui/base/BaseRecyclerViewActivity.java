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
    protected final List<T> emptyListForParsing = new ArrayList<>();
    private final List<T> fullDataList = new ArrayList<>();

    private BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, T> adapter;


    public List<T> getEmptyListForParsing() {
        emptyListForParsing.clear();
        return emptyListForParsing;
    }

    @Override
    protected void handleAPIResponse(APIResponse apiResponse) throws JSONException {


        setFullDataList(parseData(apiResponse));


        if (!fullDataList.isEmpty()) {
            //Rendering
            if (adapter == null) {
                adapter = getNewAdapter(fullDataList);
                getRecyclerView().setAdapter(adapter);
            } else {
                adapter.setData(fullDataList);
                adapter.notifyDataSetChanged();
            }

            onSuccessCall();

        } else {
            Log.e("x", "No contacts found!");
            onError(getErrorOnEmptyData());
        }
    }

    protected final List<T> getFullDataList() {
        return fullDataList;
    }

    public void setFullDataList(List<T> fullDataList) {
        this.fullDataList.clear();
        this.fullDataList.addAll(fullDataList);
    }

    protected abstract String getErrorOnEmptyData();

    protected abstract RecyclerView getRecyclerView();

    protected abstract BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, T> getNewAdapter(List<T> dataList);

    protected abstract List<T> parseData(APIResponse apiResponse) throws JSONException;

    public BaseRecyclerViewAdapter<? extends RecyclerView.ViewHolder, T> getAdapter() {
        return adapter;
    }
}
