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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

import jp.xet.baseunits.time.Duration;
import jp.xet.baseunits.util.Ratio;

import com.google.common.base.Preconditions;

/**
 * 金額を表すクラス。
 * 
 * <p>ある一定の「量」と「通貨単位」から成るクラスである。</p>
 * 
 * @author daisuke
 * @since 1.0
 */
@SuppressWarnings("serial")
public class Money implements Comparable<Money>, Serializable {
	
	private static final Currency USD = Currency.getInstance("USD");
	
	private static final Currency EUR = Currency.getInstance("EUR");
	
	private static final Currency JPY = Currency.getInstance("JPY");
	
	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
	
	
	/**
	 * {@code amount}で表す量のドルを表す金額を返す。
	 * 
	 * <p>This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.</p>
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量のドルを表す金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money dollars(BigDecimal amount) {
		Preconditions.checkNotNull(amount);
		return Money.valueOf(amount, USD);
	}
	
	/**
	 * {@code amount}で表す量のドルを表す金額を返す。
	 * 
	 * <p>WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.</p>
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量のドルを表す金額
	 * @since 1.0
	 */
	public static Money dollars(double amount) {
		return Money.valueOf(amount, USD);
	}
	
	/**
	 * This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.
	 * @param amount 量
	 * @return {@code amount}で表す量のユーロを表す金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money euros(BigDecimal amount) {
		Preconditions.checkNotNull(amount);
		return Money.valueOf(amount, EUR);
	}
	
	/**
	 * WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.
	 * @param amount 量
	 * @return {@code amount}で表す量のユーロを表す金額
	 * @since 1.0
	 */
	public static Money euros(double amount) {
		return Money.valueOf(amount, EUR);
	}
	
	/**
	 * {@link Collection}に含む全ての金額の合計金額を返す。
	 * 
	 * <p>合計金額の通貨単位は、 {@code monies}の要素の（共通した）通貨単位となるが、
	 * {@link Collection}が空の場合は、現在のデフォルトロケールにおける通貨単位で、量が0の金額を返す。</p>
	 * 
	 * @param monies 金額の集合
	 * @return 合計金額
	 * @throws ClassCastException 引数の通貨単位の中に通貨単位が異なるものを含む場合。
	 * 				ただし、量が0の金額については通貨単位を考慮しないので例外は発生しない。
	 * @throws NullPointerException 引数またはその要素に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money sum(Collection<Money> monies) {
		Preconditions.checkNotNull(monies);
		if (monies.isEmpty()) {
			return Money.zero(Currency.getInstance(Locale.getDefault()));
		}
		Iterator<Money> iterator = monies.iterator();
		Money sum = iterator.next();
		while (iterator.hasNext()) {
			Money each = iterator.next();
			sum = sum.plus(each);
		}
		return sum;
	}
	
	/**
	 * This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.
	 * 
	 * @param amount 量
	 * @param currency 通貨単位
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money valueOf(BigDecimal amount, Currency currency) {
		Preconditions.checkNotNull(amount);
		Preconditions.checkNotNull(currency);
		return Money.valueOf(amount, currency, RoundingMode.UNNECESSARY);
	}
	
	/**
	 * For convenience, an amount can be rounded to create a Money.
	 * 
	 * @param rawAmount 量
	 * @param currency 通貨単位
	 * @param roundingMode 丸めモード
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public static Money valueOf(BigDecimal rawAmount, Currency currency, RoundingMode roundingMode) {
		Preconditions.checkNotNull(rawAmount);
		Preconditions.checkNotNull(currency);
		Preconditions.checkNotNull(rawAmount);
		BigDecimal amount = rawAmount.setScale(currency.getDefaultFractionDigits(), roundingMode);
		return new Money(amount, currency);
	}
	
	/**
	 * WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.
	 * 
	 * @param dblAmount 量
	 * @param currency 通貨単位
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money valueOf(double dblAmount, Currency currency) {
		Preconditions.checkNotNull(currency);
		return Money.valueOf(dblAmount, currency, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * Because of the indefinite precision of double, this method must round off
	 * the value. This method gives the client control of the rounding mode.
	 * 
	 * @param dblAmount 量
	 * @param currency 通貨単位
	 * @param roundingMode 丸めモード
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public static Money valueOf(double dblAmount, Currency currency, RoundingMode roundingMode) {
		Preconditions.checkNotNull(currency);
		Preconditions.checkNotNull(roundingMode);
		BigDecimal rawAmount = new BigDecimal(dblAmount);
		return Money.valueOf(rawAmount, currency, roundingMode);
	}
	
	/**
	 * This creation method is safe to use. It will adjust scale, but will not
	 * round off the amount.
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量の円を表す金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money yens(BigDecimal amount) {
		Preconditions.checkNotNull(amount);
		return Money.valueOf(amount, JPY);
	}
	
	/**
	 * WARNING: Because of the indefinite precision of double, this method must
	 * round off the value.
	 * 
	 * @param amount 量
	 * @return {@code amount}で表す量の円を表す金額
	 * @since 1.0
	 */
	public static Money yens(double amount) {
		return Money.valueOf(amount, JPY);
	}
	
