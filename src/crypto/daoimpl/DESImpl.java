package crypto.daoimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator; 
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import crypto.dao.DES;

public class DESImpl implements DES {

	public static final String KEY_ALGORITHM = "DES";

	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	@Override
	public String initKey() throws Exception {
		// 实例化密钥生成器
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化密钥生成器
		kg.init(56);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获得密钥
		return new String(secretKey.getEncoded());
	}

	@Override
	public Key toKey(String key) throws Exception {
		// 实例化DES密钥材料
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		// 实例化秘密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);
		// 生成秘密私钥
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	@Override
	public String encrypt(String filePath, String dir, String key)
			throws Exception {
		// 还原密钥
		Key k = toKey(key);
		// 读取被加密信息
		String str = readFile(filePath);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		String result = new String(cipher.doFinal(str.getBytes()));
		writeFile(dir, result);
		return result;
	}

	@Override
	public String decrypt(String dir, String key) throws Exception {
		String path = dir + "encode.txt";
		// 还原密钥
		Key k = toKey(key);
		// 读取被解密信息
		String str = readFile(path);
		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化、设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(str.getBytes()));
	}

	/**
	 * 读取文件
	 * 
	 * @param filePath
	 * @return
	 */
	public String readFile(String filePath) {
		String result = null;
		File file = new File(filePath);
		if (file.exists()) {
			String tmp;
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((tmp = br.readLine()) != null)
					result = result + "\n" + tmp;
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void writeFile(String filePath, String str) throws Exception {
		File file = new File(filePath, "encode.txt");
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		} else {
			file.createNewFile();
		}

		try {
			FileOutputStream bw = new FileOutputStream(file);
			bw.write(str.getBytes());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "D:\\1.txt";
		String dir="D:\\";
		DES des = new DESImpl();
		String key = des.initKey();
		String result = des.encrypt(filePath,dir, key);
		System.out.println(result);
		System.out.println(des.decrypt("D:\\", key));

	}

}
