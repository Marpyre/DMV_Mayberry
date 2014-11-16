DMV deploys as http://localhost:8080/smarple1Dmv/index.html

Mayberry deploys as http://localhost:8080/smarple1Mayberry/index.html

Completed:

1. DMV EBJ Module Built.
	a. DTOs outlined.
	b. EJBs outlined. (Not all business logic implemented)
	c. EJB interfaces outlined.
2. DMV Test Module Built.
	a. EJB Tests outlined.
3. DMV EAR Module Built.
4. DMV WAR Module Built.
	a. UI started, not all functions finished. TestUtils functions are mostly complete.
5. Mayberry WAR Module Built.
	a. UI started.

TODO:

1. Add business logic for DMV EJBs to use.
2. Complete DMV EJBs and their interfaces.
3. Add methods from EJBs to test modules once they are implemented.
4. Complete DMV UI and utilize missing methods.
5. Add Mayberry BL and EJBs.
6. Add Mayberry Testing.
7. Complete Mayberry UI.
8. Complete end to end junit tests.

Fixes Needed:

1. Residue from database setup needs to be resolved (need to add proper set up/ 
tear down scripts to correct poms instead of just in the Impl poms)