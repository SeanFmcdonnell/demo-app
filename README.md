# Finicity DS API Demo App #

### What is this repository for? ###

* This repo hosts the source code for the Finicity DS API Demo App
* This app demonstrates the features of the Finicity DS API
* It is designed so that it could be used as a starting point for a partner app
* Version: 1.0.0

### How do I get set up? ###

* Summary of set up
    * Checkout this repository, or fork it
    * 
* Configuration
* Dependencies
    * Java > 1.8
* Dev Dependencies
    * NPM
        * Run ```npm install .```
        * And ```npm install -g bower grunt```
        * Then ```bower install```
* Build
    * Java
        * Run ```mvn clean install``` from the project root
        * This will build a fat jar in app/target
* Dev Deployment
    * Java
        * Run ```java -jar app/target/app-{version}.jar server config.yml```
    * Web
        * Run ```grunt serve``` from ui
* Deployment
    * Java
        * Same as above. Optional: setup a job to run it
    * Web
        * Add the UI folder as the html root
        * See [demo-app.sample](demo-app.sample) for a sample nginx config

### Who do I talk to? ###

* Connor Kuhn [connor.kuhn@finicity.com](mailto:connor.kuhn@finicity.com)
* Finicity DS Support Team [New Support Request](https://finicity.zendesk.com/hc/en-us/requests/new)
