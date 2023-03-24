package org.mule.extension.openai.internal;

import org.mule.extension.openai.internal.connection.provider.OpenAIConnectionProvider;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@Operations(OpenAIOperations.class)
@ConnectionProviders(OpenAIConnectionProvider.class)
public class OpenAIConfiguration {

}
