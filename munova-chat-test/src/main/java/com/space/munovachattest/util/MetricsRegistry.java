package com.space.munovachattest.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.LongAdder;

public class MetricsRegistry {
    // --- COUNT METRICS ---
    public static final LongAdder connectSuccess = new LongAdder();
    public static final LongAdder connectFail = new LongAdder();

    public static final LongAdder chatCreateSuccess = new LongAdder();
    public static final LongAdder chatCreateFail = new LongAdder();

    public static final LongAdder joinSuccess = new LongAdder();
    public static final LongAdder joinFail = new LongAdder();

    public static final LongAdder messagesSent = new LongAdder();
    public static final LongAdder messagesReceived = new LongAdder();
    public static final LongAdder messageErrors = new LongAdder();
    public static final LongAdder e2eCount = new LongAdder();

    // --- LATENCY METRICS (ns 기록) ---
    public static final ConcurrentLinkedQueue<Long> connectLatency = new ConcurrentLinkedQueue<>();
    public static final ConcurrentLinkedQueue<Long> joinLatency = new ConcurrentLinkedQueue<>();
    public static final ConcurrentLinkedQueue<Long> sendLatency = new ConcurrentLinkedQueue<>();
    public static final ConcurrentLinkedQueue<Long> chatCreateLatency = new ConcurrentLinkedQueue<>();
    public static final ConcurrentLinkedQueue<Long> e2eLatency = new ConcurrentLinkedQueue<>();


    // ===== RECORD METHODS =====

    public static void recordConnectSuccess(long latencyMs) {
        connectSuccess.increment();
        connectLatency.add(latencyMs);
    }

    public static void recordConnectFail() {
        connectFail.increment();
    }

    public static void recordChatCreateSuccess(long latencyMs) {
        chatCreateSuccess.increment();
        chatCreateLatency.add(latencyMs);
    }

    public static void recordChatCreateFail() {
        chatCreateFail.increment();
    }

    public static void recordJoinSuccess(long latencyMs) {
        joinSuccess.increment();
        joinLatency.add(latencyMs);
    }

    public static void recordJoinFail() {
        joinFail.increment();
    }

    public static void recordMessageSent() {
        messagesSent.increment();
    }

    public static void recordMessageReceived(Long latencyMs) {
        messagesReceived.increment();
        if (latencyMs != null) {
            sendLatency.add(latencyMs);
        }
    }

    public static void recordMessageError() {
        messageErrors.increment();
    }

    public static void recordChatCreateLatency(long latencyMs) {
        chatCreateLatency.add(latencyMs);
    }

    public static void recordE2EStartup(long ns) {
        e2eCount.increment();
        e2eLatency.add(ns);
    }


    // ===== SUMMARY 출력 =====

    public static void printSummary() {
        System.out.println("\n\n===== 📊 BOT METRICS SUMMARY =====");

        System.out.println("\n▶ E2E STARTUP");
        System.out.println("   count: " + e2eCount.sum());
        System.out.println("   avg(ns): " + avg(e2eLatency));

        System.out.println("\n▶ CHAT CREATE");
        System.out.println("   count: " + chatCreateLatency.size());
        System.out.println("   avg(ns): " + avg(chatCreateLatency));

        System.out.println("▶ CONNECT");
        System.out.println("   success: " + connectSuccess.sum());
        System.out.println("   fail   : " + connectFail.sum());
        System.out.println("   avg(ns): " + avg(connectLatency));

        System.out.println("\n▶ JOIN");
        System.out.println("   success: " + joinSuccess.sum());
        System.out.println("   fail   : " + joinFail.sum());
        System.out.println("   avg(ns): " + avg(joinLatency));

        System.out.println("\n▶ MESSAGE");
        System.out.println("   sent       : " + messagesSent.sum());
        System.out.println("   received   : " + messagesReceived.sum());
        System.out.println("   errors     : " + messageErrors.sum());
        System.out.println("   avg latency(ns): " + avg(sendLatency));

        System.out.println("==================================\n");
    }

    private static long avg(ConcurrentLinkedQueue<Long> values) {
        if (values.isEmpty()) return 0;
        long sum = 0;
        for (Long v : values) sum += v;
        return sum / values.size();
    }
}
