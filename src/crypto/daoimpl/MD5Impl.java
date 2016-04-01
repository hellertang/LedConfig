package crypto.daoimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;

import crypto.dao.MD5;

public class MD5Impl implements MD5 {

	private static final String Algorithm = "MD5";
	private static MessageDigest md5 = null;

	@Override
	public String EnCode(String filePath) throws Exception {
		String result = null;
		md5 = MessageDigest.getInstance(Algorithm);
		String message = ReadFile(filePath);
		result = new String(md5.digest(message.getBytes()));
		return result;
	}

	/**
	 * 读取文件函数
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

	public static void main(String[] args) throws Exception {
		String filePath = "D:\\1.txt";
		MD5 t = new MD5Impl();
		System.out.println(t.EnCode(filePath));
	}
}
