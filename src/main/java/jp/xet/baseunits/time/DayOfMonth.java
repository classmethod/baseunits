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

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * 1ヶ月間の中の特定の「日」を表すクラス。
 * 
 * <p>{@link CalendarDate} が「2012年11月27日」等を表現するのに対して、この型は年や月の概念を持たず、
 * 単に「27日」等を表現する。</p>
 * 
 * <p>タイムゾーンの概念はない。また、このクラスは特定の瞬間をモデリングしたものではなく、その日1日間全ての範囲を表すクラスである。</p>
 * 
 * @author daisuke
 * @since 1.0
 * @see CalendarDate
 */
@SuppressWarnings("serial")
public class DayOfMonth implements Comparable<DayOfMonth>, Serializable {
	
	/**
	 * {@link DayOfMonth}の値の最小値
	 * 
	 * @since 2.0
	 */
	public static final int MIN_VALUE = 1;
	
	/**
	 * {@link DayOfMonth}の値の最大値
	 * 
	 * @since 2.0
	 */
	public static final int MAX_VALUE = 31;
	
	/**
	 * {@link DayOfMonth}の最小値
	 * 
	 * @since 2.0
	 */
	public static final DayOfMonth MIN = DayOfMonth.valueOf(MIN_VALUE);
	
	/**
	 * {@link DayOfMonth}の最大値
	 * 
	 * @since 2.0
	 */
	public static final DayOfMonth MAX = DayOfMonth.valueOf(MAX_VALUE);
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param day 日をあらわす正数（1〜31）
	 * @return {@link DayOfMonth}
	 * @throws IllegalArgumentException 引数の値が1〜31の範囲ではない場合
	 * @since 1.0
	 */
	public static DayOfMonth valueOf(int day) {
		return new DayOfMonth(day);
	}
	
	
	/** 日をあらわす正数 */
	final int value;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param initial 日
	 * @since 1.0
	 */
	public DayOfMonth(int initial) {
		if (initial < MIN_VALUE || initial > MAX_VALUE) {
			throw new IllegalArgumentException("Illegal value for day of month: " + initial
					+ ", please use a value between 1 and 31");
		}
		value = initial;
	}
	
	@Override
	public int compareTo(DayOfMonth other) {
		return value - other.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DayOfMonth other = (DayOfMonth) obj;
		if (value != other.value) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	
	/**
	 * この日が、{@code other}よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象日
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isAfter(DayOfMonth other) {
		if (other == null) {
			return false;
		}
		return value > other.value;
	}
	
	/**
	 * この日が、{@code other}よりも未来であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一である場合は {@code true} を返す。</p>
	 * 
	 * @param other 比較対象日
	 * @return 未来である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.6
	 */
	public boolean isAfterOrEquals(DayOfMonth other) {
		if (other == null) {
			return false;
		}
		return value >= other.value;
	}
	
	/**
	 * この日を、指定した暦月に適用可能かどうか調べる。
	 * 
	 * <p>例えば、31日は6月に適用不可能であるが、7月には適用可能である。
	 * また、29日は閏年の2月に適用可能であるが、平年の2月には適用不可能である。</p>
	 * 
	 * @param month 暦月
	 * @return 適用可能な場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isApplyable(CalendarMonth month) {
		Preconditions.checkNotNull(month);
		return month.getLastDayOfMonth().isBefore(this) == false;
	}
	
	/**
	 * この日を、指定した年月に適用可能かどうか調べる。
	 * 
	 * <p>例えば、31日は6月に適用不可能であるが、7月には適用可能である。
	 * また、29日は閏年の2月に適用可能であるが、平年の2月には適用不可能である。</p>
	 * 
	 * @param year 年
	 * @param month 月
	 * @return 適用可能な場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isApplyable(int year, MonthOfYear month) {
		Preconditions.checkNotNull(month);
		return month.getLastDayOfThisMonth(year).isBefore(this) == false;
	}
	
	/**
	 * この日が、{@code other}よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一日である場合は {@code false} を返す。</p>
	 * 
	 * @param other 比較対象日
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isBefore(DayOfMonth other) {
		if (other == null) {
			return false;
		}
		return value < other.value;
	}
	
	/**
	 * この日が、{@code other}よりも過去であるかどうかを検証する。
	 * 
	 * <p>{@code other} が {@code null} である場合は {@code false} を返す。
	 * また、同一日である場合は {@code true} を返す。</p>
	 * 
	 * @param other 比較対象日
	 * @return 過去である場合は{@code true}、そうでない場合は{@code false}
	 * @since 2.6
	 */
	public boolean isBeforeOrEquals(DayOfMonth other) {
		if (other == null) {
			return false;
		}
		return value <= other.value;
	}
	
	/**
	 * 指定した暦月におけるこの日を表す暦日を返す。
	 * 
	 * @param month 暦月
	 * @return {@link CalendarDate}
	 * @throws IllegalArgumentException 引数{@code month}の暦日にこの日が存在しない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public CalendarDate on(CalendarMonth month) {
		return CalendarDate.from(month, this);
	}
	
	/**
	 * この日をあらわす正数を返す。
	 * 
	 * @return 日をあらわす正数（1〜31）
	 * @since 2.0
	 */
	public int toInt() {
		return value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
//	public DayOfYear on(MonthOfYear month) {
//		// ...
//	}
}
