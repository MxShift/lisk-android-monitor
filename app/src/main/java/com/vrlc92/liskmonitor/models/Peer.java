package com.vrlc92.liskmonitor.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * Created by victorlins on 4/14/16.
 */

public class Peer {
    private String ip;
    private Integer port;
    private Integer state;
    private String os;
    private String version;

    public enum PeerState {
        BANNED(0),
        DISCONNECTED(1),
        CONNECTED(2);

        private int state;

        PeerState(int state) {
            this.state = state;
        }

        public static PeerState fromState(int state){
            switch (state){
                case 0:
                    return BANNED;
                case 1:
                    return DISCONNECTED;
                case 2:
                    return CONNECTED;
                default:
                    return null;
            }
        }

        public int getState() {
            return state;
        }
    }

    private static String TAG = Peer.class.getSimpleName();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static Peer fromJson(JSONObject jsonObject){
        Peer peer = new Peer();

        if (jsonObject == null) {
            return peer;
        }

        try {
            peer.ip = jsonObject.getString("ip");
        } catch (JSONException e) {
            Logger.getLogger(TAG).warning(String.format("peer.ip (%s)", e.getLocalizedMessage()));
        }

        try {
            peer.port = jsonObject.getInt("port");
        } catch (JSONException e) {
            Logger.getLogger(TAG).warning(String.format("peer.port (%s)", e.getLocalizedMessage()));
        }

        try {
            peer.state = jsonObject.getInt("state");
        } catch (JSONException e) {
            Logger.getLogger(TAG).warning(String.format("peer.state (%s)", e.getLocalizedMessage()));
        }

        try {
            peer.os = jsonObject.getString("os");
        } catch (JSONException e) {
            Logger.getLogger(TAG).warning(String.format("peer.os (%s)", e.getLocalizedMessage()));
        }

        try {
            peer.version = jsonObject.getString("version");
        } catch (JSONException e) {
            Logger.getLogger(TAG).warning(String.format("peer.version (%s)", e.getLocalizedMessage()));
        }

        return peer;
    }
}
