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
package jp.xet.baseunits.spring;

import java.util.TimeZone;

import org.springframework.core.convert.converter.Converter;

/**
 * Spring {@link Converter} implementation for {@link TimeZone}.
 * 
 * @since 2.12
 * @version $Id$
 * @author daisuke
 */
public class TimeZoneConverter implements Converter<String, TimeZone> {
	
	@Override
	public TimeZone convert(String source) {
		return TimeZone.getTimeZone(source);
	}
}
