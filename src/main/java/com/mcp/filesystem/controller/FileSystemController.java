package com.mcp.filesystem.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileSystemController {
   private final ChatClient chatClient;
   public FileSystemController(ChatClient chatClient) {
       this.chatClient = chatClient;
   }

   @GetMapping("/actions")
   public ResponseEntity<String> doActions(@RequestParam String prompt){
       return new ResponseEntity<>(chatClient.prompt(prompt).call().content(), HttpStatus.OK);
   }
}
