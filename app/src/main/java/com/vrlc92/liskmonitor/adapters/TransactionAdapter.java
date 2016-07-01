package com.vrlc92.liskmonitor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private Context mContext;
    private List<Transaction> mTransactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        mContext = context;
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
        TextView transactionTimeAgoTextView = holder.transactionTimeTextView;
        TextView transactionSenderIdTextView = holder.transactionSenderIdTextView;
        TextView transactionRecipientIdTextView = holder.transactionRecipientIdTextView;
        TextView transactionFeeTextView = holder.transactionFeeTextView;
        TextView transactionConfirmationsTextView = holder.transactionConfirmationsTextView;
        TextView transactionAmountTextView = holder.transactionAmountTextView;

        Transaction transaction = mTransactions.get(position);

        switch (transaction.getType()){
            case 1:
                transactionRecipientIdTextView.setText(mContext.getString(R.string.second_signature_creation));
                break;
            case 2:
                transactionRecipientIdTextView.setText(mContext.getString(R.string.delegate_registration));
                break;
            case 3:
                transactionRecipientIdTextView.setText(mContext.getString(R.string.delegate_vote));
                break;
            default:
                transactionRecipientIdTextView.setText(transaction.getRecipientId());
                break;
        }

        transactionIdTextView.setText(transaction.getId());
        transactionTimeAgoTextView.setText(Utils.getTimeAgo(transaction.getTimestamp()));
        transactionSenderIdTextView.setText(transaction.getSenderId());
        transactionFeeTextView.setText(String.valueOf(transaction.getFee() * Math.pow(10, -8)));
        transactionConfirmationsTextView.setText(String.valueOf(transaction.getConfirmations()));
        transactionAmountTextView.setText(Utils.formatDecimal(transaction.getAmount()));
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView transactionIdTextView;
        public TextView transactionTimeTextView;
        public TextView transactionSenderIdTextView;
        public TextView transactionRecipientIdTextView;
        public TextView transactionConfirmationsTextView;
        public TextView transactionFeeTextView;
        public TextView transactionAmountTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            transactionIdTextView = (TextView) itemView.findViewById(R.id.transaction_id);
            transactionTimeTextView = (TextView) itemView.findViewById(R.id.transaction_time);
            transactionSenderIdTextView = (TextView) itemView.findViewById(R.id.transaction_sender_id);
            transactionRecipientIdTextView = (TextView) itemView.findViewById(R.id.transaction_recipient_id);
            transactionConfirmationsTextView = (TextView) itemView.findViewById(R.id.transaction_confirmations);
            transactionFeeTextView = (TextView) itemView.findViewById(R.id.transaction_fee);
            transactionAmountTextView = (TextView) itemView.findViewById(R.id.transaction_amount);
        }
    }
}
