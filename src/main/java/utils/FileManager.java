package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileManager {

	public static Properties loadProperties(String fileName) throws IOException{
	    
		Properties props = new Properties();
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream(fileName);
	    if (inputStream == null) {
	        throw new IllegalArgumentException("Properties file not found: " + fileName);
	    }
	    props.load(inputStream);
	    inputStream.close();
	    return props;
	}
	
	public static Properties updateProperties(String fileName, String key, String value) throws IOException {
	    
	    // Thread.currentThread().getContextClassLoader().clearCache();

	    Properties props = loadProperties(fileName); // Load existing properties

	    props.setProperty(key, value); // Update the specified key-value pair

	    // Check if the property was actually updated
	    if (!props.containsKey(key) || !props.getProperty(key).equals(value)) {
	        System.out.println("Warning: Property update might not have been successful.");
	    }

	    FileOutputStream out = new FileOutputStream(fileName);
	    props.store(out, "Updated " + fileName); // Optional comment
	    out.close();
	    
	    return props;
	}

}
