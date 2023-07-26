package com.example.core.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "MENU")
public class Menu extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BATCH_NO")
    private String batchNo;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_MENU")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JacksonXmlProperty(localName = "MenuDetail")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Set<MenuDetail> menuDetails;


    public Menu() {
    }

    public Menu(Long id, String description, String lastApproveBy, Date lastApproveDate, String lastUpdateBy, Date lastUpdateDate, String batchNo) {
        this.id = id;
        this.description = description;
        this.lastApproveBy = lastApproveBy;
        this.lastApproveDate = lastApproveDate;
        this.lastUpdateBy = lastUpdateBy;
        this.lastUpdateDate = lastUpdateDate;
        this.batchNo = batchNo;
    }

}
