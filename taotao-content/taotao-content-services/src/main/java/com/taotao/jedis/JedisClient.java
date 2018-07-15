package com.taotao.jedis;

public interface JedisClient {
	/**
	 * 存入String类型数据到redis中
	 * @param key 键
	 * @param value 值
	 * @return 返回存入的记录条数
	 */
	String set(String key, String value);
	/**
	 * 根据key取value redis中string类型的数据
	 * @param key 键
	 * @return
	 */
	String get(String key);

	/**
	 * 根据key来删除value
	 * @param key 键
	 * @return
	 */
	long del(String key);
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return
	 */
	Boolean exists(String key);
	/**
	 * 设置key的生存时间
	 * @param key
	 * @param seconds 秒为单位
	 * @return
	 */
	Long expire(String key, int seconds);
	/**
	 * 查看key的生存时间
	 * @param key
	 * @return
	 */
	Long ttl(String key);
	/**
	 * key递增
	 * @param key
	 * @return
	 */
	Long incr(String key);
	/**
	 *设置散列
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);
	/**
	 * 获取散列
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, String field);
	/**
	 * 删除散列
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, String... field);

}
