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
package jp.xet.baseunits.aws.dynamodb;

import jp.xet.baseunits.time.TimePoint;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

/**
 * {@link DynamoDBMarshaller} implementation for {@link TimePoint}.
 * 
 * @since 2.10
 * @author daisuke
 */
public class TimePointMarshaller implements DynamoDBMarshaller<TimePoint> {
	
	private static final String NULL = "null";
	
	
	@Override
	public String marshall(TimePoint obj) {
		return obj == null ? NULL : Long.toString(obj.toEpochMillisec());
	}
	
	@Override
	public TimePoint unmarshall(Class<TimePoint> clazz, String obj) {
		if (obj == null || obj.equals(NULL)) {
			return null;
		}
		return TimePoint.from(Long.parseLong(obj));
	}
}
