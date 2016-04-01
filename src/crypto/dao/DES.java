package crypto.dao;

import java.security.Key;

public interface DES {

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initKey() throws Exception;

	/**
	 * 转换密钥
	 * 
	 * @param Key
	 * @return
	 * @throws Exception
	 */
	public Key toKey(String Key) throws Exception;

	/**
	 * 加密文件
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String filePath, String dir, String key) throws Exception;

	/**
	 * 解密文件
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public String decrypt(String filePath, String key) throws Exception;

}
