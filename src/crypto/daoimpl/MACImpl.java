package crypto.daoimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import crypto.dao.MAC;

public class MACImpl implements MAC {

	private static final String AlgorithmMD5 = "HmacMD5";
	private static final String AlgorithmSHA = "HmacSHA1";
	private static final String AlgorithmSHA256 = "HmacSHA256";
	private static final String AlgorithmSHA384 = "HmacSHA384";
	private static final String AlgorithmSHA512 = "HmacSHA512";

	@Override
	public String initHmacMD5Key() throws Exception {
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmMD5);
		// 产生密匙
		SecretKey secretKey = keyGenerator.generateKey();
		// 获得密匙
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacMD5(String filePath, String key) throws Exception {
		String message = ReadFile(filePath);
		// 还原密匙
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmMD5);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey() throws Exception {
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA);
		// 产生密匙
		SecretKey secretKey = keyGenerator.generateKey();
		// 获得密匙
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA(String filePath, String key) throws Exception {
		String message = ReadFile(filePath);
		// 还原密匙
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey256() throws Exception {
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA256);
		// 产生密匙
		SecretKey secretKey = keyGenerator.generateKey();
		// 获得密匙
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA256(String filePath, String key)
			throws Exception {
		String message = ReadFile(filePath);
		// 还原密匙
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA256);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey384() throws Exception {
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA384);
		// 产生密匙
		SecretKey secretKey = keyGenerator.generateKey();
		// 获得密匙
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA384(String filePath, String key)
			throws Exception {
		String message = ReadFile(filePath);
		// 还原密匙
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA384);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey512() throws Exception {
		// 初始化KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA512);
		// 产生密匙
		SecretKey secretKey = keyGenerator.generateKey();
		// 获得密匙
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA512(String filePath, String key)
			throws Exception {
		String message = ReadFile(filePath);
		// 还原密匙
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA384);
		// 实例化Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// 初始化Mac
		mac.init(secretKey);
		// 执行消息摘要
		return new String(mac.doFinal(message.getBytes()));
	}

	/**
	 * 读取文件函数
	 * 
	 * @param filePath
	 * @return result
	 */
	public String ReadFile(String filePath) {
		String res = "";
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				res = res + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
