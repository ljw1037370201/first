package com.taotao.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtils;

public class MyTest {
	@Test
	public void demo1() throws Exception{
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.25.133",21);
		ftpClient.login("ftpuser", "ftpuser");
		InputStream inputStream = new FileInputStream(new File("D:\\tupai\\1234.jpg"));
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		boolean setFileType = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		System.out.println(setFileType);
		ftpClient.storeFile("b.jpg", inputStream);
		inputStream.close();
		ftpClient.logout();
	}
	@Test
	public void demo2() throws Exception{
		InputStream input = new FileInputStream(new File("D:\\tupai\\4557.png"));
		boolean uploadFile = FtpUtils.uploadFile("192.168.25.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images", "first", "abc11.jpg", input);
		System.out.println(uploadFile);
	}
}
