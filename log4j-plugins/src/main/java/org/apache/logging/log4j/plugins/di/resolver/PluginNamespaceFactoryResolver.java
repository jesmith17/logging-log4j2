/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.logging.log4j.plugins.di.resolver;

import java.util.function.Supplier;

import org.apache.logging.log4j.plugins.di.InstanceFactory;
import org.apache.logging.log4j.plugins.di.Key;
import org.apache.logging.log4j.plugins.di.spi.FactoryResolver;
import org.apache.logging.log4j.plugins.di.spi.ResolvableKey;
import org.apache.logging.log4j.plugins.model.PluginNamespace;
import org.apache.logging.log4j.plugins.model.PluginRegistry;

/**
 * Factory resolver for {@link PluginNamespace}. Keys must have a namespace defined.
 */
public class PluginNamespaceFactoryResolver implements FactoryResolver<PluginNamespace> {
    @Override
    public boolean supportsKey(final Key<?> key) {
        return key.getRawType() == PluginNamespace.class && !key.getNamespace().isEmpty();
    }

    @Override
    public Supplier<PluginNamespace> getFactory(final ResolvableKey<PluginNamespace> resolvableKey, final InstanceFactory instanceFactory) {
        final String namespace = resolvableKey.getKey().getNamespace();
        return () -> instanceFactory.getInstance(PluginRegistry.class).getNamespace(namespace);
    }
}
