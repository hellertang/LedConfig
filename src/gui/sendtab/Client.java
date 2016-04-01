package gui.sendtab;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedInputStream;

/**
 *
 */
public class Client extends Socket {

	// private static final InetAddress SERVER_IP = InetAddress.getLocalHost();
	// private static final String SERVER_IP = "127.0.0.1";
	// private static final int SERVER_PORT = 5566;

	private Socket client;
	private FileInputStream fis;
	private DataOutputStream dos;
	private File file;
	// private int i;
	private String filepath;
	private String[] filelist;

	public Client(String IP, int PORT, String PATH) {
		try {
			try {
				String SERVER_IP = IP;
				int SERVER_PORT = PORT;
				String FILE_PATH = PATH;
				// client = new Socket(InetAddress.getLocalHost(), SERVER_PORT);
				client = new Socket(SERVER_IP, SERVER_PORT);
				System.out.println("hello!");
				filepath = FILE_PATH;
				file = new File(filepath);
				filelist = file.list();

				/*
				 * file[0] = new File("/home/big_tang/socket/client/1.jpg");
				 * file[1] = new File("/home/big_tang/socket/client/1.txt");
				 * file[2] = new File("/home/big_tang/socket/client/2.jpg");
				 * 
				 * System.out.println("文件名: "+file[1].getName()+", 文件大小: "
				 * +file[1].length());
				 * System.out.println("文件名: "+file[2].getName()+", 文件大小: "
				 * +file[2].length());
				 * System.out.println("文件名: "+file[3].getName()+", 文件大小: "
				 * +file[3].length());
				 */
				// System.out.println("请输入：");
				BufferedInputStream in = new BufferedInputStream(
						client.getInputStream());
				// DataOutputStream out = new
				// DataOutputStream(client.getOutputStream());
				OutputStream out = client.getOutputStream();
				// BufferedReader wt = new BufferedReader(new
				// InputStreamReader(System.in));
				// PrintWriter out1 = new PrintWriter(client.getOutputStream());
				// ----------------------------------------------------------------------------------------------------
				String set = "set";
				byte[] newSet = set.getBytes();
				System.out.println(set);
				System.out.println(newSet);
				out.write(newSet);
				// out1.println(set);
				out.flush();

				//
				int setting = (1 << 10) | (2 << 8) | (3 << 6) | (3 << 4)
						| (2 << 2) | 1;
				System.out.println(" setting send =  " + setting);
				String s = String.valueOf(setting);
				byte[] newSetting = s.getBytes();
				out.write(newSetting);
				out.flush();
				//
				while (true) {
					byte[] b = new byte[1024];
					int lenth = 0;
					lenth = in.read(b);
					String ret = new String(b, 0, lenth).trim();
					System.out.println("receive ack =" + ret);
					if (ret.equals("ACK")) {
						System.out.println(" receive ACK");
						break;
					}
				}

				String prm = "prm_num";
				byte[] newPrm = prm.getBytes();
				out.write(newPrm);
				out.flush();
				//
				int prm_num = filelist.length;
				String s1 = String.valueOf(prm_num);
				byte[] newPrm_num = s1.getBytes();
				System.out.println("prm_num send = " + prm_num);
				out.write(newPrm_num);
				out.flush();
				//
				while (true) {
					byte[] b = new byte[1024];
					int lenth = 0;
					lenth = in.read(b);
					String ret2 = new String(b, 0, lenth).trim();
					System.out.println("receive ack2 =" + ret2);
					if (ret2.equals("ACK")) {
						System.out.println(" receive ACK");
						break;
					}
				}
				// ---------------111111111111111111111111--------------------------------------------------------------------------------------------------
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + filelist[i]);
					System.out.println(readfile.getPath());

					String size = "data_size";
					System.out.println("size");
					byte[] newSize = size.getBytes();
					System.out.println(newSize);
					out.write(newSize);
					System.out
							.println("size<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					out.flush();
					//
					long s2 = readfile.length();
					System.out.println("data size send = " + s2);
					String s3 = Long.toString(s2);
					String buffer1 = String.format("%-15s", s3);
					String buffer2 = readfile.getName();
					String buffer = buffer1 + buffer2;
					byte[] newBuffer = buffer.getBytes();
					out.write(newBuffer);
					out.flush();
					//
					/*
					 * String buffer2=file.getName();
					 * System.out.println("data size send = "+ buffer2); byte[]
					 * newBuffer2=buffer1.getBytes(); out.write(newBuffer2);
					 * out.flush();
					 */

					while (true) {
						byte[] b = new byte[1024];
						int lenth = 0;
						lenth = in.read(b);
						String ret3 = new String(b, 0, lenth).trim();
						System.out.println("receive ack3 =" + ret3);
						if (ret3.equals("ACK")) {
							System.out.println(" receive ACK");
							break;
						}
					}

					//
					System.out.println("now send data: ");
					// out.write(System.in);
					//
					String data = "data";
					System.out.println("data \n");
					byte[] newData = data.getBytes();
					out.write(newData);
					out.flush();
					//
					fis = new FileInputStream(readfile);
					dos = new DataOutputStream(client.getOutputStream());
					//
					byte[] sendBytes = new byte[1024];
					int length = 0;
					while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
						dos.write(sendBytes, 0, length);
						dos.flush();
					}
					while (true) {
						byte[] b = new byte[1024];
						int lenth = 0;
						lenth = in.read(b);
						String ret4 = new String(b, 0, lenth).trim();
						System.out.println("receive ack =" + ret4);
						if (ret4.equals("ACK")) {
							System.out.println(" receive ACK");
							break;
						}
					}
				}
				// ------------------------------------------------------------------------------------------

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null)
					fis.close();
				if (dos != null)
					dos.close();
				client.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * public static void main(String[] args) throws Exception { new Client1();
	 * }
	 */
}
