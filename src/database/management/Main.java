package database.management;

import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<String> columnNames = new LinkedList<>();
		LinkedList<String> typeNames = new LinkedList<>();
		LinkedList<String> values = new LinkedList<>();
		values.add("st");
		values.add("11");
		values.add("11");

		columnNames.add("col1");
		columnNames.add("col2");
		columnNames.add("col3");

		typeNames.add("varchar(255)");
		typeNames.add("int");
		typeNames.add("int");
		ArgumentValidation A = new ArgumentValidation();
		A.creatDataBase("DB_ONE");
		// A.use("DB_To");
		A.use("DB_ONE");
		A.creatTable("table1", columnNames, typeNames);
		A.creatTable("table2", columnNames, typeNames);
		// A.dropTable("table1");

		A.insertIntoTable("table2", values);
		values = new LinkedList<>();
		values.add("st");
		values.add("11");
		values.add("10000");
		A.insertIntoTable("table2", values);
		// A.insertIntoTable("table1", values);
		A.selectAll("table2");
		A.insertIntoTable("table2", values);
		A.selectAll("table2");
		//A.deleteFromTable("table2", "col3", "10000", "=");
		A.selectAll("table2");
	 columnNames = new LinkedList<>();
		columnNames.add("col3");
		columnNames.add("col1");
		columnNames.add("col2");
		A.selectFromTable("table2",columnNames);
		/*
		 * values = new LinkedList<>(); values.add("st"); values.add("10");
		 * values.add("20"); A.insertIntoTable("table2", values); values = new
		 * LinkedList<>(); values.add("st"); values.add("66"); values.add("11");
		 * A.insertIntoTable("table2", values); A.selectAll("table2");
		 * //A.deleteFromTable("table2", "col3", "11", "="); values = new
		 * LinkedList<>(); values.add("col3"); values.add("col2");
		 * //values.add("col1"); A.creatTable("table1", columnNames, typeNames);
		 * A.selectFromTable("table2", values); values = new LinkedList<>();
		 * values.add("st"); values.add("66"); values.add("11");
		 * A.insertIntoTable("table1", values);
		 */

		// A.insertIntoTable("table2", values);
		// A.insertIntoTable("table2", values);

		// System.out.println(A.dbManage.getDataBases().get(0).getTables().get(0).getColumns().get(0).getValues());
		// System.out.println(A.dbManage.getDataBases().get(0).getTables().get(0).getColumns().get(1).getValues());
		// System.out.println(A.dbManage.getDataBases().get(0).getTables().get(0).getColumns().get(2).getValues());
	}

}