	/**
	 * 指定した通貨単位を持つ、量が0の金額を返す。
	 * 
	 * @param currency 通貨単位
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Money zero(Currency currency) {
		Preconditions.checkNotNull(currency);
		return Money.valueOf(0.0, currency);
	}
	
	
	/** 量 */
	final BigDecimal amount;
	
	/** 通貨単位 */
	final Currency currency;
	
	
	/**
	 * The constructor does not complex computations and requires simple, inputs
	 * consistent with the class invariant. Other creation methods are available
	 * for convenience.
	 * 
	 * @param amount 量
	 * @param currency 通貨単位
	 * @throws IllegalArgumentException 引数{@code amount}のスケールと引数{@code currency}のfraction digitsが一致しない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Money(BigDecimal amount, Currency currency) {
		Preconditions.checkNotNull(amount);
		Preconditions.checkNotNull(currency);
		if (amount.scale() != currency.getDefaultFractionDigits()) {
			throw new IllegalArgumentException("Scale of amount does not match currency");
		}
		this.currency = currency;
		this.amount = amount;
	}
	
	/**
	 * Returns a {@link Money} whose amount is the absolute amount of this {@link Money}, and whose scale is this.scale().
	 * 
	 * @return 絶対金額
	 * @since 1.0
	 */
	public Money abs() {
		return Money.valueOf(amount.abs(), currency);
	}
	
	/**
	 * この金額に対して、指定した{@code ratio}の割合の金額を返す。
	 * 
	 * @param ratio 割合
	 * @param scale スケール
	 * @param roundingMode 丸めモード
	 * @return 指定した割合の金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money applying(Ratio ratio, int scale, RoundingMode roundingMode) {
		Preconditions.checkNotNull(ratio);
		Preconditions.checkNotNull(roundingMode);
		BigDecimal newAmount = ratio.times(amount).decimalValue(scale, roundingMode);
		return Money.valueOf(newAmount, currency);
	}
	
	/**
	 * この金額に対して、指定した{@code ratio}の割合の金額を返す。
	 * 
	 * @param ratio 割合
	 * @param roundingMode 丸めモード
	 * @return 指定した割合の金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money applying(Ratio ratio, RoundingMode roundingMode) {
		Preconditions.checkNotNull(ratio);
		Preconditions.checkNotNull(roundingMode);
		return applying(ratio, currency.getDefaultFractionDigits(), roundingMode);
	}
	
	/**
	 * このオブジェクトの{@link #amount}フィールド（量）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * <p>How best to handle access to the internals? It is needed for
	 * database mapping, UI presentation, and perhaps a few other
	 * uses. Yet giving public access invites people to do the
	 * real work of the Money object elsewhere.
	 * Here is an experimental approach, giving access with a 
	 * warning label of sorts. Let us know how you like it.</p>
	 * 
	 * @return 量
	 * @since 1.0
	 */
	public BigDecimal breachEncapsulationOfAmount() {
		return amount;
	}
	
	/**
	 * このオブジェクトの{@link #currency}フィールド（通貨単位）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 通貨単位
	 * @since 1.0
	 */
	public Currency breachEncapsulationOfCurrency() {
		return currency;
	}
	
