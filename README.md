Quick Guide on how to use Vaadin
=================================


If you are new to Maven, read [these instructions](https://vaadin.com/blog/-/blogs/the-maven-essentials-for-the-impatient-developer) to get your project properly imported into Eclipse or your IDE of choice.

Workflow
========

To compile the entire project, run "mvn install" or build in your IDE. Internet connection is necessary.

Run the application on your preferred server(Apache Tomcat,  Glassfish, etc.) or  run "mvn jetty:run" and open http://localhost:8080/ .

Debugging client side code
  - run "mvn vaadin:run-codeserver" on a separate console while the application is running
  - activate Super Dev Mode in the debug window of the application


Developing a theme using the runtime compiler
---------------------------------------------

When developing the theme, Vaadin can be configured to compile the SASS based
theme at runtime in the server. This way you can just modify the scss files in
your IDE and reload the browser to see changes.

To use on the runtime compilation, open pom.xml and comment out the compile-theme
goal from vaadin-maven-plugin configuration. To remove a possibly existing
pre-compiled theme, run "mvn clean package" once.

When using the runtime compiler, running the application in the "run" mode
(rather than in "debug" mode) can speed up consecutive theme compilations
significantly.

It is highly recommended to disable runtime compilation for production WAR files.

Using Vaadin pre-releases
-------------------------

If Vaadin pre-releases are not enabled by default, use the Maven parameter
"-P vaadin-prerelease" or change the activation default value of the profile in pom.xml .
