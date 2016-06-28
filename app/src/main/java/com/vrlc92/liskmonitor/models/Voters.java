package com.vrlc92.liskmonitor.models;

import java.util.Collections;
import java.util.List;

/**
 * Created by victorlins on 4/16/16.
 */

public class Voters {
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
