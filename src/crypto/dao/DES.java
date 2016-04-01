package crypto.dao;

import java.security.Key;

public interface DES {

	/**
	 * ��ʼ����Կ
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initKey() throws Exception;

	/**
	 * ת����Կ
	 * 
	 * @param Key
	 * @return
	 * @throws Exception
	 */
	public Key toKey(String Key) throws Exception;

	/**
	 * �����ļ�
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String filePath, String dir, String key) throws Exception;

	/**
	 * �����ļ�
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public String decrypt(String filePath, String key) throws Exception;

}
