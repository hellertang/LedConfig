package crypto.dao;

public interface SHA {

	/**
	 * SHAժҪ�㷨ʵ��
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA(String filePath) throws Exception;

	/**
	 * SHA256ժҪ�㷨ʵ��
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA256(String filePath) throws Exception;

	/**
	 * SHA384ժҪ�㷨ʵ��
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA384(String filePath) throws Exception;
	
	/**
	 * SHA512ժҪ�㷨
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	public String encodeSHA512(String filePath) throws Exception;

}
