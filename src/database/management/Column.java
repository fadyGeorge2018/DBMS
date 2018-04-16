package database.management;

import java.util.LinkedList;

import parserForSQL.Int;
import parserForSQL.Type;
import parserForSQL.VarChar;

public class Column {
	private String name;
	private String typee;
	private Type type;
	private LinkedList<String> values = new LinkedList<>();

	public Column(String name, String type) {
		this.name = name;
		this.typee = type;
		if (type.equals("int")) {
			this.type = new Int();
		} else if (type.equals("varchar(255)")) {
			this.type = new VarChar();
		}
	}

	public void add(String value) {
		values.add(value);
	}

	public String getName() {
		return this.name;
	}

	public Type getType() {
		return this.type;
	}

	public LinkedList<String> getValues() {
		return this.values;
	}

	public void insertValue(String value) {
		values.add(value);
	}

	public void deleteValue(int index) {
		values.remove(index);
	}

	public LinkedList<Object> getRows(String operator, String value) {
		LinkedList<Object> rows = new LinkedList<>();
		for (int i = 0; i < values.size(); i++) {
			if (type.compareValue(operator, values.get(i), value) == true) {
				rows.add(i);
			}
		}

		return rows;
	}
	public void printValues(int index){
		System.out.print(values.get(index));
		System.out.print("   ");
	}
}
