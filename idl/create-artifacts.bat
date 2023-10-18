@echo off
call flatc --java ProtocolMessages.fbs

if not %errorlevel% == 0 goto end

echo --------------------------------
echo Successfully created artifacts
echo --------------------------------

:end
