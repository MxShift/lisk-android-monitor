package com.vrlc92.liskmonitor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Transaction;
import com.vrlc92.liskmonitor.utils.Utils;

import java.util.List;

/**
 * Created by victorlins on 4/18/16.
 */
public class TransactionAdapter extends
        RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> mTransactions;

    public TransactionAdapter(List<Transaction> transactions) {
        mTransactions = transactions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View transactionView = inflater.inflate(R.layout.transaction_row, parent, false);

        return new ViewHolder(transactionView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView transactionIdTextView = holder.transactionIdTextView;
        TextView transactionAmmoutTextView = holder.transactionAmmoutTextView;

        Transaction transaction = mTransactions.get(position);
        transactionIdTextView.setText(transaction.getId());
        transactionAmmoutTextView.setText(Utils.formatDecimal(transaction.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView transactionIdTextView;
        public TextView transactionAmmoutTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            transactionIdTextView = (TextView) itemView.findViewById(R.id.transaction_id);
            transactionAmmoutTextView = (TextView) itemView.findViewById(R.id.transaction_ammount);
        }
    }
}
