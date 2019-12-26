package musers.model.address;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import musers.model.user.Users;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public @Data class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Long streetNumber;

    @NonNull
    private String complementStreetNumber;

    @NonNull
    private String streetType;

    @NonNull
    private String street;

    @NonNull
    private String complementStreet;

    @NonNull
    private String lattitude;

    @NonNull
    private String longitude;


    // Jointures
    @ManyToMany
    @JoinTable(
            name="address_city",
            joinColumns=@JoinColumn(name="id_address", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="insee_city", referencedColumnName="insee")
    )
    @NonNull
    private List<City> cityList;

    @OneToMany
    private List<Users> usersList;







}
