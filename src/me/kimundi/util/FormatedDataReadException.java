package me.kimundi.util;

public class FormatedDataReadException extends RuntimeException{
	private static final long serialVersionUID = -1745905498939333378L;
	
	public FormatedDataReadException(String message, Throwable cause) {
		super(message, cause); 
	}
	
	public FormatedDataReadException(String message) { 
		super(message); 
	}
}
