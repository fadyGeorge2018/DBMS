package parserForSQL;

public abstract class Type {
	public abstract boolean validate(String value);

	public abstract boolean compareValue(String operator, String currentValue, String valueToBeChecked);
}
