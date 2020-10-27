package ro.ubb.model;

import lombok.*;
import ro.ubb.model.enums.Category;
import ro.ubb.model.enums.Status;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@ToString
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "announcements")
public class Announcement {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "created_date")
    private Date date;

    @Column(name = "category")
    private Category category;

    @Column(name = "duration")
    private int duration;

    @Column(name = "status")
    private Status status;

    @Column(name = "price_per_day")
    private int pricePerDay;

    @ToString.Exclude
    @OneToMany(mappedBy = "announcement", orphanRemoval = true, cascade = CascadeType.MERGE)
    private Set<Image> images;


    @ToString.Exclude
    @OneToMany(mappedBy = "discussedAnnouncement", orphanRemoval = true, cascade = CascadeType.MERGE)
    private Set<Discussion> discussions;


    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.MERGE
                    },
            targetEntity = Wishlist.class)
    @JoinTable(name = "wishlist_items",
            inverseJoinColumns = @JoinColumn(name = "wishlist_id",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "announcement_id",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Wishlist> appearsOnWishlists;
}
