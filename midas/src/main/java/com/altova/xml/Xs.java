// Xs.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.xml;

import com.altova.typeinfo.ValueFormatter;

public class Xs
{
	public static final ValueFormatter StandardFormatter = new XmlFormatter();
	public static final ValueFormatter TimeFormatter = new XmlTimeFormatter();
	public static final ValueFormatter DateFormatter = new XmlDateFormatter();
	public static final ValueFormatter DateTimeFormatter = StandardFormatter;
	public static final ValueFormatter GYearFormatter = new XmlGYearFormatter();
	public static final ValueFormatter GMonthFormatter = new XmlGMonthFormatter();
	public static final ValueFormatter GDayFormatter = new XmlGDayFormatter();
	public static final ValueFormatter GYearMonthFormatter = new XmlGYearMonthFormatter();
	public static final ValueFormatter GMonthDayFormatter = new XmlGMonthDayFormatter();
	public static final ValueFormatter HexBinaryFormatter = new XmlHexBinaryFormatter();
	public static final ValueFormatter IntegerFormatter = new XmlIntegerFormatter();
	public static final ValueFormatter DecimalFormatter = StandardFormatter;
	public static final ValueFormatter AnySimpleTypeFormatter = StandardFormatter;
	public static final ValueFormatter DurationFormatter = StandardFormatter;
	public static final ValueFormatter DoubleFormatter = StandardFormatter;
	public static final ValueFormatter Base64BinaryFormatter = StandardFormatter;
}
