package database.management;

import java.util.LinkedList;

public class DataBase {
	private String name;
	private LinkedList<Table> tables = new LinkedList<>();

	public DataBase(String name) {
		this.name = name;
	}

	public void createTable(String tableName) {
		tables.add(new Table(tableName));
	}

	public String getName() {
		return this.name;
	}

	public LinkedList<Table> getTables() {
		return this.tables;
	}

	public void dropTable(int index) {
		tables.remove(index);
	}

	public int searchForTable(String name) {
		int index = -1;
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getName().equals(name)) {
				index = i;
			}
		}	
		return index;
	}
}
