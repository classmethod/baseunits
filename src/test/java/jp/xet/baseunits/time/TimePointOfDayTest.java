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
package jp.xet.baseunits.time;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

import java.util.TimeZone;

import jp.xet.baseunits.time.HourOfDay.Meridian;

import org.junit.Test;

/**
 * {@link TimePointOfDay}のテストクラス。
 */
@SuppressWarnings("javadoc")
public class TimePointOfDayTest {
	
	private static final TimePointOfDay UTC_3PM = TimePointOfDay.at12hrUTC(3, Meridian.PM, 0, 0, 0);
	
	private static final TimePointOfDay JST_9PM = TimePointOfDay.at12hr(9, Meridian.PM, 0, 0, 0,
			TimeZone.getTimeZone("Japan"));
	
	private static final TimePointOfDay UTC_3AM = TimePointOfDay.at12hrUTC(3, Meridian.AM, 0, 0, 0);
	
	private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
	
	
	@Test
	public void test01_toString() {
		TimePointOfDay jstNoon = TimePointOfDay.from(TimeOfDay.NOON, TimeZone.getTimeZone("JST"));
		
		assertThat(jstNoon, hasToString("03:00:00,000 UTC")); // JST 12:00 = UTC 03:00
		assertThat(jstNoon.toString("HH:mm zzz", TimeZone.getTimeZone("JST")), is("12:00 JST"));
		assertThat(jstNoon.toString("HH:mm zzz", TimeZone.getTimeZone("UTC")), is("03:00 UTC"));
	}
	
	/**
	 * {@link TimePointOfDay#compareTo(TimePointOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_compareTo() throws Exception {
		assertThat(TimePointOfDay.atUTC(4, 30).compareTo(TimePointOfDay.atMidnightUTC()), is(greaterThan(0)));
		assertThat(TimePointOfDay.atMidnightUTC().compareTo(TimePointOfDay.atUTC(4, 30)), is(lessThan(0)));
		assertThat(TimePointOfDay.atMidnightUTC().compareTo(TimePointOfDay.UTC_MIDNIGHT), is(0));
		try {
			TimePointOfDay.atMidnightUTC().compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
	
	/**
	 * {@link TimePointOfDay#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_equals() throws Exception {
		assertThat(TimePointOfDay.atUTC(0, 0).equals(TimePointOfDay.UTC_MIDNIGHT), is(true));
		assertThat(TimePointOfDay.UTC_NOON.equals(JST_9PM), is(true));
		assertThat(UTC_3PM.equals(UTC_3AM), is(false));
		assertThat(UTC_3PM.equals(UTC_3PM), is(true));
		assertThat(UTC_3PM.equals(null), is(false));
		assertThat(UTC_3PM.equals(new Object()), is(false));
		
		assertThat(TimePointOfDay.at(1, 2, UTC), is(not(TimePointOfDay.at(1, 2, 3, UTC))));
		assertThat(TimePointOfDay.at(1, 2, UTC), is(TimePointOfDay.atUTC(1, 2)));
		assertThat(TimePointOfDay.atUTC(1, 2), is(TimePointOfDay.atUTC(1, 2, 0)));
		
		assertThat(UTC_3PM.asTimeOfDay(UTC).asTimePointOfDay(UTC), is(UTC_3PM));
	}
	
	/**
	 * {@link TimePointOfDay#parse(String, String, TimeZone)}, {@link TimePointOfDay#parseUTCFrom(String, String)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_parse() throws Exception {
		assertThat(TimePointOfDay.parse("12:34:33", "HH:mm:ss", UTC), is(TimePointOfDay.at(12, 34, 33, UTC)));
		assertThat(TimePointOfDay.parse("123433", "HHmmss", UTC), is(TimePointOfDay.at(12, 34, 33, UTC)));
		assertThat(TimePointOfDay.parseUTCFrom("14:42:01", "HH:mm:ss"), is(TimePointOfDay.atUTC(14, 42, 1)));
	}
	
	/**
	 * {@link TimePointOfDay#plus(Duration)}, {@link TimePointOfDay#minus(Duration)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_plus_minus() throws Exception {
		assertThat(TimePointOfDay.UTC_NOON.plus(Duration.hours(3)), is(TimePointOfDay.atUTC(15, 0)));
		assertThat(TimePointOfDay.UTC_NOON.minus(Duration.hours(3)), is(TimePointOfDay.atUTC(9, 0)));
	}
}
