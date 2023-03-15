package org.mule.extension.openai.internal.types;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.*;

import java.util.Set;

public class CompletionModelsValueProvider implements ValueProvider{

    @Override
    public Set<Value> resolve() throws ValueResolvingException {
        return ValueBuilder.getValuesFor("text-davinci-003", "text-curie-001", "text-ada-001", "text-babbage-001","code-davinci-002");
    }
    
}
