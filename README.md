WhirrToolbox
============

[Launch CDH on EC2 from ArcMap in under 5 minutes](http://thunderheadxpler.blogspot.com/2013/05/bigdata-launch-cdh-on-ec2-from-arcmap.html)

Make sure to first install in your local maven repo arcobjects.jar. You can get a copy from **Your ArcGIS Desktop Folder**\java\lib folder

    $ mvn install:install-file -Dfile=arcobjects.jar -DgroupId=com.esri -DartifactId=arcobjects -Dversion=10.1 -Dpackaging=jar -DgeneratePom=true

## Compiling and packaging
    $ mvn clean package

## Installing the extension in ArcMap
Copy from the **target** folder the file **WhirrToolbox-1.0-SNAPSHOT.jar** and the folder **libs** into **Your Desktop Folder**\java\lib\ext.

Check out [this](http://help.arcgis.com/en/arcgisdesktop/10.0/help/index.html#/A_quick_tour_of_managing_tools_and_toolboxes/003q00000001000000/) to see how to add a Toolbox and a Tool to ArcMap.

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
