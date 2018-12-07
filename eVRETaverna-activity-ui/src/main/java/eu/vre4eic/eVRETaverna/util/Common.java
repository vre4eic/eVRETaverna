/*******************************************************************************
 * Copyright 2018 cesare
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
