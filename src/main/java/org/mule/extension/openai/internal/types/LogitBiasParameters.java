package org.mule.extension.openai.internal.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

public class LogitBiasParameters {

	@Parameter
	@Placement(order = 1, tab = "General")
	private String tokenId;

	@Parameter
	@Placement(order = 2, tab = "General")
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
