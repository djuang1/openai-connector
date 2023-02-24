package org.mule.extension.openai.internal;

import java.time.Duration;

import com.theokanning.openai.service.OpenAiService;

public final class OpenAIConnection {

  private OpenAiService client;

  public OpenAIConnection(String apiKey, Integer timeout) {
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
