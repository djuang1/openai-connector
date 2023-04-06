package org.mule.extension.openai.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.extension.openai.internal.connection.provider.OpenAIConnection;
import org.mule.extension.openai.internal.types.ChatModelsValueProvider;
import org.mule.extension.openai.internal.types.CompletionModelsValueProvider;
import org.mule.extension.openai.internal.types.EmbeddingsModelsValueProvider;
import org.mule.extension.openai.internal.types.ImageSizeValueProvider;
import org.mule.extension.openai.internal.types.LogitBiasParameters;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import static org.mule.runtime.extension.api.annotation.param.Optional.PAYLOAD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.image.ImageResult;

public class OpenAIOperations {

	@MediaType("application/java")
	@DisplayName("Create completion")
	public CompletionResult createCompletion(@Connection OpenAIConnection connection,
			@OfValues(CompletionModelsValueProvider.class) @Summary("ID of the model to use") String model,
			@Content(primary = true) @Optional(defaultValue = PAYLOAD) @Summary("The prompt to generate completions for, encoded as a string.") String prompt,
			@Optional @Summary("The suffix that comes after a completion of inserted text.") String suffix,
			@Optional(defaultValue = "16") @Summary("The maximum number of tokens to generate in the completion.\nThe token count of your prompt plus 'Max tokens' cannot exceed the model's context length. Most models have a context length of 2048 tokens (except for the newest models, which support 4096).") Integer maxTokens,
			@Optional(defaultValue = "1") @Summary("What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.\nIt is generally recommend altering this or 'Top p' but not both.") Double temperature,
			@Optional(defaultValue = "1") @Summary("An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with 'Top p' probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.\nIt is generally recommend altering this or 'Temperature' but not both.") Double topP,
			@Optional(defaultValue = "1") @Summary("How many completions to generate for each prompt.\nNote: Because this parameter generates many completions, it can quickly consume your token quota. Use carefully and ensure that you have reasonable settings for 'Max tokens' and 'Stop'.") Integer n,
			@Optional @Summary("Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens. For example, if 'Log probabilities' is 5, the API will return a list of the 5 most likely tokens. The API will always return the 'Log probabilities' of the sampled token, so there may be up to 'Log probabilities + 1' elements in the response./nThe maximum value for 'Log probabilities' is 5. If you need more than this, please contact OpenAI Help center and describe your use case.") Integer logProbabilities,
			@Optional(defaultValue = "false") @Summary("Echo back the prompt in addition to the completion.") boolean echo,
			@Content @NullSafe @DisplayName("Stop") @Optional @Summary("Up to 4 sequences where the API will stop generating further tokens. The returned text will not contain the stop sequence.") List<String> stops,
			@Optional(defaultValue = "0") @Summary("Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.") Double presencePenalty,
			@Optional(defaultValue = "0") @Summary("Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.") Double frequencyPenalty,
			@Optional(defaultValue = "1") @Summary("Generates 'Best Of' completions server-side and returns the 'best' (the one with the highest log probability per token). Results cannot be streamed.\nWhen used with 'N', 'Best of' controls the number of candidate completions and 'N' specifies how many to return - 'Best of' must be greater than 'N'.\nNote: Because this parameter generates many completions, it can quickly consume your token quota. Use carefully and ensure that you have reasonable settings for 'Max tokens' and 'Stop'.") Integer bestOf,
			@Content @NullSafe @DisplayName("Logit bias") @Optional @Summary("Modify the likelihood of specified tokens appearing in the completion.\nAccepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias value from -100 to 100. You can use the OpenAI tokenizer tool (which works for both GPT-2 and GPT-3) to convert text to token IDs. Mathematically, the bias is added to the logits generated by the model prior to sampling. The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant token.\nAs an example, you can pass [{tokenId: \"50256\",biasValue: -100}] to prevent the <|endoftext|> token from being generated.") List<LogitBiasParameters> logitBiases,
			@Optional @Summary("A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.") String user) {

		LogitBiasParameters logitBias = new LogitBiasParameters();

		CompletionRequest completionRequest = CompletionRequest.builder()
				.model(model)
				.prompt(prompt)
				.suffix(suffix)
				.maxTokens(maxTokens)
				.temperature(temperature)
				.topP(topP)
				.n(n)
				.logprobs(logProbabilities)
				.echo(echo)
				.stop(stops)
				.presencePenalty(presencePenalty)
				.frequencyPenalty(frequencyPenalty)
				.bestOf(bestOf)
				.logitBias(logitBias.toMap(logitBiases))
				.user(user)
				.build();

		return connection.getClient().createCompletion(completionRequest);
	}

