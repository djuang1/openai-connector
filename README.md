# OpenAI Connector for Mule 4.x

## Overview
MuleSoft Connector for OpenAI's GPT-3 APIs. It leverages the Java libraries from this [project from Theo Kanning](https://github.com/TheoKanning/openai-java). 

## Installation Instructions

1.  Clone the repo
2.  Deploy the connector to your local Maven repo  `mvn clean install`
3.  Add the connector dependency to your project `pom.xml` file

```
<dependency>
    <groupId>com.dejim</groupId>
    <artifactId>openai</artifactId>
    <version>1.0.12-SNAPSHOT</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

## Operations

### Send Prompt

This operation will send a prompt to OpenAI and generate a completion.

#### Code Example
```
<flow name="chatgpt-project-mule4Flow">
		<http:listener doc:name="Listener" config-ref="HTTP_Listener_config" path="/test"/>
		<openai:create-completion doc:name="Create Completion"  config-ref="OpenAI_Config" prompt='#[payload default "Somebody once told me the world is gonna roll me"]' model="ada"/>
		<ee:transform doc:name="Transform Message" >
			<ee:message >
				<ee:set-payload ><![CDATA[%dw 2.0
output application/json
---
payload]]></ee:set-payload>
			</ee:message>
		</ee:transform>
	</flow>
```


### Generate Image File

This operation will generate an image file with DALL-E given a prompt. `response_format` is set to `b64_json` for this operation and the output is a Base64 string. Documentation for the OpenAI API can be found [here](https://beta.openai.com/docs/api-reference/images/create)

#### Code Example
```
<flow name="chatgpt-project-mule4Flow1">
		<http:listener doc:name="Listener" config-ref="HTTP_Listener_config" path="/image"/>
		<openai:create-image-file doc:name="Create Image File"  config-ref="OpenAI_Config" prompt='#["penguin"]'/>
		<ee:transform doc:name="Transform Message" >
			<ee:message >
				<ee:set-payload ><![CDATA[%dw 2.0
import * from dw::core::Binaries
output application/octet-stream
---
fromBase64(payload)
]]></ee:set-payload>
			</ee:message>
		</ee:transform>
		<file:write doc:name="Write" path="/Users/djuang/Desktop/image.png"/>
	</flow>
```

### Generate Image URL

This operation will generate an image URL with DALL-E given a prompt. `response_format` is set to `url` for this operation and the output is a URL. Documentation for the OpenAI API can be found [here](https://beta.openai.com/docs/api-reference/images/create)

#### Code Example
```
<flow name="chatgpt-project-mule4Flow2">
		<http:listener doc:name="Listener" config-ref="HTTP_Listener_config" path="/url"/>
		<openai:create-image-url doc:name="Create Image URL"  config-ref="OpenAI_Config" prompt='#["mulesoft"]'/>
		<http:request method="GET" doc:name="Request" url="#[payload]"/>
	</flow>
```

```
Author: Dejim Juang - dejimj@gmail.com
Last Update: January 20, 2023
```