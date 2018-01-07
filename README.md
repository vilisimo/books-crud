# vault
Proof of concept Dropwizard CRUD project to gain familiarity with:
* Dropwizard
* Camel
* Guice
* JDBI
* Groovy
* Spock
* Docker

# Short Description
There is no complex architecture or mind-bending structure. The app simply receives a request on `/books` endpoint, does some rudimentary checks to ensure a `Book` instance is valid, and passes it through to the persistence layer through JMS (via ActiveMQ with help from Apache camel). There, it is saved in the database (hsqldb), using JDBI. Standard CRUD operations are all supported.

# Is that it?
Yes. No additional logic is in place, as the aim of the project was to get the exposure to the technologies mentioned above.

