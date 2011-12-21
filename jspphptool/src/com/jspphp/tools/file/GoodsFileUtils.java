package com.jspphp.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Properties;

public class GoodsFileUtils {
	public static final String READFILEPREFIX = "read_";
	public static final String WRITEFILEPREFIX = "price_";
	public static final String FILEPOSTFIX = ".properties";

	public static void addprice(GoodsBean goodsBean) throws Exception {
		String path = getSharePropertiesPath();
		File priceFile = new File(path + "/" + WRITEFILEPREFIX + goodsBean.getGoodsId() + FILEPOSTFIX);
		File readFile = new File(path + "/" + READFILEPREFIX + goodsBean.getGoodsId() + FILEPOSTFIX);
		RandomAccessFile accessFile = new RandomAccessFile(priceFile, "rw");

		FileLock fileLock = null;

		long start = System.currentTimeMillis();
		long end = 0;
		while (true) {
			try {
				FileChannel channel = accessFile.getChannel();
				fileLock = channel.tryLock();
				end = System.currentTimeMillis();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("获取文件锁失败：原因为:" + e.getMessage());
				end = System.currentTimeMillis();
			}
			if (fileLock != null) {
				break;
			} else {
				long result = end - start;// 获取文件锁的毫秒数
				long permin = 1 * 60 * 1000;// 1分钟的毫秒数
				// long permin = 1000;// 1分钟的毫秒数
				// 1分钟还没有获取该文件的锁，退出，报错
				if (result > permin) {
					System.out.println("fileLock:" + fileLock);
					throw new Exception("获取文件锁超时");
				}
				System.out.println("正在尝试获取该文件的独占锁，请等待!");
				continue;
			}
		}
		InputStream fin = null;
		OutputStream outputStream = null;
		Properties propFile = new Properties();
		try {
			fin = new RAFInputStream(accessFile);
			outputStream = new RAFOutputStream(accessFile);
			propFile.load(fin);
			double maxPrice = Double.parseDouble(propFile.getProperty("maxPrice").trim());
			long realTime = Long.parseLong(propFile.getProperty("realTime").trim());
			// 是否正在拍卖
			int isSelled = Integer.parseInt(propFile.getProperty("isSelled").trim());
			goodsBean.setIsSelled(isSelled);
			if (isSelled != 1)
				return;
			// 取得间隔时间
			InputStream in = (GoodsFileUtils.class.getResourceAsStream("/auction.properties"));
			Properties pro = new Properties();
			pro.load(in);
			long overTime = Long.parseLong(pro.getProperty("overTime").trim()) * 1000;
			in.close();
			System.out.println("goodsBean.getMaxprice() " + goodsBean.getMaxprice() + " ====maxPrice " + maxPrice);

			if (goodsBean.getMaxprice() > maxPrice) {
				// 保存数据
				propFile.setProperty("goodsId", goodsBean.getGoodsId());
				propFile.setProperty("userId", goodsBean.getUserId());
				propFile.setProperty("userName", goodsBean.getUserName());
				propFile.setProperty("maxPrice", goodsBean.getMaxprice() + "");
				// 如果时间延长，修改实际结束时间
				if (goodsBean.getRealTime() > (realTime - overTime)) {
					propFile.setProperty("realTime", String.valueOf(goodsBean.getRealTime() + overTime));
					goodsBean.setIsDelay(1);
				}
				// 标记成功
				goodsBean.setStatus(1);
				accessFile.seek(0);
				propFile.store(outputStream, "");
				outputStream.flush();
				// 写读数据文件
				Properties writeFile = new Properties();
				writeFile.load(new FileInputStream(readFile));
				OutputStream out = new FileOutputStream(readFile);
				writeFile.setProperty("goodsId", goodsBean.getGoodsId());
				writeFile.setProperty("userId", goodsBean.getUserId());
				writeFile.setProperty("userName", goodsBean.getUserName());
				writeFile.setProperty("maxPrice", goodsBean.getMaxprice() + "");
				// 如果时间延长，修改实际结束时间
				if (goodsBean.getRealTime() > (realTime - overTime)) {
					writeFile.setProperty("realTime", String.valueOf(goodsBean.getRealTime() + overTime));
				}
				writeFile.store(out, "");
				out.flush();
			} else {
				goodsBean.setStatus(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			if (fileLock != null) {
				fileLock.release();
				fileLock = null;
			}
			if (outputStream != null)
				outputStream.close();
			if (fin != null)
				fin.close();
		}
	}

	/**
	 * 修改文件
	 * 
	 * @param bean
	 */
	public static boolean updatePrice(GoodsBean bean) throws Exception {
		String path = getSharePropertiesPath();
		System.out.println("udapte is begin...");
		File priceFile = new File(path + "/" + WRITEFILEPREFIX + bean.getGoodsId() + FILEPOSTFIX);
		System.out.println(priceFile.getPath());
		File readFile = new File(path + "/" + READFILEPREFIX + bean.getGoodsId() + FILEPOSTFIX);
		System.out.println(readFile.getPath());
		RandomAccessFile accessFile = new RandomAccessFile(priceFile, "rw");
		FileLock lock = null;
		long start = System.currentTimeMillis();
		long end = 0l;
		while (true) {
			try {
				FileChannel channel = accessFile.getChannel();
				lock = channel.tryLock();
				end = System.currentTimeMillis();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("获取文件锁失败：原因为:" + e.getMessage());
				end = System.currentTimeMillis();
			}
			if (lock != null) {
				break;
			} else {
				long result = end - start;// 获取文件锁的毫秒数
				long permin = 1 * 60 * 1000;// 1分钟的毫秒数
				// long permin = 1000;// 1分钟的毫秒数
				// 1分钟还没有获取该文件的锁，退出，报错
				if (result > permin) {
					System.out.println("fileLock:" + lock);
					throw new Exception("获取文件锁超时");
				}
				System.out.println("正在尝试获取该文件的独占锁，请等待!");
			}
		}
		InputStream in = null;
		OutputStream out = null;
		Properties pricePro = new Properties();
		try {
			// 写数据文件
			in = new RAFInputStream(accessFile);
			out = new RAFOutputStream(accessFile);
			pricePro.load(in);
			int isSelled = Integer.parseInt(pricePro.getProperty("isSelled").trim());
			if (isSelled == 2)
				return false;
			accessFile.seek(0);
			pricePro.setProperty("isSelled", String.valueOf(bean.getIsSelled()));
			pricePro.store(out, "");
			out.flush();
			bean.setMaxprice(Double.parseDouble(pricePro.getProperty("maxPrice").trim()));
			bean.setRealTime(Long.parseLong(pricePro.getProperty("realTime").trim()));
			bean.setUserName(pricePro.getProperty("userName"));
			bean.setUserId(pricePro.getProperty("userId"));
			// bean.setUserName(pricePro.getProperty("userName").equals("") ?"none":pricePro.getProperty("userName"));
			// bean.setUserId(pricePro.getProperty("userId").equals("")? "0" : pricePro.getProperty("userId"));
			// 写读数据文件
			Properties readPro = new Properties();
			readPro.load(new FileInputStream(readFile));
			OutputStream outStream = new FileOutputStream(readFile);
			readPro.setProperty("isSelled", String.valueOf(bean.getIsSelled()));
			readPro.store(outStream, "");
			outStream.flush();
			outStream.close();
			pricePro = null;
			readPro = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (lock != null) {
				lock.release();
				lock = null;
			}
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
		}
		return true;
	}

	/**
	 * 读数据文件
	 * 
	 * @param goodsBean
	 * @return
	 */
	public static GoodsBean readprice(GoodsBean goodsBean) {
		String path = getSharePropertiesPath();
		File readFile = new File(path + "/read_" + goodsBean.getGoodsId() + ".properties");
		Properties propFile = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(readFile);
			propFile.load(in);
			goodsBean.setBegTime(Long.parseLong(propFile.getProperty("begTime").trim()));
			goodsBean.setEndTime(Long.parseLong(propFile.getProperty("endTime").trim()));
			goodsBean.setRealTime(Long.parseLong(propFile.getProperty("realTime").trim()));
			goodsBean.setUserName(propFile.getProperty("userName").trim());
			goodsBean.setIsSelled(Integer.parseInt(propFile.getProperty("isSelled").trim()));
			goodsBean.setMaxprice(Double.parseDouble(propFile.getProperty("maxPrice").trim()));
			propFile = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
					readFile = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return goodsBean;
	}

	/**
	 * 共享文件路径
	 * 
	 * @return
	 */
	public static String getSharePropertiesPath() {
		InputStream in = GoodsFileUtils.class.getResourceAsStream("auction.properties");
		// InputStream in = GoodsFileUtils.class.getResourceAsStream("/auction.properties");
		Properties pro = new Properties();
		String path = "";
		try {
			pro.load(in);
			path = pro.getProperty("sharePath");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return path;
	}

	public static void main(String[] args) {
		// getSharePropertiesPath();
		// GoodsBean goodsbean = new GoodsBean();
		// goodsbean.setGoodsId("1");
		// readprice(goodsbean);

		GoodsBean goodsbean = new GoodsBean();
		goodsbean.setGoodsId("1");
		try {
			updatePrice(goodsbean);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
