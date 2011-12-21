package com.jspphp.tools.file;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;

public class ThreadNew extends Thread {
	private FileLock lock;
	private static Object latch = new Object();

	public void run() {
		synchronized (latch) {
			try {
				FileOutputStream fout = new FileOutputStream("lock");
				lock = fout.getChannel().lock();
				System.out.println(hashCode() + " Locked");
				Thread.sleep(1000);
				System.out.println(hashCode() + " unLocked");
				lock.release();
				fout.close();
			} catch (Exception ex) {
				System.out.println("Lock Error: " + hashCode() + " " + ex.getMessage());
			}
		}
	}

	public static void main(String[] args) {
	}
}
