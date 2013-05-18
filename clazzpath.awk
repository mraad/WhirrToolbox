BEGIN{
  print "${pom.artifactId}-${pom.version}.jar"
}
{
  print $1
}