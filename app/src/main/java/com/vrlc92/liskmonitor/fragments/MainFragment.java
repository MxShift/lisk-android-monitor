package com.vrlc92.liskmonitor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrlc92.liskmonitor.MainActivity;
import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Account;
import com.vrlc92.liskmonitor.models.Block;
import com.vrlc92.liskmonitor.models.Delegate;
import com.vrlc92.liskmonitor.models.Forging;
import com.vrlc92.liskmonitor.models.PeerVersion;
import com.vrlc92.liskmonitor.models.Settings;
import com.vrlc92.liskmonitor.models.Status;
import com.vrlc92.liskmonitor.services.LiskService;
import com.vrlc92.liskmonitor.services.RequestListener;
import com.vrlc92.liskmonitor.utils.Utils;

public class MainFragment extends Fragment {

    private TextView usernameTextview;
    private TextView addressTextview;
    private TextView balanceTextview;
    private TextView rankTextview;
    private TextView productivityTextview;
    private TextView feesTextview;
    private TextView rewardsTextview;
    private TextView forgedTextview;
    private TextView versionTextview;
    private TextView blocksTextview;
    private TextView heightTextview;
    private TextView lastBlockForgedTextView;
    private TextView delegateApprovalTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameTextview = (TextView)view.findViewById(R.id.account_username);
        addressTextview = (TextView)view.findViewById(R.id.account_address);
        balanceTextview = (TextView)view.findViewById(R.id.account_balance);
        rankTextview = (TextView)view.findViewById(R.id.delegate_rank);
        productivityTextview = (TextView)view.findViewById(R.id.delegate_productivity);
        feesTextview = (TextView)view.findViewById(R.id.forgin_fees);
        rewardsTextview = (TextView)view.findViewById(R.id.forging_rewards);
        forgedTextview = (TextView)view.findViewById(R.id.forgin_forged);
        versionTextview = (TextView)view.findViewById(R.id.peer_version);
        blocksTextview = (TextView)view.findViewById(R.id.sync_blocks);
        heightTextview = (TextView)view.findViewById(R.id.sync_height);
        lastBlockForgedTextView = (TextView)view.findViewById(R.id.delegate_last_block_forged);
        delegateApprovalTextView = (TextView)view.findViewById(R.id.delegate_approval);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRequests();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Utils.isOnline(getActivity())) {
            showLoadingIndicatorView();
            loadRequests();
        } else {
            Utils.showMessage(getResources().getString(R.string.internet_off), getView());
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

    private void loadRequests(){
        loadDelegate();
        loadPeerVersion();
        loadStatus();
        loadLastForgedBlock();
    }

    private void loadLastForgedBlock() {
        Settings settings = Utils.getSettings(getActivity());

        LiskService.getInstance().requestLastBlockForged(settings, new RequestListener<Block>() {
            @Override
            public void onFailure(Exception e) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());
                    }
                });
            }

            @Override
            public void onResponse(final Block block) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        if (block != null && block.getTimestamp() > 0) {
                            lastBlockForgedTextView.setText(Utils.getTimeAgo(block.getTimestamp()));
                        } else {
                            lastBlockForgedTextView.setText(R.string.not_forging);
                        }
                    }
                });
            }
        });
    }

    private void loadDelegate(){
        Settings settings = Utils.getSettings(getActivity());

        if (Utils.validateUsername(settings.getUsername())){
            usernameTextview.setText(settings.getUsername());
        }

        LiskService.getInstance().requestDelegate(settings, new RequestListener<Delegate>() {
            @Override
            public void onFailure(Exception e) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());
                    }
                });
            }

            @Override
            public void onResponse(final Delegate delegate) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);
                        rankTextview.setText(String.valueOf(delegate.getRate()));
                        String productivity = delegate.getProductivity() + getString(R.string.percent_symbol);
                        productivityTextview.setText(productivity);

                        String approval = delegate.getApproval() + getString(R.string.percent_symbol);
                        delegateApprovalTextView.setText(approval);

                        loadForging();
                        loadAccount();
                    }
                });
            }
        });
    }

    private void loadPeerVersion(){
        Settings settings = Utils.getSettings(getActivity());

        LiskService.getInstance().requestPeerVersion(settings, new RequestListener<PeerVersion>() {
            @Override
            public void onFailure(Exception e) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());
                    }
                });
            }

            @Override
            public void onResponse(final PeerVersion peerVersion) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        versionTextview.setText(peerVersion.getVersion());
                    }
                });
            }
        });
    }

    private void loadStatus(){
        Settings settings = Utils.getSettings(getActivity());

        LiskService.getInstance().requestStatus(settings, new RequestListener<Status>() {
            @Override
            public void onFailure(Exception e) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());
                    }
                });
            }

            @Override
            public void onResponse(final Status status) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        heightTextview.setText(String.valueOf(status.getHeight()));
                        blocksTextview.setText(String.valueOf(status.getBlocks()));
                    }
                });
            }
        });
    }

    private void loadForging(){
        Settings settings = Utils.getSettings(getActivity());

        LiskService.getInstance().requestForging(settings, new RequestListener<Forging>() {
            @Override
            public void onFailure(Exception e) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());
                    }
                });
            }

            @Override
            public void onResponse(final Forging forging) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        String fees = Utils.formatDecimal(forging.getFees());
                        String rewards = Utils.formatDecimal(forging.getRewards());
                        String forged = Utils.formatDecimal(forging.getForged());

                        feesTextview.setText(fees);
                        rewardsTextview.setText(rewards);
                        forgedTextview.setText(forged);
                    }
                });
            }
        });
    }

    private void loadAccount (){
        final Settings settings = Utils.getSettings(getActivity());

        LiskService.getInstance().requestAccount(settings, new RequestListener<Account>() {
            @Override
            public void onFailure(Exception e) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        Utils.showMessage(getString(R.string.unable_to_retrieve_data), getView());
                    }
                });
            }

            @Override
            public void onResponse(final Account account) {
                if (!isAdded()) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingIndicatorView();
                        mSwipeRefreshLayout.setRefreshing(false);

                        String balance = Utils.formatDecimal(account.getBalance());

                        addressTextview.setText(account.getAddress());
                        balanceTextview.setText(balance);
                    }
                });
            }
        });
    }

    private void showLoadingIndicatorView() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showLoadingIndicatorView();
        }
    }

    private void hideLoadingIndicatorView() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.hideLoadingIndicatorView();
        }
    }
}
