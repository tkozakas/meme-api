# Meme api

Meme api that provides random memes, news, and other functionalities.

## Configuration
In order to run the API, you need to create a `.env` file in the root directory of the project with the following environment variables:
```env
ELEVENLABS_API_KEY=<<YOUR_ELEVENLABS_API_KEY1, YOUR_ELEVENLABS_API_KEY2, ...>>
ELEVENLABS_VOICE_ID=Ybqj6CIlqb6M85s9Bl4n;

GROQ_API_KEY=<<YOUR_GROQ_API_KEY>>;
GROQ_MODEL=<<YOUR_GROQ_MODEL>>;

NEWS_API_KEY=<<YOUR_NEWS_API_KEY>>;
```

## Running the API
In order to run the API, you need to run the following command:
```bash
mvn clean install
mvn spring-boot:run
```
