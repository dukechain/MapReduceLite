package com.demo.mapreducelite.runtime;

import java.io.Serializable;

/**
 * Created by chenxu on 26.09.16.
 */
public class Action implements Serializable {

    protected long clusterID = 0L;
    protected long jobID = 0L;

    protected ActionType msgType = null;

    //protected JobConfiguration jobConfig;

    public Action(ActionType msgType) {
        this.msgType = msgType;
    }

}
