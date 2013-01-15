package me.kimundi.util;

// TODO: Change to RuntimeException before Release
public class FormatedDataReadException extends RuntimeException{
	public FormatedDataReadException(String message, Throwable cause) {
		super(message, cause); }
	public FormatedDataReadException(String message) { 
		super(message); }
}
