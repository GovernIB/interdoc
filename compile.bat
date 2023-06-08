@echo off

type help.txt

cmd /C mvn -U -DskipTests %* install

if %errorlevel% EQU 0 (

	@echo off
	IF DEFINED INTERDOC_DEPLOY_DIR (
	  @echo on
	  echo --------- COPIANT EAR ---------

	  xcopy /Y interdoc-ear\target\interdoc.ear %INTERDOC_DEPLOY_DIR%

	) ELSE (
	  echo  =================================================================
	  echo    Definex la variable d'entorn INTERDOC_DEPLOY_DIR 
	  echo    apuntant al directori de deploy del JBOSS  i automaticament 
	  echo    s'hi copiara l'ear generat.
	  echo  =================================================================
	) 

)