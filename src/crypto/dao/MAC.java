package crypto.dao;

public interface MAC {

	/**
	 * 初始化HmacMD5密钥
	 * @return
	 * @throws Exception
	 */
	public String initHmacMD5Key() throws Exception;

	/**
	 * HmacMD5算法实现
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacMD5(String filePath, String key) throws Exception;

	/**
	 * 初始化HmacSHA密钥
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey() throws Exception;

	/**
	 * HmacSHA算法实现
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA(String filePath, String key) throws Exception;

	/**
	 * 初始化HmacSHA256密钥
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey256() throws Exception;
	
	/**
	 * HmacSHA256算法实现
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA256(String filePath, String key) throws Exception;

	/**
	 * 初始化HmacSHA384密钥
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey384() throws Exception;

	/**
	 * HmacSHA384算法实现
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA384(String filePath, String key) throws Exception;
	
	/**
	 * 初始化HmacSHAKey512密钥
	 * @return
	 * @throws Exception
	 */
	public String initHmacSHAKey512() throws Exception;

	/**
	 * HmacSHA512算法实现
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeHmacSHA512(String filePath, String key) throws Exception;
}
