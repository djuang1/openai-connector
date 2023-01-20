package org.mule.extension.openai.internal;

import com.theokanning.openai.OpenAiService;

public final class OpenAIConnection {

  private OpenAiService client;

  public OpenAIConnection(String apiKey) {
    this.client = new OpenAiService(apiKey);
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
