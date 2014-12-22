################
## Completed: ##
################

DMV:
1. Added DMV access restrictions so correct roles could do corresponding read/write/admin type functions.
2. DMV RMI client authentication: added valid login to existing RMI tests.
3. DMV JMS Publisher: publish information whenever a person changes physical details or residency.
4. Added unit test that verifies access controls work. (Unit test purposely tries to violate access restrictions).
5. Add DMV War access restrictions (Note: all the pages are still available if the user types the page in directly, 
I just would need to put the pages inside a folder so the security mapping will make them unavailable unless they login first. 
Also note that the dmv home button not only sends you home, but also logs the user out. Same with Mayberry.) 

Mayberry:
1. Added Mayberry Access restrictions so correct roles could do corresponding functions.
2. Mayberry RMI client authentication: added valid login to existing RMI tests.
3. Mayberry JMS Publisher: publish information whenever a POI changes.
4. Wrote JavaSE Ant Script to subscribe to Mayberry JMS Topic. Added Selector to subscribe only to POI messages classified as "Dangerous".
5. Mayberry MDB Subscriber: added MDB to subscribe to person changes and publish if it related to a POI.
6. Mayberry EJB Timer: Auto wake-up method that prints all POIs to Mayberry topic. (Mayberry JMS subscriber 
will not see these messages since they are not marked as Dangerous)
7. Added unit test that verifies access controls work. (Unit test purposely tries to violate access restrictions).
8. Add Mayberry War access restrictions.

End to End test:
1. Automated JUnit test.

############
## TODOs: ##
############

DMV:
1. Complete DMV GUI. (I did finish the dmv admin reset and populate functions. 
Also, the dmv tactical get driver and add driver capabilities. I am still using lookups instead of injecting the beans on each get/post
to accomplish this.)

Mayberry:
1. Complete Mayberry GUI. (I did finish the Mayberry admin reset).

End to End test:
1. Manual gui test.

############
## NOTES: ##
############

DMV deploys as http://localhost:8080/smarple1Dmv/index.html
Mayberry deploys as http://localhost:8080/smarple1Mayberry/index.html

To run JavaSE Ant script Mayberry JMS Subscriber:
build project at Datashare root level first.
cd smarple1DataShare\smarple1Mayberry\smarple1MayberryWAR>
ant -v -f target/test-classes/smarple1Mayberry-ant.xml subscriber

Current Database Setup:
As we discussed, current db setup shares database among all modules -> means modules all share same database, 
must be a server db instance, and means that I could have "Bleedover" between tests.