package com.example.core.version17.module;

public abstract class AbstractProcess {

    protected String data;

    public AbstractProcess() {
        initData();
    }

    public abstract void initData();
}
