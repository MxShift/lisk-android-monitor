package com.vrlc92.liskmonitor.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vrlc92.liskmonitor.R;
import com.vrlc92.liskmonitor.models.Peer;

import java.util.List;

/**
 * Created by victorlins on 4/19/16.
 */
public class PeersAdapter extends
        RecyclerView.Adapter<PeersAdapter.ViewHolder> {

    private Context mContext;
    private List<Peer> mPeers;

    public PeersAdapter(Context context, List<Peer> peers) {
        mContext = context;
        mPeers = peers;
    }

    @Override
    public PeersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.peer_row, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(PeersAdapter.ViewHolder viewHolder, int position) {

        Peer peer = mPeers.get(position);

        TextView ipAddressTextView = viewHolder.ipAddressTextView;
        TextView portTextView = viewHolder.portTextView;
        ImageView statusTextView = viewHolder.statusImageView;
        TextView versionTextView = viewHolder.versionTextView;

        ipAddressTextView.setText(peer.getIp());
        portTextView.setText(String.valueOf(peer.getPort()));

        Peer.PeerState peerState = Peer.PeerState.fromState(peer.getState());

        switch (peerState){
            case BANNED:
                statusTextView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_banned));
                break;
            case DISCONNECTED:
                statusTextView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_disconnected));
                break;
            case CONNECTED:
                statusTextView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_connected));
                break;
            default:
                statusTextView.setImageDrawable(null);
                break;
        }

        versionTextView.setText(peer.getVersion());
    }

    @Override
    public int getItemCount() {
        return mPeers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ipAddressTextView;
        public TextView portTextView;
        public ImageView statusImageView;
        public TextView versionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ipAddressTextView = (TextView) itemView.findViewById(R.id.peer_ip_address);
            portTextView = (TextView) itemView.findViewById(R.id.peer_port);
            statusImageView = (ImageView) itemView.findViewById(R.id.peer_status);
            versionTextView = (TextView) itemView.findViewById(R.id.peer_version);
        }
    }
}
