/*
 * Copyright 2025 the original author or authors.
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

import java.lang.annotation.Annotation;
import java.util.Set;

import org.jspecify.annotations.Nullable;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.aot.AotContext;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFragmentsContributor;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.config.AotRepositoryContextSupport;
import org.springframework.data.repository.config.AotRepositoryInformation;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.AnnotationRepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;

/**
 * @author Christoph Strobl
 */
public class TestMongoAotRepositoryContext extends AotRepositoryContextSupport {

	private final AotRepositoryInformation repositoryInformation;
	private final Class<?> repositoryInterface;

	public TestMongoAotRepositoryContext(BeanFactory beanFactory, Class<?> repositoryInterface,
			@Nullable RepositoryComposition composition) {
		super(AotContext.from(beanFactory, new StandardEnvironment()));

		this.repositoryInterface = repositoryInterface;

		RepositoryMetadata metadata = AnnotationRepositoryMetadata.getMetadata(repositoryInterface);

		RepositoryComposition.RepositoryFragments fragments = MongoRepositoryFragmentsContributor.DEFAULT
				.describe(metadata);

		this.repositoryInformation = new AotRepositoryInformation(metadata, SimpleMongoRepository.class,
				fragments.stream().toList());
	}

	@Override
	public String getModuleName() {
		return "MongoDB";
	}

	@Override
	public RepositoryConfigurationSource getConfigurationSource() {
		return null;
	}

	@Override
	public Set<String> getBasePackages() {
		return Set.of(repositoryInterface.getPackageName());
	}

	@Override
	public Set<Class<? extends Annotation>> getIdentifyingAnnotations() {
		return Set.of(Document.class);
	}

	@Override
	public RepositoryInformation getRepositoryInformation() {
		return repositoryInformation;
	}

	@Override
	public Set<MergedAnnotation<Annotation>> getResolvedAnnotations() {
		return Set.of();
	}

	@Override
	public Set<Class<?>> getResolvedTypes() {
		return Set.of();
	}

}
