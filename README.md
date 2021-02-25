# JarCompare Notes

## What is JarCompare?
JarCompare is a tool to compare 2 directories with JAR file content. This will analyze and list down only the changed JAR files for their source / binary compatibility. Additionally it will also list down the newly added and removed JAR(s). This tool internally uses https://lvc.github.io/japi-compliance-checker/ 

## How to build the image
Go to DockerFile directory and type

    docker build . -t org.jarcompare

## How to run
- Create the workspace directory. (e.g. C:\workspace)
- Copy the directories which needs to be compared to workspace directory.
- Create a config.properties file in workspace directory and add the following properties

    v1.path=/workspace/compare/SP1/Client
    
    v2.path=/workspace/compare/SP2/Client

These paths should be relative to "/workspace" directory as we mount to workspace directory below. Once done, you can run the container as follows. 

In here for "C:\workspace" please change with your respective workspace folder path.  

    docker run -v C:\workspace:/workspace org.jarcompare:latest
    
## How to download Docker image from DockerHub
Instead of building this locally you can pull this docker image directly from DockerHub as well.

    docker pull nilindra/org.jarcompare:latest    

## How to run Docker image from DockerHub

    docker run -v C:\workspace:/workspace nilindra/org.jarcompare:latest

## Special Notes
  - Response times could be further improved with parallelizing the process.
  - Any improvements / suggestions are well come to nilindra_99 at yahoo dot com.
  
## License
This program is free software. You may use, redistribute and/or modify it under the terms of [LICENSE](LICENSE).
