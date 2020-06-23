package com.jkk.demo;

public class HelloWorld {
	public static void main(String args[]) {
		int i=0;
		String greetings[]= {"Hello World!","Hello!","HELLO WORLD!!"};
		while(i<3) {
			System.out.println(greetings[i]);
			i++;
		}
		System.out.println("Normol ended.");
	}

}