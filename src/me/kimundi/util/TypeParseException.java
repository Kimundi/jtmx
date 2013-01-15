package me.kimundi.util;

public class TypeParseException extends FormatedDataReadException {
	public TypeParseException(String message, Throwable cause) {
		super(message, cause); }
	public TypeParseException(String message) { 
		super(message); }
}