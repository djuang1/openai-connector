package org.mule.extension.openai;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import org.hamcrest.CoreMatchers;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.junit.Test;

public class OpenaiOperationsTestCase extends MuleArtifactFunctionalTestCase {

  @Override
  protected String getConfigFile() {
    return "test-mule-config.xml";
  }

  @Test
  public void executeCreateCompletionOperation() throws Exception {
    String payloadValue = ((String) flowRunner("createCompletionFlow").run()
                                      .getMessage()
                                      .getPayload()
                                      .getValue());
    assertThat(payloadValue, CoreMatchers.not(isEmptyOrNullString()));
  }

}
