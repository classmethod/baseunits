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
package jp.xet.baseunits.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.base.Preconditions;

/**
 * {@link Ratio}は、2つ同じ単位を持つの量の商（比率）であり、単位のない値である。
 * 
 * <p>このクラスの利点は、比率の計算を遅延評価できることにある。</p>
 * 
 * <p>Ratio represents the unitless division of two quantities of the same type.
 * The key to its usefulness is that it defers the calculation of a decimal
 * value for the ratio. An object which has responsibility for the two values in
 * the ratio and understands their quantities can create the ratio, which can
 * then be used by any client in a unitless form, so that the client is not
 * required to understand the units of the quantity. At the same time, this
 * gives control of the precision and rounding rules to the client, when the
 * time comes to compute a decimal value for the ratio. The client typically has
 * the responsibilities that enable an appropriate choice of these parameters.<p>
 * 
 * @author daisuke
 * @since 1.0
 */
public class Ratio {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param fractional 分数
	 * @return 与えた分数であらわされる比率
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public static Ratio of(BigDecimal fractional) {
		return new Ratio(fractional, BigDecimal.valueOf(1));
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 * @return 引数に与えた分子、分母からなる比
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @throws ArithmeticException 引数{@code denominator}が0だった場合
	 * @since 1.0
	 */
	public static Ratio of(BigDecimal numerator, BigDecimal denominator) {
		return new Ratio(numerator, denominator);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 * @return 引数に与えた分子、分母からなる比率
	 * @throws ArithmeticException 引数{@code denominator}が0だった場合
	 * @since 1.0
	 */
	public static Ratio of(long numerator, long denominator) {
		return new Ratio(BigDecimal.valueOf(numerator), BigDecimal.valueOf(denominator));
	}
	
	
	/** 分子をあらわす数 */
	private final BigDecimal numerator;
	
	/** 分母をあらわす数 */
	private final BigDecimal denominator;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @throws ArithmeticException 引数{@code denominator}が0だった場合
	 * @since 1.0
	 */
	public Ratio(BigDecimal numerator, BigDecimal denominator) {
		Preconditions.checkNotNull(numerator);
		Preconditions.checkNotNull(denominator);
		if (denominator.equals(BigDecimal.ZERO)) {
			throw new ArithmeticException("denominator is zero");
		}
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	/**
	 * このオブジェクトの{@link #denominator}フィールド（分母をあらわす数）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 分母をあらわず数
	 * @since 1.0
	 */
	public BigDecimal breachEncapsulationOfDenominator() {
		return denominator;
	}
	
	/**
	 * このオブジェクトの{@link #numerator}フィールド（分子をあらわす数）を返す。
	 * 
	 * <p>CAUTION: このメソッドは、このオブジェクトがカプセル化する要素を外部に暴露する。取り扱いには充分注意のこと。</p>
	 * 
	 * @return 分子をあらわす数
	 * @since 1.0
	 */
	public BigDecimal breachEncapsulationOfNumerator() {
		return numerator;
	}
	
	/**
	 * 比率を {@link BigDecimal}型で取得する。
	 *  
	 * @param scale 小数点以下の有効数字
	 * @param roundingMode 丸めモード
	 * @return この比率の {@link BigDecimal} 型の表現
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.1
	 */
	public BigDecimal decimalValue(int scale, RoundingMode roundingMode) {
		Preconditions.checkNotNull(roundingMode);
		return numerator.divide(denominator, scale, roundingMode);
	}
	
	/**
	 * このオブジェクトと、{@code obj}の同一性を検証する。
	 * 
	 * <p>{@code obj}が {@code null}である場合、または{@link Ratio}型やそのサブクラスではない場合、{@code false}を返す。</p>
	 * 
	 * <p>{@code obj}の、分母と分子が共に一致する場合、{@code true}を返す。
	 * {@code 2/3} と {@code 4/6} は、評価結果としては同一であるが、分母同士、分子同士が
	 * 異なるため、このメソッドでは {@code true} と判断されず、 {@code false} となる。
	 * 
	 * @param obj 比較対象オブジェクト
	 * @return 同一の場合は{@code true}、そうでない場合は{@code false}
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since 1.0
	 */
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
		Ratio other = (Ratio) obj;
		if (denominator.equals(other.denominator) == false) {
			return false;
		}
		if (numerator.equals(other.numerator) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + denominator.hashCode();
		result = prime * result + numerator.hashCode();
		return result;
	}
	
	/**
	 * この比率と {@code multiplier} の積からなる比率。
	 * 
	 * <p>計算結果は、分母は変化せず、分子は分子と {@code multiplyer} の積からなる比率となる。</p>
	 * 
	 * @param multiplier 乗数
	 * @return 積
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Ratio times(BigDecimal multiplier) {
		Preconditions.checkNotNull(multiplier);
		return Ratio.of(numerator.multiply(multiplier), denominator);
	}
	
	/**
	 * この比率と {@code multiplier} の積からなる比率。
	 * 
	 * <p>計算結果は、分子同士・分母同士の積からなる比率となる。</p>
	 * 
	 * @param multiplier 乗数比率
	 * @return 積
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 1.0
	 */
	public Ratio times(Ratio multiplier) {
		Preconditions.checkNotNull(multiplier);
		return Ratio.of(numerator.multiply(multiplier.numerator), denominator.multiply(multiplier.denominator));
	}
	
	/** この比率の文字列表現を取得する。
	 * 
	 * <p>"分子/分母"という表記となる。</p>
	 * 
	 * @see java.lang.Object#toString()
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return numerator.toString() + "/" + denominator;
	}
}
