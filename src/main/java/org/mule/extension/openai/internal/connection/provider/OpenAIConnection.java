package org.mule.extension.openai.internal.connection.provider;

import java.time.Duration;

import org.mule.runtime.extension.api.annotation.param.ConfigOverride;

import com.theokanning.openai.service.OpenAiService;

public final class OpenAIConnection {

  private OpenAiService client;

  public OpenAIConnection(String apiKey, @ConfigOverride Integer timeout) {
    this.client = new OpenAiService(apiKey, Duration.ofSeconds(timeout));
  }

  public OpenAiService getClient() {
    return client;
  }

  public void setClient(OpenAiService client) {
    this.client = client;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
