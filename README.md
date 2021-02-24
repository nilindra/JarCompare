# JarCompare Notes

## What is JarCompare?
JarCompare is a tool to compare 2 directories with JAR file content. This will analyze and list down the changed JAR files for their source / binarary compatibility. Additionally it will also list down the newly added and removed JAR(s). This tool internally uses https://lvc.github.io/japi-compliance-checker/ 

## How to build the image
Go to DockerFile directory and type

    docker build . -t org.jarcompare

## How to run
- Create the workspace directory.
- Copy the directories which needs to be compared to workspace directory.
- Create a config.properties file in workspace directory and add the following properties

    v1.path=/workspace/compare/SP1/Client
    
    v2.path=/workspace/compare/SP2/Client

Once done, you can run the container as follows. 

In here for C:\workspace please change with your respective workspace folder path.  

    docker run -v C:\workspace:/workspace org.jarcompare
    
## License
This program is free software. You may use, redistribute and/or modify it under the terms of [LICENSE](LICENSE).
