package com.example.springcore.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "MENU")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LAST_APPROVE_BY")
    private String lastApproveBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "LAST_APPROVE_DATE")
    private Date lastApproveDate;

    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastApproveBy() {
        return lastApproveBy;
    }

    public void setLastApproveBy(String lastApproveBy) {
        this.lastApproveBy = lastApproveBy;
    }

    public Date getLastApproveDate() {
        return lastApproveDate;
    }

    public void setLastApproveDate(Date lastApproveDate) {
        this.lastApproveDate = lastApproveDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Set<MenuDetail> getMenuDetails() {
        return menuDetails;
    }

    public void setMenuDetails(Set<MenuDetail> menuDetails) {
        this.menuDetails = menuDetails;
    }
}
