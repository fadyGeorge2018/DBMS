package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import saving.SaveXML;

public class DatabaseMangement implements IDatabase {
	private LinkedList<LinkedList<LinkedList<String>>> allTables = new LinkedList<LinkedList<LinkedList<String>>>();
	private LinkedList<LinkedList<String>> table = new LinkedList<LinkedList<String>>();
	private LinkedList<String> helpNode = new LinkedList<String>();
	private LinkedList<LinkedList<String>> loading = new LinkedList<LinkedList<String>>();
	private LinkedList<Object> rowsIndex = new LinkedList<>();
	private LinkedList<Object> columnsIndex = new LinkedList<>();
	private String databaseName;
	public SaveXML save = new SaveXML();

	public DatabaseMangement() {
		setLoading(save.getSavedList());
		setAlltables(save.loadXML(save.getSavedList()));
	}

	public void CreatDataBase(String DBName) {
		boolean check = findDatabase(DBName);
		if (check == true) {
			System.out.println("tha name of database: " + DBName + "is duplicated ");
		} else {
			LinkedList<LinkedList<String>> help = new LinkedList<LinkedList<String>>();
			LinkedList<String> data = new LinkedList<>();
			data.add(DBName);
			help.add(data);
			allTables.add(help);
		}
	}

	public void CreatTable(String TableName, LinkedList<String> ColumnsName, LinkedList<String> ColumnsType) {
		boolean found = false;
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		boolean check = findDatabase(databaseName);
		int index;
		int correct = 0;
		for (int k = 0; k < ColumnsName.size(); k++) {
			for (int j = k + 1; j < ColumnsName.size(); j++) {
				if (ColumnsName.get(k).equals(ColumnsName.get(j))) {
					System.out.println(ColumnsName.get(k) + "column is duplicated");
					return;
				}
			}
		}
		if (check == false) {
			System.out.println("the name of database: " + databaseName + " is not found");

		} else {
			index = checkTableIntoDatabase(databaseName, TableName);

			if (index == -1 && ColumnsName.size() == ColumnsType.size()) { // index==-1
				for (int s = 0; s < allTables.size(); s++) {
					if (allTables.get(s).get(0).get(0).equals(databaseName) && allTables.get(s).get(0).size() == 1) {
						allTables.get(s).get(0).add(TableName);
						correct = s;
						found = true;
					}
				}
				table = new LinkedList<LinkedList<String>>();
				helpNode = new LinkedList<>();
				LinkedList<String> socend = new LinkedList<>();

				helpNode.add(databaseName);
				helpNode.add(TableName);
				socend.add(databaseName);
				socend.add(TableName);

				if (found == false) {// new table
					table.add(helpNode);
					allTables.add(table);
				}
				for (int ind = 0; ind < ColumnsName.size(); ind++) {
					socend.add(ColumnsName.get(ind));

				}
				loading.add(socend);

				for (int k = 0; k < ColumnsType.size(); k++) {
					helpNode = new LinkedList<String>();
					helpNode.add(ColumnsType.get(k));
					helpNode.add(ColumnsName.get(k));
					if (found == true) {
						allTables.get(correct).add(helpNode);
					}
					table.add(helpNode);

				}
				save.saveXML(allTables, loading);

			} else {
				if (index != -1)
					System.out.println("the tablename: " + TableName + "is duplicate");
				else
					System.out.println("column name size is not equal size of column type");

			}

		}
	}

	public void InsertIntoTable(String TableName, LinkedList<String> Values) {
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		int index;
		index = checkTableIntoDatabase(databaseName, TableName);
		if (index == -1) {
			System.out.println("the table name: " + TableName + " is not found");
			return;
		}

		if (Values.size() != allTables.get(index).size() - 1) {

			return;
		}
		for (int i = 0; i < Values.size(); i++) {
			if (checkType(Values.get(i), index, i) == false) {
				System.out.println("invalid type"); /// print two errors
				return;
			}
		}
		for (int k = 0; k < Values.size(); k++) {
			allTables.get(index).get(k + 1).add(Values.get(k));
		}
		save.saveXML(allTables, loading);

	}

