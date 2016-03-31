/*
 * File specifications for .jtab file
 * 
 * Line 1: the number of fields (x)
 * Line 2 -> x+1: one item in the database
 * Loop previous for each item in the database
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Table {
	ArrayList<ArrayList<String>> fields;//The fields in the table 
	int data;//The number of data in the table
	int numFields;//The number of fields
	String name;//The table's name and the file name
	
	public Table(String name){
		this.data = 0;
		this.fields = new ArrayList<ArrayList<String>>();
		this.numFields = 0;
		this.name = name;
	}
	
	public boolean setFields(int size){//Sets up the fields; returns false if fields have already been set or if size is not applicable
		if(numFields != 0 || size <= 0){
			return false;
		}
		
		for(int i = 0; i < size; i++){//Create the fields
				fields.add(new ArrayList<String>());
		}
		
		numFields = size;
		
		return true;
	}
	
	public boolean isEmpty(){//Returns if the table is empty
		return data == 0;
	}
	
	public boolean insert(ArrayList<String> items){//Adds the passed items to the table; returns false if too many data are passed
		if(items.size() > fields.size()){
			return false;//Not enough room for the data
		}
		
		for(int i = 0; i < items.size(); i++){//Insert all of the items
			fields.get(i).add(items.get(i));//Add item i to field i
		}
		
		data++;//Update the number of data
		
		return true;
	}
	
	public boolean insert(String... items){//Adds the passed items to the table; returns false if too many data are passed
		if(items.length > fields.size()){
			return false;//Not enough room for the data
		}
		
		for(int i = 0; i < items.length; i++){//Insert all of the items
			fields.get(i).add(items[i]);//Add item i to field i
		}
		
		data++;//Update the number of data
		
		return true;
	}
	
	public boolean remove(String key){//Removes the item with passed key; returns false if the key is not found
		int id = -1;//The id to be removed
		
		for(int i = 0; i < fields.get(0).size(); i++){//Search for the key in the first field
			if(fields.get(0).get(i).equals(key)){//if the key is found
				id = i;//Save the id
				break;
			}
		}
		
		if(id == -1){
			return false;//Key was not found
		}
		
		for(int i = 0; i < fields.size(); i++){//Remove the value at given id for all fields
			fields.get(i).remove(id);
		}
		
		data--;//Update the number of data
		
		return true;
	}
	
	public ArrayList<String> search(int field, String key){//Searches field for the key and returns all data on that item; returns null if not found
		if(field >= fields.size()){
			return null;//Field doesn't exist
		}
		
		int id = -1;//The id of the found item
		ArrayList<String> item = new ArrayList<String>();//Contains the found item
		
		for(int i = 0; i < fields.get(field).size(); i++){//Search for the key in the given field
			if(fields.get(field).get(i).equals(key)){//if the key is found
				id = i;//Save the id
				break;
			}
		}
		
		if(id == -1){
			return null;//Key not found
		}
		
		for(int i = 0; i < fields.size(); i++){//Add the values to the item
			item.add(fields.get(i).get(id));
		}
		
		return item;
	}
	
	public ArrayList<ArrayList<String>> multisearch(int field, String key){//Searches field for the key and returns all data on all items with that key in the field; returns null if not found
		if(field >= fields.size()){
			return null;//Field doesn't exist
		}
		
		ArrayList<Integer> id = new ArrayList<Integer>();//The ids of the found items
		ArrayList<ArrayList<String>> item = new ArrayList<ArrayList<String>>(); 
		
		for(int i = 0; i < fields.get(field).size(); i++){//Search for the key in the given field
			if(fields.get(field).get(i).equals(key)){//if the key is found
				id.add(i);//Save the id
			}
		}
		
		if(id.isEmpty()){
			return null;//Key not found
		}
		
		for(int j = 0; j < id.size(); j++){//Loop through all the ids
			int curr = id.get(j);//The current id
			item.add(new ArrayList<String>());//The current item
			for(int i = 0; i < fields.size(); i++){//Add the values to the item
				item.get(j).add(fields.get(i).get(curr));
			}
		}
		
		return item;//Item is an array list of array lists, each of which represents an item
	}
	
	public ArrayList<ArrayList<String>> multisearchBitString(int field, int key){//Searches field for the bit string being 1 at location key and returns all data on all items with that bit in the field; returns null if not found
		if(field >= fields.size()){
			return null;//Field doesn't exist
		}
		
		ArrayList<Integer> id = new ArrayList<Integer>();//The ids of the found items
		ArrayList<ArrayList<String>> item = new ArrayList<ArrayList<String>>(); 
		
		for(int i = 0; i < fields.get(field).size(); i++){//Search for the key in the given field
			if(fields.get(field).get(i).charAt(key) == '1'){//if the bit is true
				id.add(i);//Save the id
			}
		}
		
		if(id.isEmpty()){
			return null;//Key not found
		}
		
		for(int j = 0; j < id.size(); j++){//Loop through all the ids
			int curr = id.get(j);//The current id
			item.add(new ArrayList<String>());//The current item
			for(int i = 0; i < fields.size(); i++){//Add the values to the item
				item.get(j).add(fields.get(i).get(curr));
			}
		}
		
		return item;//Item is an array list of array lists, each of which represents an item
	}
	
	public boolean save(){//Saves the table to 'name'.jtab; returns false if IOException occurred
		try {
			PrintWriter out = new PrintWriter(new File(name + ".jtab"));
			out.println(numFields);//Output the number of fields
			
			for(int i = 0; i < fields.get(0).size(); i++){//The number of items
				for(int j = 0; j < fields.size(); j++){//The number of fields
					out.println(fields.get(j).get(i));//Output the data
				}
			}
			
			out.close();
			
		} 
		catch (IOException e) {
			return false;//Exception occurred 
		}
		return true;
	}
	
	public boolean load(){//Loads the table from 'name'.jtab; returns false if IOException or NumberFormatException occurred
		ArrayList<String> item;//The item to be added
		
		try{
			Scanner in = new Scanner(new File(name + ".jtab"));
			numFields = Integer.parseInt(in.nextLine());//Set numFields
			for(int i = 0; i < numFields; i++){
				fields.add(new ArrayList<String>());
			}
			
			while(in.hasNextLine()){
				item = new ArrayList<String>();//Next item
				for(int i = 0; i < numFields; i++){//Get the item data
					item.add(in.nextLine());
				}
				insert(item);//Add the item to the table
			}
			
			in.close();
		}
		catch (IOException e){
			return false;//Exception occurred
		}
		catch (NumberFormatException e){
			return false;//Exception occurred
		}
		return true;
	}
}
