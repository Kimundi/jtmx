package me.kimundi.jtmx.io;

import me.kimundi.util.FormatedDataReadException;

public class JTMXParseException extends FormatedDataReadException {
	private static final long serialVersionUID = 2358757110525517365L;

	public JTMXParseException(String errMessage, Throwable cause) {
		super(errMessage, cause);
	}

	public JTMXParseException(String errMessage) {
		super(errMessage);
	}
}
