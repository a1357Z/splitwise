package splitwise;

import java.util.HashMap;
import java.util.Map;

public class User {
	private String userId;
	private Map<String, Integer> money_owe;
	private Map<String, Integer> money_get;
	
	public User(String id) {
		this.userId = id;
		money_owe = new HashMap<>();
		money_get = new HashMap<>();
	}
	
	public String getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, Integer> getMoney_owe() {
		return money_owe;
	}
	
	public void setMoney_owe(Map<String, Integer> owe) {
		this.money_owe = owe;
	}


	public Map<String, Integer> getMoney_get() {
		return money_get;
	}

	public void setMoney_get(Map<String, Integer> getMap) {
		// TODO Auto-generated method stub
		this.money_get = getMap;
	}


}
