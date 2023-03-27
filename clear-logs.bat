@echo off

set "LOGS_DIR=%~dp0logs"
set "API_DIR=%~dp0ms-api/logs"
set "CONSUMER_DIR=%~dp0ms-consumer/logs"

if exist "%LOGS_DIR%" (
    echo Deleting all files and directories in "%LOGS_DIR%"...
    rd /s /q "%LOGS_DIR%"
) else (
    echo Directory "%LOGS_DIR%" not found.
)

if exist "%API_DIR%" (
    echo Deleting all files and directories in "%API_DIR%"...
    rd /s /q "%API_DIR%"
) else (
    echo Directory "%API_DIR%" not found.
)

if exist "%CONSUMER_DIR%" (
    echo Deleting all files and directories in "%CONSUMER_DIR%"...
    rd /s /q "%CONSUMER_DIR%"
) else (
    echo Directory "%CONSUMER_DIR%" not found.
)