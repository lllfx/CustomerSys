package tools;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Tools {

	/**
	 * �Д��ַ����Ƿ���Ч
	 * @param str
	 * @return
	 */
	public static boolean strIsOk(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * �����w�O������Ļ���g
	 * @param component
	 */
	public static void setCenterLoaction(Component component) {
		int fw = component.getWidth();
		int fh = component.getHeight();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();
		component.setLocation((width - fw) / 2, (height - fh) / 2);

	}

}
