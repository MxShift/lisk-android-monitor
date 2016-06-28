package com.vrlc92.liskmonitor.models;

import java.util.Collections;
import java.util.List;

/**
 * Created by victorlins on 4/14/16.
 */

public class Votes {
    private List<Delegate> delegates;

    public List<Delegate> getDelegates() {
        return Collections.unmodifiableList(delegates);
    }

    public void setDelegates(List<Delegate> delegates) {
        this.delegates = delegates;
    }
}
