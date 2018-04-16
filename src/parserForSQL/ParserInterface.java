package parserForSQL;

import java.util.LinkedList;

public interface ParserInterface {
String findDataBaseName(String expression);
String findTableName(String expression);
void setColumnsToBeCreated(String expression);
LinkedList<String> getColumnName();
LinkedList<String> getColumnType();
LinkedList<String> getValuesList();
String tableNameToBeInsertedAt(String expression);
LinkedList<String> valuesToBeInserted(String expression);
String tableNameToBeDeleted(String expression);
String findOperator (String expression);
void findTheCondition(String expression);
String tableToBeSelected(String expression);
void setColumnsToBeSelected(String expression);
String tableToBeUpdated(String expression);
String valueToBeChecked(String expression);
String columnToBeChecked(String expression , String operator);
void columnsUpdatedWithValues(String expression);
String columnsToBeAddedOrDeleted(String expression);
String columnType(String expression);
}