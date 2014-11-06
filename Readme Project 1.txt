Completed:

Added .ddl files for auto creation/deletion of database schema.
Added .ddl files for add/remove of db indexes.
Added DMV Business Objects.
Added Mayberry Business Objects.
Added JPA entity mapping for DMV Business Objects.
Added JPA entity relationships for DMV Business Objects.
Added JUnit tests for DMV Business Objects.
Added JPA entity mapping for Mayberry Business Objects.
Added JPA entity relationships for Mayberry Business Objects.
Added injester capability. Passes all unit tests.

TODO: Complete end-to-end test scenarios. 
The implementation logic should be straightforward to code, 
just ran out of time there.
TODO: Complete DAOs.
TODO: Make sure nothing else missed from project 1.

The project was in a decent state for some semester topics -- like core entity and relationship mapping. However, topics like DAOs, business logic, 
validation, the ingestor, and the end-to-end were significantly under-address to bypassed totally.


README	10	10	 
builds cleanly	15	15	(-0) The initial build failed from an empty localRespository
managed schema	4	5	(-1) Column sizes are decently constrained but everything in your module is optional. Even the tag# and VIN# are not required for a VehicleRegistration. It is highly recommeneded that you have a well-constrained schema to help keep your data model consistent in the face of the different data accesses that will occur over an enterprise application.
business objects	10	10	 
used validation API	0	5	(-5) I could find no use of the validation API
JPA DAO & mapping	25	25	 
Ingest	0	10	(-10) Not implemented. This is one reason why your DAOs look so thin.
business logic	0	10	(-10) Not implemented. This is one reason why your DAOs look so thin.
End to End test	2	10	(-8) Mostly not implemented. Some specific comments below.

 	 	
 	66	100	 

== README

* decent. It would good that you pointed out the things that were not finished. All can be resolved. Please work with me/newsgroup to complete/fix.

* There should be no reason why this can't work the way you intend. Try moving your resources specification from the root, down to each child. If still any issue please work with me on this. You cannot have a persistence.xml
in the src/main when it comes time to deploy to the server-side.
 20 Note: Mayberry project was not finding META-INF folder in test directory, 
 21 so I moved it into the main directory.                                  
 22 Then my persistence files could be found and filtered.                  
 23 Could not find a setup difference between my mayberry                   
 24 and dmv to figure out why they are behaving differently. 

== Builds cleanly

* (-0) The initial build failed from an empty localRespository -- (based on the state of the code, I will not worry about issues in the build)
Looks like two straight forward "simple"/singleImpls
$ find . -name pom.xml
./pom.xml
./smarple1Dmv/smarple1DmvImpl/pom.xml
./smarple1Dmv/pom.xml
./smarple1Mayberry/smarple1MayberryImpl/pom.xml
./smarple1Mayberry/pom.xml

* Why are you declaring all these dependencies in a *ROOT* pom? Root poms are meant to (passively) define things -- not to (actively) declare things. This root pom is dictating dependencies for every child module. You are able to get 
away with this in this project since your current child modules are the same. That will *NOT* be true in project 2. Please refactor based on what was demonstrated in the EntityManagerEx.
159         <dependencies>
160                 <dependency>
161                         <groupId>commons-logging</groupId>
162                         <artifactId>commons-logging</artifactId>
163                         <scope>compile</scope>
164                 </dependency>
165 
166                 <dependency>
167                         <groupId>org.hibernate.javax.persistence</groupId>
168                         <artifactId>hibernate-jpa-2.1-api</artifactId>
169                         <scope>provided</scope>
170                 </dependency>
171 


* Filtering is not generally something you want across all files in all resources in all child modules. This will corrupt any binary files and expand variables at compile-time in files meant to be exapnded at runtime. I would leavesthis detail to the child modules.

* You have also made an incorrect declaration. You have told the resources plugin to place your src/main/resources in target/test-classes.
 99         <build>
100                 <!-- filtering will replace URLs, credentials, etc in the files copied 
101                         to the target directory and used during testing. -->
102                 <testResources>
103                         <testResource>
104                                 <directory>src/test/resources</directory>
105                                 <filtering>true</filtering>
106                         </testResource>
107                         <testResource>
108                                 <directory>src/main/resources</directory>
109                                 <filtering>true</filtering>
110                         </testResource>
111                 </testResources>

Note the copies -- you do not have any DDL files in src/test/resources
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/tuningadd.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/create.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/delete.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/tuningdrop.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/drop.ddl

./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/tuningadd.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/create.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/delete.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/tuningdrop.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/drop.ddl

The above should use <resources> for src/main/resources
http://maven.apache.org/plugins/maven-resources-plugin/examples/filter.html


* Good use of the orderFile with filesets. Good paying attention to newsgroup errors/resolutions.
 51 <configuration>
 52         <autocommit>true</autocommit>
 
 53         <orderFile>descending</orderFile>
 
 54         <fileset>
 55                 <basedir>${basedir}/src</basedir>
 56                 <includes>
 57                         <include>main/resources/ddl/**/*tuningdrop*.ddl</include>
 58                         <include>main/resources/ddl/**/*drop*.ddl</include>
 59                 </includes>
 60         </fileset>
 61         <onError>continue</onError>
 62 </configuration>

 
