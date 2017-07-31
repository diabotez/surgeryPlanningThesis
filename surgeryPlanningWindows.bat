SET pathToJarFile="C:\Users\Diana\Downloads\surgery planning"

SET pathToCplexLibrary="C:\Program Files\IBM\ILOG\CPLEX_Studio1262\cplex\bin\x64_win64;C:\Program Files\IBM\ILOG\CPLEX_Studio1262\opl\bin\x64_win64"

SET jarFile="surgeryPlanning.jar"


cd %pathToJarFile
% 
java -Djava.library.path=%pathToCplexLibrary% -jar %jarFile%
