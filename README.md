# JarCompare Notes

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
This program is free software. You may use, redistribute and/or modify it under the terms of the GNU LGPL
