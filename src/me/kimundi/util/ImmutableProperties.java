package me.kimundi.util;

import static me.kimundi.util.TypeParser.boolParser;
import static me.kimundi.util.TypeParser.doubleParser;
import static me.kimundi.util.TypeParser.floatParser;
import static me.kimundi.util.TypeParser.intParser;
import static me.kimundi.util.TypeParser.longParser;
import static me.kimundi.util.TypeParser.parse;
import static me.kimundi.util.TypeParser.stringParser;
import static me.kimundi.util.TypeParser.uintParser;
import static me.kimundi.util.TypeParser.ulongParser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

public class ImmutableProperties implements Map<String, String> {
	private final ImmutableMap<String, String> map;
	
	public ImmutableProperties(Map<String, String> map) {	
		this.map = ImmutableMap.copyOf(map);
	}

	public ImmutableProperties() {	
		this.map = ImmutableMap.of();
	}
	
	public ImmutableMap<String, String> getMap() {
		return map;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return map.entrySet();
	}

	@Override
	public String get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public String put(String key, String value) {
		return map.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		map.putAll(m);
	}

	@Override
	public String remove(Object key) {
		return map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<String> values() {
		return map.values();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutableProperties other = (ImmutableProperties) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public String toString() {
		return "Properties wrapper of " + map.toString();
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
	
	public static ImmutableProperties copyOf(Map<String, String> map) {
		return new ImmutableProperties(ImmutableMap.copyOf(map));
	}

}
