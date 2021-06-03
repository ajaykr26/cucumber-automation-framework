@echo off
set /P environment="Enter environment:"
set /P teckstack="Enter techStack:"
set /P testSuiteName="Enter test Suite Name:"

mvn clean test -Dcukes.environment=%environment% -Dcukes.techstack=%teckstack% -DsuiteXmlFile=%testSuiteName%
