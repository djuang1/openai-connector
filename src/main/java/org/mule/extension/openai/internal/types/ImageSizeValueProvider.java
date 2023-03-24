package org.mule.extension.openai.internal.types;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;
import org.mule.runtime.extension.api.values.ValueBuilder;

import java.util.Set;

public class ImageSizeValueProvider implements ValueProvider{

    @Override
    public Set<Value> resolve() throws ValueResolvingException {
        return ValueBuilder.getValuesFor("256x256", "512x512", "1024x1024");
    }
    
}
