package org.mule.extension.openai.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAIConnectionProvider implements PoolingConnectionProvider<OpenAIConnection> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIConnectionProvider.class);

  @Parameter
  @DisplayName("API Key")
  @Password
  private String apiKey;

  @Parameter
  @DisplayName("Timeout")
  @Optional(defaultValue = "30")
  private Integer timeout;

  @Override
  public OpenAIConnection connect() throws ConnectionException {
    return new OpenAIConnection(apiKey, timeout);
  }

  @Override
  public void disconnect(OpenAIConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting: " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(OpenAIConnection connection) {
    return ConnectionValidationResult.success();
  }
}