	public void selectAll(String TableName) {
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		columnsIndex = new LinkedList<>();
		int index = checkTableIntoDatabase(databaseName, TableName);
		if (index == -1) {
			System.out.println("the table name: " + TableName + " is not found");
			return;
		}
		for (int i = 1; i < allTables.get(index).size(); i++) {
			columnsIndex.add(i);
		}
		printTable(index, columnsIndex);

	}

	public void selectFromTable(String TableName, LinkedList<String> selectedcolum) {
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		boolean found = false;
		columnsIndex = new LinkedList<>();
		int index = checkTableIntoDatabase(databaseName, TableName);
		if (index == -1) {
			System.out.println("the table name: " + TableName + " is not found");
			return;
		}
		for (int i = 0; i < selectedcolum.size(); i++) {
			found = false;
			for (int j = 1; j < allTables.get(index).size(); j++) { // allTables.get(index).size()-1
				if (allTables.get(index).get(j).get(1).equals(selectedcolum.get(i))) {
					found = true;
					columnsIndex.add(j);
				}
			}
			if (found == false) {
				System.out
						.println("the column name:" + selectedcolum.get(i) + "is not found in tha table:" + TableName);
				return;
			}
		}
		printTable(index, columnsIndex);

	}

	public void DeleteFromTable(String TableName, String columnToBeChecked, String valueToBeChecked, String operator) {
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		int index = checkTableIntoDatabase(databaseName, TableName);
		boolean found = false;
		int indexForColumnCondition = -1;
		rowsIndex = new LinkedList<>();
		String ColumnIndex = null;
		if (index == -1) {
			System.out.println("the table name: " + TableName + " is not found");
			return;
		}
		for (int ind = 1; ind < allTables.get(index).size(); ind++) {
			if (allTables.get(index).get(ind).get(1).equals(columnToBeChecked)) {// TableName
				found = true;
				indexForColumnCondition = ind;
				for (int s = 2; s < allTables.get(index).get(ind).size(); s++) {
					if (checkType(allTables.get(index).get(ind).get(s), index, ind - 1) == false) {
						System.out.println("this type is incorrect");
						return;
					}
				}
				ColumnIndex = allTables.get(index).get(ind).get(0);
			}

		}
		if (found == false) {
			System.out.println("the column name:" + columnToBeChecked + " is not found in the table:" + TableName);
			return;
		}

		rowsIndex = getRows(index, operator, ColumnIndex, indexForColumnCondition, valueToBeChecked);
		for (int i = rowsIndex.size() - 1; i > -1; i--) {
			for (int j = 1; j < allTables.get(index).size(); j++) {// allTables.get(index).size()-1
				allTables.get(index).get(j).remove((int) rowsIndex.get(i));
			}
		}
		save.saveXML(allTables, loading);

	}

	public void UpdateTable(String TableName, String conditioncolum, String operator, String conditionvalue,
			LinkedList<String> setcolums, LinkedList<String> setvalues) {
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		int index = checkTableIntoDatabase(databaseName, TableName);
		boolean found = false;
		boolean checkType = false;
		int counter = 0;
		columnsIndex = new LinkedList<>();
		rowsIndex = new LinkedList<>();
		int columnConditionIndex = -1;
		String columnConditionType = null;

		if (index == -1) {
			System.out.println("the table name: " + TableName + " is not found");
			return;
		}
		if (setcolums.size() > allTables.get(index).size() - 1) {
			System.out.println("the update size is larger than columns size");
			return;
		}
		if (setcolums.size() != setvalues.size()) {
			System.out.println("the set columns size is not equals to set values size");
		}

		for (int i = 1; i < allTables.get(index).size(); i++) { // allTables.get(index).size()-1
			if (allTables.get(index).get(i).get(1).equals(conditioncolum)) {
				found = true;
				columnConditionIndex = i;
				columnConditionType = allTables.get(index).get(i).get(0);
			}
		}
		if (found == false) {
			System.out.println("this column name:" + conditioncolum + " is not found" + TableName);
			return;
		}
		for (int i = 0; i < setcolums.size(); i++) {
			for (int j = 1; j < allTables.get(index).size(); j++) { // allTables.get(index).size()-1
				if (allTables.get(index).get(j).get(1).equals(setcolums.get(i))) {
					counter++;
					columnsIndex.add(j);
					checkType = checkType(setvalues.get(i), index, j - 1);
					if (checkType == false) {
						System.out.println("invalid type for column condition");
						return;
					}
				}
			}

		}
		if (counter != setcolums.size()) {
			System.out.println("there is a column is not found in this table");
			return;
		}

		rowsIndex = getRows(index, operator, columnConditionType, columnConditionIndex, conditionvalue);
		updateValues(rowsIndex, setvalues, columnsIndex, index);
		save.saveXML(allTables, loading);

	}

