package database.management;

import java.util.LinkedList;



public class DataBaseManagement {
	private LinkedList<DataBase> databases = new LinkedList<>();
	private String db = null;

	
	public LinkedList<DataBase> getDataBases() {
		return databases;
	}
	public void setDataBase (String name){
		this.db =name;
	}
	public String getDatabase(){
		return this.db;
	}
    public void createDataBase(String name){
    	databases.add(new DataBase(name));
    }
    public void dropDataBase(int index){
    	databases.remove(index);
    }
    public int searchForDataBase(String name){
    	int index =-1;
    	for(int i=0;i<databases.size();i++){
    		if(databases.get(i).equals(name)){
    			index=i;
    		}
    	}
    	return index;
    }
}
