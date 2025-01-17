# URL Shortener Backend  

## Overview  
The URL Shortener Backend is a robust and efficient web application designed to simplify long URLs into concise, shareable short links. Built using **Spring Boot**, this backend service handles URL shortening, redirection, and optional analytics to track usage metrics.  

This project is ideal for learning backend development principles, RESTful API design, and database integration with modern frameworks.  

## Key Features  
- **Short URL Generation**: Converts long, cumbersome URLs into short, user-friendly links.  
- **Redirection**: Automatically redirects users from a short URL to the original long URL.  
- **Custom Short URLs** *(Optional)*: Allow users to define custom aliases for short URLs.  
- **Analytics** *(Optional)*: Track the number of clicks and other usage statistics for each short URL.  
- **Database Integration**: Securely stores URLs and mappings using MongoDB or MySQL.  

## Technologies Used  
- **Language:** Java  
- **Framework:** Spring Boot  
- **Database:** MongoDB / MySQL  
- **Tools:** Maven, REST APIs, Postman (for testing)  

## API Endpoints  
### 1. Generate Short URL  
**POST** `/api/shorten`  
- **Description**: Accepts a long URL and returns a corresponding short URL.  
- **Request Body Example**:  
  ```json
  {  
    "longUrl": "https://www.example.com/some-long-url"  
  }  
