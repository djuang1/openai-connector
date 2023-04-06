package org.mule.extension.openai.internal.extension;

import static org.mule.runtime.api.meta.Category.CERTIFIED;

import org.mule.extension.openai.internal.config.OpenAIConfiguration;
import org.mule.extension.openai.internal.error.OpenAIErrorType;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;
import org.mule.runtime.extension.api.annotation.license.RequiresEnterpriseLicense;

@Xml(prefix = "openai")
@Extension(name = "OpenAI Connector - Mule 4", category = CERTIFIED, vendor = "Dejim Juang")
@RequiresEnterpriseLicense(allowEvaluationLicense = true)
@ErrorTypes(OpenAIErrorType.class)
@Configurations(OpenAIConfiguration.class)
public class OpenAIConnector {

}
