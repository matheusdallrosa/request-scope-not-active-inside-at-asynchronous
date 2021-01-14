# Reproducer
This project is a reproducer to show that the request scope is not active
during the execution of methods with Microprofile @Asynchronous

When AsynchronousSevice.asynchronousMethod is called via
the RESTEasy resource named Reproducer, then the request scope is active.

When AsynchronousSevice.asynchronousMethod is called via 
the RabbitMQ consumer named Consumer, then the request scope is not active.

To have the Consumer.onMessage called you can run the following container:
```
docker container run --publish 5672:5672 --publish 15672:15672 rabbitmq:management
```

And send any message to the queue named: my-queue
via the RabbitMQ UI  http://localhost:15672/#/

# request-scope-not-active-inside-at-asynchronous project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `request-scope-not-active-inside-at-asynchronous-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/request-scope-not-active-inside-at-asynchronous-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/request-scope-not-active-inside-at-asynchronous-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

# RESTEasy JAX-RS

<p>A Hello World RESTEasy resource</p>

Guide: https://quarkus.io/guides/rest-json
