@echo off

set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_33
set PATH=%JAVA_HOME%\bin;%PATH%

if exist "%~dp0\tools\lib\jni4net.j-0.8.6.0.jar" goto gen
call "%~dp0\tools\loadTools.cmd"

:gen
"%~dp0\tools\lib\jni4net.proxygen-0.8.6.0.exe" tools\proxygen\robocode.control.proxygen.xml
"%~dp0\tools\lib\jni4net.proxygen-0.8.6.0.exe" tools\proxygen\robocode.proxygen.xml


if exist "%~dp0\robocode.dotnet.nhost\target\robocode.dotnet.nhost-1.7.4.2.dll" goto gen2
echo cat't find robocode.dotnet.nhost\target\robocode.dotnet.nhost-1.7.4.2.dll, please compile it
goto end

:gen2
"%~dp0\tools\lib\jni4net.proxygen-0.8.6.0.exe" tools\proxygen\robocode.proxygen.net.xml

:end