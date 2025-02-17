Test Task: RESTful API Service for a TO-DO List
Objective
Develop a RESTful API service that allows users to manage their personal TO-DO list.

Requirements
-The system should support multiple users, allowing new users to be created.
-Users should be able to manage their tasks with the following operations:
--Create a new task
--Delete an existing task
--Edit a task
--Retrieve a specific task
--Retrieve all tasks assigned to a specific user
--Retrieve all tasks for all users
-Each task can contain attachments, which should be available for download.

Task Structure
Each task should include the following fields:

--Start Date – when the task begins
--End Date – when the task is due
--Assignee – the user responsible for completing the task
--Description – a brief explanation of the task
--Tags – labels or categories for better organization

Task Reassignment
-A task can be transferred from one user (User A) to another (User B) using a re-assign REST endpoint.

Technology Stack
-Java 17
-Spring Boot
-Spring Data JPA
-Spring MVC (for REST API)
-Any relational database (PostgreSQL, MySQL, etc.)
