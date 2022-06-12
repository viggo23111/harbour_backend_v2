package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test123");
    User admin = new User("admin", "test123");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.setRole(userRole);
    admin.setRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");

    Owner owner1;
    Owner owner2;
    Harbour harbour1;
    Harbour harbour2;
    Boat boat1;
    Boat boat2;
    Boat boat3;
    Boat boat4;
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

}
