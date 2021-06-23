package com.ejemplo.E02RestEjemplo.Entities;


import java.math.BigDecimal;
import java.sql.Blob;

import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"id", 
    		     "reorder_level", 
    		     "target_level", 
    		     "discontinued", 
                     "minimum_reorder_quantity", 
                     "supplier_ids", 
                     "product_code", 
                     "product_name", 
                     "description", 
                     "quantity_per_unit", 
                     "category", 
                     "standard_cost", 
                     "list_price"})

public class Product {
	private Integer id;
	private Integer reorder_level;
	private Integer target_level;
	private Boolean discontinued;
	private Integer minimum_reorder_quantity;
	private String supplier_ids;
	private String product_code;
	private String product_name;
	private String description;
	private String quantity_per_unit;
	private String category;
	private BigDecimal standard_cost;
	private BigDecimal list_price;
	private Blob attachment;
	
	public Product() {

	}

	public Product(
			Integer id, 
			Integer reorder_level, 
			Integer target_level, 
			Boolean discontinued,
			Integer minimum_reorder_quantity, 
			String supplier_ids, 
			String product_code, 
			String product_name,
			String description, 
			String quantity_per_unit, 
			String category, 
			BigDecimal standard_cost,
			BigDecimal list_price, 
			Blob attachment) {

		this.id = id;
		this.reorder_level = reorder_level;
		this.target_level = target_level;
		this.discontinued = discontinued;
		this.minimum_reorder_quantity = minimum_reorder_quantity;
		this.supplier_ids = supplier_ids;
		this.product_code = product_code;
		this.product_name = product_name;
		this.description = description;
		this.quantity_per_unit = quantity_per_unit;
		this.category = category;
		this.standard_cost = standard_cost;
		this.list_price = list_price;
		this.attachment = attachment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReorder_level() {
		return reorder_level;
	}

	public void setReorder_level(Integer reorder_level) {
		this.reorder_level = reorder_level;
	}

	public Integer getTarget_level() {
		return target_level;
	}

	public void setTarget_level(Integer target_level) {
		this.target_level = target_level;
	}

	public Boolean getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Boolean discontinued) {
		this.discontinued = discontinued;
	}

	public Integer getMinimum_reorder_quantity() {
		return minimum_reorder_quantity;
	}

	public void setMinimum_reorder_quantity(Integer minimum_reorder_quantity) {
		this.minimum_reorder_quantity = minimum_reorder_quantity;
	}

	public String getSupplier_ids() {
		return supplier_ids;
	}

	public void setSupplier_ids(String supplier_ids) {
		this.supplier_ids = supplier_ids;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}


	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity_per_unit() {
		return quantity_per_unit;
	}

	public void setQuantity_per_unit(String quantity_per_unit) {
		this.quantity_per_unit = quantity_per_unit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getStandard_cost() {
		return standard_cost;
	}

	public void setStandard_cost(BigDecimal standard_cost) {
		this.standard_cost = standard_cost;
	}

	public BigDecimal getList_price() {
		return list_price;
	}

	public void setList_price(BigDecimal list_price) {
		this.list_price = list_price;
	}

	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "Product [" + (product_code != null ? "product_code=" + product_code + ", " : "")
				+ (product_name != null ? "product_name=" + product_name + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (category != null ? "category=" + category + ", " : "") + "standard_cost=" + standard_cost + "]";
	}


}



