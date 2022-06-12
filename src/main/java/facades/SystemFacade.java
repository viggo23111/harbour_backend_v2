package facades;

import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class SystemFacade {


    private static SystemFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private SystemFacade() {}

    public static SystemFacade getSystemFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SystemFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public Owner createOwner(Owner owner){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return owner;
    }
    public BoatDTO createBoat(BoatDTO boatDTO){
        Boat boat = new Boat(boatDTO.getBrand(), boatDTO.getMake(), boatDTO.getName(), boatDTO.getImage());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatDTO(boat);
    }

    public Harbour createHarbour(Harbour harbour){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(harbour);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return harbour;
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        SystemFacade fe = getSystemFacadeExample(emf);
    }

    public List<OwnerDTO> getAllOwners(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
            List<Owner> owners = query.getResultList();
            return OwnerDTO.getDtos(owners);
        } finally {
            em.close();
        }
    }

    public List<BoatDTO> getAllBoats(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
            List<Boat> boats = query.getResultList();
            return BoatDTO.getDtos(boats);
        } finally {
            em.close();
        }
    }

    public List<HarbourDTO> getAllHarbours(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h", Harbour.class);
            List<Harbour> harbours = query.getResultList();
            return HarbourDTO.getDtos(harbours);
        } finally {
            em.close();
        }
    }



    public List<BoatDTO> getAllBoatsByHarbourID(int harbourID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbour.id=:harbourID ", Boat.class);
            query.setParameter("harbourID", harbourID);
            List<Boat> boats = query.getResultList();
            if(boats.size()==0) {
                System.out.println("Provided harbour id not found!");
               // throw new NotFoundException("Provided Zipcode not found");
            }
            return BoatDTO.getDtos(boats);
        }finally {
            em.close();
        }
    }

    public List<OwnerDTO> getAllOwnersByBoatID(int boatID) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o join Boat b where b=o.boats and b.id =:boatID", Owner.class);
            query.setParameter("boatID", boatID);
            List<Owner> owners = query.getResultList();
            if(owners.size()==0) {
                System.out.println("Provided harbour id not found!");
                // throw new NotFoundException("Provided Zipcode not found");
            }
            return OwnerDTO.getDtos(owners);
        }finally {
            em.close();
        }
    }

    public BoatDTO connectBoatToHarbour(int boatID, int harbourID) {
        EntityManager em = emf.createEntityManager();
        try{
            Boat boat = em.find(Boat.class,boatID);
            if(boat == null){
                System.out.println("Boat not found");
            }
            Harbour harbour = em.find(Harbour.class,harbourID);
            if(harbour == null){
                System.out.println("Harbour not found");
            }
            boat.addHarbour(harbour);
            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);

        } finally {
            em.close();
        }
    }

    public BoatDTO addOwnerToBoat(int boatID, int ownerID) {
        EntityManager em = emf.createEntityManager();
        try{
            Boat boat = em.find(Boat.class,boatID);
            if(boat == null){
                System.out.println("Boat not found");
            }
            Owner owner = em.find(Owner.class,ownerID);
            if(owner == null){
                System.out.println("Owner not found");
            }
            boat.addOwner(owner);
            BoatDTO updated = new BoatDTO(boat);
            em.getTransaction().begin();
            em.merge(boat);
            em.merge(owner);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    public BoatDTO removeOwnerFromBoat(int boatID, int ownerID) {
        EntityManager em = emf.createEntityManager();

        try{
            Boat boat = em.find(Boat.class,boatID);
            if(boat == null){
                System.out.println("Boat not found");
            }
            System.out.println("Boat id: " + boat.getId());
            Owner owner = em.find(Owner.class,ownerID);
            System.out.println("Owner name: " +owner.getName());
            if(owner == null){
                System.out.println("Owner not found");
            }
            owner.removeBoat(boat);
            BoatDTO updated = new BoatDTO(boat);
            em.getTransaction().begin();
            em.merge(boat);
            em.merge(owner);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    public void deleteBoat(int boatID) {
        EntityManager em = emf.createEntityManager();
        try {
            Boat boat = em.find(Boat.class, boatID);
            if(boat == null){
                System.out.println("Boat with provided id was not found");
            }
            em.getTransaction().begin();
            em.remove(boat);
            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

}
