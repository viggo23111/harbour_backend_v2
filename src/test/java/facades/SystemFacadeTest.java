package facades;

import dtos.BoatDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
public class SystemFacadeTest {

    private static EntityManagerFactory emf;
    private static SystemFacade facade;
    Owner owner1;
    Owner owner2;
    Harbour harbour1;
    Harbour harbour2;
    Boat boat1;
    Boat boat2;
    Boat boat3;
    Boat boat4;

    public SystemFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = SystemFacade.getSystemFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        owner1 = new Owner("testOwner1","testAddress1","testPhone1");
        owner2 = new Owner("testOwner2","testAddress2","testPhone2");
        boat1 = new Boat("boat1","boat1","boat1","boat1");
        boat2 = new Boat("boat2","boat2","boat2","boat2");
        boat3 = new Boat("boat3","boat3","boat3","boat3");
        boat4 = new Boat("boat4","boat4","boat4","boat4");
        harbour1 = new Harbour("harbour1","harbour1","harbour1");
        harbour2 = new Harbour("harbour2","harbour2","harbour2");

        owner1.addBoat(boat1);
        owner1.addBoat(boat2);
        owner2.addBoat(boat3);
        owner2.addBoat(boat1);

        harbour1.addBoat(boat1);
        harbour2.addBoat(boat2);
        harbour2.addBoat(boat3);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();

            em.persist(owner1);
            em.persist(owner2);
            em.persist(boat1);
            em.persist(boat2);
            em.persist(boat3);
            em.persist(boat4);
            em.persist(harbour1);
            em.persist(harbour2);

            em.getTransaction().commit();

            // facade.addOwnerToBoat(boat4.getId(),owner1.getId());
            // facade.addOwnerToBoat(boat4.getId(),owner2.getId());

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void getAllOwnersTest(){
        System.out.println("Get all owners test!");
        assertEquals(2,facade.getAllOwners().size());
    }

    @Test
    public void getAllBoatsByHarbourIDTest(){
        System.out.println("Get all Boats by harbour id test!");
        assertEquals(1,facade.getAllBoatsByHarbourID(harbour1.getId()).size());
    }

    @Test
    public void getAllOwnersByBoatIDTest(){
        System.out.println("Get all owners by boat id test!");
        assertEquals(1,facade.getAllOwnersByBoatID(boat3.getId()).size());
    }


    @Test
    public void createBoatTest() {
        System.out.println("Create boat test!");
        Boat boat = new Boat("Testboat","test","test","test");
        BoatDTO testBoat = new BoatDTO(boat);
        facade.createBoat(testBoat);
        assertEquals(5,facade.getAllBoats().size());

    }

    @Test
    public void connectBoatToHarbourTest() {
        System.out.println("connect boat test!");
        facade.connectBoatToHarbour(boat1.getId(),harbour2.getId());
        assertEquals(3,facade.getAllBoatsByHarbourID(harbour2.getId()).size());
    }

    @Test
    public void addOwnerToBoatTest() {
        System.out.println("add owner to boat test!");
        facade.addOwnerToBoat(boat4.getId(),owner1.getId());
        facade.addOwnerToBoat(boat4.getId(),owner2.getId());
        assertEquals(2,facade.getAllOwnersByBoatID(boat4.getId()).size());
    }

    @Test
    public void removeOwnerFromBoatTest() {
        System.out.println("remove owner to boat test!");
        facade.removeOwnerFromBoat(boat1.getId(), owner1.getId());
        assertEquals(1,facade.getAllOwnersByBoatID(boat1.getId()).size());
    }

    @Test
    public void deleteBoatTest() {
        System.out.println("delete boat test!");
        facade.deleteBoat(boat1.getId());
        facade.deleteBoat(boat2.getId());
        facade.deleteBoat(boat3.getId());

        assertEquals(1,facade.getAllBoats().size());
    }




}
