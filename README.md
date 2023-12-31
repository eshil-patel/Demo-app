Demo Multi-threaded(platform threads initially) Spring boot App, Connected to local Postgres. 
Using Java 21, and Spring security. 
You will need the following: 
1. Docker Desktop + Postgres (see https://www.dbvis.com/thetable/how-to-set-up-postgres-using-docker/) and follow instructions. Will have to use cmd to pull/initialize Postgres the first time 
2. Java 21 and Up to date Intellij 
3. Dbeaver to create users table and populate with some test data (https://dbeaver.io/download/)
4. Postman from July 2023 or later to use performance testing feature (can mock having multiple users to see impact of virtual threads)

Notes for Java 21 integration with virtual threads:
1. Use semaphore for database connections
2. REST calls to other microservices + calls to a distributed cache will be on case by case basis
3. Most beneficial for I/O heavy services 
4. ThreadLocal variables might become a memory issue if you spawn too many virtual threads 


