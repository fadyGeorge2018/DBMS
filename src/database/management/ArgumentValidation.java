package database.management;

import java.util.LinkedList;

public class ArgumentValidation implements IDataBaseValidation {
	public DataBaseManagement dbManage = new DataBaseManagement();
	private int dbUsed = -1;
	private String dbUsedName = null;

	@Override
	public void creatDataBase(String dbName) {
		// TODO Auto-generated method stub
		int index =dbManage.searchForDataBase(dbName);
		if (index == -1) {
			dbManage.createDataBase(dbName);
		} else {
			System.out.println("DataBase is duplicated");
		}

	}

	@Override
	public void creatTable(String tableName, LinkedList<String> columnsName, LinkedList<String> columnsType) {
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index == -1) {
				dbManage.getDataBases().get(dbUsed).createTable(tableName);
				dbManage.getDataBases().get(dbUsed).getTables().getLast().createColumns(columnsName, columnsType);
			} else {
				System.out.println("Table is duplicated");
			}
		} else {
			System.out.println("you have to use the 'use' statement");
		}

	}

	@Override
	public void insertIntoTable(String tableName, LinkedList<String> values) {
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				if (values.size() == dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().size()) {

					dbManage.getDataBases().get(dbUsed).getTables().get(index).fillColumns(values);
				} else {
					System.out.println("size of values is not equale of size of columns");
				}
			}
		} else {
			System.out.println("you have to use the 'use' statement");
		}
	}

	@Override
	public void selectAll(String tableName) {
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				LinkedList<Object> columns = new LinkedList<>();
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
						.size(); i++) {
					columns.add(i);
				}
				dbManage.getDataBases().get(dbUsed).getTables().get(index).printColumns(columns);
			} else {
				System.out.println("this table name is not found");
			}
		} else {
			System.out.println("you have to use the 'use' statement");
		}

	}

	@Override
	public void selectFromTable(String tableName, LinkedList<String> selectedColumn) {
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				LinkedList<Object> columns = new LinkedList<>();
				for (int i = 0; i < selectedColumn.size(); i++) {
					int indexOfColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
							.searchForColumn(selectedColumn.get(i));
					if (indexOfColumn == -1) {
						System.out.println("there is column not found");
						return;
					} else {
						columns.add(indexOfColumn);
					}
				}
				dbManage.getDataBases().get(dbUsed).getTables().get(index).printColumns(columns);
			} else {
				System.out.println("this table name is not found");
			}
		} else {
			System.out.println("you have to use the 'use' statement");
		}

	}

	@Override
	public void deleteFromTable(String tableName, String columnToBeChecked, String valueToBeChecked, String operator) {
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexOfColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnToBeChecked);
				if (indexOfColumn != -1) {
					if (dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(indexOfColumn)
							.getType().validate(valueToBeChecked) == true) {
						LinkedList<Object> rows = dbManage.getDataBases().get(dbUsed).getTables().get(index)
								.getColumns().get(indexOfColumn).getRows(operator, valueToBeChecked);
						dbManage.getDataBases().get(dbUsed).getTables().get(index).deleteRows(rows);
					} else {
						System.out.println("this value cannot to compare for invalid type");
					}
				} else {
					System.out.println("this column name is not found");
				}
			} else {
				System.out.println("this table name is not found");
			}
		}

	}

	@Override
	public void updateTable(String tableName, String conditionColumn, String operator, String conditionValue,
			LinkedList<String> setColumns, LinkedList<String> setValues) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropDataBase(String databaseName) {
		// TODO Auto-generated method stub
		int index = dbManage.searchForDataBase(databaseName);
		if (index == -1) {
			System.out.println("This Database is not found");
		} else {
			dbManage.dropDataBase(index);
			use(dbUsedName);
		}

	}

	@Override
	public void dropTable(String tableName) {
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				dbManage.getDataBases().get(dbUsed).dropTable(index);
			} else {
				System.out.println("Table name is not found");
			}
		} else {
			System.out.println("you have to use the 'use' statement");
		}
	}

	public void use(String name) {
		dbUsed = -1;
		for (int i = 0; i < dbManage.getDataBases().size(); i++) {
			if (dbManage.getDataBases().get(i).getName().equals(name)) {
				dbUsed = i;
				dbUsedName = name;
			}
		}

	}

		

}
