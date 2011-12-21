package com.jspphp.tools;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class SjbZipUtil {
	public static int bufsize = 8192;
	private String zipBase;
	private int maxFileNameLength;
	private long totalLength;
	private long bytesHandled;

	private String getSpace(int num) {
		String space = "";
		for (int i = 0; i < num; i++) {
			space += " ";
		}
		return space;
	}

	private void getLength(File path) {
		if (path.isFile()) {
			totalLength += path.length();
			if (path.getPath().length() > maxFileNameLength) {
				maxFileNameLength = path.getPath().length();
			}
		} else {
			File[] fs = path.listFiles();
			for (int i = 0; i < fs.length; i++) {
				getLength(fs[i]);
			}
		}
	}

	private String lengthString(long fileLength) {
		long newValue = 0;
		String size = null;
		if (fileLength < 1024) {
			size = fileLength + " B";
		} else if (fileLength < (1024 * 1024)) {
			newValue = fileLength / 1024;
			size = newValue + " KB";
		} else if (fileLength < (1024 * 1024 * 1024)) {
			newValue = fileLength / (1024 * 1024);
			size = newValue + " MB";
		} else {
			newValue = fileLength / (1024 * 1024 * 1024);
			size = newValue + " GB";
		}
		return size;
	}

	public void zip(String path) throws Exception {
		zip(path, null);
	}

	public void zip(String path, String dest) throws Exception {
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("File : " + path + " Not Exists!");
			return;
		}
		getLength(f);

		System.out.println("total " + lengthString(totalLength) + " to zip");
		String path2 = "";
		for (int i = 0; i < path.length(); i++) {
			char c = path.charAt(i);
			if (c == '\\') {
				c = '/';
			}
			path2 += c;
		}
		path = path2;

		zipBase = path.substring(path.lastIndexOf("/") == -1 ? 0 : path
				.lastIndexOf("/") + 1);
		if (dest == null) {
			if (f.isFile()) {
				if (dest == null) {
					zipBase = "./";
					dest = path.substring(0,
							(path.lastIndexOf(".") == -1 ? path.length() : path
									.lastIndexOf(".")))
							+ ".zip";
				}
			} else {
				path2 = path.substring(0, path.lastIndexOf("/") == -1 ? path
						.length() : path.lastIndexOf("/"));
				if (path.equals(path2)) {
					dest = ".";
				}
				dest = path2
						+ "/"
						+ zipBase.substring(0,
								(zipBase.lastIndexOf(".") == -1 ? zipBase
										.length() : zipBase.lastIndexOf(".")))
						+ ".zip";
			}
		}
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(dest));
		zipFile(f, zipOut);
		zipOut.close();
		System.out.print("\r100%\t");
		System.out.println(path + " has been compressed to zip file : " + dest
				+ getSpace(40));
	}

	public void zipFile(File f, ZipOutputStream zipOut) throws Exception {
		if (f.isDirectory()) {
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; i++) {
				zipFile(fs[i], zipOut);
			}
		} else {
			String path = f.getPath();
			FileInputStream fileIn = new FileInputStream(path);
			String entryName = path;
			int basePos = path.indexOf(zipBase);
			if (basePos > 0) {
				entryName = path.substring(basePos);
			}
			ZipEntry theEntry = new ZipEntry(entryName);
			theEntry.setSize(f.length());

			zipOut.putNextEntry(theEntry);

			byte[] buffer = new byte[bufsize];
			int bytesRead = fileIn.read(buffer, 0, bufsize);
			bytesHandled += bytesRead;

			while (bytesRead > 0) {
				zipOut.write(buffer, 0, bytesRead);
				bytesRead = fileIn.read(buffer, 0, bufsize);
				bytesHandled += bytesRead;
				int percent = (int) (((double) bytesHandled / totalLength) * 100);
				System.out.print("\r" + percent + "%  ");
				if (totalLength > 1024 * 1024 * 80) {
					int j;
					for (j = 0; j < percent % 4 + 1; j++) {
						System.out.print(">");

					}
					for (int x = 0; x < 4 - j; x++) {
						System.out.print(" ");
					}
				}
				System.out.print("  Compress File " + path
						+ getSpace(maxFileNameLength - path.length()));
			}
			fileIn.close();
			zipOut.closeEntry();
		}
	}

	public void unzip(String zipFileName) throws Exception {
		unzip(zipFileName, null);
	}

	@SuppressWarnings("unchecked")
	public void unzip(String zipFileName, String dest) throws Exception {
		File f = new File(zipFileName);
		if (!f.exists()) {
			System.out.println("Zip File : " + zipFileName + " Not Exists!");
			return;
		}

		byte[] buffer = new byte[bufsize];

		ZipFile zf = new ZipFile(zipFileName);

		ZipEntry theEntry = null;

		Enumeration enumer = zf.entries();
		while (enumer.hasMoreElements()) {
			theEntry = (ZipEntry) enumer.nextElement();
			totalLength += theEntry.getSize();
			if (theEntry.getName().length() > maxFileNameLength) {
				maxFileNameLength = theEntry.getName().length();
			}
		}

		System.out.println("Zip File " + lengthString(f.length())
				+ " and Total " + lengthString(totalLength) + " Data to unzip");

		enumer = zf.entries();
		while (enumer.hasMoreElements()) {
			theEntry = (ZipEntry) enumer.nextElement();
			String entryName = theEntry.getName();
			String entryName2 = "";
			for (int i = 0; i < entryName.length(); i++) {
				char c = entryName.charAt(i);
				if (c == '\\') {
					c = '/';
				}
				entryName2 += c;
			}
			entryName = entryName2;
			zipBase = ".";
			if (dest != null) {
				if (dest.endsWith("/")) {
					dest = dest.substring(0, dest.length() - 1);
				}
				zipBase = dest;
			}
			String tmpFolder = zipBase;

			int begin = 0;
			int end = 0;
			while (true) {
				end = entryName.indexOf("/", begin);
				if (end == -1) {
					break;
				}
				tmpFolder += "/" + entryName.substring(begin, end);
				begin = end + 1;
				f = new File(tmpFolder);
				if (!f.exists()) {
					f.mkdir();
				}
			}
			OutputStream os = new FileOutputStream(zipBase + "/" + entryName);
			InputStream is = zf.getInputStream(theEntry);
			int bytesRead = is.read(buffer, 0, bufsize);
			bytesHandled += bytesRead;
			while (bytesRead > 0) {
				os.write(buffer, 0, bytesRead);
				bytesRead = is.read(buffer, 0, bufsize);
				bytesHandled += bytesRead;
				System.out.print("\r");
				int percent = (int) (((double) bytesHandled / totalLength) * 100);
				System.out.print("\r" + percent + "%  ");
				if (totalLength > 1024 * 1024 * 80) {
					int j;
					for (j = 0; j < percent % 4 + 1; j++) {
						System.out.print(">");

					}
					for (int x = 0; x < 4 - j; x++) {
						System.out.print(" ");
					}
				}

				System.out.print("  Unzip File " + entryName
						+ getSpace(maxFileNameLength - entryName.length()));
			}
			is.close();
			os.close();
		}

		System.out.println("\r100%  Zip File : " + zipFileName
				+ " has been unCompressed to " + dest + "/ !" + getSpace(40));
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out
					.println("usage : java bs.ppl.comm.ZipUtil -zip[-unzip] filename");
			return;
		}
		SjbZipUtil zip = new SjbZipUtil();
		String dest = null;
		if (args[0].equals("-zip")) {
			if (args.length > 2) {
				dest = args[2];
			}
			zip.zip(args[1], dest);
		}
		if (args[0].equals("-unzip")) {
			if (args.length > 2) {
				dest = args[2];
			}
			zip.unzip(args[1], dest);
		}
	}
}
