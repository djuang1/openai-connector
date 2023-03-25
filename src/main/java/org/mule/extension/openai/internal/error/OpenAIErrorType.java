package org.mule.extension.openai.internal.error;

import static java.util.Optional.ofNullable;
import static org.mule.runtime.extension.api.error.MuleErrors.CONNECTIVITY;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.Optional;

public enum OpenAIErrorType implements ErrorTypeDefinition<OpenAIErrorType>{
    
    EXECUTION;
    
    private ErrorTypeDefinition parent;

    OpenAIErrorType() {

    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return ofNullable(parent);
    }
}
