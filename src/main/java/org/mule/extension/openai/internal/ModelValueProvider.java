package org.mule.extension.openai.internal;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.*;

import java.util.Set;

public class ModelValueProvider implements ValueProvider{

    @Override
    public Set<Value> resolve() throws ValueResolvingException {
        return ValueBuilder.getValuesFor("davinci", "curie", "ada", "babbage");
    }
    
}