	public void DropDataBase(String DatabaseName) {
		boolean check = findDatabase(DatabaseName);
		int databaseIndex = 0;
		if (check == false) {
			System.out.println("the database:" + DatabaseName + " is not found");
		} else {
			for (int i = allTables.size() - 1; i > -1; i--) {
				if (allTables.get(i).get(0).get(0).equals(DatabaseName))
					allTables.remove(i);
				databaseIndex = i; /// remove
			}
			databaseName = null;
			save.dropDatabase(DatabaseName);

			save.saveXML(allTables, loading);
		}
	}

	public void DropTable(String TableName) {
		if (databaseName == null) {
			System.out.println("you have to use 'USE' statement");
			return;
		}
		int index = checkTableIntoDatabase(databaseName, TableName);
		if (index == -1) {
			System.out.println("the table name:" + TableName + " is not found");
		} else {
			allTables.remove(index);
			save.dropTable(databaseName, TableName);
			save.saveXML(allTables, loading);

		}
	}

	private boolean findDatabase(String DatabaseName) {
		boolean check = false;
		for (int i = 0; i < allTables.size(); i++) {
			if (allTables.get(i).get(0).get(0).equals(DatabaseName)) {
				check = true;
				break;
			}
		}
		return check;
	}

	public void setDatabase(String DbName) {
		boolean found = false;
		for (int i = 0; i < allTables.size(); i++) {
			if (allTables.get(i).get(0).get(0).equals(DbName)) {
				databaseName = DbName;
				found = true;
			}
		}
		if (found == false) {
			System.out.println("the name:" + DbName + " is not found");
		}

	}

	private int checkTableIntoDatabase(String databasename, String tablename) {
		int index = -1;

		for (int i = 0; i < allTables.size(); i++) {
			if (allTables.get(i).get(0).get(0).equals(databasename)) {
				if (allTables.get(i).get(0).size() == 1) {
					index = -1;
					break;
				} else if (allTables.get(i).get(0).get(1).equals(tablename)) {
					index = i;
					break;
				}
			}

		}
		return index;
	}

	public boolean checkType(String requiredValues, int indexForTable, int i) {
		boolean check = true;
		try {
			String type = allTables.get(indexForTable).get(i + 1).get(0);
			if (!type.equals("varchar(255)")) { // !type.equals("String")
				Integer test = Integer.parseInt(requiredValues);
			}
		} catch (NumberFormatException e) {
			check = false;
		}

		return check;
	}

	private LinkedList<Object> getRows(int tableIndex, String operator, String type, int columnConditionPlace,
			String valueForConditoin) {
		LinkedList<Object> rows = new LinkedList<>();
		if (type.equals("int")) {
			try {

				int value = Integer.valueOf(valueForConditoin);
				if (operator.equals("<")) {
					for (int i = 2; i < allTables.get(tableIndex).get(columnConditionPlace).size(); i++) {
						int valueInTable = Integer.valueOf(allTables.get(tableIndex).get(columnConditionPlace).get(i));
						if (valueInTable < value) {
							rows.add(i);
						}
					}
				} else if (operator.equals(">")) {
					for (int i = 2; i < allTables.get(tableIndex).get(columnConditionPlace).size(); i++) {
						int valueInTable = Integer.valueOf(allTables.get(tableIndex).get(columnConditionPlace).get(i));
						if (valueInTable > value) {
							rows.add(i);
						}
					}
				} else if (operator.equals("=")) {
					for (int i = 2; i < allTables.get(tableIndex).get(columnConditionPlace).size(); i++) {
						int valueInTable = Integer.valueOf(allTables.get(tableIndex).get(columnConditionPlace).get(i));
						if (valueInTable == value) {
							rows.add(i);
						}
					}
				}

			} catch (NumberFormatException e) {
				System.out.println("Invalid data type for condition\n");

			}

		} else if (type.equals("varchar(255)")) {
			if (operator.equals("<")) {
				for (int i = 2; i < allTables.get(tableIndex).get(columnConditionPlace).size(); i++) {
					String valueFromTable = allTables.get(tableIndex).get(columnConditionPlace).get(i);
					if (valueFromTable.compareTo(valueForConditoin) < 0) {
						rows.add(i);
					}
				}
			} else if (operator.equals(">")) {
				for (int i = 2; i < allTables.get(tableIndex).get(columnConditionPlace).size(); i++) {
					String valueFromTable = allTables.get(tableIndex).get(columnConditionPlace).get(i);
					if (valueFromTable.compareTo(valueForConditoin) > 0) {
						rows.add(i);
					}
				}
			} else if (operator.equals("=")) {
				for (int i = 2; i < allTables.get(tableIndex).get(columnConditionPlace).size(); i++) {
					String valueFromTable = allTables.get(tableIndex).get(columnConditionPlace).get(i);
					if (valueFromTable.equals(valueForConditoin)) {
						rows.add(i);
					}
				}
			}
		}
		return rows;
	}

