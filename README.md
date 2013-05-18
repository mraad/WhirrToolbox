WhirrToolbox
============

GP tool to launch CDH on EC2 from ArcMap.

Make sure to first install in your local maven repo arcobjects.jar. You can get a copy from **Your ArcGIS Desktop Folder**\java\lib folder

    $ mvn install:install-file -Dfile=arcobjects.jar -DgroupId=com.esri -DartifactId=arcobjects -Dversion=10.1 -Dpackaging=jar -DgeneratePom=true

## Compiling and packaging
    $ mvn clean package

## Installing the extension in ArcMap
Copy from the **target** folder the file **WhirrToolbox-1.0-SNAPSHOT.jar** and the folder **libs** into **Your Desktop Folder**\java\lib\ext.
