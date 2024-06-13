
# MedListen

## Abstract
MedListen is an intelligent application designed to assist users in understanding medical information in simple terms. Users can input their medical data, case reports, X-rays, and audio transcriptions from doctors. The application leverages the OpenAI API to provide explanations in plain language, translate them into any language, and generate audio explanations. It can also answer general medical questions, with a recommendation to consult a doctor for complex queries. The project consists of a React frontend, a Spring Boot backend, and optionally a fine-tuned GPT-2 model for enhanced medical question answering.

## Note: This project is a work in progress, and additional features and improvements are continuously being developed.

## Table of Contents
- [Abstract](#abstract)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
  - [Backend](#backend)
  - [Frontend](#frontend)
  - [Optional: Custom Machine Learning Model](#optional-custom-machine-learning-model)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features
- Input medical data, case reports, X-rays, and audio transcriptions
- Simple language explanations using OpenAI API
- Translate explanations into any language
- Generate audio explanations
- Answer general medical questions
- Optional custom machine learning model trained on medical textbooks

## Technologies Used
- **Backend:** Spring Boot, Java, H2 Database, OpenAI API
- **Frontend:** React, Axios, Styled-components
- **Optional Model:** Python, Hugging Face Transformers, Flask

## Setup Instructions

### Backend

#### Prerequisites
- Java 11 or higher
- Maven
- OpenAI API key

#### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/medical-chatgpt.git
   cd medical-chatgpt/backend
   ```

2. Set up environment variables for OpenAI API key:
   ```sh
   export OPENAI_API_KEY=your_openai_api_key
   ```

3. Build and run the Spring Boot application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

#### Detailed Explanation
The backend is built using Spring Boot, providing RESTful API endpoints for user registration, handling medical records, and communicating with the OpenAI API. It includes services for managing conversations and integrating with the OpenAI API to fetch responses.

**Key Components:**
- `User`: Entity class representing a user in the system.
- `MedicalRecord`: Entity class for storing medical records.
- `Conversation`: Entity class for maintaining conversation context.
- `UserRepository`, `MedicalRecordRepository`, `ConversationRepository`: Repositories for data access.
- `UserService`, `MedicalRecordService`, `ConversationService`, `OpenAIService`: Services for business logic.
- `UserController`, `MedicalRecordController`, `ExplanationController`: REST controllers for handling HTTP requests.
- `SecurityConfig`: Configuration for securing API endpoints.

### Frontend

#### Prerequisites
- Node.js
- npm

#### Installation
1. Navigate to the frontend directory:
   ```sh
   cd ../frontend
   ```

2. Install dependencies:
   ```sh
   npm install
   ```

3. Start the React application:
   ```sh
   npm start
   ```

### Optional: Custom Machine Learning Model

#### Prerequisites
- Python 3.7 or higher
- Pip
- Flask

#### Installation
1. Set up a Python virtual environment:
   ```sh
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   ```

2. Install dependencies:
   ```sh
   pip install transformers datasets torch flask
   ```

3. Prepare your medical textbook data and fine-tune the model:
   ```sh
   python fine_tune_model.py
   ```

4. Serve the model with Flask:
   ```sh
   python app.py
   ```

#### Integration with Backend
If using the custom model, modify the `MLService` in the Spring Boot backend to call your Flask API.

**MLService.java:**
```java
package com.example.medicalapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String flaskApiUrl = "http://localhost:5000/ask";

    public String askQuestion(String question) {
        Map<String, String> request = new HashMap<>();
        request.put("question", question);

        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, request, Map.class);
        return (String) response.getBody().get("answer");
    }
}
```

Update the `MLController` to route requests to the `MLService`.

**MLController.java:**
```java
package com.example.medicalapp.controller;

import com.example.medicalapp.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ml")
public class MLController {
    @Autowired
    private MLService mlService;

    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("question");
        String answer = mlService.askQuestion(question);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
```

Update the frontend to call the new endpoint as shown in the provided `ChatPage.js`.

## Usage
1. Run the backend (Spring Boot application).
2. Run the frontend (React application).
3. Access the application at `http://localhost:3000`.

Users can input medical data and ask questions through the chat interface. The backend processes the input, communicates with the OpenAI API or the custom model, and returns a simple explanation.

## Endpoints

**Backend Endpoints:**
- `POST /api/users/register`: Register a new user.
- `GET /api/users/{username}`: Retrieve user information.
- `POST /api/records`: Save a medical record.
- `GET /api/records/user/{userId}`: Retrieve medical records for a user.
- `POST /api/explain/start`: Start a new conversation.
- `POST /api/explain/continue`: Continue an existing conversation.
- `POST /api/ml/ask`: Ask a medical question (using the custom model).

## Contributing
1. Fork the repository.
2. Create a new branch.
3. Make your changes.
4. Submit a pull request.

## License
This project is licensed under the MIT License.
