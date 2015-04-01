Installing Open Church.

# Introduction #
It should be noted that, at this stage, OpenChurch is still in the very early stages.  There is a J2EE application that you can build and deploy, but it doesn't do very much yet.  There are also JUnit tests which can be run for the curious; but they will give you little information beyond an idea of the early data structure.

## Requirements ##

In order to successfully install OpenChurch, you will require the following:
  * Java 1.5 or above
  * Maven
  * A J2EE server such as Glassfish or JBoss **It should be noted that OpenChurch has only been tested on Glassfish**
  * A SQL compliant database such as Oracle, MySQL or MS SQL Server **It should be noted that OpenChurch has only been tested on MySQL**



## Installation ##

  1. Download the source code from subversion. Click on the 'Source' tab above to find details.
> Locate the pom.xml in openchurch/openchurch-core/openchurch-core-data and uncomment the appropriate JDBC driver in the dependencies
  1. In the root directory of the project, you will find a directory called SQL.  Run the SQL that you find there on your database. **Please Note:** This script has only currently been tested on MS SQL Server & MySQL. It cannot be guaranteed to work on any other database server.  Other servers will be fully supported in the near future.
  1. Create a JNDI Datasource called 'jdbc/openchurch' pointing to the databse that was created in the last step.
  1. Edit the openchurch-core/openchurch-core-data/src/test/resources/db.properties file to contain the relvant values for connecting to your database and save it as db.properties .  This is for the unit tests.
  1. In the root directory of the project, run ` mvn clean install package `
  1. If all has gone well you should now have an ear file in the target directory.

If these instructions do not work for you then please feel free to email me at phunnilemur at gmail dot com to ask for advice.