WhirrToolbox
============

[Launch CDH on EC2 from ArcMap in under 5 minutes](http://thunderheadxpler.blogspot.com/2013/05/bigdata-launch-cdh-on-ec2-from-arcmap.html)

Make sure to install **arcobjects.jar** in your local [maven repo](http://maven.apache.org/guides/introduction/introduction-to-repositories.html). You can typically find it in C:\Program Files (x86)\ArcGIS\Desktop10.1\java\lib.

    $ mvn install:install-file -Dfile=arcobjects.jar -DgroupId=com.esri -DartifactId=arcobjects -Dversion=10.1 -Dpackaging=jar -DgeneratePom=true

## Compiling and packaging

    $ mvn clean package

## Installing the extension in ArcMap
Copy from the **target** folder the **WhirrToolbox-1.1-SNAPSHOT.jar** file and the **libs** folder into the C:\Program Files (x86)\ArcGIS\Desktop10.1\java\lib\ext folder.

Before starting ArcMap, you have to adjust the ArcGIS JVM Heap values. Run as **administrator** JavaConfigTool located in C:\Program Files (x86)\ArcGIS\Desktop10.1\bin

![JavaConfigTool](https://dl.dropboxusercontent.com/u/2193160/JavaConfigTool.png)

Check out [this](http://help.arcgis.com/en/arcgisdesktop/10.0/help/index.html#/A_quick_tour_of_managing_tools_and_toolboxes/003q00000001000000/) to see how to add a Toolbox and a Tool to ArcMap.

Start ArcMap. Create a toolbox named 'WhirrToolbox' and add LaunchClusterTool, DestroyClusterTool and ClusterPropertiesTool.

![WhirrToolbox](https://dl.dropboxusercontent.com/u/2193160/WhirrToolbox.png "Whirr Toolbox")

## LaunchClusterTool
![LunchClusterTool](https://dl.dropboxusercontent.com/u/2193160/LaunchClusterTool.png)

### Sample cluster.propertiers file
    whirr.provider=aws-ec2
    whirr.identity=my-aws-access-key-id
    whirr.credential=my-aws-secret-access-key
    whirr.cluster-name=hadoopcluster
    whirr.instance-templates=1 hadoop-jobtracker+hadoop-namenode,3 hadoop-datanode+hadoop-tasktracker
    whirr.public-key-file=/Users/mraad/mypublickey
    whirr.private-key-file=/Users/mraad/myprivatekey
    whirr.env.repo=cdh4
    whirr.hadoop.install-function=install_cdh_hadoop
    whirr.hadoop-configure-function=configure_cdh_hadoop
    whirr.hardware-id=m1.large
    whirr.image-id=us-east-1/ami-ccb35ea5
    whirr.location-id=us-east-1

## DestroyClusterTool
![DestroyClusterTool](https://dl.dropboxusercontent.com/u/2193160/DestroyClusterTool.png)

## ClusterPropertiesTool
![ClusterPropertiesTool](https://dl.dropboxusercontent.com/u/2193160/ClusterPropertiesTool.png)

Converts the cluster properties into Hadoop properties
