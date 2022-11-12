package splitwise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SplitwiseSystem {
	private List<User> users;
	
	public SplitwiseSystem() {
		users = new ArrayList<>();
	}
	
	
	public void takeInput(String[] parts) {
//		
		if(parts[0] == "SHOW" ) {
			if(parts.length == 1) {
				Iterator<User> it = users.iterator();
				
				while(it.hasNext()){
				    giveOutput(it.next().getUserId());
			    }
				return;
			}else {
				String uid = parts[1];
				giveOutput(uid);
				return;
			}
		}else {
			String lendingId = parts[1];
			int amount = Integer.parseInt(parts[2]);
			int count = Integer.parseInt(parts[3]);
			int idx = 4;
			
			String[] paidIds = new String[count];
			
			int t = 0, counter = count;
			while(counter-- > 0) {
				paidIds[t++] = parts[idx++]; 
			}
			
			String transType = parts[idx++];
			System.out.println("transType: "+ transType);
			if(transType == "EQUAL") {
				divideEqually(lendingId, amount, paidIds);
				return;
			}else {
				int[] paidAmounts = new int[count]; 
				
				t = 0; counter = count;
				while(counter-- > 0) {
					paidAmounts[t++] = Integer.parseInt(parts[idx++]); 
				}
				
				if(transType == "EXACT") {
					divideExactly(lendingId, amount, paidIds, paidAmounts);
					return;
				}else {
					dividePercentWise(lendingId, amount, paidIds, paidAmounts);
				}
			}
			
		}
	}
	
	public void giveOutput(String uid) {
		User target = this.getOrCreateUserIfNotExists(uid);
		Map<String, Integer> oweMap = target.getMoney_owe();
    	Map<String, Integer> getMap = target.getMoney_get();
    	
    	//scan all the other users and print the values
    	Iterator<User> it = users.iterator();
		while(it.hasNext()){
			User other = it.next();
		    if(other == target) {
		    	continue;
		    }
		    
		    int amountOwe = !oweMap.containsKey(other.getUserId()) ? 0 : oweMap.get(other.getUserId());
		    int amountGet = !getMap.containsKey(other.getUserId()) ? 0 : getMap.get(other.getUserId());
		    
		    amountOwe = amountOwe - amountGet;
		    if(amountOwe > 0) {
		    	System.out.println("User: " + uid + "owes User: " + other.getUserId()+ " :" + amountOwe);
		    }else if(amountOwe < 0) {
		    	System.out.println("User: " + other.getUserId() + "owes User: " +uid + " :" + amountOwe);
		    }else {
		    	System.out.println("No balances");
		    }
	    }
    	
	}
	
	private void divideExactly(String lendingId, int amount, String[] paidIds, int[] paidAmounts) {
		// TODO Auto-generated method stub
		for(int i=0;i<paidIds.length;i++) {
			
			int perhead = paidAmounts[i];
			divisionLogic(lendingId, perhead, paidIds[i]);
		}
	}

	

	private void dividePercentWise(String lendingId, int amount, String[] paidIds, int[] paidPercent) {
		// TODO Auto-generated method stub
		for(int i=0;i<paidIds.length;i++) {
			
			int perhead = (paidPercent[i]*amount)/100;
			divisionLogic(lendingId, perhead, paidIds[i]);
		}
	}
	
	
	private void divideEqually(String lendingId, int amount, String[] paidIds) {
		int perhead = amount/paidIds.length;
		
		for(int i=0;i<paidIds.length;i++) {
			
			
			divisionLogic(lendingId, perhead, paidIds[i]);
		}
	}
	
	private User getOrCreateUserIfNotExists(String uid) {
		Iterator<User> it = users.iterator();
		while(it.hasNext()){
			User target = it.next();
		    if(target.getUserId() == uid) {
		    	//found a match
		    	return target;
		    }
	    }
		
		//user not found so create a new one
		User member = new User(uid);
		users.add(member);
		return member;
	}
	
	private void divisionLogic(String lendingId, int amount, String receivingId) {
		
		
		//make sure to create both the lending and receiving user first if not existing
		User lender = getOrCreateUserIfNotExists(lendingId);
		User receiver = getOrCreateUserIfNotExists(receivingId);
		
		
		    	
    	Map<String, Integer> oweMap = receiver.getMoney_owe();
    	Map<String, Integer> getMap = lender.getMoney_get();
    	
    	//for the receiver of money
    	if(oweMap.containsKey(lendingId)) {
    		//we already owe some money
    		oweMap.put(lendingId, oweMap.get(lendingId) + amount);
    	}else {
    		oweMap.put(lendingId, amount);
    	}
    	//update how much the receiver owes
    	receiver.setMoney_owe(oweMap);
    	
    	//for the lender of money
    	if(getMap.containsKey(receivingId)) {
    		//we already get some money
    		getMap.put(receivingId, getMap.get(receivingId) + amount);
    	}else {
    		getMap.put(receivingId, amount);
    	}
    	
    	//update how much lender gets
    	lender.setMoney_get(getMap);
		    
	    
		
		
	}
}
