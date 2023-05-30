package com.example.springmq.constants;

import java.util.ArrayList;
import java.util.List;

public class KeyMQ {
    public static final String VERSION = "1.0";

    public static final String INFO = "Animal";

    public static final List<String> LIST_MQ = new ArrayList<>();

    static {
        LIST_MQ.add("MQ-01");
        LIST_MQ.add("MQ-02");
    }
}
