package br.com.marcio.model;

public class MathModel {

	public static Double resultado(String numberOne, String calc, String numberTwo) {
		Double num1 = convertToDouble(numberOne);
		Double num2 = convertToDouble(numberTwo);
		Double result = 0.0;
		if(calc.equals("mult")){
			result = num1 * num2;
		}if(calc.equals("div")){
			 result = num1 / num2;
		}if(calc.equals("menos")){
			 result = num1 - num2;
		}if(calc.equals("mais")){
			 result = num1 + num2;
		}
		return result;
	}

	public static Double convertToDouble(String strNumber) {
		if(strNumber == null) return 0D;
		String number = strNumber.replaceAll(",", ".");
		if(isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}

	public static boolean isNumeric(String strNumber) {
		if(strNumber == null) return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
