package com.esalcedo.callcenter.core.util;

import java.io.Serializable;
import java.util.Random;

/**
 * @author esalcedo
 *
 */
public class Util implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Get a random number between two numbers.
	 * @param from
	 * @param to
	 * @return int
	 */
	public static int getRandom(int from, int to) {
	    if (from < to)
	        return from + new Random().nextInt(Math.abs(to - from));
	    return from - new Random().nextInt(Math.abs(to - from));
	}
	
	/**
	 * 
	 * @param s
	 */
	public static void log( String s )
    {
		System.out.println(s);
    }
}
