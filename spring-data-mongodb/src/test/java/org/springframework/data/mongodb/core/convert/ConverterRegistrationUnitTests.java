/*
 * Copyright 2011-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.core.convert;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.data.mongodb.core.mapping.Person;

/**
 * Unit tests for {@link ConverterRegistration}.
 * 
 * @author Oliver Gierke
 */
public class ConverterRegistrationUnitTests {

	@Test
	public void considersNotExplicitlyReadingDependingOnTypes() {

		ConverterRegistration context = new ConverterRegistration(Person.class, String.class, false, false);
		assertThat(context.isWriting(), is(true));
		assertThat(context.isReading(), is(false));

		context = new ConverterRegistration(String.class, Person.class, false, false);
		assertThat(context.isWriting(), is(false));
		assertThat(context.isReading(), is(true));

		context = new ConverterRegistration(String.class, Class.class, false, false);
		assertThat(context.isWriting(), is(true));
		assertThat(context.isReading(), is(true));
	}

	@Test
	public void forcesReadWriteOnlyIfAnnotated() {

		ConverterRegistration context = new ConverterRegistration(String.class, Class.class, false, true);
		assertThat(context.isWriting(), is(true));
		assertThat(context.isReading(), is(false));

		context = new ConverterRegistration(String.class, Class.class, true, false);
		assertThat(context.isWriting(), is(false));
		assertThat(context.isReading(), is(true));
	}

	@Test
	public void considersConverterForReadAndWriteIfBothAnnotated() {

		ConverterRegistration context = new ConverterRegistration(String.class, Class.class, true, true);
		assertThat(context.isWriting(), is(true));
		assertThat(context.isReading(), is(true));
	}
}
