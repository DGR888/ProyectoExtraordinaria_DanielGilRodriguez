package com.ejemplo.E02RestEjemplo;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ejemplo.E02RestEjemplo.Entities.Product;
import com.ejemplo.E02RestEjemplo.Models.ProductosModel;



public class AppProductos 
{
	public static void main(String[] args) {

        try {
            Product producto;
            ProductosModel losProductos = new ProductosModel();  
            //Creamos un objeto de ProductosModel

            System.out.println("Conexion a la BBDD con éxito");

//            Esto es el read de productos {
            producto = losProductos.read(5);  //Leemos el producto con id = 5
            if(producto != null)  //Si el producto es distinto de null
                System.out.println(producto.toString());  //Convertimos el producto a String
//            }

//            Esto es el insert de los pedidos {
            Integer id = losProductos.insert(producto);  //Usamos la funcion insert para insertar producto
            System.out.println("Acabo de insertar el pedido: "+ id);
//            }

//            Esto es el update de los productos {
            producto = losProductos.read(id);  //Leemos el producto mediante su id
             //Acutalizamos el valor de payment_type a hola

            if(losProductos.update(producto)) {
                System.out.println("producto actualizado correctamente, veamos si es verdad: ");
                producto = losProductos.read(id);
                System.out.println(producto.toString());
            }
            else
                System.err.println("No he podido leer el producto");
//            }

//            Esto es el delete de los pedidos {
//           boolean idprodcuto = losProductos.delete(86);
//          System.out.println("Acabo de borrar un producto");



            System.out.println("Ahora vamos a leer el cliente");

            ArrayList<Product> productos = losProductos.lista("id>40", 10, 2,"ORDER list_price");
            listaPedidos(productos);

        } catch(SQLException e) {
            System.err.println("No se ha podido conectar a la BBDD " + e.getMessage());
            System.exit(1);
        }
        
       
    }
	
	 static void listaPedidos(ArrayList<Product> productos) {
         if(productos == null) {
             System.out.println("No hay elementos en la lista");
             return;
         }
  
         for(Product producto : productos) {
             System.out.println(producto.toString());
         }
     } 

}
