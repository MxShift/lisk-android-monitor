package com.vrlc92.liskmonitor.models;

/**
 * Created by victorlins on 4/18/16.
 */
public class Settings {
    private String username;
    private String liskAddress;
    private String publicKey;
    private String ipAddress;
    private int port;
    private boolean sslEnabled;
    private boolean defaultServerEnabled;
    private long notificationInterval;

    public static String USERNAME_ATTR = "settings.username";
    public static String LISK_ADDRESS_ATTR = "settings.lisk_address";
    public static String PUBLIC_KEY_ATTR = "settings.public_key";
    public static String IP_ATTR = "settings.ip_address";
    public static String PORT_ATTR = "settings.port";
    public static String SSL_ENABLED_ATTR = "settings.ssl_enabled";
    public static String DEFAULT_SERVER_ENABLED_ATTR = "settings.default_server_enabled";
    public static String NOTIFICATION_INTERVAL_ATTR = "settings.notification_interval_attr";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLiskAddress() {
        return liskAddress;
    }

    public void setLiskAddress(String liskAddress) {
        this.liskAddress = liskAddress;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public boolean getDefaultServerEnabled() {
        return defaultServerEnabled;
    }

    public void setNotificationInterval(long notificationInterval) {
        this.notificationInterval = notificationInterval;
    }

    public long getNotificationInterval() {
        return notificationInterval;
    }

    public void setDefaultServerEnabled(boolean defaultServerEnabled) {
        this.defaultServerEnabled = defaultServerEnabled;
        if (defaultServerEnabled) {
            setIpAddress(null);
            setPort(-1);
            setSslEnabled(true);
        }
    }
}
