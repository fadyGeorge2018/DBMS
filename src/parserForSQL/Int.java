package parserForSQL;

public class Int extends Type {

	@Override
	public boolean validate(String value) {
		// TODO Auto-generated method stub
		try {
			Integer test = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			System.out.println("This value contradicts the column type");
			return false;
		}
		return true;
	}

	public boolean compareValue(String operator, String currentValue, String valueToBeChecked) {
		Integer test = Integer.parseInt(valueToBeChecked);
		Integer current = Integer.parseInt(currentValue);
		if (operator.equals("<")) {
			if (current < test) {
				return true;
			}
		} else if (operator.equals(">")) {
			if (current > test) {
				return true;
			}
		} else if (operator.equals("=")) {
			if (current.equals(test)) {
				return true;
			}
		}
		return false;
	}

}
