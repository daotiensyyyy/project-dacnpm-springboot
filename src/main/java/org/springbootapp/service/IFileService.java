package org.springbootapp.service;

import java.io.InputStream;

public interface IFileService {
	void save(InputStream is, String path);
}
