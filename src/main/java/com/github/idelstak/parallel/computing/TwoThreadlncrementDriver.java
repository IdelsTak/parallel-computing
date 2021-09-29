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
public class TwoThreadlncrementDriver {
    
    public static int counter;
    public static int N;

    public static void main(String[] args) throws Exception {
        N = (args.length > 0) ? Integer.parseInt(args[0]) : 100;
        Thread t1 = new Thread(new IncrementThread());
        Thread t2 = new Thread(new IncrementThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }
    
}
