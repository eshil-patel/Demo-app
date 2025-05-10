Demo Multi-threaded(platform threads initially) Spring boot App(3.2.1), 
Connected to local Postgres. Using Java 21, and Spring security. 
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

Future work 
1. Connect to Neo4j
2. Use graphQL endpoints on Neo4j and potentially rest endpoints 


Helpful properties
jdk.virtualThreadScheduler.parallelism
jdk.virtualThreadScheduler.maxPoolSize
spring.threads.virtual.enabled=true

Helpful links
https://vived.substack.com/p/spring-framework-61-and-spring-boot
https://quarkus.io/blog/virtual-thread-1/#five-things-you-need-to-know-before-using-virtual-threads-for-everything)
Using Pre-spring 3.2 
https://stackoverflow.com/questions/77282538/how-to-use-non-pooling-virtual-threads-to-execute-asynchronous-tasks-in-spring-b
https://medium.com/@egorponomarev/spring-boot-3-2-with-virtual-threads-and-graalvm-out-of-the-box-1911d3ebf0b6
https://mariadb.com/resources/blog/benchmark-jdbc-connectors-and-java-21-virtual-threads/
General threads vs cores discussion
https://stackoverflow.com/questions/34689709/java-threads-and-number-of-cores
https://stackoverflow.com/questions/47489338/what-is-the-difference-between-thenapply-and-thenapplyasync-of-java-completablef
OSC book
https://os.ecci.ucr.ac.cr/slides/Abraham-Silberschatz-Operating-System-Concepts-10th-2018.pdf
Deferred results and returning completable futures in the controller
https://spring.io/reactive/
Multi version release Jars
https://nipafx.dev/multi-release-jars-multiple-java-versions/
GraphQL links
https://stackoverflow.com/questions/75921067/spring-boot-graphql-querymapping-not-being-called
https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/#tutorials-written-by-the-community
https://medium.com/@benjamin.habegger/step-by-step-guide-to-build-a-graphql-server-in-java-over-multiple-data-sources-30c0c7b4b409

Using Datadog agent locally for traceId testing 
Download the agent from maven, and use jar as agent for application 

Start app with following VM args :
-javaagent:"C:\Users\xeshi\.m2\repository\com\datadoghq\dd-java-agent\1.49.0\dd-java-agent-1.49.0.jar"
-Ddd.profiling.enabled=true
-Ddd.logs.injection=true
-Ddd.agent.port=8126
-Ddd.service=ecommerce-service
-Ddd.env=local
-XX:FlightRecorderOptions=stackdepth=256

Potentially useful links
https://github.com/open-telemetry/opentelemetry-java-instrumentation/discussions/3168
