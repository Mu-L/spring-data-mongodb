/*
 * Copyright 2022-2025 the original author or authors.
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
package org.springframework.data.mongodb.repository.aot;

import org.jspecify.annotations.Nullable;

import org.springframework.aot.generate.GenerationContext;
import org.springframework.data.mongodb.aot.LazyLoadingProxyAotProcessor;
import org.springframework.data.mongodb.aot.MongoAotPredicates;
import org.springframework.data.repository.config.AotRepositoryContext;
import org.springframework.data.repository.config.RepositoryRegistrationAotProcessor;
import org.springframework.data.util.TypeContributor;

/**
 * Mongodb-specific {@link RepositoryRegistrationAotProcessor} that contributes generated code for repositories.
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 * @since 4.0
 */
public class AotMongoRepositoryPostProcessor extends RepositoryRegistrationAotProcessor {

	private static final String MODULE_NAME = "mongodb";

	private final LazyLoadingProxyAotProcessor lazyLoadingProxyAotProcessor = new LazyLoadingProxyAotProcessor();

	@Override
	protected void configureTypeContributions(AotRepositoryContext repositoryContext,
			GenerationContext generationContext) {
		super.configureTypeContributions(repositoryContext, generationContext);

		repositoryContext.getResolvedTypes().stream().filter(MongoAotPredicates.IS_SIMPLE_TYPE.negate()).forEach(type -> {
			TypeContributor.contribute(type, it -> true, generationContext);
			lazyLoadingProxyAotProcessor.registerLazyLoadingProxyIfNeeded(type, generationContext);
		});
	}

	@Override
	protected @Nullable MongoRepositoryContributor contributeAotRepository(AotRepositoryContext repositoryContext) {

		if (!repositoryContext.isGeneratedRepositoriesEnabled(MODULE_NAME)) {
			return null;
		}

		return new MongoRepositoryContributor(repositoryContext);
	}

}
