package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Boat.deleteAllRows", query = "DELETE from Boat")
@Table(name = "boat")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "make")
    private String make;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "boats")
    private Set<Owner> owners = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="harbour_id")
    private Harbour harbour;

    public Boat() {
    }

    public Boat(String brand, String make, String name, String image) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void addOwner(Owner owner) {
        this.owners.add(owner);
        if(!owner.getBoats().contains(this)){
            owner.addBoat(this);
        }
    }


    public Harbour getHarbour() {
        return harbour;
    }

    public void addHarbour(Harbour harbour) {
        this.harbour = harbour;
        if(!harbour.getBoats().contains(this)){
            harbour.addBoat(this);
        }
    }

    @Override
    public String toString() {
        return "Boat{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", harbour=" + harbour +
                '}';
    }
}