package com.vrlc92.liskmonitor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vrlc92.liskmonitor.MainActivity;
import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.adapters.DelegatesAdapter;
import com.vrlc92.liskmonitor.models.Delegate;
import com.vrlc92.liskmonitor.models.Settings;
import com.vrlc92.liskmonitor.services.LiskService;
import com.vrlc92.liskmonitor.services.RequestListener;
import com.vrlc92.liskmonitor.utils.Utils;

import java.util.List;

public class DelegatesFragment extends Fragment implements RequestListener<List<Delegate>>{

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public DelegatesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delegates, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.delegates_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        if (Utils.isOnline(getActivity())) {
            loadDelegates();
        } else {
            Utils.showMessage(getResources().getString(R.string.internet_off), view);
        }
    }

    @Override
    public void onDestroy() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.hideLoadingIndicatorView();
        }
        super.onDestroy();
    }

    private void loadDelegates() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showLoadingIndicatorView();
        }

        refreshContent();
    }

    @Override
    public void onFailure(final Exception e) {
        if (!isAdded()) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());

                mSwipeRefreshLayout.setRefreshing(false);

                MainActivity activity = (MainActivity)getActivity();
                if (activity != null) {
                    activity.hideLoadingIndicatorView();
                }
            }
        });
    }

    @Override
    public void onResponse(final List<Delegate> delegates) {
        if (!isAdded()) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = getView();

                RecyclerView rvDelegates = (RecyclerView) view.findViewById(R.id.rvDelegates);

                DelegatesAdapter adapter = new DelegatesAdapter(getContext(), delegates);
                rvDelegates.setAdapter(adapter);
                rvDelegates.setLayoutManager(new LinearLayoutManager(getActivity()));

                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.hideLoadingIndicatorView();
                }

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void refreshContent() {
        Settings settings = Utils.getSettings(getActivity());
        LiskService.getInstance().requestDelegates(settings, this);
    }

}
