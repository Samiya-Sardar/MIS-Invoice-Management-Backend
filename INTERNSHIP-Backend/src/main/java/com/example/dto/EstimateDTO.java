package com.example.dto;

import java.time.LocalDate;

public class EstimateDTO {
	private Long estimateId;
    private Long chainId;
    private String groupName;
    private String brandName;
    private String zoneName;
    private String service;
    private Integer quantity;
    private Float costPerUnit;
    private Float totalCost;
    private LocalDate deliveryDate;
    private String deliveryDetails;
    public EstimateDTO() {}
	public EstimateDTO(Long estimateId, Long chainId, String groupName, String brandName, String zoneName,
			String service, Integer quantity, Float costPerUnit, Float totalCost, LocalDate deliveryDate,
			String deliveryDetails) {
		super();
		this.estimateId = estimateId;
		this.chainId = chainId;
		this.groupName = groupName;
		this.brandName = brandName;
		this.zoneName = zoneName;
		this.service = service;
		this.quantity = quantity;
		this.costPerUnit = costPerUnit;
		this.totalCost = totalCost;
		this.deliveryDate = deliveryDate;
		this.deliveryDetails = deliveryDetails;
	}
	public Long getEstimateId() {
		return estimateId;
	}
	public void setEstimateId(Long estimateId) {
		this.estimateId = estimateId;
	}
	public Long getChainId() {
		return chainId;
	}
	public void setChainId(Long chainId) {
		this.chainId = chainId;
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
    

}
