package com.mcp.filesystem.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    public ChatModel getChatModel(OllamaApi ollamaApi) {
        return OllamaChatModel
                .builder()
                .ollamaApi(ollamaApi)
                .options(OllamaChatOptions
                        .builder()
                        .model("qwen2.5:1.5b")
                        .build()
                ).build();
    }

    /*
    prompt1=Who are you? How can you help me?
    prompt2=Create a file named shikhar.txt in /Users/shikharmishra/Documents/TempFileSystem with content "Hello Shikhar!"
    prompt3=Create a directory named Thankyou in /Users/shikharmishra/Documents/TempFileSystem
    prompt4=prompt=List the folders and files under /Users/shikharmishra/Documents/TempFileSystem
    prompt5=delete the folder name Thankyou in  /Users/shikharmishra/Documents/TempFileSystem

     */
    @Bean
    public ChatClient getChatClient(ChatModel getChatModel, ToolCallbackProvider toolCallbackProvider) {
        return ChatClient
                .builder(getChatModel)
                .defaultTools(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }


}
