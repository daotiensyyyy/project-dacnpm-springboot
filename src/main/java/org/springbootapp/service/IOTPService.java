package org.springbootapp.service;

public interface IOTPService {
	
	int generateOTP(String key);

	int getOTP(String key);

	void clearOTP(String key);
}
