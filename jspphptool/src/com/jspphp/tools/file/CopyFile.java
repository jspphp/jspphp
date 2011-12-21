package com.jspphp.tools.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFile {
	public static void main(String[] args) throws Exception {
		String infile = "F:/Media/[www.1kk.cc][DVD国语]真相[第2集].rmvb";
		String outfile = "C:\\真相2.rmvb";
		// 获取源文件和目标文件的输入输出流
		FileInputStream fin = new FileInputStream(infile);
		FileOutputStream fout = new FileOutputStream(outfile);
		// 获取输入输出通道
		FileChannel fcin = fin.getChannel();
		FileChannel fcout = fout.getChannel();
		System.out.println(fcin.size() / 1024 / 1024);
		long begin = System.currentTimeMillis();
		long end = 0L;
		// 创建缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		while (true) {
			// clear方法重设缓冲区，使它可以接受读入的数据
			buffer.clear();
			// 从输入通道中将数据读到缓冲区
			int r = fcin.read(buffer);
			// read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1
			if (r == -1) {
				break;
			}
			// flip方法让缓冲区可以将新读入的数据写入另一个通道
			buffer.flip();
			// 从输出通道中将数据写入缓冲区
			fcout.write(buffer);
			System.out.println(fcin.position());
		}
		end = System.currentTimeMillis() - begin;
		System.out.println(end);
	}
}
