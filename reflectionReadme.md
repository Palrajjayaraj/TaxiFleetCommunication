# Reflection and inference while designing and implementing the Taxi Fleet Communication System

## What aspect of this exercise did I find most interesting?
    - Learning visual studio planutml diagram tools, ealier I was using UML eclipse tools.
    - Designing the System using the class diagram and sequence diagrams.
    - Designing the application in modules and layered approach.
    - Making various decisions (However it turn out to be)
    - Learning SSE to push notifications to the clients in real time.
    - Learning dockerization to run application as one.
    - long unsleepy nights to find the solution for problem.
    - Debugging :)

## What did I find most cumbersome?
    - Deciding on whether to build a web app or standalone java application.
    - Working through Docker setup and ensuring clean builds with no cache, cloned sources, and working module paths took more effort than expected.
  
## Reflecting on my decisions
### Dilemmas
- I decided to implement a desktop application at the beginning, then, switched to Web front end application.
  -  Still not confident that I have made the right decision. 
  -  But, I made the system as modular as possible, so that web front end application can be easily replaced by a desktop application module.
- Created modules for User, Taxi and System (one for each role). But, is this the 
  - But, As most of the things are managed by the system, all the things are part of System module itself and only POJOs are present in the user and taxi modules. They would be totally eliminated itself.
- Less time for JUnit testing.
  - Initially, I started with adding test cases, but, with each on going days, I was not able to add tests for the newly introduced backend classes.
  - This I should have added tests and planned better.
  
  ### Positives
- Please refer to the 'What aspect of this exercise did I find most interesting' section.