package utils;

public class Ping {

	private static Ping addr = new Ping();

	private String IP;
	private String Port;

	public static Ping get() {
		return addr;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getPort() {
		return Port;
	}

	public void setPort(String port) {
		Port = port;
	}

}
