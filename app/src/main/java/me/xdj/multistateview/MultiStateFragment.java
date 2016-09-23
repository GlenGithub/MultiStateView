package me.xdj.multistateview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.xdj.view.MultiStateView;

/**
 * Created by xdj on 16/2/5.
 */
public class MultiStateFragment extends Fragment {

    private static final String TAG = MultiStateFragment.class.getSimpleName();

    public static final int OTHER_STATUS = 1111;

    private MultiStateView mMultiStateView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        mMultiStateView = (MultiStateView) view.findViewById(R.id.multi_state_view);
        mMultiStateView.addViewForStatus(OTHER_STATUS, R.layout.view_other_status);
//        mMultiStateView.addViewForStatus(BaseMultiStateView.STATE_LOADING, R.layout.msv_view_state_loading);
//        mMultiStateView.addViewForStatus(BaseMultiStateView.STATE_FAIL, R.layout.msv_view_state_fail);
//        mMultiStateView.addViewForStatus(BaseMultiStateView.STATE_EMPTY, R.layout.msv_view_state_empty);

        mMultiStateView.getView(MultiStateView.STATE_FAIL).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.STATE_LOADING);
                        mMultiStateView.setViewState(MultiStateView.STATE_EMPTY);
                    }
                });
        mMultiStateView.getView(MultiStateView.STATE_EMPTY).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.STATE_LOADING);
                        mMultiStateView.setViewState(MultiStateView.STATE_CONTENT);
                    }
                });
        mMultiStateView.getView(MultiStateView.STATE_CONTENT)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.STATE_LOADING);
                        mMultiStateView.setViewState(OTHER_STATUS);
                    }
                });

        mMultiStateView.setViewState(MultiStateView.STATE_LOADING);
        mMultiStateView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.STATE_FAIL);
            }
        }, 2000);

        return view;
    }

    public static MultiStateFragment newInstance() {
        return new MultiStateFragment();
    }

    public void refresh() {
        mMultiStateView.setViewState(MultiStateView.STATE_LOADING);
        mMultiStateView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.STATE_FAIL);
            }
        }, 2000);
    }
}
