package org.mule.extension.openai.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
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
      @DisplayName("Max Tokens") @Optional(defaultValue = "50") Integer maxTokens,
      @DisplayName("Temperature") @Optional(defaultValue = "0.5") Double temperature,
      @OfValues(ModelValueProvider.class) String model) {

    // System.out.println("\nCreating completion...");
    CompletionRequest completionRequest = CompletionRequest.builder()
        .model(model)
        .prompt(prompt)
        .n(1)
        .maxTokens(maxTokens)
        .temperature(temperature)
        .user("MuleSoft OpenAI Connector")
        .build();

    List<CompletionChoice> choices = connection.getClient().createCompletion(completionRequest).getChoices();

    System.out.println(choices.get(0).getText());

    return choices.get(0).getText();
  }

  @MediaType("image/png")
  @DisplayName("Create Image File")
  public InputStream createImageFile(@Connection OpenAIConnection connection,
      @Optional(defaultValue = PAYLOAD) String prompt) {

    // System.out.println("\nCreating image file...");
    CreateImageRequest imageRequest = CreateImageRequest.builder()
        .prompt(prompt)
        .n(1)
        .responseFormat("b64_json")
        .size("512x512")
        .user("MuleSoft OpenAI Connector")
        .build();

    List<Image> images = connection.getClient().createImage(imageRequest).getData();
    byte[] decodedString = images.get(0).getB64Json().getBytes();
    
    return new ByteArrayInputStream(decodedString);
  }

  @MediaType(value = ANY, strict = false)
  @DisplayName("Create Image URL")
  public String createImageUrl(@Connection OpenAIConnection connection,
      @Optional(defaultValue = PAYLOAD) String prompt) {

    // System.out.println("\nCreating image URL...");
    CreateImageRequest imageRequest = CreateImageRequest.builder()
        .prompt(prompt)
        .n(1)
        .responseFormat("url")
        .size("512x512")
        .user("MuleSoft OpenAI Connector")
        .build();

    List<Image> images = connection.getClient().createImage(imageRequest).getData();
    
    return images.get(0).getUrl();
  }
}
