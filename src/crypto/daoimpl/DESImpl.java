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
		// ʵ������Կ������
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// ��ʼ����Կ������
		kg.init(56);
		// ������Կ
		SecretKey secretKey = kg.generateKey();
		// �����Կ
		return new String(secretKey.getEncoded());
	}

	@Override
	public Key toKey(String key) throws Exception {
		// ʵ����DES��Կ����
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		// ʵ����������Կ����
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);
		// ��������˽Կ
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	@Override
	public String encrypt(String filePath, String dir, String key)
			throws Exception {
		// ��ԭ��Կ
		Key k = toKey(key);
		// ��ȡ��������Ϣ
		String str = readFile(filePath);
		// ʵ����
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		String result = new String(cipher.doFinal(str.getBytes()));
		writeFile(dir, result);
		return result;
	}

	@Override
	public String decrypt(String dir, String key) throws Exception {
		String path = dir + "encode.txt";
		// ��ԭ��Կ
		Key k = toKey(key);
		// ��ȡ��������Ϣ
		String str = readFile(path);
		// ʵ����
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// ��ʼ��������Ϊ����ģʽ
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(str.getBytes()));
	}

	/**
	 * ��ȡ�ļ�
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
