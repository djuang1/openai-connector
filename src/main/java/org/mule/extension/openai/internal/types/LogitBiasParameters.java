package org.mule.extension.openai.internal.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.runtime.extension.api.annotation.param.Parameter;

public class LogitBiasParameters {

	@Parameter
	private String tokenId;

	@Parameter
	private Integer biasValue;

	public String getTokenId() {
		return tokenId;
	}
	
	public Integer getBiasValue() {
		return biasValue;
	}
	
	public static Map<String, Integer> toMap(List<LogitBiasParameters> logitBiasList) {		
		Map<String, Integer> logitBiasMap = null;
		
		if (logitBiasList != null) {
			logitBiasMap = new HashMap<String, Integer>();
			
			for (LogitBiasParameters lb : logitBiasList)
				logitBiasMap.put(lb.getTokenId(), lb.getBiasValue());
		}
		
		return logitBiasMap;
	}
}
