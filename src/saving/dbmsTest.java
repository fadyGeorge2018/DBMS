package saving;

import java.util.LinkedList;

import org.junit.Test;

public class dbmsTest {

	@Test
	public void test() {
		LinkedList<LinkedList<LinkedList<String>>> table = new LinkedList<>();
		LinkedList<String> l1 = new LinkedList<>();
		l1.add("int");
		l1.add("ID");
		l1.add("25");
		l1.add("26");
		l1.add("27");
		l1.add("28");
		l1.add("29");
		LinkedList<String> l2 = new LinkedList<>();
		l2.add("varchar");
		l2.add("NAME");
		l2.add("fady");
		l2.add("mohamed");
		l2.add("ahmed");
		l2.add("tarek");
		l2.add("kareem");
		LinkedList<String> l3 = new LinkedList<>();
		l3.add("Company");
		l3.add("Names");
		LinkedList<String> l11 = new LinkedList<>();
		l11.add("int");
		l11.add("NUMBER");
		l11.add("25");
		l11.add("26");
		l11.add("27");
		l11.add("28");
		l11.add("29");
		LinkedList<String> l22 = new LinkedList<>();
		l22.add("varchar");
		l22.add("STRING");
		l22.add("fady");
		l22.add("mohamed");
		l22.add("ahmed");
		l22.add("tarek");
		l22.add("kareem");
		LinkedList<String> l33 = new LinkedList<>();
		l33.add("Company");
		l33.add("Types");
		LinkedList<LinkedList<String>> ll2 = new LinkedList<>();
		ll2.add(l3);
		ll2.add(l1);
		ll2.add(l2);
		LinkedList<LinkedList<String>> ll3 = new LinkedList<>();
		ll3.add(l33);
		ll3.add(l11);
		ll3.add(l22);
		
		table.add(ll2);
		table.add(ll3);

		LinkedList<LinkedList<LinkedList<String>>> loadedTable = new LinkedList<>();
		SaveXML s = new SaveXML();
		LinkedList<LinkedList<String>> tableDatabaseNames = new LinkedList<>();
		LinkedList<String> dbTab1 = new LinkedList<>();
		dbTab1.add("Company");
		dbTab1.add("Names");
		dbTab1.add("NAME");
		dbTab1.add("ID");
		LinkedList<String> dbTab2 = new LinkedList<>();
		dbTab2.add("Company");
		dbTab2.add("Types");
		dbTab2.add("NUMBER");
		dbTab2.add("STRING");
		tableDatabaseNames.add(dbTab1);
		tableDatabaseNames.add(dbTab2);
		//s.saveXML(table,tableDatabaseNames);
		System.out.println(loadedTable = s.loadXML(tableDatabaseNames));
		//s.dropTable("Company", "Names");
		//s.dropDatabase("Company");
		//System.out.println(s.getSavedList());
		
	}

}
