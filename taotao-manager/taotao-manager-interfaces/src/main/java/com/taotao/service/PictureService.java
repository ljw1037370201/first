package com.taotao.service;

import com.taotao.common.pojo.PictureResult;

public interface PictureService {
	/**
	 * 上传图片，注意：不写入数据库
	 * @param bytes 文件的byte数组
	 * @param name 文件的名字
	 * @return 返回的结果PictureResult对象，是一个jeson数据
	 */
	public PictureResult uploadFile(byte[] bytes, String name);
}
