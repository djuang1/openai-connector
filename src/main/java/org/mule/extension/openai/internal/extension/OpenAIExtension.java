package org.mule.extension.openai.internal.extension;

import static org.mule.runtime.api.meta.Category.CERTIFIED;

import org.mule.extension.openai.internal.OpenAIConfiguration;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.license.RequiresEnterpriseLicense;

@Xml(prefix = "openai")
@Extension(name = "OpenAI Connector", category = CERTIFIED, vendor = "Dejim Juang")
@RequiresEnterpriseLicense(allowEvaluationLicense = true)
@Configurations(OpenAIConfiguration.class)
public class OpenAIExtension {

}
