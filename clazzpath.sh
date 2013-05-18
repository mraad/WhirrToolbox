pushd target
ls libs/*.jar | awk -f ../clazzpath.awk > ../src/main/resources/classpath.txt
popd
