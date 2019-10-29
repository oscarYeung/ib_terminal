package com.henyep.ib.terminal.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProperitesUtil {
	private static Properties r = new Properties();

	public static void main(String[] args) {
		ProperitesUtil p = new ProperitesUtil();
//		System.out.println("user:  " + p.get("user"));
	}

	public ProperitesUtil() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			if (System.getProperty("os.name").contains("Window")) {
				path = path.replace("classes", "config") + "database.properties";
			} else {
				String _temp = ProperitesUtil.class.getResource("").toString();
				System.out.println("0000000000--" + _temp);
				_temp = _temp.substring(_temp.indexOf("/"), _temp.indexOf("classes"));
				path = _temp + "config/database.properties";
				System.out.println("ProperitesUtil path --" + path);
				// path =
				// "/usr/local/apache-tomcat-6.0.32/acAdmin/WebRoot/WEB-INF/config/base.properties";
			}

			r.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String string(String key) {
		return get(key);
	}

	public static Integer integer(String key) {
		return new Integer(get(key));
	}

	public static Boolean bool(String key) {
		return new Boolean(get(key));
	}

	private static String get(String key) {
		if (null == key)
			return null;

		return r.getProperty(key);
	}

	public HttpSession getSession(HttpServletRequest req) {
		return req.getSession();
	}
}
