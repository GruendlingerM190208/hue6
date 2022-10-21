/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.htlgrieskirchen.pos3.pcp;

import java.util.concurrent.ArrayBlockingQueue;

public class Storage {
    private final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    private int fetchedCounter;
    private int storedCounter;
    private int underflowCounter;
    private int overflowCounter;
    private boolean productionComplete = false;

    public Storage() {
    }

    public synchronized boolean put(Integer data) throws InterruptedException {
        if (queue.size() < 10) {
            queue.put(data);
            storedCounter++;
            return true;
        } else {
            overflowCounter++;
            return false;
        }
    }

    public synchronized Integer get() {
        Integer e = queue.poll();
        if (e == null) {
            underflowCounter++;
        } else {
            fetchedCounter++;
        }
        return e;
    }

    public boolean isProductionComplete() {
        return productionComplete;
    }

    public void setProductionComplete() {
        productionComplete = true;
    }

    public int getFetchedCounter() {
        return fetchedCounter;
    }

    public int getStoredCounter() {
        return storedCounter;
    }

    public int getUnderflowCounter() {
        return underflowCounter;
    }

    public int getOverflowCounter() {
        return overflowCounter;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
