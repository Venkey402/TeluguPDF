EM [at] echo off

for /f "tokens=1" %%i in ('date /t') do set DATE_DOW=%%i

for /f "tokens=2" %%i in ('date /t') do set DATE_DAY=%%i
for /f %%i in ('echo %date_day:/=-%') do set DATE_DAY=%%i
for /f %%i in ('time /t') do set DATE_TIME=%%i
for /f %%i in ('echo %date_time::=-%') do set DATE_TIME=%%i

"C:\Bitnami\testlink-1.9.20-15\mariadb\bin\mysqldump" --user=root --password=root --databases telugu >"C:\DontDelete_D driver\New folder\TeluguPDF\src\test\resources\db_backup\%DATE_DAY%_%DATE_TIME%_backupTelugu.sql"