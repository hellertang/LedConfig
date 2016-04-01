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
		// ��ʼ��KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmMD5);
		// �����ܳ�
		SecretKey secretKey = keyGenerator.generateKey();
		// ����ܳ�
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacMD5(String filePath, String key) throws Exception {
		String message = ReadFile(filePath);
		// ��ԭ�ܳ�
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmMD5);
		// ʵ����Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// ��ʼ��Mac
		mac.init(secretKey);
		// ִ����ϢժҪ
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey() throws Exception {
		// ��ʼ��KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA);
		// �����ܳ�
		SecretKey secretKey = keyGenerator.generateKey();
		// ����ܳ�
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA(String filePath, String key) throws Exception {
		String message = ReadFile(filePath);
		// ��ԭ�ܳ�
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA);
		// ʵ����Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// ��ʼ��Mac
		mac.init(secretKey);
		// ִ����ϢժҪ
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey256() throws Exception {
		// ��ʼ��KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA256);
		// �����ܳ�
		SecretKey secretKey = keyGenerator.generateKey();
		// ����ܳ�
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA256(String filePath, String key)
			throws Exception {
		String message = ReadFile(filePath);
		// ��ԭ�ܳ�
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA256);
		// ʵ����Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// ��ʼ��Mac
		mac.init(secretKey);
		// ִ����ϢժҪ
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey384() throws Exception {
		// ��ʼ��KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA384);
		// �����ܳ�
		SecretKey secretKey = keyGenerator.generateKey();
		// ����ܳ�
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA384(String filePath, String key)
			throws Exception {
		String message = ReadFile(filePath);
		// ��ԭ�ܳ�
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA384);
		// ʵ����Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// ��ʼ��Mac
		mac.init(secretKey);
		// ִ����ϢժҪ
		return new String(mac.doFinal(message.getBytes()));
	}

	@Override
	public String initHmacSHAKey512() throws Exception {
		// ��ʼ��KeyGenerator
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmSHA512);
		// �����ܳ�
		SecretKey secretKey = keyGenerator.generateKey();
		// ����ܳ�
		return new String(secretKey.getEncoded());
	}

	@Override
	public String encodeHmacSHA512(String filePath, String key)
			throws Exception {
		String message = ReadFile(filePath);
		// ��ԭ�ܳ�
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), AlgorithmSHA384);
		// ʵ����Mac
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		// ��ʼ��Mac
		mac.init(secretKey);
		// ִ����ϢժҪ
		return new String(mac.doFinal(message.getBytes()));
	}

	/**
	 * ��ȡ�ļ�����
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
