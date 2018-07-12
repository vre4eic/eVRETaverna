package eu.vre4eic.eVRETaverna.util;


import java.net.MalformedURLException;
import java.net.URL;

public class Common {
	
	private static String token;
	private static String userId;
	private static String password;
	private static URL evreUrl;
	

	
	
	public static URL getLoginUrl() throws MalformedURLException {
		return new URL(evreUrl+"/NodeService/user/login?username="+userId+"&pwd="+password);
	}

	

	public static URL getEvreUrl() {
		return evreUrl;
	}

	public static void setEvreUrl(URL evreUrl) {
		Common.evreUrl = evreUrl;
	}
	public static String getUserId() {
		return userId;
	}

	public static void setUserId(String userId) {
		Common.userId = userId;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Common.password = password;
	}

	public Common() {
		token="";
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		Common.token = token;
	}

}
