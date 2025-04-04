if (Test-Path "./build") { Remove-Item -Force -Recurse .\build }
if (Test-Path "./sources.txt") { Remove-Item -Force -Recurse .\sources.txt }
if (Test-Path "./binaries.txt") { Remove-Item -Force -Recurse .\binaries.txt }
touch .\sources.txt .\binaries.txt
("./" + (Get-ChildItem -Filter "*.java" -Recurse -File -Name)).Replace("\", "/") | Out-File sources.txt -Encoding ascii
javac --% -d build @sources.txt
Set-Location .\build
("./" + (Get-ChildItem -Filter "*.class" -Recurse -File -Name)).Replace("\", "/") | Out-File ..\binaries.txt -Encoding ascii
"Manifest-Version: 1.0
Main-Class: $($args[0])" | Out-File Manifest.txt -Encoding ascii
jar --% -cfmv ..\App.jar Manifest.txt @..\binaries.txt
Set-Location .\..
