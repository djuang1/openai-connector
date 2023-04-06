package org.mule.extension.openai;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.isEmptyOrNullString;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.embedding.EmbeddingResult;
import com.theokanning.openai.image.ImageResult;

import org.junit.Test;

public class OpenaiOperationsTestCase extends MuleArtifactFunctionalTestCase {

	@Override
	protected String getConfigFile() {
		return "test-mule-config.xml";
	}

	@Test
	public void executeCreateCompletionOperation() throws Exception {
		CompletionResult payloadValue = ((CompletionResult) flowRunner("createCompletionFlow")
				.run()
				.getMessage()
				.getPayload()
				.getValue());
		String choiceText = payloadValue.getChoices().get(0).getText();

		assertThat(choiceText, CoreMatchers.not(isEmptyOrNullString()));
	}
	
	@Test
	public void executeCreateChatCompletionOperation() throws Exception {
		ChatCompletionResult payloadValue = ((ChatCompletionResult) flowRunner("createChatCompletionFlow")
				.run()
				.getMessage()
				.getPayload()
				.getValue());
		String choiceText = payloadValue.getChoices().get(0).getMessage().getContent();

		assertThat(choiceText, CoreMatchers.not(isEmptyOrNullString()));
	}
	
	@Test
	public void executeCreateImageUrl() throws Exception {
		ImageResult payloadValue = ((ImageResult) flowRunner("createImageUrlFlow")
				.run()
				.getMessage()
				.getPayload()
				.getValue());

		String choiceText = payloadValue.getData().get(0).getUrl().toString();

		assertThat(choiceText, CoreMatchers.not(isEmptyOrNullString()));
	}
	
	@Test
	public void executeCreateEmbeddings() throws Exception {
		EmbeddingResult payloadValue = ((EmbeddingResult) flowRunner("createEmbeddingsFlow")
				.run()
				.getMessage()
				.getPayload()
				.getValue());
		
		String embeddingValue = payloadValue.getData().get(0).getEmbedding().get(0).toString();

		assertThat(embeddingValue, CoreMatchers.not(isEmptyOrNullString()));
	}

}
