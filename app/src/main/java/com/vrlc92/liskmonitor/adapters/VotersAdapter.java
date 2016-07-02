package com.vrlc92.liskmonitor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Account;
import com.vrlc92.liskmonitor.models.Voters;
import com.vrlc92.liskmonitor.utils.Utils;

import java.util.List;

/**
 * Created by victorlins on 4/21/16.
 */
public class VotersAdapter extends
        RecyclerView.Adapter<VotersAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Account> mAccounts;

    public VotersAdapter(Context context, Voters voters) {
        mContext = context;
        mAccounts = voters.getAccounts();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View voterView = inflater.inflate(R.layout.voter_row, parent, false);

        // Return a new holder instance

        return new ViewHolder(voterView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView addressTextView = holder.addressTextView;
        TextView usernameTextView = holder.usernameTextView;
        TextView balanceTextView = holder.balanceTextView;

        Account account = mAccounts.get(position);
        addressTextView.setText(account.getAddress());

        if (Utils.validateUsername(account.getUsername())) {
            usernameTextView.setText(account.getUsername());
        } else {
            usernameTextView.setText(mContext.getString(R.string.undefined));
        }

        String balance = Utils.formatDecimal(account.getBalance());
        balanceTextView.setText(balance);
    }

    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView addressTextView;
        public final TextView usernameTextView;
        public final TextView balanceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            addressTextView = (TextView) itemView.findViewById(R.id.voter_address);
            usernameTextView = (TextView) itemView.findViewById(R.id.voter_username);
            balanceTextView = (TextView) itemView.findViewById(R.id.voter_balance);
        }
    }
}
