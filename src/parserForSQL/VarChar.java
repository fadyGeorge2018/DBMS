package parserForSQL;

public class VarChar extends Type {

	@Override
	public boolean validate(String value) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean compareValue(String operator, String currentValue, String valueToBeChecked) {
		// TODO Auto-generated method stub
		if (operator.equals("=")) {
			if (currentValue.equals(valueToBeChecked)) {
				return true;
			}
		} else if (operator.equals("<")) {
			if (currentValue.compareTo(valueToBeChecked) < 0) {
				return true;
			}
		} else if (operator.equals(">")) {

			if (currentValue.compareTo(valueToBeChecked) > 0) {
				return true;
			}
		}
		return false;
	}

}
