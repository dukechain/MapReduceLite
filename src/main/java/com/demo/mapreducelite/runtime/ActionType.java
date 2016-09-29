package com.demo.mapreducelite.runtime;

/**
 * Created by chenxu on 26.09.16.
 */
public enum ActionType {

    ClusterStart,
    ClusterReady,
    JobSubmit,
    JobSubmitReady,
    MapperStart,
    MapperFinished,
    ReducerStart,
    ReducerFinished,
    JobEnd, JobFinished
}
