## SPRING AI
- The BeanOutputParser provides OpenAI with a schema and metadata about the JSON structure we are providing.

## client 
- ChatClient is the interface
  - which has a DefaultChatClient implementation
  - which uses the ChatModel  
    - which the looks for the available implementation in this case OpenAiChatModel

- so you can use the ChatClient directly or OpenAiChatModel

## crafting questions
- https://platform.openai.com/docs/guides/prompt-engineering
- https://www.deeplearning.ai/short-courses/chatgpt-prompt-engineering-for-developers/
- 
# OpenAI chat properties
- https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html#_chat_properties
- 
# OpenAI connection and config properties
- https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html#_connection_properties
- https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html#_configuration_properties


# BRING YOUR OWN DATA
------------------------
- AI limitations
  - They are trained with public knowledge up to certain date
    - They don't know about your private/corporate data

- What can we do about this problem
  - Fine tune the Model
  - "Stuff the prompt" - add your data into the prompt
  - Function calling
- Retrieval Augmented Generation (RAG)
  - How to retrieve the relevant data for the user input and add it to your prompt
  - There are many strategies

# Stuff the Prompt
- passing the most updated data or missing data to your LLM to return an accurate or near accurate response

# RAG
- in stuffing the prompt, if we have a lot of text we intend to query,
  - sending everything off to the LLM will become very expensive. 
    - what we would want to do is to pick the relevant data that relates to our ask and stuff the prompt with that

# Vector databases - https://docs.spring.io/spring-ai/reference/api/vectordbs.html
- require vectors
- the don't return exact match but they perform similarity searches
  - a vector database returns vectors that are “similar” to the query vector

# Embedding API --> https://docs.spring.io/spring-ai/reference/api/embeddings.html
- converts text into numerical vectors

# vision capabilities: translating image to text/code #1
- load in image and ask the AI to interpret