	/**
	 * 金額同士の比較を行う。
	 * 
	 * <p>相対的に量が小さい方を「小さい」と判断する。通貨単位が異なる場合は {@link ClassCastException}を
	 * スローするが、どちらか片方の量が{@code 0}である場合は例外をスローしない。</p>
	 * 
	 * <p>例えば{@code 10 USD}と{@code 0 JPY}は、後者の方が小さい。
	 * また、{@code 0 USD}と{@code 0 JPY}は同じである。</p>
	 * 
	 * @param other 比較対象
	 * @return {@link Comparable#compareTo(Object)}に準じる
	 * @throws ClassCastException 比較対象の通貨単位が異なり、かつ双方の量がどちらも0ではない場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	@Override
	public int compareTo(Money other) {
		if (other == null) {
			throw new NullPointerException();
		}
		if (hasSameCurrencyAs(other) == false) {
			throw new ClassCastException("Compare is not defined between different currencies");
		}
		return amount.compareTo(other.amount);
	}
	
	/**
	 * この金額を、{@code divisor}個に均等に分割した場合の金額を返す。
	 * 
	 * @param divisor 除数
	 * @param roundingMode 丸めモード
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money dividedBy(BigDecimal divisor, RoundingMode roundingMode) {
		Preconditions.checkNotNull(divisor);
		Preconditions.checkNotNull(roundingMode);
		BigDecimal newAmount = amount.divide(divisor, roundingMode);
		return Money.valueOf(newAmount, currency);
	}
	
	/**
	 * この金額を、{@code divisor}個に均等に分割した場合の金額を返す。
	 * 
	 * <p>丸めモードは {@link RoundingMode#HALF_EVEN} を適用する。</p>
	 * 
	 * @param divisor 除数
	 * @return 金額
	 * @since 1.0
	 */
	public Money dividedBy(double divisor) {
		return dividedBy(divisor, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * この金額を、{@code divisor}個に均等に分割した場合の金額を返す。
	 * 
	 * @param divisor 除数
	 * @param roundingMode 丸めモード
	 * @return 金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money dividedBy(double divisor, RoundingMode roundingMode) {
		Preconditions.checkNotNull(roundingMode);
		return dividedBy(new BigDecimal(divisor), roundingMode);
	}
	
	/**
	 * この金額の、{@code divisor}に対する割合を返す。
	 * 
	 * @param divisor 除数
	 * @return 割合
	 * @throws ClassCastException 引数の通貨単位がこの金額の通貨単位と異なる場合
	 * @throws ArithmeticException 引数{@code divisor}の量が0だった場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Ratio dividedBy(Money divisor) {
		Preconditions.checkNotNull(divisor);
		checkHasSameCurrencyAs(divisor);
		return Ratio.of(amount, divisor.amount);
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
		Money other = (Money) obj;
		if (amount.equals(other.amount) == false) {
			return false;
		}
		if (hasSameCurrencyAs(other) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		assert amount != null;
		assert currency != null;
		final int prime = 31;
		int result = 1;
		result = prime * result + amount.hashCode();
		result = prime * result + currency.hashCode();
		return result;
	}
	
	/**
	 * このインスタンがあらわす金額が、{@code other}よりも大きいかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}とする。</p>
	 * 
	 * @param other 基準金額
	 * @return 大きい場合は{@code true}、そうでない場合は{@code false}
	 * @throws ClassCastException 引数の通貨単位がこの金額の通貨単位と異なる場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isGreaterThan(Money other) {
		return compareTo(other) > 0;
	}
	
	/**
	 * このインスタンがあらわす金額が、{@code other}よりも小さいかどうか調べる。
	 * 
	 * <p>等価の場合は{@code false}とする。</p>
	 * 
	 * @param other 基準金額
	 * @return 小さい場合は{@code true}、そうでない場合は{@code false}
	 * @throws ClassCastException 引数の通貨単位がこの金額の通貨単位と異なる場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public boolean isLessThan(Money other) {
		return compareTo(other) < 0;
	}
	
	/**
	 * このインスタンがあらわす金額が、負の金額かどうか調べる。
	 * 
	 * <p>ゼロの場合は{@code false}とする。</p>
	 * 
	 * @return 負の金額である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isNegative() {
		return amount.compareTo(BigDecimal.ZERO) < 0;
	}
	
	/**
	 * このインスタンがあらわす金額が、正の金額かどうか調べる。
	 * 
	 * <p>ゼロの場合は{@code false}とする。</p>
	 * 
	 * @return 正の金額である場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isPositive() {
		return amount.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * このインスタンがあらわす金額が、ゼロかどうか調べる。
	 * 
	 * @return ゼロである場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0
	 */
	public boolean isZero() {
		return equals(Money.valueOf(0.0, currency));
	}
	
	/**
	 * この金額から{@code other}を差し引いた金額を返す。
	 * 
	 * @param other 金額
	 * @return 差し引き金額
	 * @throws ClassCastException 引数の通貨単位がこの金額の通貨単位と異なる場合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Money minus(Money other) {
		Preconditions.checkNotNull(other);
		return plus(other.negated());
	}
	
	/**
	 * Returns a {@link Money} whose amount is (-amount), and whose scale is this.scale().
	 * 
	 * @return 金額
	 * @since 1.0
	 */
	public Money negated() {
		return Money.valueOf(amount.negate(), currency);
	}
	
	/**
	 * 指定した時間量に対する、この金額の割合を返す。
	 * 
	 * @param duration 時間量
	 * @return 割合
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public MoneyTimeRate per(Duration duration) {
		Preconditions.checkNotNull(duration);
		return new MoneyTimeRate(this, duration);
	}
	
	/**
	 * この金額に{@code other}を足した金額を返す。
	 * 
	 * @param other 金額
	 * @return 足した金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @throws ClassCastException 引数の通貨単位がこの金額の通貨単位と異なる場合
	 * @since 1.0
	 */
	public Money plus(Money other) {
		Preconditions.checkNotNull(other);
		checkHasSameCurrencyAs(other);
		return Money.valueOf(amount.add(other.amount), currency);
	}
	
	/**
	 * この金額に{@code factor}を掛けた金額を返す。
	 * 
	 * <p>丸めモードは {@link RoundingMode#HALF_EVEN} を適用する。</p>
	 * 
	 * TODO: Many apps require carrying extra precision in intermediate
	 * calculations. The use of Ratio is a beginning, but need a comprehensive
	 * solution. Currently, an invariant of Money is that the scale is the
	 * currencies standard scale, but this will probably have to be suspended or
	 * elaborated in intermediate calcs, or handled with defered calculations
	 * like Ratio.
	 * 
	 * @param factor 係数
	 * @return 掛けた金額
	 * @since 1.0
	 */
	public Money times(BigDecimal factor) {
		return times(factor, DEFAULT_ROUNDING_MODE);
	}
	
	/**
	 * この金額に{@code factor}を掛けた金額を返す。
	 * 
	 * TODO: BigDecimal.multiply() scale is sum of scales of two multiplied
	 * numbers. So what is scale of times?
	 * 
	 * @param factor 係数
	 * @param roundingMode 丸めモード
	 * @return 掛けた金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money times(BigDecimal factor, RoundingMode roundingMode) {
		Preconditions.checkNotNull(factor);
		Preconditions.checkNotNull(roundingMode);
		return Money.valueOf(amount.multiply(factor), currency, roundingMode);
	}
	
	/**
	 * この金額に{@code amount}を掛けた金額を返す。
	 * 
	 * <p>丸めモードは {@link RoundingMode#HALF_EVEN} を適用する。</p>
	 * 
	 * @param amount 係数
	 * @return 掛けた金額
	 * @since 1.0
	 */
	public Money times(double amount) {
		return times(new BigDecimal(amount));
	}
	
	/**
	 * この金額に{@code amount}を掛けた金額を返す。
	 * 
	 * @param amount 係数
	 * @param roundingMode 丸めモード
	 * @return 掛けた金額
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public Money times(double amount, RoundingMode roundingMode) {
		Preconditions.checkNotNull(roundingMode);
		return times(new BigDecimal(amount), roundingMode);
	}
	
	/**
	 * この金額に{@code amount}を掛けた金額を返す。
	 * 
	 * <p>丸めモードは {@link RoundingMode#HALF_EVEN} を適用する。</p>
	 * 
	 * @param amount 係数
	 * @return 掛けた金額
	 * @since 1.0
	 */
	public Money times(int amount) {
		return times(new BigDecimal(amount));
	}
	
	@Override
	public String toString() {
		return currency.getSymbol() + " " + amount;
	}
	
	/**
	 * 指定したロケールにおける、単位つきの金額表現の文字列を返す。
	 * 
	 * @param locale ロケール。{@code null}の場合は {@link Locale#getDefault()} を利用する。
	 * @return 金額の文字列表現
	 * @since 1.0
	 */
	public String toString(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		Preconditions.checkNotNull(locale);
		return currency.getSymbol(locale) + " " + amount;
	}
	
//	BigDecimal getAmount() {
//		return amount;
//	}
//	
//	Currency getCurrency() {
//		return currency;
//	}
	
	boolean hasSameCurrencyAs(Money arg) {
		return currency.equals(arg.currency) || arg.amount.equals(BigDecimal.ZERO) || amount.equals(BigDecimal.ZERO);
	}
	
	/**
	 * この金額に、最小の単位金額を足した金額、つまりこの金額よりも1ステップ分大きな金額を返す。
	 * 
	 * @return この金額よりも1ステップ分大きな金額
	 */
	Money incremented() {
		return plus(minimumIncrement());
	}
	
	/**
	 * 最小の単位金額を返す。
	 * 
	 * <p>例えば、日本円は1円であり、US$は1セント（つまり0.01ドル）である。</p>
	 * 
	 * This probably should be Currency responsibility. Even then, it may need
	 * to be customized for specialty apps because there are other cases, where
	 * the smallest increment is not the smallest unit.
	 * 
	 * @return 最小の単位金額
	 */
	Money minimumIncrement() {
		BigDecimal increment = BigDecimal.ONE.movePointLeft(currency.getDefaultFractionDigits());
		return Money.valueOf(increment, currency);
	}
	
	private void checkHasSameCurrencyAs(Money aMoney) {
		if (hasSameCurrencyAs(aMoney) == false) {
			throw new ClassCastException(aMoney.toString() + " is not same currency as " + this.toString());
		}
	}
	
//  TODO: Provide some currency-dependent formatting. Java 1.4 Currency doesn't do it.
//  public String formatString() {
//      return currency.formatString(amount());
//  }
//  public String localString() {
//      return currency.getFormat().format(amount());
//  }
}