	@MediaType("application/java")
	@DisplayName("Create chat completion")
	public ChatCompletionResult createChatCompletion(@Connection OpenAIConnection connection,
			@OfValues(ChatModelsValueProvider.class) @Summary("ID of the model to use.") String model,
			@Content(primary = true) @Expression(ExpressionSupport.REQUIRED) @Summary("The messages to generate chat completions for, in the chat format.") List<ChatMessage> messages,
			@Optional(defaultValue = "1") @Summary("What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.\nIt is generally recommend altering this or 'Top P' but not both.") Double temperature,
			@Optional(defaultValue = "1") @Summary("An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with 'Top p' probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.\nIt is generally recommend altering this or 'Temperature' but not both.") Double topP,
			@Optional(defaultValue = "1") @Summary("How many completions to generate for each input message.") Integer n,
			@Optional @Summary("The maximum number of tokens allowed for the generated answer. By default, the number of tokens the model can return will be (4096 - prompt tokens).") Integer maxTokens,
			@Optional(defaultValue = "0") @Summary("Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.") Double presencePenalty,
			@Optional(defaultValue = "0") @Summary("Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.") Double frequencyPenalty,
			@Content @NullSafe @DisplayName("Logit bias") @Optional @Summary("Modify the likelihood of specified tokens appearing in the completion.\nAccepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias value from -100 to 100. You can use the OpenAI tokenizer tool (which works for both GPT-2 and GPT-3) to convert text to token IDs. Mathematically, the bias is added to the logits generated by the model prior to sampling. The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant token.\nAs an example, you can pass [{tokenId: \"50256\",biasValue: -100}] to prevent the <|endoftext|> token from being generated.") List<LogitBiasParameters> logitBiases,
			@Optional @Summary("A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.") String user) {

		LogitBiasParameters logitBias = new LogitBiasParameters();

		ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
				.model(model)
				.messages(messages)
				.temperature(temperature)
				.topP(topP)
				.n(n)
				.maxTokens(maxTokens)
				.presencePenalty(presencePenalty)
				.frequencyPenalty(frequencyPenalty)
				.logitBias(logitBias.toMap(logitBiases))
				.user(user)
				.build();

		return connection.getClient().createChatCompletion(chatRequest);
	}

	@MediaType("image/png")
	@DisplayName("Create image file")
	public List<InputStream> createImageFile(@Connection OpenAIConnection connection,
			@Content(primary = true) @Summary("A text description of the desired image(s). The maximum length is 1000 characters.") String prompt,
			@Optional(defaultValue = "1") @Summary("The number of images to generate. Must be between 1 and 10.") Integer n,
			@Optional(defaultValue = "1024x1024") @OfValues(ImageSizeValueProvider.class) @Summary("The size of the generated images.") String size,
			@Optional @Summary("A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.") String user) {

		CreateImageRequest imageRequest = CreateImageRequest.builder()
				.prompt(prompt)
				.n(n)
				.size(size)
				.responseFormat("b64_json")
				.user(user)
				.build();

		List<InputStream> imagesBytes = new ArrayList<>();
		List<Image> images = connection.getClient().createImage(imageRequest).getData();
		
		for (Image image : images) {
			imagesBytes.add(new ByteArrayInputStream(image.getB64Json().getBytes()));
		}

		return imagesBytes;
	}

	@MediaType("application/java")
	@DisplayName("Create image url")
	public ImageResult createImageUrl(@Connection OpenAIConnection connection,
			@Content(primary = true) @Summary("A text description of the desired image(s). The maximum length is 1000 characters.") String prompt,
			@Optional(defaultValue = "1") @Summary("The number of images to generate. Must be between 1 and 10.") Integer n,
			@Optional(defaultValue = "1024x1024") @OfValues(ImageSizeValueProvider.class) @Summary("The size of the generated images.") String size,
			@Optional @Summary("A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.") String user) {

		CreateImageRequest imageRequest = CreateImageRequest.builder()
				.prompt(prompt)
				.n(n)
				.size(size)
				.responseFormat("url")
				.user(user)
				.build();

		return connection.getClient().createImage(imageRequest);
	}
	
	@MediaType("application/java")
	@DisplayName("Create embeddings")
	public EmbeddingResult createEmbeddings(@Connection OpenAIConnection connection,
			@OfValues(EmbeddingsModelsValueProvider.class) @Summary("ID of the model to use.") String model,
			@Content(primary = true) @DisplayName("Input") @Expression(ExpressionSupport.REQUIRED) @Summary("Input text to get embeddings for, encoded as a string or array of tokens. To get embeddings for multiple inputs in a single request, pass an array of strings or array of token arrays. Each input must not exceed 8192 tokens in length.") List<String> inputs,
			@Optional @Summary("A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.") String user) {
		
		EmbeddingRequest embeddingsRequest = EmbeddingRequest.builder()
				.model(model)
				.input(inputs)
				.user(user)
				.build();
		
		return connection.getClient().createEmbeddings(embeddingsRequest);
	}
}
