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
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

/**
 * 時間記述のなんとなくテスト。
 */
public class NominalTimeTest {
	
	private static final TimeZone HONOLULU_TIME = TimeZone.getTimeZone("Pacific/Honolulu");
	
	
	/**
	 * {@link CalendarDate}と{@link TimeOfDay}を結合するテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_CombineNominalTimes() throws Exception {
		TimeOfDay fiveFifteenPM = TimeOfDay.from(17, 15, 0, 0);
		CalendarDate april19_2006 = CalendarDate.from(2006, 4, 19);
		TimePoint expectedCombination = TimePoint.at(2006, 4, 19, 17, 15, 0, 0, HONOLULU_TIME);
		TimePoint actual = fiveFifteenPM.asTimePointGiven(april19_2006, HONOLULU_TIME);
		assertThat(actual, is(expectedCombination));
	}
}
