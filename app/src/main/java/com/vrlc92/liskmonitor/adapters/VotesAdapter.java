package com.vrlc92.liskmonitor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Delegate;
import com.vrlc92.liskmonitor.models.Votes;

import java.util.List;

/**
 * Created by victorlins on 4/20/16.
 */
public class VotesAdapter extends
        RecyclerView.Adapter<VotesAdapter.ViewHolder> {

    private Votes mVotes;
    private List<Delegate> mDelegates;

    public VotesAdapter(Votes votes) {
        mVotes = votes;
        mDelegates = votes.getDelegates();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View voteView = inflater.inflate(R.layout.vote_row, parent, false);

        return new ViewHolder(voteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView rankTextView = holder.rankTextView;
        TextView nameTextView = holder.nameTextView;
        TextView addressTextView = holder.addressTextView;

        Delegate delegate = mDelegates.get(position);
        rankTextView.setText(delegate.getRate().toString());
        nameTextView.setText(delegate.getUsername());
        addressTextView.setText(delegate.getAddress());
    }

    @Override
    public int getItemCount() {
        return mDelegates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rankTextView;
        public TextView nameTextView;
        public TextView addressTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            rankTextView = (TextView) itemView.findViewById(R.id.vote_delegate_rank);
            nameTextView = (TextView) itemView.findViewById(R.id.vote_delegate_name);
            addressTextView = (TextView) itemView.findViewById(R.id.vote_delegate_address);
        }
    }
}