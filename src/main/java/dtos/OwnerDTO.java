package dtos;

import entities.Boat;
import entities.Owner;

import java.util.*;

public class OwnerDTO {
    private int id;
    private String name;
    private String address;
    private String phone;

    private Set<BoatDTO> boats = new HashSet<>();

    public OwnerDTO(Owner owner) {
        this.id=owner.getId();
        this.name= owner.getName();
        this.address= owner.getAddress();
        this.phone= owner.getPhone();

        for (Boat boat : owner.getBoats()) {
            this.boats.add(new BoatDTO(boat));
        }
    }

    public static List<OwnerDTO> getDtos(List<Owner> ownerList){
        List<OwnerDTO> ownerDTOS = new ArrayList();
        ownerList.forEach(owner->ownerDTOS.add(new OwnerDTO(owner)));
        return ownerDTOS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<BoatDTO> getBoats() {
        return boats;
    }

    public void setBoats(Set<BoatDTO> boats) {
        this.boats = boats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDTO ownerDTO = (OwnerDTO) o;
        return id == ownerDTO.id && Objects.equals(name, ownerDTO.name) && Objects.equals(address, ownerDTO.address) && Objects.equals(phone, ownerDTO.phone) && Objects.equals(boats, ownerDTO.boats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, boats);
    }
}
