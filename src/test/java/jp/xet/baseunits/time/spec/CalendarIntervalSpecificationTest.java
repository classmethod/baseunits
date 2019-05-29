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
package jp.xet.baseunits.time.spec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;

import org.junit.Test;

/**
 * {@link CalendarIntervalSpecification}のテストクラス。
 */
public class CalendarIntervalSpecificationTest {
	
	private static final CalendarIntervalSpecification Y2011 = new CalendarIntervalSpecification(
			CalendarInterval.year(2011));
	
	private static final CalendarInterval Y2011_JUL_AUG = CalendarInterval.inclusive(
			CalendarDate.from(2011, 7, 1), CalendarDate.from(2011, 8, 31));
	
	private static final CalendarInterval Y2010JUL_2011AUG = CalendarInterval.inclusive(
			CalendarDate.from(2010, 7, 1), CalendarDate.from(2011, 8, 31));
	
	private static final CalendarInterval EVER_2011AUG = CalendarInterval.inclusive(
			null, CalendarDate.from(2011, 8, 31));
	
	private static final CalendarInterval Y2010JUL_EVER = CalendarInterval.inclusive(
			CalendarDate.from(2010, 7, 1), null);
	
	private static final CalendarInterval Y2012JUL_2012AUG = CalendarInterval.inclusive(
			CalendarDate.from(2012, 7, 1), CalendarDate.from(2012, 8, 31));
	
	private static final CalendarInterval Y2010JUL_2010AUG = CalendarInterval.inclusive(
			CalendarDate.from(2010, 7, 1), CalendarDate.from(2010, 8, 31));
	
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_firstOccurrenceIn() {
		assertThat(Y2011.firstOccurrenceIn(Y2011_JUL_AUG), is(CalendarDate.from(2011, 7, 1)));
		assertThat(Y2011.firstOccurrenceIn(Y2010JUL_2011AUG), is(CalendarDate.from(2011, 1, 1)));
		assertThat(Y2011.firstOccurrenceIn(EVER_2011AUG), is(CalendarDate.from(2011, 1, 1)));
		assertThat(Y2011.firstOccurrenceIn(Y2010JUL_EVER), is(CalendarDate.from(2011, 1, 1)));
		assertThat(Y2011.firstOccurrenceIn(Y2012JUL_2012AUG), is(nullValue()));
		assertThat(Y2011.firstOccurrenceIn(Y2010JUL_2010AUG), is(nullValue()));
	}
	
	@Test
	@SuppressWarnings("javadoc")
	public void test_isSatisfiedBy() {
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2011, 1, 1)), is(true));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2011, 2, 1)), is(true));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2011, 3, 1)), is(true));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2011, 4, 1)), is(true));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2011, 5, 1)), is(true));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2011, 12, 31)), is(true));
		
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2010, 1, 1)), is(false));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2012, 2, 1)), is(false));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2010, 3, 1)), is(false));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2012, 4, 1)), is(false));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2010, 5, 1)), is(false));
		assertThat(Y2011.isSatisfiedBy(CalendarDate.from(2012, 12, 31)), is(false));
	}
	
	/**
	 * 細かいメソッドいろいろテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_misc() throws Exception {
		assertThat(Y2011.getInterval(), is(CalendarInterval.year(2011)));
		assertThat(Y2011, hasToString(CalendarInterval.year(2011).toString()));
		
		Iterator<CalendarDate> itr = Y2011.iterateOver(CalendarInterval.inclusive(
				CalendarDate.from(2010, 1, 1), CalendarDate.from(2013, 4, 5)));
		
		CalendarDate expected = CalendarDate.from(2011, 1, 1);
		while (itr.hasNext()) {
			CalendarDate d = itr.next();
			assertThat(d, is(expected));
			expected = expected.nextDay();
		}
		assertThat(expected, is(CalendarDate.from(2012, 1, 1)));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
		
		Iterator<CalendarDate> itr2 = Y2011.iterateOver(CalendarInterval.year(2013));
		assertThat(itr2.hasNext(), is(false));
	}
}