* The dependencies in Mayberry seem pretty light. No logging? No JPA?

 11         <artifactId>smarple1MayberryImpl</artifactId>
 16         <dependencies>
 17                 <dependency>
 18                         <groupId>junit</groupId>
 19                         <artifactId>junit</artifactId>
 20                 </dependency>
 21         </dependencies>

 
== Managed schema

* Getting duplicate DDL files in the target/test-classes tree because of the mis-configuration of the testResources element in the pom.

Looks like everything is being hand-authored with no auto-generated files. How did you sanity check your mappings. If you generated at one time -- why did you remove it (even if it was generating only a sanity check)
 find . -name *.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/tuningadd.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/create.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/delete.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/tuningdrop.ddl
./smarple1Dmv/smarple1DmvImpl/target/test-classes/ddl/drop.ddl

./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/tuningadd.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/create.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/delete.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/tuningdrop.ddl
./smarple1Dmv/smarple1DmvImpl/target/classes/ddl/drop.ddl

./smarple1Dmv/smarple1DmvImpl/src/main/resources/ddl/tuningadd.ddl
./smarple1Dmv/smarple1DmvImpl/src/main/resources/ddl/create.ddl
./smarple1Dmv/smarple1DmvImpl/src/main/resources/ddl/delete.ddl
./smarple1Dmv/smarple1DmvImpl/src/main/resources/ddl/tuningdrop.ddl
./smarple1Dmv/smarple1DmvImpl/src/main/resources/ddl/drop.ddl

./smarple1Mayberry/smarple1MayberryImpl/target/test-classes/ddl/create.ddl
./smarple1Mayberry/smarple1MayberryImpl/target/test-classes/ddl/delete.ddl
./smarple1Mayberry/smarple1MayberryImpl/target/test-classes/ddl/drop.ddl

./smarple1Mayberry/smarple1MayberryImpl/target/classes/ddl/create.ddl
./smarple1Mayberry/smarple1MayberryImpl/target/classes/ddl/delete.ddl
./smarple1Mayberry/smarple1MayberryImpl/target/classes/ddl/drop.ddl

./smarple1Mayberry/smarple1MayberryImpl/src/main/resources/ddl/create.ddl
./smarple1Mayberry/smarple1MayberryImpl/src/main/resources/ddl/delete.ddl
./smarple1Mayberry/smarple1MayberryImpl/src/main/resources/ddl/drop.ddl


* Created sample index. Note that the most common index to start with are FKs and (yes) where clauses.

1 Create index smarple1dmvLASTFIRSTNAME on smarple1dmv_person (LAST_NAME, FIRST_NAME);
  
  
* (-1) Column sizes are decently constrained but everything in your module is optional. Even the tag# and VIN# are not required for a VehicleRegistration. It is highly recommeneded that you have a well-constrained schema to 
help keep your data model consistent in the face of the different data accesses that will occur over an enterprise application.

 52 CREATE TABLE smarple1dmv_vehicle_registration (
 53         ID BIGINT generated by default as identity (start with 1) not null,
 54         TAG_NO VARCHAR(25),
 55         VIN VARCHAR(50),
 56         MAKE VARCHAR(50),
 57         MODEL VARCHAR(50),
 58         COLOR VARCHAR(20),
 59         YEAR SMALLINT,
 60         EXPIRATION DATE,
 61 
 62         PRIMARY KEY(ID)
 63 );

* Even the FKs of your link table have optional values. DBs (Oracle) are less efficient when faced will nullable FKs or other columns that they are required to search over. 
 65 CREATE TABLE smarple1dmv_vehicle_owner_link (
 66         PERSON_ID BIGINT,
 67         VEHICLE_ID BIGINT,
 68         
 69         FOREIGN KEY (PERSON_ID) REFERENCES smarple1dmv_person(ID),
 70         FOREIGN KEY (VEHICLE_ID) REFERENCES smarple1dmv_vehicle_registration(ID)
 71 );
 
  
== Business objects

