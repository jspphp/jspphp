package com.jspphp.tools.file;

import java.io.Serializable;

public class GoodsBean implements Serializable {

	private static final long serialVersionUID = 5624400751560600432L;
	private String goodsId;
	private String goodsName;
	private String userId;
	private String userName;
	private double maxprice;
	private String maxUserName;
	private long begTime;
	private long endTime;
	private long realTime;
	private long overTime;
	private long clickCount;// 竞价次数
	private double earnestMoney;// 保证金
	private int status; // 0:失败,1:成功
	private int isSelled;// 是否售出 0:未开始,1:正在竞拍,2,已售出
	private int isDelay;// 0未延迟;1:延迟

	public int getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(int isDelay) {
		this.isDelay = isDelay;
	}

	public long getClickCount() {
		return clickCount;
	}

	public void setClickCount(long clickCount) {
		this.clickCount = clickCount;
	}

	public double getEarnestMoney() {
		return earnestMoney;
	}

	public void setEarnestMoney(double earnestMoney) {
		this.earnestMoney = earnestMoney;
	}

	public int getIsSelled() {
		return isSelled;
	}

	public void setIsSelled(int isSelled) {
		this.isSelled = isSelled;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMaxUserName() {
		return maxUserName;
	}

	public void setMaxUserName(String maxUserName) {
		this.maxUserName = maxUserName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(double maxprice) {
		this.maxprice = maxprice;
	}

	public long getBegTime() {
		return begTime;
	}

	public void setBegTime(long begTime) {
		this.begTime = begTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getRealTime() {
		return realTime;
	}

	public void setRealTime(long realTime) {
		this.realTime = realTime;
	}

	public long getOverTime() {
		return overTime;
	}

	public void setOverTime(long overTime) {
		this.overTime = overTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
