package com.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estimate")
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimate_id")
    private Long estimateId;

    @ManyToOne
    @JoinColumn(name = "chain_id", referencedColumnName = "chain_id", nullable = false)
    private ChainEntity chain; // Link to the Chain entity

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "zone_name", nullable = false)
    private String zoneName;

    @Column(name = "service", nullable = false)
    private String service;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "cost_per_unit", nullable = false)
    private Float costPerUnit;

    @Column(name = "total_cost", nullable = false)
    private Float totalCost;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "delivery_details", columnDefinition = "TEXT")
    private String deliveryDetails;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "update_at", insertable = false)
    private LocalDateTime updateAt;
    public Estimate() {}
	public Estimate(Long estimateId, ChainEntity chain, String groupName, String brandName, String zoneName,
			String service, Integer quantity, Float costPerUnit, Float totalCost, LocalDate deliveryDate,
			String deliveryDetails, LocalDateTime createdAt, LocalDateTime updateAt) {
		super();
		this.estimateId = estimateId;
		this.chain = chain;
		this.groupName = groupName;
		this.brandName = brandName;
		this.zoneName = zoneName;
		this.service = service;
		this.quantity = quantity;
		this.costPerUnit = costPerUnit;
		this.totalCost = totalCost;
		this.deliveryDate = deliveryDate;
		this.deliveryDetails = deliveryDetails;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}
	public Long getEstimateId() {
		return estimateId;
	}
	public void setEstimateId(Long estimateId) {
		this.estimateId = estimateId;
	}
	public ChainEntity getChain() {
		return chain;
	}
	public void setChain(ChainEntity chain) {
		this.chain = chain;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Float getCostPerUnit() {
		return costPerUnit;
	}
	public void setCostPerUnit(Float costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryDetails() {
		return deliveryDetails;
	}
	public void setDeliveryDetails(String deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
    




}
