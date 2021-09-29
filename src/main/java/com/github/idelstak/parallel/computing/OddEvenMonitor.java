package com.github.idelstak.parallel.computing;

/*
 * Copyright (C) 2021 Hiram K. <https://github.com/IdelsTak>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class OddEvenMonitor {

    public static final boolean ODD_TURN = true;
    public static final boolean EVEN_TURN = false;
    private boolean turn = ODD_TURN;

    public static void main(String[] args) throws InterruptedException {
        OddEvenMonitor monitor = new OddEvenMonitor();
        Thread t1 = new OddThread(monitor);
        Thread t2 = new EvenThread(monitor);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    // Need synchronized in order to call wait()
    public synchronized void waitTurn(boolean oldTurn) {
        while (turn != oldTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException in wait(): " + e);
            }
        }
        //Move on, it’s our turn.
    }

    // Need synchronized in order to call notify()
    public synchronized void toggleTurn() {
        turn ^= true;
        notify();
    }

    private static class OddThread extends Thread {

        private final OddEvenMonitor monitor;

        private OddThread(OddEvenMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 100; i += 2) {
                monitor.waitTurn(OddEvenMonitor.ODD_TURN);
                System.out.println("i = " + i);
                monitor.toggleTurn();
            }
        }
    }

    private static class EvenThread extends Thread {

        private final OddEvenMonitor monitor;

        private EvenThread(OddEvenMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public void run() {
            for (int i = 2; i <= 100; i += 2) {
                monitor.waitTurn(OddEvenMonitor.EVEN_TURN);
                System.out.println("i = " + i);
                monitor.toggleTurn();
            }
        }
    }

}
