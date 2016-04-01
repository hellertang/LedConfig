package crypto.dao;

public interface MAC {

	/**
	 * ��ʼ��HmacMD5��Կ
	 * @return
	 * @throws Exception
	 */
	public String initHmacMD5Key() throws Exception;

	/**
	 * HmacMD5�㷨ʵ��
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacMD5(String filePath, String key) throws Exception;

	/**
	 * ��ʼ��HmacSHA��Կ
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey() throws Exception;

	/**
	 * HmacSHA�㷨ʵ��
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA(String filePath, String key) throws Exception;

	/**
	 * ��ʼ��HmacSHA256��Կ
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey256() throws Exception;
	
	/**
	 * HmacSHA256�㷨ʵ��
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA256(String filePath, String key) throws Exception;

	/**
	 * ��ʼ��HmacSHA384��Կ
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey384() throws Exception;

	/**
	 * HmacSHA384�㷨ʵ��
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA384(String filePath, String key) throws Exception;
	
	/**
	 * ��ʼ��HmacSHAKey512��Կ
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey512() throws Exception;

	/**
	 * HmacSHA512�㷨ʵ��
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA512(String filePath, String key) throws Exception;
}
