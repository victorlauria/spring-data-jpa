package br.com.esocial.comum.dao.daogenerico;

import java.util.HashMap;

/**
 * 
 * @author Felipe Regalgo
 */
public class MapForCaseInsensitiveKey extends HashMap<String, String> {

    @Override
    public String get(Object key) {
	return super.get(key.toString().toUpperCase());
    }

    @Override
    public String put(String key, String value) {
	return super.put(key.toUpperCase(), value);
    }
}