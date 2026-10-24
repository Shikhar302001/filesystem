# AI Filesystem Agent

This project is a Spring Boot application that demonstrates how to use a local AI model to perform operations on your computer's filesystem. It uses natural language prompts to create, list, and delete files and directories.

The application leverages the **Spring AI** framework to connect with a local **Ollama** model, and the **Model-Context-Protocol (MCP)** to provide the AI with tools to interact with the filesystem.

## How It Works

1.  A user sends a natural language command (e.g., "create a file named hello.txt") to a REST API endpoint.
2.  The `FileSystemController` passes this prompt to the Spring AI `ChatClient`.
3.  The `ChatClient` is configured to use a local Ollama model (`qwen2.5:1.5b` by default).
4.  The `ChatClient` is also equipped with filesystem tools provided by an MCP server (`@modelcontextprotocol/server-filesystem`). The Spring Boot application automatically starts this MCP server as a subprocess.
5.  The AI model interprets the user's prompt and determines which filesystem tool to call (e.g., create file, create directory, list contents).
6.  The MCP server executes the requested action on a pre-configured directory on the local machine.
7.  The AI model generates a natural language response based on the result of the filesystem operation, which is then sent back to the user.

## Prerequisites

-   **Java 21** or later
-   **Maven**
-   **Node.js and npx**: Required to run the MCP filesystem server.
-   **Ollama**: Installed and running. You can download it from [ollama.com](https://ollama.com/).

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/shikhar302001/filesystem.git
cd filesystem
```

### 2. Configure the Target Directory

The application needs to know which directory it can manage.

1.  Create a directory on your local machine that the AI will have access to. For example:
    ```bash
    mkdir ~/MyAiFileSystem
    ```
2.  Open the `src/main/resources/mcp-servers.json` file.
3.  Replace the hardcoded path `/Users/shikharmishra/Documents/TempFileSystem` with the absolute path to the directory you just created.

    ```json
    {
      "mcpServers": {
        "filesystem": {
          "command": "npx",
          "args": [
            "-y",
            "@modelcontextprotocol/server-filesystem",
            "/path/to/your/MyAiFileSystem" // <-- CHANGE THIS PATH
          ]
        }
      }
    }
    ```

### 3. Set up Ollama

1.  Make sure the Ollama service is running.
    ```bash
    ollama serve
    ```
2.  Pull the required model. This project is configured to use `qwen2.5:1.5b`.
    ```bash
    ollama pull qwen2.5:1.5b
    ```

### 4. Run the Application

Use the Maven wrapper to build and run the Spring Boot application.

```bash
./mvnw spring-boot:run
```

The application will start on port `8080`.

## Usage

You can interact with the application by sending requests to the `/file/actions` endpoint with your prompt as a query parameter.

### Examples using cURL

**1. Create a file with content:**

```bash
curl -X GET 'http://localhost:8080/file/actions?prompt=Create%20a%20file%20named%20shikhar.txt%20with%20content%20%22Hello%20Shikhar!%22'
```

**2. Create a directory:**

```bash
curl -X GET 'http://localhost:8080/file/actions?prompt=Create%20a%20directory%20named%20Thankyou'
```

**3. List files and folders:**

```bash
curl -X GET 'http://localhost:8080/file/actions?prompt=List%20the%20folders%20and%20files%20under%20the%20current%20directory'
```

**4. Delete a folder:**

```bash
curl -X GET 'http://localhost:8080/file/actions?prompt=delete%20the%20folder%20named%20Thankyou'
```

## Configuration

-   **`src/main/resources/application.properties`**:
    -   `spring.ai.ollama.base-url`: The URL for your running Ollama instance. Defaults to `http://localhost:11434`.
-   **`src/main/java/com/mcp/filesystem/config/ModelConfig.java`**:
    -   The Ollama model name (`qwen2.5:1.5b`) can be changed in the `getChatModel` bean.