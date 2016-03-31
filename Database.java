import java.util.ArrayList;

public class Database {
	private Table subs;//Subscriptions
	private Table emps;//employees
	private Table pubs;//publications 
	
	//Subscriptions Constants
	private final int CUSTOMER_ID = 0;
	private final int NAME = 1;
	private final int ADDRESS = 2;
	private final int DISTRICT_ID = 3;
	private final int ON_HOLIDAY = 4;
	private final int BILLING_DATE = 5;
	private final int MONTHLY_TOTAL = 6;
	private final int PUBS = 7;
	private final int ON_HOLIDAY_ENDDATE = 8;
	
	//Employees Constants
	private final int E_EMPLOYEE_ID = 0;
	private final int E_NAME = 1;
	private final int E_DISTRICT_ID = 2;
	private final int E_RANK = 3;
	
	//Publications Constants
	private final int P_PUB_ID = 0;
	private final int P_PRICE = 1;
	
	//Extra Constants
	private final int NUM_PUBS = 4;//The number of publications
	
	public Database(){
		subs = new Table("subs");
		emps = new Table("emps");
		pubs = new Table("pubs");
	}
	
	public boolean getConnection(){//Opens all of the tables from the files
		return subs.load() && emps.load() && pubs.load();
	}
	
	public boolean closeConnection(){//Saves all of the tables to the files
		return subs.save() && emps.save() && pubs.save();
	}
	
	public ArrayList<ArrayList<String>> searchByName(String key){
		return subs.multisearch(NAME, key);
	}
	
	public ArrayList<ArrayList<String>> searchByAddress(String key){
		return subs.multisearch(ADDRESS, key);
	}
	
	public ArrayList<ArrayList<String>> seachByDistrict(String key){
		return subs.multisearch(DISTRICT_ID, key);
	}
	
	public ArrayList<String> searchByDeliveryPersonID(String key){//Returns only ArrayList<String> rather than ArrayList<ArrayList<String>> 
		return emps.search(E_EMPLOYEE_ID, key);
	}
	
	public ArrayList<ArrayList<String>> searchByDeliveryPersonName(String key){
		return emps.multisearch(E_NAME, key);
	}
	
	public ArrayList<ArrayList<String>> searchByOnHoliday(String key){
		return subs.multisearch(ON_HOLIDAY, key);
	}
	
	public ArrayList<ArrayList<String>> searchByBillingDate(String key){
		return subs.multisearch(BILLING_DATE, key); 
	}
	
	public ArrayList<String> searchByCustomerID(String key){//Returns only ArrayList<String> rather than ArrayList<ArrayList<String>> 
		return subs.search(CUSTOMER_ID, key);
	}
	
	public ArrayList<ArrayList<String>> searchByPublication(String key){//Returns null if key is invalid 
		int loc;//the location in the bit string
		try{
			loc = Integer.parseInt(key);
		}
		catch(NumberFormatException e){
			return null;
		}
		
		return subs.multisearchBitString(PUBS, loc);
	}
	
	public boolean editSubscription(String id, String p){//Changes whether the customer with id is subscribed to publication p; returns false if customer doesn't exist or p is not an integer
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		int pub;
		try{
			pub = Integer.parseInt(p);
		}
		catch(NumberFormatException e){
			return false;
		}
		
		char[] subsc = data.get(PUBS).toCharArray();//The publication bit string
		subsc[pub] = (subsc[pub] == '1') ? '0' : '1';//Update the bit string
		
		data.set(PUBS, new String(subsc));
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;		
	}
	
	public boolean editCustomerName(String id, String name){
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		data.set(NAME, name);
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
	
	public boolean editCustomerAddress(String id, String address){
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		data.set(ADDRESS, address);
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
	
	public boolean editCustomerDistrictID(String id, String did){
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		data.set(DISTRICT_ID, did);
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
	
	public boolean editCustomerBillingDate(String id, String date){
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		data.set(BILLING_DATE, date);
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
	
	public boolean deleteCustomer(String id){
		return subs.remove(id);
	}
	
	public boolean createCustomer(String id, String date){//id is the new id, date is the billing date
		ArrayList<String> data = new ArrayList<String>();//New customer
		data.add(id);//id
		data.add("");//name
		data.add("");//address
		data.add("");//district id
		data.add("false");//on holiday
		data.add(date);//billing date
		data.add("");//total
		data.add("0000");//pub id
		data.add("00-00-0000");
		
		return subs.insert(data);
	}
	
	public boolean addOnHoliday(String id, String date){//date is the end date
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		data.set(ON_HOLIDAY_ENDDATE, date);
		data.set(ON_HOLIDAY, "true");
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
	
	public boolean deleteOnHoliday(String id){//date is the end date
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		data.set(ON_HOLIDAY, "false");
		data.set(ON_HOLIDAY_ENDDATE, "00-00-0000");
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
	
	public String getNewCustomerID(){//Returns the first available customer ID
		int id = 0;
		while(subs.search(CUSTOMER_ID, ""+id) != null){
			id++;
		}
		
		return id+"";
	}
	
	public String getPrice(String id){//Returns the price of publication with id; returns null if id doesn't exist
		ArrayList<String> data = pubs.search(P_PUB_ID, id);
		if(data == null){
			return null;
		}
		return data.get(P_PRICE);
	}
	
	public boolean updateMonthlyTotal(String id){//Updates the monthly total for customer with id
		ArrayList<String> data = searchByCustomerID(id); //Get the data for customer id
		if(data == null){
			return false;
		}
		
		double total = 0;//monthly total
		String pubIDs = data.get(PUBS);//the pubs subbed to
		
		for(int i = 0; i < NUM_PUBS; i++){
			if(pubIDs.charAt(i) == '1'){
				total += Double.parseDouble(getPrice(i+""));//Add the price to the total
			}
		}
		
		data.set(MONTHLY_TOTAL, total+"");
		
		subs.remove(id);//Remove old data
		subs.insert(data);//Add new data
		
		return true;
	}
}
