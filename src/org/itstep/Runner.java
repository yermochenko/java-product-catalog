package org.itstep;

import java.io.IOException;

public class Runner {
	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {
		System.out.println("START");
		Singleton1 s = null;
		Class.forName("org.itstep.Singleton");
		Thread.sleep(2000);
		s = Singleton1.getInstance();
		System.out.println(s);
		System.out.println(Singleton1.getInstance());
		System.out.println("END");

		Singleton2 s1 = Singleton2.getInstance();
		System.out.println(s1);

		Singleton3 s2 = Singleton3.INSTANCE;
		System.out.println(s2);
	}
}
