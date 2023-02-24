# OpenAI Connector for Mule 4.x

## Overview
MuleSoft Connector for OpenAI's GPT-3 APIs. It leverages the Java libraries from this [project from Theo Kanning](https://github.com/TheoKanning/openai-java). An example of using the connector can be found [here](https://github.com/djuang1/openai-example-mule4)

## Installation Instructions

1.  Clone the repo
2.  Deploy the connector to your local Maven repo  `mvn clean install`. If you are using Maven 3.8.1+, you may run into issues with HTTP repositories being blocked. Follow the help article [here](https://help.mulesoft.com/s/article/Maven-error-when-building-application-Blocked-Mirror-for-repositories) to resolve the issue. 
3.  Add the connector dependency to your project `pom.xml` file

```
<dependency>
    <groupId>com.dejim</groupId>
    <artifactId>openai</artifactId>
    <version>1.0.4</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

## Configuration

1. Log into your [OpenAI account](https://beta.openai.com/login/)
2. In your account settings, click [View API keys](https://beta.openai.com/account/api-keys).
3. Click `+ Create new secret key`
4. Copy your API key.

<img src="https://raw.githubusercontent.com/djuang1/openai-connector/main/docs/openai_config.png" width="350"/>

## Operations

### Send Prompt

This operation will send a prompt to OpenAI and generate a completion.

#### Code Example
```
<flow name="chatgpt-project-mule4Flow">
    <http:listener doc:name="Listener" config-ref="HTTP_Listener_config" path="/test"/>
    <openai:create-completion doc:name="Create Completion" config-ref="OpenAI_Config" model="text-davinci-003" prompt="Somebody once told me the world is gonna roll me" maxTokens="#[1028]" temperature="0.8"/>
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