package crypto.daoimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;

import crypto.dao.MD5;
import crypto.dao.SHA;

public class SHAImpl implements SHA {
	private static final String Algorithm = "SHA";
	private static final String Algorithm256 = "SHA-256";
	private static final String Algorithm384 = "SHA-384";
	private static final String Algorithm512 = "SHA-512";

	private static MessageDigest md = null;

	@Override
	public String encodeSHA(String filePath) throws Exception {
		String result = null;
		md = MessageDigest.getInstance(Algorithm);
		String message = ReadFile(filePath);
		result = new String(md.digest(message.getBytes()));
		return result;
	}

	@Override
	public String encodeSHA256(String filePath) throws Exception {
		String result = null;
		md = MessageDigest.getInstance(Algorithm256);
		String message = ReadFile(filePath);
		result = new String(md.digest(message.getBytes()));
		return result;
	}

	@Override
	public String encodeSHA384(String filePath) throws Exception {
		String result = null;
		md = MessageDigest.getInstance(Algorithm384);
		String message = ReadFile(filePath);
		result = new String(md.digest(message.getBytes()));
		return result;
	}

	@Override
	public String encodeSHA512(String filePath) throws Exception {
		String result = null;
		md = MessageDigest.getInstance(Algorithm512);
		String message = ReadFile(filePath);
		result = new String(md.digest(message.getBytes()));
		return result;
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
	
	public static void main(String[] args) throws Exception {
		String filePath = "D:\\1.txt";
		SHA t = new SHAImpl();
		System.out.println(t.encodeSHA512(filePath));
	}
}
