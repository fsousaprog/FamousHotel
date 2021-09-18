# Famous Hotel Booking API

***
### Summary
This API has the purpose to help book the only and disputed room of the Famous Hotel.

#### The user is able to:
* See the available dates on the calendar
* Place a reservation on a preferred date
* **Change** the date or guests of the reservation anytime
* **Cancel** the reservation anytime

***
### Diagrams
Use Case

![Use Case](src/_docs/Use Case Diagram.jpg)

Entity

![Entity](src/_docs/Entity Diagram.jpg)

***
### Technologies
Frontend:
- HTML
- CSS
- Javascript
- Bootstrap

Backend:
- Java 8
- Spring Boot
- Spring Framework (Core, MVC)
- Lombok

Persistence:
- Spring Data JPA
- H2

Tests:
- SpringBootTest
- JUnit5
- Mockito

***
### Some reasons
Frontend:
- HTML+Thymeleaf works great with Spring MVC to make the communication between html and controllers.
It's simple to configure and to use, making it fast and light.
- CSS+Bootstrap were my choice for style. Bootstrap is an easy-to-use library
with nice components, and CSS is a must to design HTML pages in my opinion.
- Javascript+JQuery+Ajax for the responsivity. There is not much of them in the project,
but they are still necessary to give some behaviours to HTML components and to communicate with
the backend on-demand.

Backend:
- Java 8 is where I'm more used to develop, so it was my first choice purely because of experience.
Still, I had a troubles configuring Java 11 and I didn't need anything in particular, so 8 works great.
- Spring Boot is really helpful nowadays. I got used to starting applications with it instead of configuring
a local Tomcat 7 or 8 and deploying my war there. I still configure the pom manually, but the dependency
wizard in Spring Initializer is great to search what spring is capable of.
- Spring MVC is the best way I know to rapidly create a RESTful API and communicate with the frontend.
And Spring Core makes dependency injection and inversion of control more practical with its annotations
and minimal to none configuration.
- Lombok is more like a lazy way to implement POJOs, but I think it makes the code cleaner.

Persistence:
- Spring JPA is the one I most used for some time now, and I got used to it. It gives the DAOs, Repositories, Entities, etc..,
a cleaner code, with a lot less lines and better understandable and usable data services.
- H2 is fast and easy to configure and use, gaining time and reducing waste. Since it's a non-safe test application,
I think it's okay to use it, but I might change to MySQL in the future, so it really stores the data.

Tests:
- Taking advantage of Spring Boot, the annotation @SpringBootTest makes the bean already scanned and available for
use in the test environment, gaining time and a cleaner code without all the beans initializations.
- JUnit 5 is kind of a default when implementing unity tests in a java application.
- Mockito is very useful to create proxy elements of services and beans not controlled by spring.

***
### Project Structure

```
src
└───_docs
│   │   Entity Diagram.jpg
│   │   Use Case Diagram.jpg
│
└───main
│   └───java
│   │   └───com.alten.Famoushotel
│   │   │   └───control
│   │   │   │   │   HomeController.java
│   │   │   │   │   ReservationController.java
│   │   │   │
│   │   │   └───entity
│   │   │   │   │   Reservation.java
│   │   │   │
│   │   │   └───model
│   │   │   │   │   ReservationDTO.java
│   │   │   │
│   │   │   └───repository
│   │   │   │   │   ReservationRepository.java
│   │   │   │
│   │   │   └───service
│   │   │   │   │   ReservationService.java
│   │   │   │
│   │   │   └───util
│   │   │   │   │   Constants.java
│   │   │   │
│   │   │   FamousHotelApplication.java
│   │
│   └───resources
│       └───static
│       │   └───css
│       │   │   │   main.css
│       │   │
│       │   └───img
│       │   │   │   cancun.jpg
│       │   │   │   hotel_hall.jpg
│       │   │   │   hotel_outside.jpg
│       │   │   │   hotel_room.jpg
│       │   │
│       │   └───js
│       │       │   main.js
│       │
│       └───templates
│       │   │   booking.html
│       │   │   home.html
│       │   │   manageReservation.html
│       │   │   result.html
│       │
│       │   application.properties
│
└───test
    └───java
        └───com.alten.FamousHotel
            └───control
            │   │   ReservationControllerTest.java
            │
            └───service
            │   │   ReservationServiceTest.java
            │   
            │   FamousHotelApplicationTests.java
```

The struct has base on MVC and everything web-related is being contained in the 'resources' folder.

The 'model' was used alongside 'entity', giving the entities more security and consistency while handling only DTOs.

The tests are place in same folder structure as 'main' to easily identify them.

