/*
 * Copyright 2010-2019 Miyamoto Daisuke.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.baseunits.wicket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.TimePoint;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 * Converts from Object to {@link CalendarDate}.
 * 
 * @author daisuke
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TimePointConverter extends AbstractConverter<TimePoint> {
	
	/** default date pattern for {@link SimpleDateFormat} */
	public static final String DEFAILT_PATTERN_JAVA = "yyyy/MM/dd HH:mm:ss.SSS";
	
//	/** default date pattern for JQuery */
//	public static final String DEFAILT_PATTERN_JQ = "yy/mm/dd";
	
	private final String datePattern;
	
	private final TimeZone timeZone;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param datePattern {@link SimpleDateFormat}に基づくパターン
	 * @param timeZone タイムゾーン
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public TimePointConverter(String datePattern, TimeZone timeZone) {
		Args.notNull(datePattern, "datePattern");
		Args.notNull(timeZone, "timeZone");
		this.datePattern = datePattern;
		this.timeZone = timeZone;
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param timeZone タイムゾーン
	 */
	public TimePointConverter(TimeZone timeZone) {
		this(DEFAILT_PATTERN_JAVA, timeZone);
	}
	
	@Override
	public TimePoint convertToObject(String value, Locale locale) {
		if (Strings.isEmpty(value)) {
			return null;
		}
		
		try {
			return TimePoint.parse(value, datePattern, timeZone);
		} catch (ParseException e) {
			throw newConversionException("Cannot convert '" + value + "' to TimePoint", value, locale);
		}
	}
	
	@Override
	public String convertToString(TimePoint value, Locale locale) {
		return value == null ? null : value.toString(datePattern, timeZone);
	}
	
	@Override
	protected Class<TimePoint> getTargetType() {
		return TimePoint.class;
	}
}
