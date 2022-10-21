/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.ArrayList;
import java.util.List;

public class Producer implements Runnable {
    private final String name;
    private final Storage storage;
    private final int sleepTime;

    private final List<Integer> sent = new ArrayList<>();
    private final int numberOfItems;

    public Producer(String name, Storage storage, int sleepTime, int numberOfItems) {
        this.name = name;
        this.storage = storage;
        this.sleepTime = sleepTime;
        this.numberOfItems = numberOfItems;
    }

    public List<Integer> getSent() {
        return sent;
    }

    @Override
    public void run() {
        int counter = 0;
        for (int i = 1; i < numberOfItems+1; i++) {
            try {
                boolean failed = true;
                while (failed){
                    if (storage.put(i)) {
                        counter++;
                        failed = false;
                    } else {
                        if(counter != 0){
                            sent.add(counter);
                            counter = 0;
                        }
                        Thread.sleep(sleepTime);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        storage.setProductionComplete();
    }
}
