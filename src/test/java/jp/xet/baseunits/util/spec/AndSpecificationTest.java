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
package jp.xet.baseunits.util.spec;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 * {@link AndSpecification}のテストクラス。
 */
public class AndSpecificationTest {
	
	/**
	 * {@code false} AND {@code false} が {@code false} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_falsefalse_To_false() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any(Void.class))).thenReturn(false);
		when(mock2.isSatisfiedBy(any(Void.class))).thenReturn(false);
		
		AndSpecification<Void> and = new AndSpecification<Void>(mock1, mock2);
		assertThat(and.isSatisfiedBy(null), is(false));
		
		verify(mock1).isSatisfiedBy(null);
//		verify(mock2, never()).isSatisfiedBy(null);
	}
	
	/**
	 * {@code false} AND {@code true} が {@code false} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_falsetrue_To_false() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any(Void.class))).thenReturn(false);
		when(mock2.isSatisfiedBy(any(Void.class))).thenReturn(true);
		
		AndSpecification<Void> and = new AndSpecification<Void>(mock1, mock2);
		assertThat(and.isSatisfiedBy(null), is(false));
		
		verify(mock1).isSatisfiedBy(null);
//		verify(mock2, never()).isSatisfiedBy(null);
	}
	
	/**
	 * {@code true} AND {@code false} が {@code false} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_truefalse_To_false() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any())).thenReturn(true);
		when(mock2.isSatisfiedBy(any())).thenReturn(false);
		
		AndSpecification<Void> nandot = new AndSpecification<Void>(mock1, mock2);
		assertThat(nandot.isSatisfiedBy(null), is(false));
		
		verify(mock1).isSatisfiedBy(null);
		verify(mock2).isSatisfiedBy(null);
	}
	
	/**
	 * {@code true} AND {@code true} が {@code true} となること。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_truetrue_To_true() throws Exception {
		@SuppressWarnings("unchecked")
		Specification<Void> mock1 = mock(Specification.class);
		@SuppressWarnings("unchecked")
		Specification<Void> mock2 = mock(Specification.class);
		when(mock1.isSatisfiedBy(any())).thenReturn(true);
		when(mock2.isSatisfiedBy(any())).thenReturn(true);
		
		AndSpecification<Void> and = new AndSpecification<Void>(mock1, mock2);
		assertThat(and.isSatisfiedBy(null), is(true));
		
		verify(mock1).isSatisfiedBy(null);
		verify(mock2).isSatisfiedBy(null);
	}
}
