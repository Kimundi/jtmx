package me.kimundi.util;

import java.util.HashMap;
import static me.kimundi.util.TypeParser.*;

// Need Immutable version
public class Properties extends HashMap<String, String> {
	public void setProperty(String key, String value) {
		put(key, value);
	}
	
	public void setProperty(String key, Object value) {
		put(key, value.toString());
	}
	
	public String getProperty(String key) throws TypeParseException {
		return parse(get(key), stringParser, null, false);
	}
	
	public String getProperty(String key, String defvalue) throws TypeParseException {
		return parse(get(key), stringParser, defvalue, true);
	}
	
	public int getPropertyAsInt(String key) throws TypeParseException {
		return parse(get(key), intParser, null, false);
	}

	public int getPropertyAsInt(String key, int defvalue) throws TypeParseException {
		return parse(get(key), intParser, defvalue, true);
	}
	
	public int getPropertyAsUInt(String key) throws TypeParseException {
		return parse(get(key), uintParser, null, false);
	}

	public int getPropertyAsUInt(String key, int defvalue) throws TypeParseException {
		return parse(get(key), uintParser, defvalue, true);
	}

	public long getPropertyAsLong(String key) throws TypeParseException {
		return parse(get(key), longParser, null, false);
	}

	public long getPropertyAsLong(String key, long defvalue) throws TypeParseException {
		return parse(get(key), longParser, defvalue, true);
	}	
	
	public long getPropertyAsULong(String key) throws TypeParseException {
		return parse(get(key), ulongParser, null, false);
	}

	public long getPropertyAsULong(String key, long defvalue) throws TypeParseException {
		return parse(get(key), ulongParser, defvalue, true);
	}
	
	public double getPropertyAsDouble(String key) throws TypeParseException {
		return parse(get(key), doubleParser, null, false);
	}

	public double getPropertyAsDouble(String key, double defvalue) throws TypeParseException {
		return parse(get(key), doubleParser, defvalue, true);
	}
	
	public float getPropertyAsFloat(String key) throws TypeParseException {
		return parse(get(key), floatParser, null, false);
	}

	public float getPropertyAsFloat(String key, float defvalue) throws TypeParseException {
		return parse(get(key), floatParser, defvalue, true);
	}
	
	public boolean getPropertyAsBoolean(String key) throws TypeParseException {
		return parse(get(key), boolParser, null, false);
	}

	public boolean getPropertyAsBoolean(String key, boolean defvalue) throws TypeParseException {
		return parse(get(key), boolParser, defvalue, true);
	}
	
	
	
	
}
