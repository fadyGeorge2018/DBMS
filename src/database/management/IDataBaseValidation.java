package database.management;
import java.util.LinkedList;

public interface IDataBaseValidation {
		/*
	    Receive a name of database from user
		and create it
		*/
		public void creatDataBase(String dbName);
		
		/*
		Receive a name of Table from user
		and create it in last Created DataBase
		*/
		public void creatTable(String tableName,LinkedList<String> columnsName,LinkedList<String> columnsType );
		
		/*
		 Receive a name of Table that the user insert in it
		 Receive the values of each column respectively 
		 */
		 
		public void insertIntoTable (String tableName , LinkedList<String> values);
		
		public void selectAll (String tableName );
		public void selectFromTable (String tableName,LinkedList<String> selectedColumn );
		
		public void deleteFromTable (String tableName, String columnToBeChecked, String valueToBeChecked,String operator );
		
		public void updateTable(String tableName,String conditionColumn,String operator,String conditionValue,LinkedList <String> setColumns ,LinkedList <String>  setValues);		
		/*
		  Receive a name of database from user
		and delete it
		 */
		 
		public void dropDataBase(String databaseName);
		
		/*
		  Receive a name of Table from user
		and delete it
		 */
		public void dropTable(String tableName);
	
}
