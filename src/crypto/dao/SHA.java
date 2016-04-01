package crypto.dao;

public interface SHA {

	/**
	 * SHA摘要算法实现
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA(String filePath) throws Exception;

	/**
	 * SHA256摘要算法实现
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA256(String filePath) throws Exception;

	/**
	 * SHA384摘要算法实现
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA384(String filePath) throws Exception;
	
	/**
	 * SHA512摘要算法
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA512(String filePath) throws Exception;

}
