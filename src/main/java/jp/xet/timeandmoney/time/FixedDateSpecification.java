/**
 * Copyright (c) 2004 Domain Language, Inc. (http://domainlanguage.com) This
 * free software is distributed under the "MIT" licence. See file licence.txt.
 * For more information, see http://timeandmoney.sourceforge.net.
 */

package jp.xet.timeandmoney.time;

import org.apache.commons.lang.Validate;

/**
 * X月Y日、を表す日付仕様。
 * 
 * @author daisuke
 */
class FixedDateSpecification extends AnnualDateSpecification {
	
	private final int month;
	
	private final int day;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param month 月を表す正数（1〜12）
	 * @param day 日を表す正数（1〜31）
	 * @throws IllegalArgumentException 引数{@code month}が1〜12の範囲ではない場合
	 * @throws IllegalArgumentException 引数{@code day}が1〜31の範囲ではない場合
	 */
	FixedDateSpecification(int month, int day) {
		Validate.isTrue(1 <= month && month <= 12);
		Validate.isTrue(1 <= day && day <= 31); // CHECKSTYLE IGNORE THIS LINE
		this.month = month;
		this.day = day;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate date) {
		return day == date.breachEncapsulationOfDay() && month == date.breachEncapsulationOfMonth();
	}
	
	@Override
	public CalendarDate ofYear(int year) {
		return CalendarDate.date(year, month, day);
	}
	
}