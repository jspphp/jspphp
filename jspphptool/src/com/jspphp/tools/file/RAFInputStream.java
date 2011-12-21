package com.jspphp.tools.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class RAFInputStream extends InputStream {
	RandomAccessFile raf = null;

	public RAFInputStream(RandomAccessFile raf) {
		this.raf = raf;
	}

	@Override
	public int read() throws IOException {
		int value = raf.read();
		return value;
	}

}
