package me.kimundi.util;

import java.io.File;
import java.io.IOException;

public interface FormatedDataReader<T> {
	T read(File file) throws IOException, FormatedDataReadException;
}
