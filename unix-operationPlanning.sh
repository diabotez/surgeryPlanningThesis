pathToJarFile="/home/diana/git_repo/operation_planning/surgeryPlanning/dist"
pathToCplexLibrary="/usr/local/cplex/cplex/bin/x86-64_linux/:/usr/local/cplex/cplex/lib/x86-64_linux/"
jarFile="surgeryPlanning.jar"

# DO NOT CHANGE AFTER THIS LINE
# -----------------------------

cd $pathToJarFile

java -Djava.library.path=$pathToCplexLibrary -jar $pathToJarFile/$jarFile
