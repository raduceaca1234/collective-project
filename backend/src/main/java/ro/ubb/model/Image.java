package ro.ubb.model;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "images")
public class Image {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Lob
    @Column(name = "image_bytes")
    private Byte[] imageBytes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
}
