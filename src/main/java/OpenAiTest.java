import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

class OpenAiTest {
    
  public static void main(String... args) {
    String token = "";
    OpenAiService service = new OpenAiService(token,Duration.ofSeconds(30));

    System.out.println("\nCreating completion...");
    CompletionRequest completionRequest = CompletionRequest.builder()
            .model("text-ada-001")
            .prompt("write an email invitation to the CIO of a pipe manufacturing company inviting him to a hands-on iPaaS workshop")
            //.echo(true)
            //.user("testing")
            .maxTokens(1028)
            .temperature(0.8)
            .n(1)
            .build();

    //service.createCompletion(completionRequest).getChoices().forEach(System.out::println);

    //List<CompletionChoice> choices = service.createCompletion(completionRequest).toString()

    //choices.get(0).getText();

    //System.out.println(service.createCompletion(completionRequest).toString());

    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(service.createCompletion(completionRequest));
      System.out.println(jsonString);

    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } 
      
  }
}
