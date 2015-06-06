# Configure checkstyle #

Configure checkstyle with modified google style configuration.
Google style because it is actively maintained.
Modified to fit the project needs.

## Assemble the config file

```sh
cd checkstyle/
CS_VER=6.7
M2_REPO=${M2_HOME:-~/.m2}/repository
unzip ${M2_REPO}/com/puppycrawl/tools/checkstyle/${CS_VER}/checkstyle-${CS_VER}.jar google_checks.xml
head --lines=-1 google_checks.xml > checkstyle.xml
tail --lines=+7 checkstyle-mods.xml >> checkstyle.xml
rm google_checks.xml
cd ..
```
