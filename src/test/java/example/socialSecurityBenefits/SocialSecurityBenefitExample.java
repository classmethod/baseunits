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
package example.socialSecurityBenefits;

import java.math.RoundingMode;

import jp.xet.baseunits.money.Money;
import jp.xet.baseunits.time.CalendarDate;
import jp.xet.baseunits.time.CalendarInterval;
import jp.xet.baseunits.util.Ratio;
import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Example.
 */
public class SocialSecurityBenefitExample extends TestCase {
	
	/*
	 * Money calculations often must follow seemingly arbitrary rules, and the
	 * intricate computations must be exact. Real-world, public examples are
	 * provided courtesy of our Social Security Agency.
	 * 
	 * The US Social Security regulation 404.430 has the following example
	 * calculations. (see http://www.ssa.gov/OP_Home/cfr20/404/404-0430.htm)
	 * 
	 * The examples are 25 years old, but the regulations are current.
	 */

	/**
	 * Example: (Simplified exerpt from
	 * http://www.ssa.gov/OP_Home/cfr20/404/404-0439.htm) Worker is entitled to
	 * an old-age insurance benefit of $200 payable for October, which is
	 * apportioned as follows after rounding each share down to the nearest
	 * dollar. See regulation �404.304(f).
	 * 
	 * Fraction Benefit Worker 2/3 $133 Spouse 1/3 66 Total 199
	 */
	@Test
	public void testArbitraryRoundingRuleInDeductionsFromFamilyBenefits() {
		Money benefit = Money.dollars(200);
		Ratio workerShare = Ratio.of(2, 3);
		Ratio spouseShare = Ratio.of(1, 3);
		int roundingScale = 0;
		Money workerBenefit = benefit.applying(workerShare, roundingScale, RoundingMode.DOWN);
		Money spouseBenefit = benefit.applying(spouseShare, roundingScale, RoundingMode.DOWN);
		assertEquals(Money.dollars(133), workerBenefit);
		assertEquals(Money.dollars(66), spouseBenefit);
	}
	
	/**
	 * Example.
	 */
	@Test
	@Ignore
	public void testExcessEarnings() {
		/*
		 * Example 1. The self-employed beneficiary attained age 72 in July
		 * 1979. His net earnings for 1979, his taxable year, were $12,000. The
		 * pro rata share of the net earnings for the period prior to July is
		 * $6,000. His excess earnings for 1979 for retirement test purposes are
		 * $750. This is computed by subtracting $4,500 ($375�12), the exempt
		 * amount for 1979, from $6,000 and dividing the result by 2.
		 */

		// Does beneficiary attain age 72 during the benefit year 1972?
		CalendarInterval y1979 = CalendarInterval.year(1979);
		CalendarDate birthday72 = CalendarDate.from(1979, 7, 15);
		assertTrue(y1979.includes(birthday72));
		
		// Note that all calculations are based on entire months.
		// The proration is not based on the number of days prior to
		// turning 72 (the exempt age). It is base on the number
		// of months prior to the month in which he turned 72.
		
//		CalendarInterval subintervalOfYearSubjectToExcess =
//				CalendarInterval.inclusive(y1979.start(), birthday72.firstOfMonth().previousDay());
//		
//		Ratio portionOfYearSubject =
//				subintervalOfYearSubjectToExcess.lengthInMonths().dividedBy(y1979.lengthInMonths());
//		
//		Money earningsFor1979 = Money.dollars(12000);
//		assertEquals(Money.dollars(6000), netEarningsPriorToMonthOfTurning72);
		
		Money exemptMonthlyEarnings = Money.dollars(375);
		Money exemptAnnualEarnings = exemptMonthlyEarnings.times(12);
		assertEquals(Money.dollars(4500), exemptAnnualEarnings);
//		Money annualExcessEarnings = earningsFor1979.minus(exemptAnnualEarnings);
//		
//		Money excessEarnings = earningsFor1979.minus(exemptEarnings);
//		assertEquals(Money.dollars(750), excessEarnings);
	}
	
}
