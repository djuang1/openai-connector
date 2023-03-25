package org.mule.extension.openai.internal.connection.provider;

import java.util.concurrent.TimeUnit;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Password;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class OpenAIConnectionProvider implements PoolingConnectionProvider<OpenAIConnection> {

  private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIConnectionProvider.class);

  @Parameter
  @DisplayName("API Key")
  @Password
  @Placement(order = 1, tab = "General")
  private String apiKey;

  @Parameter
  @DisplayName("Timeout")
  @Optional(defaultValue = "30")
  @Placement(order = 2, tab = "General")
  private Integer timeout;

/*   @Parameter
  @Optional(defaultValue = "SECONDS")
  @Placement(tab = "General", order = 3)
  @Summary("Time unit to be used in the Timeout configurations")
  private TimeUnit connectionTimeoutUnit; */

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
