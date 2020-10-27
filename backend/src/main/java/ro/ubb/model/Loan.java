package ro.ubb.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@ToString
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "loans")
public class Loan {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "loan")
    private Discussion discussion;

    @Column(name = "loan_date")
    private Date loanDate;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name="id")
    private ClosedLoan closedLoan;

}
