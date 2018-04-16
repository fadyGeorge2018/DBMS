package parserForSQL;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DatabaseMangement;
import saving.SaveXML;

public class Validity {
	private String expression;
	private String operator;
	private String dataBaseName;
	private String tableName;
	private String columnToBeChecked;
	private String valueToBeChecked;
	private String columnToBeAdded;
	private String columnTypeToBeAdded;
	private LinkedList<String>columnName = new LinkedList<>();
	private LinkedList<String>columnType = new LinkedList<>();
	private LinkedList<String>valuesList = new LinkedList<>();
	private boolean checkValidity;
	private DatabaseMangement ParsingObject = new DatabaseMangement();
	private ParsingToJava ParsingToJavaObject = new ParsingToJava();
	
	
	public void validOrNot(String command) {
		expression = command;
		expression = expression.replaceAll("( )+", " ");
		expression = expression.trim();
		if (createDataBaseValidity() == true) {
			ParsingObject.CreatDataBase(dataBaseName);
		} else if (createTableValidity() == true) {
			ParsingObject.CreatTable(tableName, columnName, columnType);
		} else if (insertIntoValidity() == true) {
			ParsingObject.InsertIntoTable(tableName, valuesList);
		} else if (deleteValidity() == true) {
			ParsingObject.DeleteFromTable(tableName, columnToBeChecked, valueToBeChecked, operator);
		} else if (dropDataBaseValidity() == true) {
			ParsingObject.DropDataBase(dataBaseName);
		} else if (dropTableValidity() == true) {
			ParsingObject.DropTable(tableName);
		} else if (selectAllValidity() == true) {
			ParsingObject.selectAll(tableName);
		} else if (select() == true) {
			ParsingObject.selectFromTable(tableName, columnName);
		} else if (updateTableValidity() == true) {
			ParsingObject.UpdateTable(tableName, columnToBeChecked, operator,  valueToBeChecked,columnName, valuesList);
		} else if (useDataBaseValidity() == true) {
			ParsingObject.setDatabase(dataBaseName);
		} 
		else if(addColumnValidity() == true ) {
		   ParsingObject.addColumns(tableName,columnToBeAdded,columnTypeToBeAdded);
		}
		else if(dropColumnValidity() == true ) {
			   ParsingObject.dropColumn(tableName,columnToBeAdded);
			}else {
			System.out.println("Invalid SQL");
		}
	}

	public boolean createDataBaseValidity() {
		checkValidity = Pattern.matches("CREATE\\sDATABASE\\s\\w+\\s*;", expression);
		// search for database name
		if (checkValidity == true) {
			expression = expression.replaceAll(";", " ");
			dataBaseName = ParsingToJavaObject.findDataBaseName(expression);
		}
		return checkValidity;
	}

	public boolean createTableValidity() {
		Pattern columns = Pattern.compile(
				"(\\s*\\w+\\s(int|(varchar\\s*\\(\\s*255\\s*\\)))\\s*,)*\\s*\\w+\\s(int||(varchar\\s*\\(\\s*255\\s*\\)))");
		checkValidity = Pattern.matches("CREATE\\sTABLE\\s\\w+\\s*\\(" + columns.toString() + "\\s*\\)\\s*;",
				expression);
		if (checkValidity == true) {
			// search for table name
			tableName = ParsingToJavaObject.findTableName(expression);
			// search for column names and types
			ParsingToJavaObject.setColumnsToBeCreated(expression);
			columnName = ParsingToJavaObject.getColumnName();
			columnType = ParsingToJavaObject.getColumnType();
		}
		return checkValidity;
	}