* To make some of this required, add @NotNull (validation API), @Basic(optional=false) and @Column(nullable=false) to help constrain
 12 @Entity
 13 @Table(name = "smarple1dmv_location")
 14 public class Location {
 28 
 29         @Column(name = "STATE_CODE")
 30         @Enumerated(EnumType.ORDINAL)
 31         private STATE state;

* Good use of @MapsId to have the auto-generated Person.PK automatically copied into the PhysicalDetails.PK column to form a primary key join at the database level. 
 
 19 @Entity 
 20 @Table(name = "smarple1dmv_physical_details")
 21 public class PhysicalDetails implements Serializable {
 25         @Id
 26         @Column(name="PERSON_ID")
 27         private long personId;
 28         
 29         @OneToOne(optional=false,fetch=FetchType.EAGER)
 30         @MapsId
 31         private Person person;

That cuts down on an unnecessary column in the PhysicalDetails table but requires knowledge of the Person's generated PK prior to DB insert. The difference between this and a @PrimaryKeyJoinColumn
is the PD.personId would had to been known in the application prior to calling persist(). With @MapsId, you can have the provider take care of that during the persist() of an object tree with person->physicaldetails
already linked at the object level.

  1 CREATE TABLE smarple1dmv_person (
  2     ID BIGINT generated by default as identity (start with 1) not null,
  8     PRIMARY KEY(ID)
  9 );
 10 
 11 CREATE TABLE smarple1dmv_physical_details (
 12     PERSON_ID BIGINT not null PRIMARY KEY,
 20     FOREIGN KEY (PERSON_ID) REFERENCES smarple1dmv_person(ID)
 21 );
 
* I agree with the fetch=EAGER of the person for physical details. 
 19 @Entity
 21 public class PhysicalDetails implements Serializable {
 29         @OneToOne(optional=false,fetch=FetchType.EAGER)
 31         private Person person;
 32 
 
It would have been worth some discussion as to why you made person.physicalDetails @Transient -- rather than a fetch=LAZY on a @OneToOne. This can make access to just the person faster since the provider does not need to 
check for a matching PhysicalDetails (to decide early whether to fill in a null or provide a lazy-load reference) but will add more work to the DAO when trying to form a join fetch to automatically have the data loaded
when needed.
 22 @Entity 
 24 public class Person {
 56         @Transient
 57         private PhysicalDetails physicalDetails;
 

 
* Good definition of a bi-directional relationship
 22 @Entity 
 24 public class Person {
 44         @ManyToMany(cascade={CascadeType.PERSIST}, fetch=FetchType.LAZY)
 45         @JoinTable(name="smarple1dmv_vehicle_owner_link",
 46                 joinColumns=@JoinColumn(name="PERSON_ID"),
 47                 inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))
 48         private Set<VehicleRegistration> registrations;
 
 15 @Entity
 16 @Table(name = "smarple1dmv_vehicle_registration")
 17 public class VehicleRegistration {
 44         @ManyToMany(mappedBy="registrations")
 45         private Set<Person> owners;


* Your optional residence.location_id DB schema is not consistent with your Residence.location optional=false declaration.  
 18 @Entity 
 19 @Table(name = "smarple1dmv_residence")
 20 public class Residence {
 26         @OneToOne(/*>>>>*/ optional=false /*<<<<*/ ,fetch=FetchType.LAZY)
 27         @JoinColumn(name="LOCATION_ID", referencedColumnName="ID")
 28         private Location location;

 41 CREATE TABLE smarple1dmv_residence (
 44         LOCATION_ID BIGINT,  --<<<<<<<<< optional=false???
 49         FOREIGN KEY (LOCATION_ID) REFERENCES smarple1dmv_location(ID)
 50 );
 
 
* The @JoinColumn.name was needed to map the local Photo.FK to PERSON_ID, but the referencedColumnName was not required for a simple PK. The provider would have mapped that for you. 
 15 @Entity 
 17 public class Photo implements Serializable {
 24         @OneToOne(optional=false,fetch=FetchType.LAZY)
 26         @JoinColumn(name="PERSON_ID", referencedColumnName="PERSON_ID")
 27         private PhysicalDetails physicalDetails;

 
== Used validation API

* (-5) I could find no use of the validation API

$ find . -name *.java -exec grep -l validation {} \; | wc -l
0

== JPA DAO & mapping

* Overall -- quite thin. What is included is correct but it looks like the gaps in DAO functionality will eventually show up in the end-to-end.

* Ideally, your DAOs should implement an interface and not force the injected business logic client to know the implementation class.

 9 public class VehicleDAO {
 9 public class PersonDAO {

* Good use of paging. The end-to-end requires a bit more detailed query criteria, but paging is a good start. 
 21     public List<VehicleRegistration> getRegistrations(int index, int count)  {
 22             return (List<VehicleRegistration>)
 23                 em.createQuery("select v from VehicleRegistration v")
 24                                      .setFirstResult(index)
 25                                      .setMaxResults(count)
 26                                      .getResultList();
 27         }                            

== Ingest

* (-10) Not implemented. This is one reason why your DAOs look so thin.

== Business Logic

* (-10) Not implemented. This is one reason why your DAOs look so thin.

== End to End test 

* (-8) Mostly not implemented. Some specific comments below.

* Your call to cleanup() requires the database schema to have been pre-populated prior to step #1 of the end-to-end
	@Before
	public void setUp() throws Exception {
		log.debug("Creating entity manager");
		em = emf.createEntityManager();
		cleanup(); 
	}

End-to-end works with DB directly and bypasses business logic and DAO

	private void addPoi() {
		POI poi1 = new POI();
		poi1.setFirstName("Chucky");
		em.persist(poi1);
	}


* Why does a method begin() the transaction and leave it open?
	private void addActivity() {
		POI poi2 = new POI();
		poi2.setFirstName("Rick");
		em.persist(poi2);
		em.getTransaction().begin();
		em.flush();
	}