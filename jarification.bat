@echo off

:: Déclaration des variables
set "work_dir=C:\Users\Raina\Documents\Framework"
set "temp=%work_dir%\temp"
set "web=%work_dir%\web"
set "lib=%work_dir%\lib"
set "web_apps=C:\Server\tomcat\webapps"
set "war_name=FirstMVC"
set "Binary=%work_dir%\bin"

:: Effacer le dossier [temp]
if exist "%temp%" (
    rd /s /q "%temp%"
)

:: Créer la structure de dossier
mkdir "%temp%\WEB-INF\lib"
mkdir "%temp%\WEB-INF\classes"
mkdir "%temp%\WEB-INF\views"

:: Copier le contenu de [web] dans [temp]
xcopy /E /y "%web%\*" "%temp%\WEB-INF\views"
xcopy /E /y "%Binary%\*" "%temp%\WEB-INF\classes"

:: Copier le fichier [web_xml] vers [temp] + "\WEB-INF"
copy "%work_dir%\config\web.xml" "%temp%\WEB-INF"
copy "%work_dir%\config\dispatcher-servlet.xml" "%temp%\WEB-INF"

:: Copier les fichiers .jar dans [lib] vers [temp] + "\WEB-INF\lib"
xcopy /s /i "%lib%\*.jar" "%temp%\WEB-INF\lib"

:: Créer un fichier .war nommé [war_name].war à partir du dossier [temp] et son contenu dans le dossier [work_dir]
cd "%temp%"
jar cf "%work_dir%\%war_name%.war" *

:: Effacer le fichier .war dans [web_apps] s'il existe
if exist "%web_apps%\%war_name%.war" (
    del /f /q "%web_apps%\%war_name%.war"
)

:: Copier le fichier .war vers [web_apps]
copy /y "%work_dir%\%war_name%.war" "%web_apps%"

del /f /q "%work_dir%\%war_name%.war"

echo Déploiement terminé.
@REM pause
