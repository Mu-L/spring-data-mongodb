/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.repository.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.test.util.MongoTestUtils;

/**
 * Simple component exposing a {@link MongoOperations} instance as CDI bean.
 *
 * @author Oliver Gierke
 */
class MongoTemplateProducer {

	@Produces
	@ApplicationScoped
	public MongoOperations createMongoTemplate() {

		MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(MongoTestUtils.client(), "database");
		return new MongoTemplate(factory);
	}
}
