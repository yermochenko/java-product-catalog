package org.itstep;

public enum Singleton3 {
	INSTANCE;

	private double value = Math.random();
	@Override
	public String toString() {
		return "Singleton2 [value=" + value + "]";
	}
}
