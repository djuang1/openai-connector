package org.mule.extension.openai.internal.types;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.*;

import java.util.Set;

public class EmbeddingsModelsValueProvider implements ValueProvider{

    @Override
    public Set<Value> resolve() throws ValueResolvingException {
        return ValueBuilder.getValuesFor("text-embedding-ada-002", "text-search-ada-doc-001");
    }
    
}
