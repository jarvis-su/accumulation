/*
 *  Copyright (c) 2005 ACS, Inc. All rights reserved.
 *
 *  This item contains confidential and trade secret information and may not be
 *  transferred, published, disclosed, reproduced, or used by any party without
 *  the express written permission of ACS, Inc.
 */
package com.acs.fileChecker.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;

/**
 * The <code>PropertyFileReader</code> class is the implementation of
 * <code>ConfigSource</code> as specified in
 * <code>com.tps.sysmgmt.ConfigSource</code>
 * 
 * @author <a href="mailto:zzhu@tps-software.com">Zheng Zhu</a>
 * @created March 11, 2005
 * @version $Revision: 1.0 $ $Date: 2005/03/15 3:00:00 $
 * @see com.tps.sysmgmt.ConfigSource
 * @since EPPIC 1.5
 */
public class PropertyFileReader {
	// ======================================================================//
	// begin configvalues inherited
	// ======================================================================//
	// config.date.pattern=yyyy/MM/dd
	// config.datetime.pattern=yyyy/MM/dd HH:mm:ss.SSS
	// config.arrayelement.separator=,
	// config.arrayarray.separator=;
	protected char _ARRAY_ELEMENT_SEPARATOR = ',';
	protected char _ARRAY_ARRAY_ELEMENT_SEPARATOR = ';';
	protected String _DATEPATTERN = "yyyy/MM/dd";
	protected String _DATETIMEPATTERN = "yyyy/MM/dd HH:mm:ss.SSS";
	protected SimpleDateFormat _DATEFORMATTER = new SimpleDateFormat(
			_DATEPATTERN);
	protected SimpleDateFormat _DATETIMEFORMATTER = new SimpleDateFormat(
			_DATETIMEPATTERN);
	{
		_DATEFORMATTER.setLenient(false);
		_DATETIMEFORMATTER.setLenient(false);
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 */
	protected void valueNotPresentInstance(String property) {
		throw new IllegalArgumentException("Values not present for " + property);
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String retrieveValueInstance(String property) {
		return readConfigValue(property);
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public char retrieveCharValueInstance(String property) {
		String value = _properties.getProperty(property);
		try {
			if (value == null || value.length() != 1) {
				valueNotPresentInstance(property);
			}
			return value.charAt(0);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public int retrieveIntValueInstance(String property) {
		String value = _properties.getProperty(property);
		try {
			if (value == null) {
				valueNotPresentInstance(property);
			}
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public long retrieveLongValueInstance(String property) {
		String value = _properties.getProperty(property);
		try {
			if (value == null) {
				valueNotPresentInstance(property);
			}
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public byte retrieveByteValueInstance(String property) {
		String value = _properties.getProperty(property);
		try {
			if (value == null) {
				valueNotPresentInstance(property);
			}
			return Byte.parseByte(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public double retrieveDoubleValueInstance(String property) {
		String value = _properties.getProperty(property);
		try {
			if (value == null) {
				valueNotPresentInstance(property);
			}
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public java.util.Date retrieveDateValueInstance(String property) {
		String value = retrieveValueInstance(property);
		try {
			if (value == null) {
				valueNotPresentInstance(property);
			}
			return _DATEFORMATTER.parse(value);
		} catch (java.text.ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public java.util.Date retrieveDateTimeValueInstance(String property) {
		String value = retrieveValueInstance(property);
		try {
			if (value == null) {
				valueNotPresentInstance(property);
			}
			return _DATETIMEFORMATTER.parse(value);
		} catch (java.text.ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String[] retrieveStringArrayValueInstance(String property) {
		return arrayValuesInstance(property);
	}

	/**
	 * Description of the Method
	 * 
	 * @param value
	 *            Description of the Parameter
	 * @param separator
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	private String[] arrayValuesInstance(String value, char separator) {
		if (value == null) {
			return EMPTY_STRING_ARRAY;
		}

		Vector values = new Vector();
		int prevIndex = 0;
		int index = value.indexOf(separator);
		while (index != -1) {
			values.add(value.substring(prevIndex, index));
			prevIndex = index + 1;
			index = value.indexOf(separator, prevIndex);
		}
		if (prevIndex < value.length()) {
			values.add(value.substring(prevIndex, value.length()));
		}
		return (String[]) values.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * Description of the Method
	 * 
	 * @param value
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	private String[] arrayValuesFromStringInstance(String value) {
		return arrayValuesInstance(value, _ARRAY_ELEMENT_SEPARATOR);
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String[] arrayValuesInstance(String property) {
		return arrayValuesInstance(retrieveValueInstance(property),
				_ARRAY_ELEMENT_SEPARATOR);
	}

	/**
	 * Description of the Method
	 * 
	 * @param value
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	private String[] arrayArrayValuesFromStringInstance(String value) {
		return arrayValuesInstance(value, _ARRAY_ARRAY_ELEMENT_SEPARATOR);
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	private String[] arrayArrayValuesInstance(String property) {
		return arrayValuesInstance(retrieveValueInstance(property),
				_ARRAY_ARRAY_ELEMENT_SEPARATOR);
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public int[] retrieveIntArrayValueInstance(String property) {
		String[] values = arrayValuesInstance(property);
		int[] result = new int[values.length];
		try {
			for (int i = 0; i < values.length; i++) {
				result[i] = Integer.parseInt(values[i]);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public double[] retrieveDoubleArrayValueInstance(String property) {
		String[] values = arrayValuesInstance(property);
		double[] result = new double[values.length];
		try {
			for (int i = 0; i < values.length; i++) {
				result[i] = Double.parseDouble(values[i]);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public int[][] retrieveIntArrayArrayValueInstance(String property) {
		String[] values = arrayArrayValuesInstance(property);
		int[][] result = new int[values.length][];
		try {
			for (int i = 0; i < values.length; i++) {
				String[] values2 = arrayValuesFromStringInstance(values[i]);
				result[i] = new int[values2.length];
				for (int j = 0; j < values2.length; j++) {
					result[i][j] = Integer.parseInt(values2[j]);
				}
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Description of the Method
	 * 
	 * @param property
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String[][] retrieveStringArrayArrayValueInstance(String property) {
		String[] values = arrayArrayValuesInstance(property);
		String[][] result = new String[values.length][];
		try {
			for (int i = 0; i < values.length; i++) {
				result[i] = arrayValuesFromStringInstance(values[i]);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	// ======================================================================//
	// end configvalues inherited
	// ======================================================================//

	protected String _propertyFileName;
	protected String _filePath;
	protected String _fixedFullPath;
	protected Properties _properties;
	protected String _fullPath = null;

	protected static final String PATH_SEPARATOR = System
			.getProperty("file.separator");
	public static final String CONFIG_PATH_CLASSPATH_PREFIX = "/com/acs/fileChecker/config/";

	/**
	 * Constructor for the PropertyFileReader object
	 * 
	 * @param fileName
	 *            Description of the Parameter
	 * @param filePath
	 *            Description of the Parameter
	 */
	public PropertyFileReader(String fileName, String filePath) {
		_propertyFileName = fileName;
		// _properties = new Properties();
		reloadAllConfigValues();
	}

	/**
	 * Gets the properties attribute of the PropertyFileReader object
	 * 
	 * @return The properties value
	 */
	public Properties getProperties() {
		return _properties;
	}

	/**
	 * Gets the properties attribute of the PropertyFileReader class
	 * 
	 * @param fileName
	 *            Description of the Parameter
	 * @param filePath
	 *            Description of the Parameter
	 * @return The properties value
	 */
	public static Properties getProperties(String fileName, String filePath) {
		return new PropertyFileReader(fileName, filePath).getProperties();
	}

	/**
	 * Description of the Method
	 */
	protected void loadProperties() {
		InputStream is = null;

		String fileNameFromRes = "";
		try {
			_properties = new Properties();
			try {
				fileNameFromRes = CONFIG_PATH_CLASSPATH_PREFIX
						+ _propertyFileName;
				is = PropertyFileReader.class
						.getResourceAsStream(fileNameFromRes);
				if (is == null) {
					// System.exit(-1);
				} else {
					//
					// load from all 3 in correct Order
					_properties.load(is);
				}
			} catch (Throwable ioe) {
				System.err.println("Error, load properties!");
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException ioe) {
						System.err
								.println("Exception: close input stream error.");
					}
					is = null;
				}
			}

		} finally {

		}

	}

	/**
	 * Description of the Method
	 * 
	 * @return Description of the Return Value
	 */
	public boolean reloadAllConfigValues() {
		loadProperties();
		return true;
	}

	/**
	 * reads a specific configuration parameter Backwards compat.
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String readConfigValue(String id) {
		return _properties.getProperty(id);
	}

	/**
	 * Description of the Method
	 * 
	 * @param i
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public static String numToString(int i) {
		if ((i >= 0) && (i < 10)) {
			return "00" + i;
		} else if ((i >= 10) && (i < 100)) {
			return "0" + i;
		} else if ((i >= 100) && (i < 1000)) {
			return "" + i;
		}
		return "";
	}

	/**
	 * Gets the separator attribute of the PropertyFileReader class
	 * 
	 * @return The separator value
	 */
	public static String getSeparator() {
		return ".";
	}

	/**
	 * The main program for the PropertyFileReader class
	 * 
	 * @param args
	 *            The command line arguments
	 */
	public static void main(String[] args) {
		// unit test.
		PropertyFileReader pfr = new PropertyFileReader(
				"com_tps_eppic_fes_Fes_properties", null);
		System.out.println(pfr.readConfigValue("fes.numservers"));
	}
}
