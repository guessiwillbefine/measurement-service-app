cd %~dp0
del /s /q logs\*.*
rd /s /q logs\
cd ms-api
del /s /q logs\*.*
rd /s /q logs\
cd %~dp0
cd ms-consumer
del /s /q logs\*.*
rd /s /q logs\