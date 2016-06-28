package com.vrlc92.liskmonitor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Delegate;

import java.util.List;

/**
 * Created by victorlins on 4/18/16.
 */
public class DelegatesAdapter extends
        RecyclerView.Adapter<DelegatesAdapter.ViewHolder> {

    private List<Delegate> mDelegates;

    public DelegatesAdapter(List<Delegate> delegates) {
        mDelegates = delegates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View delegateView = inflater.inflate(R.layout.delegate_row, parent, false);

        return new ViewHolder(delegateView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView rankTextView = holder.rankTextView;
        TextView nameTextView = holder.nameTextView;
        TextView addressTextView = holder.addressTextView;
        TextView productivityTextView = holder.productivityTextView;

        Delegate delegate = mDelegates.get(position);
        rankTextView.setText(delegate.getRate().toString());
        nameTextView.setText(delegate.getUsername());
        addressTextView.setText(delegate.getAddress());
        productivityTextView.setText(delegate.getProductivity() + "%");
    }

    @Override
    public int getItemCount() {
        return mDelegates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rankTextView;
        public TextView nameTextView;
        public TextView addressTextView;
        public TextView productivityTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            rankTextView = (TextView) itemView.findViewById(R.id.delegate_rank);
            nameTextView = (TextView) itemView.findViewById(R.id.delegate_name);
            addressTextView = (TextView) itemView.findViewById(R.id.delegate_address);
            productivityTextView = (TextView) itemView.findViewById(R.id.delegate_productivity);
        }
    }
}
