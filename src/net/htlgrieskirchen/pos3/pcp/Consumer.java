/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.ArrayList;
import java.util.List;


public class Consumer implements Runnable {
    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> received = new ArrayList<>();
    private boolean running;

    public Consumer(String name, Storage storage, int sleepTime) {
        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
    }

    // implement this

    public List<Integer> getReceived() {
        return received;
    }

    @Override
    public void run() {
        while (!(storage.isProductionComplete() && storage.isEmpty())) {
            Integer e = storage.get();
            if (e != null) {
                received.add(e);
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {
            }
        }
    }
}

