package com.manulaiko.tabitha;

import java.util.ConcurrentModificationException;
import java.util.HashSet;

/**
 * Updater class.
 *
 * Originally written for `BlackEye` but it can be useful for
 * other things.
 *
 * Basically you subscribe `Updatable` objects to the instance and
 * the `update` method will be executed each X time.
 *
 * When the timeout ends a new thread will be started that will execute
 * the `update` method of the `Updatable` object.
 *
 * To subscribe an object use the method `subscribe`, to
 * unsusbscribe it use the method `unsusbscribe`.
 *
 * The method `startUpdater` starts the thread and the method
 * `stopUpdater` stops it.
 *
 * Example:
 *
 *     Updatable obj1 = new Updatable();
 *     Updatable obj2 = new Updatable();
 *
 *     Updater updater = new Updater();
 *     updater.subscribe(obj1);
 *     updater.start();
 *
 *     updater.subscribe(obj2);
 *     updater.unsusbscribe(obj1);
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class Updater extends Thread
{
    /**
     * Optimal time on which the objects should be updated.
     */
    public static final double OPTIMAL_TIME = 1000000000 / 60;

    /**
     * Whether the thread is running or not.
     */
    private boolean _isRunning = false;

    /**
     * Updatable events.
     */
    private HashSet<Updatable> _events = new HashSet<>();

    /**
     * Events to unsusbscribe.
     */
    private HashSet<Updatable> _unsusbscribe = new HashSet<>();

    /**
     * Events to subscribe.
     */
    private HashSet<Updatable> _subscribe = new HashSet<>();

    /**
     * Constructor.
     */
    public Updater()
    {
        this.setName("Updater");
    }

    /**
     * Adds an event to the array.
     *
     * @param event Event to add.
     */
    public void subscribe(Updatable event)
    {
        if(
            this._events.contains(event)    ||
            this._subscribe.contains(event)
        ) {
            return;
        }

        this._subscribe.add(event);
    }

    /**
     * Unsusbscribe an event.
     *
     * @param event Event to unsusbscribe.
     */
    public void unsusbscribe(Updatable event)
    {
        if(
            !this._events.contains(event)       &&
            !this._unsusbscribe.contains(event)
        ) {
            return;
        }

        this._events.remove(event);
    }

    /**
     * Updates the game
     */
    public void run()
    {
        double  delta    = 0D;
        long    lastTime = System.nanoTime();

        while(this._isRunning) {
            long now              = System.nanoTime();
            long lastTickDuration = now - lastTime;

            delta   += lastTickDuration / Updater.OPTIMAL_TIME;
            lastTime = now;

            while(delta >= 1) {
                this.update();
                delta--;
            }
        }
    }

    /**
     * Performs the update
     */
    public void update()
    {
        try {
            this._prepareArray();
            this._events.forEach((e) -> {
                Thread t = new Thread()
                {
                    /**
                     * Execute the update on a separated thread.
                     */
                    public void run()
                    {
                        e.update();
                    }
                };

                t.setName("Update Event " + e.toString());
                t.start();
            });
        } catch(ConcurrentModificationException e) {
            // Empty
        }
    }

    /**
     * Prepares the array to avoid concurrent modifications.
     */
    private void _prepareArray()
    {
        this._unsusbscribe.forEach((e)->{
            this._events.remove(e);
        });
        this._subscribe.forEach((e)->{
            this._events.add(e);
        });

        this._unsusbscribe.clear();
        this._subscribe.clear();
    }

    /**
     * Stops the thread.
     */
    public void stopUpdater()
    {
        this._isRunning = false;
    }

    /**
     * Starts the thread.
     */
    public void startUpdater()
    {
        this._isRunning = true;
        try {
            super.start();
        } catch(Exception e) {
            // Empty
        }
    }
}
