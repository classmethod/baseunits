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

/**
 * 現在の{@link TimePoint}を返す責務を表すインターフェイス。
 * 
 * @author daisuke
 * @since 1.0
 */
public interface TimeSource {
	
	/**
	 * 現在の{@link TimePoint}を返す。
	 * 
	 * @return 現在の{@link TimePoint}
	 * @throws TimeSourceException 現在の{@link TimePoint}の取得に失敗した場合
	 * @since 1.0
	 */
	TimePoint now();
}
