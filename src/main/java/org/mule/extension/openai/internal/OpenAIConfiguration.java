package org.mule.extension.openai.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@Operations(OpenAIOperations.class)
@ConnectionProviders(OpenAIConnectionProvider.class)
public class OpenAIConfiguration {

}
