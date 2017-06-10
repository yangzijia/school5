package com.b505.bean;

import java.util.Arrays;

public class BeSavingFile
{
	private String fileURL;
	private String fileExtension;
	private byte[] filesByte;
	private boolean timerDeleted;

	public String getFileURL() {
		return fileURL;
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public byte[] getFilesByte() {
		return filesByte;
	}
	public void setFilesByte(byte[] filesByte) {
		this.filesByte = filesByte;
	}
	public boolean isTimerDeleted() {
		return timerDeleted;
	}
	public void setTimerDeleted(boolean timerDeleted) {
		this.timerDeleted = timerDeleted;
	}
	@Override
	public String toString() {
		return "SaveFile [fileExtension=" + fileExtension + ", fileURL="
				+ fileURL + ", filesByte=" + Arrays.toString(filesByte)
				+ ", timerDeleted=" + timerDeleted + "]";
	}
}
