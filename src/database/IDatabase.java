package database;
import java.util.LinkedList;

public interface IDatabase {
		/*
	    Receive a name of database from user
		and create it
		*/
		public void CreatDataBase(String DBName);
		
		/*
		Receive a name of Table from user
		and create it in last Created DataBase
		*/
		public void CreatTable(String TableName,LinkedList<String> ColumnsName,LinkedList<String> ColumnsType );
		
		/*
		 Receive a name of Table that the user insert in it
		 Receive the values of each column respectively 
		 */
		 
		public void InsertIntoTable (String TableName , LinkedList<String> Values);
		
		public void selectAll (String TableName );
		public void selectFromTable (String TableName,LinkedList<String> selectedcolum );
		
		public void DeleteFromTable (String TableName, String columnToBeChecked, String valueToBeChecked,String operator );
		
		public void UpdateTable(String TableName,String conditioncolum,String operator,String conditionvalue,LinkedList <String> setcolums ,LinkedList <String>  setvalues);		
		/*
		  Receive a name of database from user
		and delete it
		 */
		 
		public void DropDataBase(String DatabaseName);
		
		/*
		  Receive a name of Table from user
		and delete it
		 */
		public void DropTable(String TableName);
	
}
