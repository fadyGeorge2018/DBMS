import static org.junit.Assert.*;

import junit.framework.Assert;
import parserForSQL.Validity;

public class test {

	Validity obj = new Validity();
	@org.junit.Test
	public void test() {
		
		
	}
	
	@org.junit.Test
	public void testCreatDataBase (){
		obj.validOrNot("CREATE DATABASE BATA");
		assertEquals(obj.createDataBaseValidity(), false);
		
		obj.validOrNot("CREATEDATABASEBATA");
		assertEquals(obj.createDataBaseValidity(), false);
	}
	

	@org.junit.Test
	public void testCreatTable (){
		obj.validOrNot("CREATE TABLENE table (col2 int);");
		assertEquals(obj.createTableValidity(), false);
		
		obj.validOrNot("CREATE TABLENAME table (col2 int)");//invalid semicolon
		assertEquals(obj.createTableValidity(), false);

		}
	
	@org.junit.Test
	public void insertIntoValidity() {
		obj.validOrNot("INSERT INTO table VALUES ('fa','ahned','8')"); // missed semicolon
		assertEquals(obj.insertIntoValidity(), false);
		
	   obj.validOrNot("INSERT INTO table VALUEs ('fa','ahned',' ');"); // invalid small 
	   assertEquals(obj.insertIntoValidity(), false);

	   obj.validOrNot("INSERT INTO table VALUES ('fa','ahned',' ');"); // invalid empty value
	   assertEquals(obj.insertIntoValidity(), false);
	   
	   obj.validOrNot("INSERT INTO tableVALUES ('fa','ahned',' ');"); // invalid spaces
	   assertEquals(obj.insertIntoValidity(), false);
		
	   obj.validOrNot("INSERT INTO table VALUES ('fa','ahned','8');"); // valid
	   assertEquals(obj.insertIntoValidity(), true);

	}
	
	@org.junit.Test
	public void deleteValidity() {
	
		obj.validOrNot("DELETE FROM TABLE WHERE col1 <> '5';"); // invalid condition
		assertEquals(obj.deleteValidity(), false);
	
		obj.validOrNot("DELETEFROMTABLEWHERE col1 > '5';"); // space condition
		assertEquals(obj.deleteValidity(), false);
	
		obj.validOrNot("DELETE FROM TABLE WHERE col1 > '5';"); // invalid condition
		assertEquals(obj.deleteValidity(), true);
	
	}
	
	@org.junit.Test
	public void dropDataBaseValidity() {
		
		obj.validOrNot("DROP DATABASE  ahmed;");
		assertEquals(obj.dropDataBaseValidity(), false); // invalid

		obj.validOrNot("DROP DATAbas ahmed");
		assertEquals(obj.dropDataBaseValidity(), false); // invalid

		
		obj.validOrNot("DROP DATABASE ahmed");
		assertEquals(obj.dropDataBaseValidity(), true);
	}
	
	@org.junit.Test
	public void dropTableValidity() {
		
		obj.validOrNot("DROP TABLE ahnem;"); // invalid semicolon
		assertEquals(obj.dropTableValidity(),false);
	
		obj.validOrNot("DROPTABLE ahnem");
		assertEquals(obj.dropTableValidity(),false);
	
		
		obj.validOrNot("DROP TABLE ahnem");
		assertEquals(obj.dropTableValidity(),true);
	}
	
	@org.junit.Test
	public void selectAllValidity() {
		
		obj.validOrNot("SELECT * FRO one;");
		assertEquals(obj.selectAllValidity(),false);  //invalid
		

		obj.validOrNot("SELECT*FROM one;");
		assertEquals(obj.selectAllValidity(),false);
	}
	
	@org.junit.Test
	public void select() {
		obj.validOrNot("SELCTcol1,col2 FROM table");
		assertEquals(obj.select(),false);
		
	}
	
	@org.junit.Test
	public void updateTableValidity() {
	
		obj.validOrNot("UPDATE table SET col1='1' WHERE col2='2';");
		assertEquals(obj.updateTableValidity(),true);
	}
	
	@org.junit.Test
	public void useDataBaseValidity() {
		
		obj.validOrNot("US data;");
		assertEquals(obj.useDataBaseValidity(),false);
	}
}
