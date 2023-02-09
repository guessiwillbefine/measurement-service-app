echo %~dp0
cd %~dp0\ms-api\logs
for /F "delims=" %%i in ('dir /b') do (rmdir "%%i" /s/q || del "%%i" /s/q)