	public boolean insertIntoValidity() {
		// modified
		Pattern values = Pattern.compile("((\\s*'(\\s*(\\w+|\\.)\\s*)*'\\s*,)*\\s*'(\\s*(\\w+|\\.)\\s*)*')");
		checkValidity = Pattern.matches("INSERT\\sINTO\\s\\w+\\sVALUES\\s*\\(\\s*"+values.toString()+"\\s*\\)\\s*;",
				expression);
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.tableNameToBeInsertedAt(expression);
			// search for values to be inserted
			valuesList = ParsingToJavaObject.valuesToBeInserted(expression);
		}
		return checkValidity;
	}

	public boolean deleteValidity() {
		checkValidity = Pattern.matches("DELETE\\sFROM(.+)\\sWHERE\\s\\w+\\s*(=||>||<)\\s*'(.+)'\\s*;", expression);
		if (checkValidity == true) {
			// search for table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search for operator
			operator = ParsingToJavaObject.findOperator(expression);
			// search for value and column name
			ParsingToJavaObject.findTheCondition(expression);
			columnToBeChecked = ParsingToJavaObject.getColumnName().getFirst();
			valueToBeChecked = ParsingToJavaObject.getValuesList().getFirst();

		}
		return checkValidity;
	}

	public boolean dropDataBaseValidity() {
		checkValidity = Pattern.matches("DROP\\sDATABASE\\s\\w+", expression);
		// search for database name
		if (checkValidity == true) {
			dataBaseName = ParsingToJavaObject.findDataBaseName(expression);
		}
		return checkValidity;
	}

	public boolean dropTableValidity() {
		checkValidity = Pattern.matches("DROP\\sTABLE\\s\\w+", expression);
		// search for table name
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.findTableName(expression);
		}
		return checkValidity;
	}

	public boolean selectAllValidity() {
		checkValidity = Pattern.matches("SELECT\\s*\\*\\s*FROM\\s\\w+\\s*;", expression);
		// search for table name
		if (checkValidity == true) {
			expression = expression.replaceAll(";", " ");
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
		}
		return checkValidity;
	}

	public boolean select() {
		Pattern columns = Pattern.compile("(\\s*\\w+\\s*,)*\\s*\\w+\\s*");
		checkValidity = Pattern.matches("SELECT\\s" + columns + "\\sFROM\\s\\w+\\s*;", expression);
		if (checkValidity == true) {
			expression = expression.replaceAll(";", " ");
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search columns name
			ParsingToJavaObject.setColumnsToBeSelected(expression);
			columnName = ParsingToJavaObject.getColumnName();

		}
		return checkValidity;
	}

	public boolean updateTableValidity() {
		Pattern equating = Pattern.compile("(\\s*\\w+\\s*=\\s*'(\\s*\\w+\\s*)*'\\s*,)*\\s*\\w+\\s*=\\s*'(\\s*\\w+\\s*)*'\\s*");
		checkValidity = Pattern.matches("UPDATE\\s\\w+\\sSET\\s"+equating+"WHERE\\s\\w+\\s*(=|>|<)\\s*'(.+)'\\s*;",
				expression);
		// search for table name
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.tableToBeUpdated(expression);
			// search for operator
			operator = ParsingToJavaObject.findOperator(expression);
			// search for value to be checked
			valueToBeChecked = ParsingToJavaObject.valueToBeChecked(expression);
			// search for column to be checked
			columnToBeChecked = ParsingToJavaObject.columnToBeChecked(expression, operator);
			// search for lists of columns and values
			ParsingToJavaObject.columnsUpdatedWithValues(expression);
			valuesList = ParsingToJavaObject.getValuesList();
			columnName = ParsingToJavaObject.getColumnName();
		}
		return checkValidity;
	}

	public boolean useDataBaseValidity() {
		checkValidity = Pattern.matches("USE\\s\\w+\\s*;", expression);
		if (checkValidity == true) {
			expression = expression.replaceAll(";", " ");
			dataBaseName = ParsingToJavaObject.findDataBaseName(expression);
		}

		return checkValidity;
	}
	public boolean addColumnValidity(){
		checkValidity = Pattern.matches("ALTER\\sTABLE\\s\\w+\\sADD\\s\\w+\\s(varchar\\(255\\)|int)", expression);
		if(checkValidity == true){
			tableName = ParsingToJavaObject.findTableName(expression);
			columnToBeAdded = ParsingToJavaObject.columnsToBeAddedOrDeleted(expression);
			columnTypeToBeAdded =ParsingToJavaObject.columnType(expression);
		}
		return checkValidity;
	}
	public boolean dropColumnValidity(){
		checkValidity = Pattern.matches("ALTER\\sTABLE\\s\\w+\\sDROP\\sCOLUMN\\s\\w+", expression);
		if(checkValidity == true){
			tableName = ParsingToJavaObject.findTableName(expression);
			columnToBeAdded = ParsingToJavaObject.columnsToBeAddedOrDeleted(expression);
		}
		return checkValidity;
	}
}
