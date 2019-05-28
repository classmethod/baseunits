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

/**
 * 1年間に一度だけマッチする暦日仕様インターフェイス。
 * 
 * @author daisuke
 * @since 2.0
 */
public interface AnnualDateSpecification extends DateSpecification {
	
	/**
	 * 指定した暦年においてこの暦日仕様を満たす暦日を返す。
	 * 
	 * @param year 西暦年をあらわす数
	 * @return {@link CalendarDate}
	 * @since 1.0
	 */
	CalendarDate ofYear(int year);
	
}
