package sample.hello.jersey.storage;

import java.util.HashMap;
import java.util.Map;

import sample.hello.jersey.bean.Provider;

public class ProviderStore {

	private static Map<String,Provider> store;
	private static ProviderStore instance = null;
	
	private ProviderStore() {
		store = new HashMap<String,Provider>();
		initOneContact();
	}
	
	public static Map<String,Provider> getStore() {
		if(instance==null) {
			instance = new ProviderStore();
		}
		return store;
	}
	
	private static void initOneContact() {
		
		Provider cHuang = new Provider("provider1", "Huang Yi Ming", "address");
		store.put(cHuang.getId(), cHuang);
	}

}