	private void updateValues(LinkedList<Object> rowsindex, LinkedList<String> values, LinkedList<Object> columnsIndex,
			int indexForTable) {
		for (int i = 0; i < rowsindex.size(); i++) {
			for (int j = 0; j < columnsIndex.size(); j++) {
				allTables.get(indexForTable).get((int) columnsIndex.get(j)).remove((int) rowsindex.get(i));
				allTables.get(indexForTable).get((int) columnsIndex.get(j)).add((int) rowsindex.get(i), values.get(j));
			}
		}

	}

	private void printTable(int indexForTable, LinkedList<Object> selectedColumns) {
		LinkedList<Object> number = new LinkedList<>();
		int getMaxlengthInColumn = -1;
		int k = 0;
		for (int i = 0; i < selectedColumns.size(); i++) {
			for (int j = 1; j < allTables.get(indexForTable).get((int) selectedColumns.get(i)).size(); j++) {
				if (allTables.get(indexForTable).get((int) selectedColumns.get(i)).get(j)
						.length() > getMaxlengthInColumn) {
					getMaxlengthInColumn = allTables.get(indexForTable).get((int) selectedColumns.get(i)).get(j)
							.length();
				}

			}
			number.add(getMaxlengthInColumn);
			getMaxlengthInColumn = -1;
		}
		for (int i = 0; i < number.size(); i++) {
			System.out.print("-");
			for (int j = 0; j < (int) number.get(i); j++) {
				System.out.print("-");
			}
		}
		System.out.print("-");
		System.out.println("");
		for (int ind = 1; ind < allTables.get(indexForTable).get(1).size(); ind++) {
			for (int j = 0; j < selectedColumns.size(); j++) {
				System.out.print("|");
				System.out.print(allTables.get(indexForTable).get((int) selectedColumns.get(j)).get(ind));
				int spaces = (int) number.get(j)
						- allTables.get(indexForTable).get((int) selectedColumns.get(j)).get(ind).length();
				for (int sp = 0; sp < spaces; sp++) {
					System.out.print(" ");
				}

				k = ind;
			}
			System.out.print("|");
			System.out.println("");
			if (k == 1) {
				for (int i = 0; i < number.size(); i++) {
					System.out.print("-");

					for (int s = 0; s < (int) number.get(i); s++) {
						System.out.print("-");
					}

				}
				System.out.print("-");
				System.out.println("");

			}

		}
		for (int i = 0; i < number.size(); i++) {
			System.out.print("-");
			for (int s = 0; s < (int) number.get(i); s++) {
				System.out.print("-");
			}

		}
		System.out.print("-");
		System.out.println(" ");
		System.out.print(" ");
		System.out.println(" ");

	}

	public void setAlltables(LinkedList<LinkedList<LinkedList<String>>> allTablesFromLoad) {
		allTables = allTablesFromLoad;
	}

	public void setLoading(LinkedList<LinkedList<String>> loadList) {
		loading = loadList;
	}
    public void addColumns (String TableName , String Columnname ,String Columntype){
    	
    }
    public void dropColumn (String TableName,String Columnname){
    	
    }

}