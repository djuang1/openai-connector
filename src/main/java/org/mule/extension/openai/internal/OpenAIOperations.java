package org.mule.extension.openai.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.apache.commons.codec.binary.Base64;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import static org.mule.runtime.extension.api.annotation.param.Optional.PAYLOAD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.List;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;

public class OpenAIOperations {

  @MediaType(value = ANY, strict = false)
  @DisplayName("Create Completion")
  public String createCompletion(@Connection OpenAIConnection connection,
      @Optional(defaultValue = PAYLOAD) String prompt,
      @OfValues(ModelValueProvider.class) String model) {

    // System.out.println("\nCreating completion...");
    CompletionRequest completionRequest = CompletionRequest.builder()
        .model(model)
        .prompt(prompt)
        // .echo(true)
        .user("MuleSoft OpenAI Connector")
        .build();

    List<CompletionChoice> choices = connection.getClient().createCompletion(completionRequest).getChoices();

    return choices.get(0).getText();
  }

  @MediaType("image/png")
  @DisplayName("Create Image")
  public InputStream createImage(@Connection OpenAIConnection connection,
      @Optional(defaultValue = PAYLOAD) String prompt) {

    // System.out.println("\nCreating image...");
    CreateImageRequest imageRequest = CreateImageRequest.builder()
        .prompt(prompt)
        .n(1)
        .responseFormat("b64_json")
        .size("256x256")
        .user("MuleSoft OpenAI Connector")
        .build();

    List<Image> images = connection.getClient().createImage(imageRequest).getData();

    byte[] decodedString = Base64.decodeBase64(images.get(0).getB64Json());

    // return images.get(0).getB64Json();
    return new ByteArrayInputStream(decodedString);
  }
}
