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
package jp.xet.baseunits.money;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link Tally}のテストクラス。
 */
public class TallyTest {
	
	/**
	 * {@link Tally#net()}のテスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_Net() throws Exception {
		Tally tally = new Tally(Money.dollars(55.34), Money.dollars(12.22), Money.dollars(-3.07));
		assertThat(tally.net(), is(Money.dollars(64.49)));
		assertThat(tally, is(anyOf(
				hasToString("[USD 55.34, USD 12.22, USD -3.07]"),
				hasToString("[$ 55.34, $ 12.22, $ -3.07]"))));
		
		Iterator<Money> itr = tally.iterator();
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(Money.dollars(55.34)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(Money.dollars(12.22)));
		assertThat(itr.hasNext(), is(true));
		assertThat(itr.next(), is(Money.dollars(-3.07)));
		assertThat(itr.hasNext(), is(false));
		
		try {
			itr.next();
			fail();
		} catch (NoSuchElementException e) {
			// success
		}
	}
	
	/**
	 * 異なった通貨単位の {@link Tally} は作れない。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_() throws Exception {
		try {
			new Tally(Money.dollars(55.34), Money.yens(123));
			fail();
		} catch (IllegalArgumentException e) {
			// success
		}
	}
}
