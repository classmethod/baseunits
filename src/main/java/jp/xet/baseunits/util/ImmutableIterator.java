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

import java.util.Iterator;

/**
 * 明示的に、対象のコレクションに対する操作ができないことを表す反復子。
 * 
 * @param <T> 要素の型
 * @author daisuke
 * @since 1.0
 */
public abstract class ImmutableIterator<T> implements Iterator<T> {
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("sorry, no can do :-(");
	}
}
