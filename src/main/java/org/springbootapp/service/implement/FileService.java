package org.springbootapp.service.implement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import org.springbootapp.service.IFileService;

@Service
public class FileService implements IFileService {

	@Override
	public void save(InputStream is, String path) {
		try {
			OutputStream os = new FileOutputStream(new File(path));
			byte[] arr = new byte[1024 * 100];
			int data;
			while ((data = is.read(arr)) != -1) {
				os.write(arr, 0, data);
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
