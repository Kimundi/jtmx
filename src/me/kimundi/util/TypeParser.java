package me.kimundi.util;

public class TypeParser {
	
	private static interface ParseWrapper<T> {
		T parse(String value) throws TypeParseException;
	}
	
	private static class NumberParser<T extends Number> implements ParseWrapper<T> {
		private ParseWrapper<T> parser;
		public NumberParser(ParseWrapper<T> parser) {
			this.parser = parser;
		}
		@Override
		public T parse(String value) throws TypeParseException {
			try {
				return parser.parse(value);
			} catch (NumberFormatException e) {
				throw new TypeParseException(
						"Value is not a Number, should be.", e);
			}
		}
	}
	
	private static class PositiveNumberParser<T extends Number> implements ParseWrapper<T> {
		private ParseWrapper<T> parser;
		public PositiveNumberParser(NumberParser<T> parser) {
			this.parser = parser;
		}
		@Override
		public T parse(String value) throws TypeParseException {
			T ret = parser.parse(value);
			if (ret.doubleValue() < 0) 
				throw new TypeParseException(
						"NumberValue is not negative, should be."); 
			return ret;
		}
	}
	
	public static <T extends Number> NumberParser<T> assertNumber(
			ParseWrapper<T> parser) {
		return new NumberParser<T>(parser);
	}
	
	public static <T extends Number> PositiveNumberParser<T> assertPositive(
			NumberParser<T> parser) {
		return new PositiveNumberParser<T>(parser);
	}
	
	public static final ParseWrapper<String> stringParser = 
			new ParseWrapper<String>(){
				public String parse(String value) throws TypeParseException {
					return value;
				}
			};
	
	public static final NumberParser<Integer> intParser = 
			assertNumber(new ParseWrapper<Integer>(){
				public Integer parse(String value) throws TypeParseException {
					return Integer.parseInt(value);
				}
			});
	
	public static final ParseWrapper<Integer> uintParser =
			assertPositive(intParser);
	
	public static final NumberParser<Long> longParser =
			assertNumber(new ParseWrapper<Long>(){
				public Long parse(String value) throws TypeParseException {
					return Long.parseLong(value);
				}
			});
	
	public final static ParseWrapper<Long> ulongParser =
			assertPositive(longParser);
	
	public static final NumberParser<Double> doubleParser =
			assertNumber(new ParseWrapper<Double>(){
				public Double parse(String value) throws TypeParseException {
					return Double.parseDouble(value);
				}
			});

	public static final NumberParser<Float> floatParser =
			assertNumber(new ParseWrapper<Float>(){
				public Float parse(String value) throws TypeParseException {
					return Float.parseFloat(value);
				}
			});
	
	public static final ParseWrapper<Boolean> boolParser = 
			new ParseWrapper<Boolean>() {
				public Boolean parse(String value) throws TypeParseException {
					if (value.toLowerCase().equals("true")) {
						return true;
					} else if (value.toLowerCase().equals("false")) {
						return false;
					} else {
						throw new TypeParseException(
								"Value is not a boolean, should be.");
					}
				}
			};
	
	public static <T> T parse(String value, ParseWrapper<T> parser, 
			T defaultvalue, boolean optional) throws TypeParseException {
		if (value == null ) {
			if (optional) return defaultvalue;
			throw new TypeParseException("Non-optional value is missing.");
		} else {
			return parser.parse(value);
		}
	}
}
