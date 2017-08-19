package com.epam.hostel.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	/**
	 * @param target string to validate
	 * @param regularExpressions regular expressions that a target string needs to match
	 * @return true if target string matches all the expressions, false otherwise
	 */
	public static boolean validate(String target, String regularExpression) {
		boolean valid = true;
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(regularExpression);
		matcher = pattern.matcher(target);
		valid = matcher.matches();

		return valid;
	}
}
