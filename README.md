# 💡 AI Code Generator - Gemini API (Java)

A simple Java-based desktop application that uses Google's Gemini API to generate beginner-friendly Java code explanations and code snippets for any programming topic.

---

## 🚀 Features

- 🔍 Takes a topic (e.g., "binary search", "stack implementation") as input  
- 🤖 Sends the topic as a prompt to Gemini 1.5/2.5 models
- 📜 Retrieves structured, clean Java code from Gemini
- 📡 Uses HTTP POST requests with JSON payload
- 🧠 Built with Gson for parsing the Gemini API response

---

## 📦 Tech Stack

- **Language:** Java  
- **API:** [Gemini 1.5 Flash / 2.5 Pro](https://ai.google.dev)  
- **Library:** [Gson](https://github.com/google/gson)  
- **IDE:** IntelliJ IDEA / VS Code  
- **Build Tool:** Manual (or Maven if you prefer)

---

## 🧪 Sample Prompt Used

```text
Give the code for this 'binary search' in simple terms suitable for a beginner,
and well-structured, understandable and clean in Java.
