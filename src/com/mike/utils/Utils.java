package com.mike.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Utils {


	public static void CopyStream(FileInputStream is, FileOutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

}