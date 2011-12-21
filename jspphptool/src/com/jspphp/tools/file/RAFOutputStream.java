package com.jspphp.tools.file;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;


public class RAFOutputStream extends OutputStream {
	RandomAccessFile raf = null;

	public RAFOutputStream(RandomAccessFile raf) {
		this.raf = raf;
	}

	@Override
	public void write(int b) throws IOException {
		raf.write(b);
	}

}
