package org.itstep;

import java.io.FileOutputStream;
import java.io.IOException;

public class Singleton1 {
	private double value = Math.random();

	private Singleton1() throws IOException {
		System.out.println("Singleton created");
		try(FileOutputStream stream = new FileOutputStream("1.txt")) {
			
		}
	}

	private static Singleton1 instance = null;

	public static Singleton1 getInstance() throws IOException {
		if(instance == null) {
			synchronized(Singleton1.class) {
				if(instance == null) {
					instance = new Singleton1();
				}
			}
		}
		return instance;
	}

	@Override
	public String toString() {
		return "Singleton [value=" + value + "]";
	}
}
