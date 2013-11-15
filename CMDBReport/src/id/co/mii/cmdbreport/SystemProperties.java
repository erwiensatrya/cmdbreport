package id.co.mii.cmdbreport;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SystemProperties {

	private static Properties properties = null;

    /**
     * Load all server properties from a file.    
     * @param filename path to a file containing the server properties
     */
    public static void loadProperties(String filename)
    {
        Properties props = new Properties(System.getProperties());
        try {
            FileInputStream in = new FileInputStream(filename);
            props.load(in);
        } catch (IOException e) {
            System.err.println("ERROR: could not load props file \"" +
                filename + "\".");
        }

        properties = props;
    }

    /**
     * Get a property value as a string.  Returns the user-provided default
     * value if the property is unset.  Removes leading and trailing
     * whitespace from property value.
     * @param propName
     * @param defaultValue
     * @return the String with the propName or defaultValue
     */
    public static String getString(String propName, String defaultValue)
    {
        String propValue = getString(propName);
        if (propValue == null)
            return defaultValue;

        return propValue;
    }

    /**
     * Get a property value as a string.  Returns null if the
     * property is unset.  Removes leading and trailing whitespace
     * from property value.
     * @param propName
     * @return the String with the propName or null
     */
    public static String getString(String propName)
    {
        String retVal = properties.getProperty(propName);
        if (retVal == null)
            return null;
        else
            // Remove leading and trailing whitespace
            return retVal.trim();
    }

    /**
     * Get a property value as a boolean.  Returns the user-provided default
     * value if the property is unset.
     * @param propName
     * @param defaultValue
     * @return boolean
     */
    public static boolean getBoolean(String propName, boolean defaultValue)
    {
        String propValue = getString(propName);
        if (propValue == null)
            return defaultValue;

        return (propValue.equalsIgnoreCase("true"));
    }

    /**
     * Get a property value as a boolean.  Returns false if the property
     * is unset.
     * @param propName
     */
    public static boolean getBoolean(String propName)
    {
        return getBoolean(propName, false);
    }

    /**
     * Get a property value as an int.  Returns the user-provided default
     * value if the property is unset.
     * @param propName
     * @param defaultValue
     * @return the int value of the property or defaultValue
     */
    public static int getInt(String propName, int defaultValue)
    {
        String propValue = properties.getProperty(propName);
        if (propValue == null)
            return defaultValue;

        int retVal;
        try {
            retVal = Integer.parseInt(propValue);
        } catch (NumberFormatException e) {
            retVal = defaultValue;
        }

        return retVal;
    }

    /**
     * Get a property value as an int.  Returns 0 if the property
     * is unset.
     * @param propName
     * @return the int value of the property or 0
     */
    public static int getInt(String propName)
    {
        return getInt(propName, 0);
    }

    /**
     * Get a property value as a float.  Returns the user-provided default
     * value if the property is unset.
     * @param propName
     * @param defaultValue
     * @return the float value of the property or null
     */
    public static float getFloat(String propName, float defaultValue)
    {
        String propValue = properties.getProperty(propName);
        if (propValue == null)
            return defaultValue;

        float retVal;
        try {
            retVal = Float.parseFloat(propValue);
        } catch (NumberFormatException e) {
            retVal = defaultValue;
        }

        return retVal;
    }

    /**
     * Get a property value as a float.  Returns 0 if the property
     * is unset.
     * @param propName
     * @return the float value of the property or 0
     */
    public static float getFloat(String propName)
    {
        return getFloat(propName, 0);
    }
}
