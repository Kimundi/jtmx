package me.kimundi.jtmx.io;

import java.text.ParseException;

import me.kimundi.util.FormatedDataReadException;

public class JTMXParseException extends FormatedDataReadException {
	public JTMXParseException(String errMessage, Throwable cause) {
		super(errMessage, cause);
	}

	public JTMXParseException(String errMessage) {
		super(errMessage);
	}
}
