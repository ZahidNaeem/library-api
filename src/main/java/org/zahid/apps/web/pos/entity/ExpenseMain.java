package org.zahid.apps.web.pos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the XXIM_EXPENSE_MAIN database table.
 */
@Entity
@Table(name = "XXIM_EXPENSE_MAIN")
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@JsonIdentityInfo(scope = ExpenseMain.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "expMainId")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQuery(name = "ExpenseMain.findAll", query = "SELECT e FROM ExpenseMain e")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseMain extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*@SequenceGenerator(name = "XXIM_EXPENSE_MAIN_EXPMAINID_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "XXIM_EXPENSE_MAIN_EXPMAINID_GENERATOR")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EXP_MAIN_ID")
    private Long expMainId;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXP_DATE")
    private Date expDate;

    private String remarks;

    // bi-directional many-to-one association to ExpenseDtl
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "expenseMain")
    private List<ExpenseDtl> expenseDtls;
}