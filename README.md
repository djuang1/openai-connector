# OpenAI Connector for Mule 4.x

## Overview
MuleSoft Connector for OpenAI's GPT APIs. It leverages the Java libraries from this [project from Theo Kanning](https://github.com/TheoKanning/openai-java). An example of using the connector can be found [here](https://github.com/djuang1/openai-example-mule4)

## Installation Instructions

1.  Clone the repository
2.  Deploy the connector to your local Maven repository using the  `mvn clean install` command. If you are using Maven 3.8.1+, you may run into issues with HTTP repositories being blocked. Follow the help article [here](https://help.mulesoft.com/s/article/Maven-error-when-building-application-Blocked-Mirror-for-repositories) to resolve the issue. 
3.  Add the connector dependency to your project `pom.xml` file

```
<dependency>
    <groupId>com.mulesoft.platform_se</groupId>
    <artifactId>openai</artifactId>
    <version>1.0.7</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

## Configuration

1. Log into your [OpenAI account](https://platform.openai.com/login/)
2. In your account settings, click [View API keys](https://platform.openai.com/account/api-keys).
3. Click `+ Create new secret key`
4. Copy your API key.

<img src="https://raw.githubusercontent.com/djuang1/openai-connector/main/docs/openai_config.png" width="350"/>

## Operations

### Create Chat Completion

This operation will send a chat conversation to OpenAPI and generate a chat completion. Documentation for the OpenAI API can be found [here](https://platform.openai.com/docs/api-reference/chat/create)

#### Code Example
```
<flow name="chatCompletionFlow">
	<http:listener config-ref="HTTP_Listener_config" path="/chat" />
	<openai:create-chat-completion config-ref="OpenAI_Config" model="gpt-3.5-turbo">
		<openai:messages><![CDATA[#[output application/java
			---
			[{
				role: "user",
				content: "Hello!"
			}]]]]>
		</openai:messages>
		<openai:logit-biases><![CDATA[#[output application/java
			---
			[
				{
					tokenId: "30",
					biasValue: -100
				}
			]]]]>
		</openai:logit-biases>
	</openai:create-chat-completion>
	<ee:transform>
		<ee:message>
			<ee:set-payload><![CDATA[%dw 2.0
				output application/json
				---
				payload]]>
			</ee:set-payload>
		</ee:message>
	</ee:transform>
</flow>
```


### Create Completion

This operation will send a prompt to OpenAI and generate a completion. Documentation for the OpenAI API can be found [here](https://platform.openai.com/docs/api-reference/completions/create)

#### Code Example
```
<flow name="completionFlow">
	<http:listener config-ref="HTTP_Listener_config" path="/completion" />
	<openai:create-completion config-ref="OpenAI_Config" model="text-davinci-003" maxTokens="7" temperature="0">
		<openai:prompt><![CDATA[Say this is a test]]></openai:prompt>
		<openai:logit-biases><![CDATA[#[output application/java
			---
			[{
				tokenId: "1332",
				biasValue: -100
			},{
				tokenId: "257",
				biasValue: -100
			}]]]]>
		</openai:logit-biases>
	</openai:create-completion>
	<ee:transform>
		<ee:message>
			<ee:set-payload><![CDATA[%dw 2.0
				output application/json
				---
				payload]]>
			</ee:set-payload>
		</ee:message>
	</ee:transform>
</flow>
```


### Create Embeddings

This operation will send input text(s) to OpenAI and generate vector representations that can be easily consumed by machine learning models and algorithms. Documentation for the OpenAI API can be found [here](https://platform.openai.com/docs/api-reference/embeddings/create)

#### Code Example
```
<flow name="embeddingsFlow">
	<http:listener config-ref="HTTP_Listener_config" path="/embeddings" />
	<openai:create-embeddings config-ref="OpenAI_Config" model="text-embedding-ada-002">
		<openai:inputs><![CDATA[#[output application/java
			---
			[
				"The food was delicious and the waiter...",
				"How are you?"
			]]]]>
		</openai:inputs>
	</openai:create-embeddings>
	<ee:transform>
		<ee:message>
			<ee:set-payload><![CDATA[%dw 2.0
				output application/json
				---
				payload]]>
			</ee:set-payload>
		</ee:message>
	</ee:transform>
</flow>
``` 


### Create Image File

This operation will generate an image file(s) with DALL-E given a prompt. `response_format` is set to `b64_json` for this operation and the output is a Base64 string. Documentation for the OpenAI API can be found [here](https://platform.openai.com/docs/api-reference/images/create)

#### Code Example
```
<flow name="imageFileFlow">
	<http:listener config-ref="HTTP_Listener_config" path="/image"/>
	<openai:create-image-file config-ref="OpenAI_Config" n="2" size="256x256">
		<openai:prompt ><![CDATA[Penguin]]></openai:prompt>
	</openai:create-image-file>
	<foreach>
		<ee:transform>
			<ee:message>
				<ee:set-payload><![CDATA[%dw 2.0
					import * from dw::core::Binaries
					output application/octet-stream
					---
					fromBase64(payload)
					]]>
				</ee:set-payload>
			</ee:message>
		</ee:transform>
		<file:write path='#["C:\image-" ++ vars.counter ++ ".png"]' />
	</foreach>
</flow>
```

### Generate Image URL

This operation will generate an image URL(s) with DALL-E given a prompt. `response_format` is set to `url` for this operation and the output is a URL. Documentation for the OpenAI API can be found [here](https://platform.openai.com/docs/api-reference/images/create)

#### Code Example
```
<flow name="imageUrlFlow">
	<http:listener config-ref="HTTP_Listener_config" path="/url"/>
	<openai:create-image-url config-ref="OpenAI_Config" size="256x256">
		<openai:prompt ><![CDATA[elephant wearing red shoes]]></openai:prompt>
	</openai:create-image-url>
	<http:request method="GET" url="#[payload[0]]"/>
</flow>
```

## About

```
Author: Dejim Juang - dejimj@gmail.com, Sapandeep Matharoo - smatharoo5@outlook.com
Last Update: March 15, 2023
```