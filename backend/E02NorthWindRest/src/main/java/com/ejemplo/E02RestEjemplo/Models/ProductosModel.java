package com.ejemplo.E02RestEjemplo.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.ejemplo.E02RestEjemplo.DBFactory.DBFactory;
import com.ejemplo.E02RestEjemplo.Entities.Product;


public class ProductosModel 
{
	Connection conexion = null;

    public ProductosModel() throws SQLException {
	DataSource ds = DBFactory.getMySQLDataSource();
	conexion = ds.getConnection();
    }

    public Product read(Integer id) {
    Product productos = null;
	Statement sentencia = null;

	String sql = "SELECT `id`, `reorder_level`, `target_level`, `discontinued`, "
		+ "`minimum_reorder_quantity`, `supplier_ids`, `product_code` , `product_name`,"
		+ "`description`, `quantity_per_unit`, `category`, `standard_cost`, `list_price`, "
		+ "`attachments` " + "FROM products " + "WHERE id = " + id;

	try {
	    sentencia = conexion.createStatement();
	    ResultSet rs = sentencia.executeQuery(sql);
	    while (rs.next()) { 
	    	productos = new Product(
			rs.getInt("id"),
			rs.getInt("reorder_level"),
			rs.getInt("target_level"),
			rs.getBoolean("discontinued"),
			rs.getInt("minimum_reorder_quantity"),
			rs.getString("supplier_ids"),
			rs.getString("product_code"),
			rs.getString("product_name"),
			rs.getString("description"),
			rs.getString("quantity_per_unit"),
			rs.getString("category"),
			rs.getBigDecimal("standard_cost"),
			rs.getBigDecimal("list_price"),
			rs.getBlob("attachments"));
	    };
	    
	} catch (SQLException e) {
	    System.err.println("Error en read de Productos: " + e.getMessage());
	    return null;
	}

	return productos;
    }

