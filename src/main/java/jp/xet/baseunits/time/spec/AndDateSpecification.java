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

import jp.xet.baseunits.time.CalendarDate;

import com.google.common.base.Preconditions;

/**
 * {@link DateSpecification}の論理積をとるクラス。
 * 
 * @author daisuke
 * @since 2.0
 */
public final class AndDateSpecification extends AbstractDateSpecification {
	
	final DateSpecification left;
	
	final DateSpecification right;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param left left side Specification.
	 * @param right right side Specification.
	 * @throws NullPointerException 引数に{@code null}を与えた場合
	 * @since 2.0
	 */
	public AndDateSpecification(DateSpecification left, DateSpecification right) {
		Preconditions.checkNotNull(left);
		Preconditions.checkNotNull(right);
		this.left = left;
		this.right = right;
	}
	
	/**
	 * 左辺値を返す。
	 * 
	 * @return 左辺値
	 */
	public DateSpecification getLeft() {
		return left;
	}
	
	/**
	 * 右辺値を返す。
	 * 
	 * @return 右辺値
	 */
	public DateSpecification getRight() {
		return right;
	}
	
	@Override
	public boolean isSatisfiedBy(CalendarDate t) {
		return left.isSatisfiedBy(t) && right.isSatisfiedBy(t);
	}
}
