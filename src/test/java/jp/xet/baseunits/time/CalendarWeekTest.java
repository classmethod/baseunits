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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * {@link CalendarWeek}のテストクラス。
 */
public class CalendarWeekTest {
	
	private static final CalendarWeek WK_2011_52ND = CalendarWeek.from(2011, 52);
	
	private static final CalendarWeek WK_2011_53RD = CalendarWeek.from(2011, 53); // equals to WK_2012_1ST
	
	private static final CalendarWeek WK_2012_1ST = CalendarWeek.from(2012, 1);
	
	private static final CalendarWeek WK_2012_2ND = CalendarWeek.from(2012, 2);
	
	// ...
	
	private static final CalendarWeek WK_2012_52ND = CalendarWeek.from(2012, 52);
	
	private static final CalendarWeek WK_2013_1ST = CalendarWeek.from(2013, 1);
	
	private static final CalendarWeek WK_2013_2ND = CalendarWeek.from(2013, 2);
	
	
	/**
	 * {@link CalendarWeek#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_equals() throws Exception {
		CalendarWeek thisWeek = CalendarWeek.from(CalendarDate.from(2012, 2, 24));
		assertThat(thisWeek.equals(thisWeek), is(true));
		assertThat(WK_2012_1ST.equals(WK_2012_1ST), is(true));
		assertThat(WK_2012_1ST.equals(WK_2012_2ND), is(false));
		assertThat(WK_2012_1ST.equals(CalendarWeek.from(2011, 1)), is(false));
		
		assertThat(WK_2011_53RD.equals(WK_2012_1ST), is(false));
		assertThat(WK_2012_1ST.equals(null), is(false));
		assertThat(WK_2012_1ST.equals(new Object()), is(false));
	}
	
	/**
	 * {@link CalendarWeek#toString()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_toString() throws Exception {
		assertThat(WK_2011_52ND.toString(), is("2011-52nd"));
		assertThat(WK_2011_53RD.toString(), is("2011-53rd"));
		assertThat(WK_2012_1ST.toString(), is("2012-1st"));
		assertThat(WK_2012_2ND.toString(), is("2012-2nd"));
		assertThat(WK_2013_1ST.toString(), is("2013-1st"));
		assertThat(WK_2013_2ND.toString(), is("2013-2nd"));
	}
	
	/**
	 * {@link CalendarWeek#from(CalendarDate)}
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_from() throws Exception {
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 25)), is(CalendarWeek.from(2011, 51)));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 26)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 27)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 28)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 29)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 30)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2011, 12, 31)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 1)), is(WK_2011_52ND));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 2)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 3)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 4)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 5)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 6)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 7)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 8)), is(WK_2012_1ST));
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 1, 9)), is(WK_2012_2ND));
		
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 2, 24)), is(CalendarWeek.from(2012, 8)));
		
		assertThat(CalendarWeek.from(CalendarDate.from(2008, 12, 28)), is(CalendarWeek.from(2008, 52)));
		assertThat(CalendarWeek.from(CalendarDate.from(2008, 12, 29)), is(CalendarWeek.from(2009, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2008, 12, 30)), is(CalendarWeek.from(2009, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2008, 12, 31)), is(CalendarWeek.from(2009, 1)));
		assertThat(CalendarWeek.from(CalendarDate.from(2009, 1, 1)), is(CalendarWeek.from(2009, 1)));
		
	}
	
	/**
	 * {@link CalendarWeek#asYearInterval()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_asYearInterval() throws Exception {
		assertThat(CalendarWeek.from(2012, 8).asYearInterval(), is(CalendarInterval.year(2012)));
	}
	
	/**
	 * {@link CalendarWeek#at(DayOfWeek)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_at() throws Exception {
		assertThat(CalendarWeek.from(2012, 8).at(DayOfWeek.FRIDAY), is(CalendarDate.from(2012, 2, 24)));
	}
	
	/**
	 * {@link CalendarWeek#breachEncapsulationOfMonth()}, {@link CalendarWeek#breachEncapsulationOfYear()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_breachEncapsulationOf() throws Exception {
		CalendarWeek week = CalendarWeek.from(2012, 8);
		assertThat(week.breachEncapsulationOfMonth(), is(WeekOfYear.valueOf(8)));
		assertThat(week.breachEncapsulationOfYear(), is(2012));
	}
	
	/**
	 * {@link CalendarWeek#compareTo(CalendarWeek)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test07_compareTo() throws Exception {
		assertThat(CalendarWeek.from(2012, 8).compareTo(CalendarWeek.from(2012, 8)), is(0));
		assertThat(CalendarWeek.from(2012, 9).compareTo(CalendarWeek.from(2012, 8)), is(greaterThan(0)));
		assertThat(CalendarWeek.from(2012, 8).compareTo(CalendarWeek.from(2012, 9)), is(lessThan(0)));
		
		try {
			CalendarWeek.from(2012, 8).compareTo(null);
			fail();
		} catch (NullPointerException e) {
			// success
		}
	}
	
	/**
	 * {@link CalendarWeek#getLastDay()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_getLastDay() throws Exception {
		assertThat(CalendarWeek.from(CalendarDate.from(2012, 2, 23)).getLastDay(), is(CalendarDate.from(2012, 2, 26)));
	}
	
	/**
	 * {@link CalendarWeek#hashCode()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_hashCode() throws Exception {
		assertThat(WK_2012_2ND.hashCode(), is(WK_2012_2ND.hashCode()));
		assertThat(CalendarWeek.from(2012, 3).hashCode(), is(not(WK_2012_2ND.hashCode())));
		assertThat(CalendarWeek.from(3, 2).hashCode(), is(not(CalendarWeek.from(2, 3).hashCode())));
	}
	
	/**
	 * {@link CalendarWeek#isAfter(CalendarWeek)}, {@link CalendarWeek#isBefore(CalendarWeek)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_isAfter_isBefore() throws Exception {
		assertThat(CalendarWeek.from(2012, 23).isAfter(CalendarWeek.from(2012, 22)), is(true));
		assertThat(CalendarWeek.from(2012, 23).isAfter(CalendarWeek.from(2012, 23)), is(false));
		assertThat(CalendarWeek.from(2012, 23).isAfter(CalendarWeek.from(2012, 24)), is(false));
		assertThat(CalendarWeek.from(2012, 23).isAfter(null), is(false));
		
		assertThat(CalendarWeek.from(2012, 23).isBefore(CalendarWeek.from(2012, 22)), is(false));
		assertThat(CalendarWeek.from(2012, 23).isBefore(CalendarWeek.from(2012, 23)), is(false));
		assertThat(CalendarWeek.from(2012, 23).isBefore(CalendarWeek.from(2012, 24)), is(true));
		assertThat(CalendarWeek.from(2012, 23).isBefore(null), is(false));
	}
	
	/**
	 * {@link CalendarWeek#plusWeeks(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_plusWeeks() throws Exception {
		assertThat(WK_2012_1ST.plusWeeks(1), is(WK_2012_2ND));
		assertThat(WK_2012_2ND.plusWeeks(-1), is(WK_2012_1ST));
		assertThat(WK_2012_2ND.plusWeeks(-1).plusWeeks(-1), is(WK_2011_52ND));
		assertThat(WK_2012_2ND.plusWeeks(-2), is(WK_2011_52ND));
		
		assertThat(WK_2013_1ST.plusWeeks(1), is(WK_2013_2ND));
		assertThat(WK_2013_2ND.plusWeeks(-1), is(WK_2013_1ST));
		assertThat(WK_2013_2ND.plusWeeks(-1).plusWeeks(-1), is(WK_2012_52ND));
		assertThat(WK_2013_2ND.plusWeeks(-2), is(WK_2012_52ND));
	}
}
