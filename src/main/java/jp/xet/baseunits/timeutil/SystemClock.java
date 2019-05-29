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
package jp.xet.baseunits.timeutil;

import jp.xet.baseunits.time.TimePoint;
import jp.xet.baseunits.time.TimeSource;

/**
 * システム時計に基づき、現在の{@link TimePoint}を返すクラス。
 * 
 * @author daisuke
 * @since 1.0
 */
public final class SystemClock implements TimeSource {
	
	private static final SystemClock INSTANCE = new SystemClock();
	
	
	/**
	 * システム時間に基づき現在の{@link TimePoint}を返す {@link TimeSource} を返す。
	 * 
	 * @return システム時間に基づき現在の{@link TimePoint}を返す {@link TimeSource}
	 * @since 1.0
	 */
	public static TimeSource timeSource() {
		// THINK なぜ INSTANCE を直接返さないのか？ 謎だ。
		return new TimeSource() {
			
			@Override
			public TimePoint now() {
				return INSTANCE.now();
			}
		};
	}
	
	private SystemClock() {
	}
	
	@Override
	public TimePoint now() {
		return TimePoint.from(System.currentTimeMillis());
	}
}
