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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import jp.xet.baseunits.time.HourOfDay.Meridian;

import org.junit.Test;

/**
 * {@link HourOfDay}のテストクラス。
 */
public class HourOfDayTest {
	
	/**
	 * {@link HourOfDay#valueOf(int)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_24Simple() throws Exception {
		assertThat(HourOfDay.valueOf(22).breachEncapsulationOfValue(), is(22));
	}
	
	/**
	 * {@link HourOfDay#valueOf(int, Meridian)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_12Simple() throws Exception {
		assertThat(HourOfDay.valueOf(10, Meridian.PM), is(HourOfDay.valueOf(22)));
		assertThat(HourOfDay.valueOf(3, Meridian.AM), is(HourOfDay.valueOf(3)));
	}
	
	/**
	 * {@link HourOfDay#valueOf(int)}の不正引数テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_24IllegalLessThanZero() throws Exception {
		try {
			HourOfDay.valueOf(-1);
			fail();
		} catch (IllegalArgumentException ex) {
			// success
		}
	}
	
	/**
	 * {@link HourOfDay#valueOf(int)}の不正引数テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_24GreaterThan() throws Exception {
		try {
			HourOfDay.valueOf(24);
			fail();
		} catch (IllegalArgumentException ex) {
			// success
		}
	}
	
	/**
	 * {@link HourOfDay#valueOf(int, Meridian)}の不正引数テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test05_12IllegalLessThanZero() throws Exception {
		try {
			HourOfDay.valueOf(-1, Meridian.PM);
			fail();
		} catch (IllegalArgumentException ex) {
			// success
		}
	}
	
	/**
	 * {@link HourOfDay#valueOf(int, Meridian)}の不正引数テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_12GreaterThan() throws Exception {
		try {
			HourOfDay.valueOf(13, Meridian.AM);
			fail();
		} catch (IllegalArgumentException ex) {
			// success
		}
	}
	
	/**
	 * {@link HourOfDay#isAfter(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test08_LaterAfterEarlier() throws Exception {
		HourOfDay later = HourOfDay.valueOf(8);
		HourOfDay earlier = HourOfDay.valueOf(6);
		assertThat(later.isAfter(earlier), is(true));
	}
	
	/**
	 * {@link HourOfDay#isAfter(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_EarlierAfterLater() throws Exception {
		HourOfDay earlier = HourOfDay.valueOf(8);
		HourOfDay later = HourOfDay.valueOf(20);
		assertThat(earlier.isAfter(later), is(false));
	}
	
	/**
	 * {@link HourOfDay#isAfter(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_EqualAfterEqual() throws Exception {
		HourOfDay anHour = HourOfDay.valueOf(8);
		HourOfDay anotherHour = HourOfDay.valueOf(8);
		assertThat(anHour.isAfter(anotherHour), is(false));
	}
	
	/**
	 * {@link HourOfDay#isBefore(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test11_LaterBeforeEarlier() throws Exception {
		HourOfDay later = HourOfDay.valueOf(8);
		HourOfDay earlier = HourOfDay.valueOf(6);
		assertThat(later.isBefore(earlier), is(false));
	}
	
	/**
	 * {@link HourOfDay#isBefore(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test12_EarlierBeforeLater() throws Exception {
		HourOfDay earlier = HourOfDay.valueOf(8);
		HourOfDay later = HourOfDay.valueOf(20);
		assertThat(earlier.isBefore(later), is(true));
	}
	
	/**
	 * {@link HourOfDay#isBefore(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test13_EqualBeforeEqual() throws Exception {
		HourOfDay anHour = HourOfDay.valueOf(8);
		HourOfDay anotherHour = HourOfDay.valueOf(8);
		assertThat(anHour.isBefore(anotherHour), is(false));
	}
	
	/**
	 * {@link HourOfDay#equals(Object)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test14_equals() throws Exception {
		HourOfDay eightHours = HourOfDay.valueOf(8);
		HourOfDay eightHours2 = HourOfDay.valueOf(8);
		HourOfDay sixHours = HourOfDay.valueOf(6);
		assertThat(eightHours.equals(eightHours), is(true));
		assertThat(eightHours.equals(eightHours2), is(true));
		assertThat(eightHours.equals(sixHours), is(false));
		assertThat(eightHours.equals(new Object()), is(false));
		assertThat(eightHours.equals(null), is(false));
	}
	
	/**
	 * {@link HourOfDay#compareTo(HourOfDay)}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test15_compareTo() throws Exception {
		HourOfDay eightHours = HourOfDay.valueOf(8);
		HourOfDay sixHours = HourOfDay.valueOf(6);
		assertThat(eightHours.compareTo(eightHours), is(0));
		assertThat(eightHours.compareTo(sixHours), is(greaterThan(0)));
		assertThat(sixHours.compareTo(eightHours), is(lessThan(0)));
	}
}
