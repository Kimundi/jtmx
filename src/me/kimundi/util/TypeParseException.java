package me.kimundi.util;

public class TypeParseException extends FormatedDataReadException {
	private static final long serialVersionUID = -8053701612233751905L;
	
	public TypeParseException(String message, Throwable cause) {
		super(message, cause); 
	}
	
	public TypeParseException(String message) { 
		super(message); 
	}
}