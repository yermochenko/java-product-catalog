package org.itstep;

public class Singleton2 {
	private double value = Math.random();
	@Override
	public String toString() {
		return "Singleton1 [value=" + value + "]";
	}

	private Singleton2() {}

	private static Singleton2 instance = new Singleton2();

	public static Singleton2 getInstance() {
		return instance;
	}
}