    /**
     * 
     * @param productos
     * @return Devuelve el id del registro recien insertado
     */
    public Integer insert(Product productos) throws  SQLException {
	Integer id = null;
	PreparedStatement ps = null;
	String sql = "INSERT INTO products ( "
		+ "`reorder_level`, `target_level`, `discontinued`, "
		+ "`minimum_reorder_quantity`, `supplier_ids`, `product_code` , `product_name`,"
		+ "`description`, `quantity_per_unit`, `category`, `standard_cost`, `list_price`, `attachments`) "
		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	try {
	    ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, productos.getReorder_level());
		ps.setInt(2, productos.getTarget_level());
		ps.setBoolean(3, productos.getDiscontinued());
		ps.setInt(4, productos.getMinimum_reorder_quantity());
		ps.setString(5, productos.getSupplier_ids());
		ps.setString(6, productos.getProduct_code());
		ps.setString(7, productos.getProduct_name());
		ps.setString(8, productos.getDescription());
		ps.setString(9, productos.getQuantity_per_unit());
		ps.setString(10, productos.getCategory());
		ps.setBigDecimal(11, productos.getStandard_cost());
		ps.setBigDecimal(12, productos.getList_price());
		ps.setBlob(13, productos.getAttachment());
		
	    if (ps.executeUpdate() > 0) {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
		    id = rs.getInt(1);
		}
	    }

	} catch (SQLException e) {
	    System.err.println("Error al insertar Productos: " + e.getMessage());
	    throw e;
	}

	return id;
    }

    public Boolean delete(Integer idProducto) throws SQLException {
	Boolean resultado = false;

	PreparedStatement ps = null;
	String sql = "DELETE FROM products where id = ?";
	try {
	    ps = conexion.prepareStatement(sql);

	    ps.setInt(1, idProducto);

	    resultado = (ps.executeUpdate() > 0);

	} catch (SQLException e) {
	    System.err.println("Error al borrar Producto: " + e.getMessage());
	    throw e;
	}

	return resultado;
    }

    public Boolean update(Product productos) throws SQLException  {
	Boolean resultado = false;

	PreparedStatement ps = null;
	String sql = "UPDATE products set "
		+ "reorder_level = ?, "
		+ "target_level = ?, "
		+ "discontinued = ?, "
		+ "minimum_reorder_quantity = ?, "
		+ "supplier_ids = ?, "
		+ "product_code  = ?, "
		+ "product_name = ?, "
		+ "description = ?, "
		+ "quantity_per_unit = ?, "
		+ "category = ?, "
		+ "standard_cost = ?, "
		+ "list_price = ?, "
		+ "attachments = ? "
		+ "where id = ?";
	
	try {
	    ps = conexion.prepareStatement(sql);
	    ps.setInt(1, productos.getReorder_level());
		ps.setInt(2, productos.getTarget_level());
		ps.setBoolean(3, productos.getDiscontinued());
		ps.setInt(4, productos.getMinimum_reorder_quantity());
		ps.setString(5, productos.getSupplier_ids());
		ps.setString(6, productos.getProduct_code());
		ps.setString(7, productos.getProduct_name());
		ps.setString(8, productos.getDescription());
		ps.setString(9, productos.getQuantity_per_unit());
		ps.setString(10, productos.getCategory());
		ps.setBigDecimal(11, productos.getStandard_cost());
		ps.setBigDecimal(12, productos.getList_price());
		ps.setBlob(13, productos.getAttachment());
	    ps.setInt(14, productos.getId());

	    resultado = (ps.executeUpdate() > 0);

	} catch (SQLException e) {
	    System.err.println("Error al actualizar Cliente: " + e.getMessage());
	    throw e;
	}

	return resultado;
    }

    public ArrayList<Product> lista(String filtro, Integer limite, Integer offset, String order)

    {
	ArrayList<Product> productos = new ArrayList<Product>();
	Statement sentencia = null;

	String sql = "SELECT `id`, "
		+ "`reorder_level`, "
		+ "`target_level`, "
		+ "`discontinued`, "
		+ "`minimum_reorder_quantity`, "
		+ "`supplier_ids`, "
		+ "`product_code` , "
		+ "`product_name`,"
		+ "`description`, "
		+ "`quantity_per_unit`, "
		+ "`category`, "
		+ "`standard_cost`, "
		+ "`list_price`, "
		+ "`attachments` " 
		+ "FROM products ";

	try {
	    if (filtro != null)
		sql += " WHERE " + filtro;
	    if (order != null)
			sql += " ORDER BY " + order;
	    if (limite != null)
		sql += " LIMIT " + limite;
	    if (offset != null)
		sql += " OFFSET " + offset;
	    
	    sentencia = conexion.createStatement();
	    ResultSet rs = sentencia.executeQuery(sql);
	    while (rs.next()) { 
	    productos.add(new Product(
	    		rs.getInt("id"),
				rs.getInt("reorder_level"),
				rs.getInt("target_level"),
				rs.getBoolean("discontinued"),
				rs.getInt("minimum_reorder_quantity"),
				rs.getString("supplier_ids"),
				rs.getString("product_code"),
				rs.getString("product_name"),
				rs.getString("description"),
				rs.getString("quantity_per_unit"),
				rs.getString("category"),
				rs.getBigDecimal("standard_cost"),
				rs.getBigDecimal("list_price"),
				rs.getBlob("attachments")));
	    };
	} catch (SQLException e) {
	    System.err.println("Error en read de Productos: " + e.getMessage());
	    return null;
	}

	return productos;
    }
    
    /*public ArrayList<Product> listaDetails(Integer id, Integer limite, Integer offset, String order)

    {
	ArrayList<Product> productos = new ArrayList<Product>();
	Statement sentencia = null;

	String sql = "SELECT `id`, "
		+ "`reorder_level`, "
		+ "`target_level`, "
		+ "`discontinued`, "
		+ "`minimum_reorder_quantity`, "
		+ "`supplier_ids`, "
		+ "`product_code` , "
		+ "`product_name`,"
		+ "`description`, "
		+ "`quantity_per_unit`, "
		+ "`category`, "
		+ "`standard_cost`, "
		+ "`list_price`, "
		+ "`attachments` " 
		+ "FROM products ";

	try {
	    if (id != null)
		sql += " WHERE id =" + id;
	    if (limite != null)
		sql += " LIMIT " + limite;
	    if (offset != null)
		sql += " OFFSET " + offset;
	    if (order != null)
			sql += " ORDER BY " + order;
	    sentencia = conexion.createStatement();
	    ResultSet rs = sentencia.executeQuery(sql);
	    while (rs.next()) { 
	    productos.add(new Product(
	    		rs.getInt("id"),
				rs.getInt("reorder_level"),
				rs.getInt("target_level"),
				rs.getBoolean("discontinued"),
				rs.getInt("minimum_reorder_quantity"),
				rs.getString("supplier_ids"),
				rs.getString("product_code"),
				rs.getString("product_name"),
				rs.getString("description"),
				rs.getString("quantity_per_unit"),
				rs.getString("category"),
				rs.getBigDecimal("standard_cost"),
				rs.getBigDecimal("list_price"),
				rs.getBlob("attachments")));
	    };
	} catch (SQLException e) {
	    System.err.println("Error en read de Productos: " + e.getMessage());
	    return null;
	}

	return productos;
    }*/
}
