Coverage: 88.3%
# Project Title

The aim of this project was to build a functioning inventory management system for a retailer. It had to consist of of a managed database, containing
tables of customers products and orders, a functional front end user interface and all the necessary back end fucntionality to link the two together.

## Getting Started

git clone https://github.com/LLow-QA/IMS-Starter.git
Jira project board link https://20novsoftware2lloyd.atlassian.net/secure/RapidBoard.jspa?rapidView=3&projectKey=FPI
### Prerequisites

A Java IDE
Latest JDK
Maven
A GUI

### Installing

What things you need to install the software and how to install them



JDK
1. Download the latest JDK https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html
2. Follow the installation wizards instructions
3. Open “command prompt” and run "java -version" for verification.
4. Edit the syustem environment variables
5. Create a new system variable called 'JAVA_HOME' and set it to the pathfile of your JDK then press OK.
6. enter the path system variable and add %JAVA_HOME%\bin and close.
7. Open “command prompt”, type "java" and hit enter. If youve followed correctly then you should get a screen of java options.
8. You can now run java files by navigating to their folder and running them using the java filename.java command.


A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Clean target folder then package and run all tests with the command: mvn clean package
Just run tests with the command: mvn test
Run a specific test with: mvn -Dit.test="EnterTestCaseHere" verify

### Unit Tests 

Explain what these tests test, why and how to run them
Unit tests test a single classes modules to check that they are working correctly.
This, in theory, enables testing of every aspect of your code
```
CustomerDAOTest.java
```

### Integration Tests 
Explain what these tests test, why and how to run them
Integration testing is testing many test classes at once to ensure that they do not interfere with eachother.
this tests that actions undertaken in previous tests dont flow through into others accidentally.
```
Testing all controllers, all DAOS or the entire test package
```

## Deployment

clone down the repository from the link above.
run this command: java -jar ims-Starter-jar-with-dependencies.jar

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Lloyd Low** - *Built upon intial work* - [LLow-QA](https://github.com/LLow-QA)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
