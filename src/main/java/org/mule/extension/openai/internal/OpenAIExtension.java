package org.mule.extension.openai.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

@Xml(prefix = "openai")
@Extension(name = "OpenAI")
@Configurations(OpenAIConfiguration.class)
public class OpenAIExtension {

}
