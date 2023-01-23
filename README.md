# Kurt-Mario

Kurt-Mario is a 2D-game, where you play as the cat-nip addicted cat, Kurt-Mario.

The goal is to collect all the cat-nip, but be aware of the cat-nip-guarding enemies, and all the dangers on the way!

Supports both 1 player and local 2 player  (Couch co-op)

## How to play:
* W - Jump
* A - Go left
* D - Go right
* S - Duck (toggle)
* F - Kick
* Simply walk up on the collectibles to collect them and score points
* Jump on top of the enemies to cause them damage

In multiplayer-mode, Player 1 moves by WASD and Player 2 moves by the arrow keys.

## Running instructions
### Prerequisites
* Git installed
* Maven installed
* Java installed
* Internet connection (needed in order to see the Top 10 High Scores and to publish your run)

This application has been tested and verified with Java 17.

### How to build & run via commandline
#### 1. Clone project
```html
SSH: git clone git@git.app.uib.no:kaffekopp/kurt-mario.git
HTTPS: git clone https://git.app.uib.no/kaffekopp/kurt-mario.git
```
#### 2. Build project
Unix-based systems:
```html
cd .../kurt-mario
mvn clean install
```
Windows:
```html
cd C:\...\kurt-mario
mvn clean install
```

##### Note:
If you get an error like
```html
"Compilation failure invalid source release 17 with --enable-preview (preview language features are only supported for release 18)"
``` 
please check that your mvn configuration is specified to use Java version 17:
```html
mvn -v
Apache Maven 3.8.5
Maven home: /usr/local/Cellar/maven/3.8.5/libexec
-> Java version: 17.0.3, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-17.0.3.jdk/Contents/Home
Default locale: en_GB, platform encoding: UTF-8
OS name: "mac os x", version: "12.3.1", arch: "x86_64", family: "mac"
```
#### 3. Run project
Linux:
```html
cd .../kurt-mario/target
java -jar kurt-mario-1.0-SNAPSHOT-fat.jar
```
macOS:
```html
cd .../kurt-mario/target
java -XstartOnFirstThread -jar kurt-mario-1.0-SNAPSHOT-fat.jar
```
Windows:
```html
cd C:\...\kurt-mario\target
java -jar kurt-mario-1.0-SNAPSHOT-fat.jar
```

### How to build & run via IDE
#### 1. Clone project
```html
SSH: git clone git@git.app.uib.no:kaffekopp/kurt-mario.git
HTTPS: git clone https://git.app.uib.no/kaffekopp/kurt-mario.git
```
#### 2. Open project
Import the project in your preferred IDE via the pom.xml file

#### 3. Run main.Main
Expand the project's file tree and find 
```html
src/main/java/main/Main.java
```
and run the main method.
<br/><br/>
If you're using macOS, please edit your Run-configuration adding the VM option "-XstartOnFirstThread"

### Bugs
* Wall-Jump - You may be able to jump very high if you're in contact with a wall. (not fixed since the fix introduced a buggy jump experience)
* Mute-button - The mute-button is not scaleable (if you start the game in full-screen mode, then minimize, the button won't work anymore)
* Lava - Sometimes the player can start to burn if a monster is in the lava, and vice-versa.
* MovingPlatforms - They may stop and start to vibrate
* Scoreboard - The scoreboard is only scaled for the default-window size and full-screen