package Actividad_04;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Scanner;

public class GestionVentasStockApp {

	public static void main(String[] args) {
		ArrayList<String[]> cesta_compra = new ArrayList<String[]>();
		Hashtable<String, Double> producto_iva = new Hashtable<String, Double>();
		Hashtable<String, Double> producto_precio = new Hashtable<String, Double>();
		Hashtable<String, Integer> producto_cantidad = new Hashtable<String, Integer>();
		String productos[] = new String[10];

		GenerarArray(productos);
		rellenarHashtableIVA(productos, producto_iva);
		rellenarHashtablePrecio(productos, producto_precio);
		rellenarHashtableCantidad(productos, producto_cantidad);
		Menu(producto_iva, producto_precio, producto_cantidad, cesta_compra);

	}

	public static void Menu(Hashtable<String, Double> producto_iva, Hashtable<String, Double> producto_precio,
			Hashtable<String, Integer> producto_cantidad, ArrayList<String[]> cesta_compra) {
		boolean condition = false;
		Scanner sc = new Scanner(System.in);
		String entrada_texto = "";
		MostrarMenu();
		while (!condition) {
			System.out.println("escoje una opción");
			entrada_texto = sc.nextLine();
			switch (entrada_texto) {
			case "1":
				AñadirArticulo(producto_iva, producto_precio, producto_cantidad, sc);
				break;
			case "2":
				ModificarProducto(producto_iva, producto_precio, producto_cantidad, sc);
				break;
			case "3":
				CestaCompra(cesta_compra, producto_iva, producto_precio, producto_cantidad, sc);
				break;
			case "4":
				MostrarProducto(producto_iva, producto_precio, producto_cantidad, sc);
				break;
			case "5":
				MostrarTodosLosProductos(producto_iva, producto_precio, producto_cantidad);
				break;
			case "6":
				MostrarMenu();
				break;
			case "7":
				System.out.println("Hasta la proxima");
				condition = !condition;
				break;
			default:
				System.out.println("opciópn no valida");
				break;
			}
		}
		sc.close();
	}

	public static void MostrarMenu() {
		System.out.println("1) Añadir articulo");
		System.out.println("2) Modificar articulo");
		System.out.println("3) Añadir a la cesta");
		System.out.println("4) consultar información de un producto");
		System.out.println("5) Listar todos los productos");
		System.out.println("6) Mostrar opciones");
		System.out.println("7) Salir del programa");
	}

	public static void CestaCompra(ArrayList<String[]> cesta_compra, Hashtable<String, Double> producto_iva,
			Hashtable<String, Double> producto_precio, Hashtable<String, Integer> producto_cantidad, Scanner sc) {
		boolean condition = false;
		String opcion = "";
		double total_pagar = 0;

		ListarNombresProductos(producto_cantidad);
		System.out.println("Escriba el producto a añadir");
		opcion = sc.nextLine();
		añadirProducto(cesta_compra, producto_cantidad, opcion, sc);
		do {
			System.out.println("Deseas añadir otro producto (s/n)");
			opcion = sc.nextLine();
			System.out.println(opcion.toLowerCase());
			switch (opcion.toLowerCase()) {
			case "s":
				ListarNombresProductos(producto_cantidad);
				System.out.println("Escriba el producto a añadir");
				opcion = sc.nextLine();
				añadirProducto(cesta_compra, producto_cantidad, opcion, sc);
				break;
			case "n":
				condition = true;
				break;
			default:
				System.out.println("Debes escribir s o n");
				break;
			}
		} while (!condition);

		total_pagar = ResumenCompra(cesta_compra, producto_iva, producto_precio);
		PagarCompra(total_pagar, producto_cantidad, cesta_compra, sc);
	}

	public static void AñadirArticulo(Hashtable<String, Double> producto_iva, Hashtable<String, Double> producto_precio,
			Hashtable<String, Integer> producto_cantidad, Scanner sc) {
		String entrada_texto = "";
		boolean condition = false;
		String nombre_producto = "";

		System.out.println("Escribe el nombre del articulo");
		entrada_texto = sc.nextLine();
		condition = entrada_texto.isEmpty();
		while (condition) {
			System.out.println("El nombre del articulo no puede estar en blanco");
			entrada_texto = sc.nextLine();
			condition = entrada_texto.isEmpty();
		}

		nombre_producto = entrada_texto;

		AñadirIVA(producto_iva, nombre_producto, sc);
		AñadirPrecio(producto_precio, nombre_producto, sc);
		AñadirCantidad(producto_cantidad, nombre_producto, sc);
	}

	public static void ModificarProducto(Hashtable<String, Double> producto_iva,
			Hashtable<String, Double> producto_precio, Hashtable<String, Integer> producto_cantidad, Scanner sc) {
		String entrada_texto = "";
		String nombre_producto = "";
		boolean condition_producto = false;
		boolean condition_opcion = true;
		ListarNombresProductos(producto_cantidad);
		while (!condition_producto) {
			System.out.println("Escribe el nombre del producto que quieras modificar");
			entrada_texto = sc.nextLine();
			condition_producto = ExisteProducto(producto_cantidad, entrada_texto);
		}

		nombre_producto = entrada_texto;

		while (condition_opcion) {
			System.out.println("Escoje una opción");
			System.out.println("1) modificar IVA");
			System.out.println("2) modificar precio");
			System.out.println("3) modificar cantidad");
			System.out.println("4) salir");
			System.out.println("escoje una opción");
			entrada_texto = sc.nextLine();
			switch (entrada_texto) {
			case "1":
				AñadirIVA(producto_iva, nombre_producto, sc);
				break;
			case "2":
				AñadirPrecio(producto_precio, nombre_producto, sc);
				break;
			case "3":
				ModificarCantidad(producto_cantidad, nombre_producto, sc);
				break;
			case "4":
				condition_opcion = !condition_opcion;
				break;
			default:
				System.out.println("No has introducido una opción valida");
				break;
			}
		}

	}

	public static void MostrarProducto(Hashtable<String, Double> producto_iva,
			Hashtable<String, Double> producto_precio, Hashtable<String, Integer> producto_cantidad, Scanner sc) {
		String entrada_texto = "";
		String iva = "";
		int cantidad = 0;
		double precio = 0;
		boolean condition = false;
		ListarNombresProductos(producto_cantidad);
		System.out.println("Escribe el nombre del producto");
		entrada_texto = sc.nextLine();
		condition = ExisteProducto(producto_cantidad, entrada_texto);
		while (!condition) {
			System.out.println("Escribe el nombre del producto");
			entrada_texto = sc.nextLine();
			condition = ExisteProducto(producto_cantidad, entrada_texto);
		}

		iva = RecuperarIVA(producto_iva, entrada_texto);
		precio = RecuperarPrecio(producto_precio, entrada_texto);
		cantidad = RecuperarCantidad(producto_cantidad, entrada_texto);

		System.out.println("Del producto " + entrada_texto + ", cantidad: " + cantidad + " Ha un precio de: " + precio
				+ " se le a de aplicar el " + iva + "% de iva");

	}

	public static void MostrarTodosLosProductos(Hashtable<String, Double> producto_iva,
			Hashtable<String, Double> producto_precio, Hashtable<String, Integer> producto_cantidad) {
		Enumeration<String> nombres_productos = producto_cantidad.keys();
		String nombre_producto = "";
		String iva = "";
		int cantidad = 0;
		double precio = 0;
		while (nombres_productos.hasMoreElements()) {
			nombre_producto = nombres_productos.nextElement();
			iva = RecuperarIVA(producto_iva, nombre_producto);
			precio = RecuperarPrecio(producto_precio, nombre_producto);
			cantidad = RecuperarCantidad(producto_cantidad, nombre_producto);

			System.out.println("Del producto " + nombre_producto + ", cantidad: " + cantidad + " Ha un precio de: "
					+ precio + " se le a de aplicar el " + iva + "% de iva");
		}
	}

	public static void AñadirIVA(Hashtable<String, Double> producto_iva, String nombre_producto, Scanner sc) {
		String entrada_texto = "";
		boolean condition = true;
		final double IVA4 = 0.04;
		final double IVA21 = 0.21;
		System.out.println(
				"Escoje el porcentaje de IVA que se le aplicara al producto 21 o 4 sin el simbolo del porcentaje");
		System.out.println("Si no se escoje nada o la opción no es valida se le aplicara el 21% por defecto");
		while (condition) {
			entrada_texto = sc.nextLine();
			switch (entrada_texto) {
			case "4":
				producto_iva.put(nombre_producto, IVA4);
				condition = false;
				break;
			case "21":
				producto_iva.put(nombre_producto, IVA21);
				condition = false;
				break;
			default:
				System.out.println("Opción no valida escoje netre 21 0 4");
				break;
			}
		}
	}

	public static void AñadirPrecio(Hashtable<String, Double> producto_precio, String nombre_producto, Scanner sc) {
		String entrada_texto = "";
		double precio = 0;
		boolean condition = false;
		System.out.println("Escribe el precio del producto");
		while (!condition) {
			entrada_texto = sc.nextLine();
			condition = comprovarEntradaTextoEsNumero(entrada_texto);
		}

		precio = Double.parseDouble(entrada_texto);
		producto_precio.put(nombre_producto, precio);
	}

	public static void AñadirCantidad(Hashtable<String, Integer> producto_cantidad, String nombre_producto,
			Scanner sc) {
		String entrada_texto = "";
		int cantidad = 0;
		boolean condition = false;
		System.out.println("Escribe la cantidad del producto");
		while (!condition) {
			entrada_texto = sc.nextLine();
			condition = comprovarEntradaTextoEsNumero(entrada_texto);
		}

		cantidad = Integer.parseInt(entrada_texto);
		producto_cantidad.put(nombre_producto, cantidad);
	}

	public static void ModificarCantidad(Hashtable<String, Integer> producto_cantidad, String nombre_producto,
			Scanner sc) {
		String entrada_texto = "";
		int cantidad = 0;
		int cantidad_actual = producto_cantidad.get(nombre_producto);
		boolean condition_menu = true;
		while (condition_menu) {
			System.out.println("La cantidad actual del producto " + nombre_producto + ", es de: "
					+ producto_cantidad.get(nombre_producto));
			System.out.println("1) aumentar cantidad");
			System.out.println("2) disminuir cantidad");
			System.out.println("3) salir");
			System.out.println("Escoje una opción");
			entrada_texto = sc.nextLine();
			switch (entrada_texto) {
			case "1":
				System.out.println("escribe la cantidad a aumentar");
				entrada_texto = sc.nextLine();
				while (!comprovarEntradaTextoEsNumero(entrada_texto)) {
					System.out.println("escribe la cantidad a aumentar");
					entrada_texto = sc.nextLine();
				}
				cantidad = Integer.parseInt(entrada_texto);
				producto_cantidad.replace(nombre_producto, (cantidad + cantidad_actual));
				break;
			case "2":
				System.out.println("escribe la cantidad a disminuir");
				System.out.println("Si la cantidada disminuir es superior a la cantidad actual");
				System.out.println("La cantidad sera de 0");
				entrada_texto = sc.nextLine();
				while (!comprovarEntradaTextoEsNumero(entrada_texto)) {
					entrada_texto = sc.nextLine();
				}
				cantidad = Integer.parseInt(entrada_texto);
				if (cantidad_actual < cantidad) {
					producto_cantidad.replace(nombre_producto, 0);
				} else {
					producto_cantidad.replace(nombre_producto, (cantidad_actual - cantidad));
				}
			case "3":
				condition_menu = !condition_menu;
				break;
			default:
				System.out.println("opcion no valida");
				break;
			}
			System.out.println(
					"La cantidad del producto " + nombre_producto + "es de: " + producto_cantidad.get(nombre_producto));
		}
	}

	public static String RecuperarIVA(Hashtable<String, Double> producto_iva, String nombre_producto) {
		String iva = Double.toString(producto_iva.get(nombre_producto) * 100);
		return iva;
	}

	public static double RecuperarPrecio(Hashtable<String, Double> producto_precio, String nombre_producto) {
		double precio = (producto_precio.get(nombre_producto) * 100) / 100;
		return precio;
	}

	public static int RecuperarCantidad(Hashtable<String, Integer> producto_cantidad, String nombre_producto) {
		int cantidad = producto_cantidad.get(nombre_producto);
		return cantidad;
	}

	public static void añadirProducto(ArrayList<String[]> cesta_compra, Hashtable<String, Integer> producto_cantidad,
			String producto, Scanner sc) {
		String cantidad_texto = "";
		String array[] = new String[2];
		boolean condition = false;

		while (!ExisteProducto(producto_cantidad, producto)) {
			ListarNombresProductos(producto_cantidad);
			System.out.println("Escribe uno de los siguientes productos");
			producto = sc.nextLine();
		}

		while (!condition) {
			System.out.println("escribe la cantidad");
			cantidad_texto = sc.nextLine();
			if(comprovarEntradaTextoEsNumero(cantidad_texto)){
				condition = ComprobarCantidad(producto_cantidad.get(producto), cantidad_texto);
			}
		}
		
		int i = ComprovarCesta(cesta_compra, producto);
		if (i == -1) {
			array[0] = producto;
			array[1] = cantidad_texto;
			cesta_compra.add(array);
		} else {
			cesta_compra.get(i)[1] = AumentarCantidad(cantidad_texto, cesta_compra.get(i)[1]);
		}

	}

	public static boolean ComprobarCantidad(int cantidad_stock, String cantidad_solicitada) {
		int resta_cantidades = cantidad_stock - Integer.parseInt(cantidad_solicitada);
		if (resta_cantidades < 0) {
			System.out.println("La cantidad no puede ser superior a " + cantidad_stock);
			return false;
		}
		return true;
	}

	public static boolean comprobarNombreProducto(String producto, Scanner sc) {
		while (producto.isEmpty()) {
			System.out.println("Escriba el producto a añadir");
			producto = sc.nextLine();
		}

		return true;
	}

	public static int ComprovarCesta(ArrayList<String[]> cesta_compra, String producto) {
		int i = 0;
		int posicion = -1;
		boolean condition = true;

		while (condition) {
			if (i == cesta_compra.size()) {
				condition = false;
			} else {
				if (producto.equals(cesta_compra.get(i)[0])) {
					condition = false;
					posicion = i;
				}
			}

			i++;
		}

		return posicion;
	}

	public static String AumentarCantidad(String new_cantidad, String old_cantidad) {
		int cant1 = Integer.parseInt(old_cantidad);
		int cant2 = Integer.parseInt(new_cantidad);
		String cantidad_final = Integer.toString(cant2 + cant1);
		return cantidad_final;
	}

	public static double ResumenCompra(ArrayList<String[]> cesta_compra, Hashtable<String, Double> producto_iva, Hashtable<String, Double> producto_precio) {
		int i = 0;
		int cantidad = 0;
		double precio = 0;
		double precio_iva = 0;
		double total = 0;
		while (i < cesta_compra.size()) {
			cantidad = Integer.parseInt(cesta_compra.get(i)[1]);
			precio = producto_precio.get(cesta_compra.get(i)[0]);
			precio *= cantidad;
			precio_iva = precio + (precio * producto_iva.get(cesta_compra.get(i)[0]));
			total += precio_iva;
			System.out.println("Articulo: " + cesta_compra.get(i)[0] + "; cantidad: " + cantidad + "; precio sin IVA: "
					+ precio + "; precio con IVA: " + precio_iva);
			i++;
		}

		System.out.println("total a pagar: " + total);

		return total;
	}

	public static void PagarCompra(double total_pagar, Hashtable<String, Integer> producto_cantidad, ArrayList<String[]> cesta_compra,Scanner sc) {
		double cantidad_pagada = 0;
		double devolver = 0;
		int i=0;
		String entrada_texto = "";

		System.out.println("Escriba la cantidad ");
		entrada_texto = sc.nextLine();
		comprovarEntradaTextoEsNumero(entrada_texto);

		cantidad_pagada = Double.parseDouble(entrada_texto);

		while (cantidad_pagada < total_pagar) {
			System.out.println("La cantidad ha de ser superior a " + total_pagar);
			entrada_texto = sc.nextLine();
			comprovarEntradaTextoEsNumero(entrada_texto);
			cantidad_pagada = Double.parseDouble(entrada_texto);
		}
		devolver = Math.round((cantidad_pagada - total_pagar) * 100) / 100;
		System.out.println("El cambio es de: " + devolver);
		
		while (i<cesta_compra.size()) {
			int cantidad_stock = producto_cantidad.get(cesta_compra.get(i)[0]);
			int cantidad_compra = Integer.parseInt(cesta_compra.get(i)[1]);
			int cantidad_final_stock = cantidad_stock - cantidad_compra;
			producto_cantidad.replace(cesta_compra.get(i)[0], cantidad_final_stock);
			i++;
		}
	}

	public static void ListarNombresProductos(Hashtable<String, Integer> producto_cantidad) {
		Enumeration<String> nombres_productos = producto_cantidad.keys();
		while (nombres_productos.hasMoreElements()) {
			String nombre_producto = nombres_productos.nextElement();
			System.out.println(nombre_producto);
		}
	}

	public static boolean ExisteProducto(Hashtable<String, Integer> producto_cantidad, String producto) {
		Enumeration<String> nombres_productos = producto_cantidad.keys();
		while (nombres_productos.hasMoreElements()) {
			String nombre_producto = nombres_productos.nextElement();
			if (producto.equals(nombre_producto)) {
				return true;
			}
		}
		return false;
	}

	public static boolean comprovarEntradaTextoEsNumero(String texto_a_comprobar) {
		try {
			double comprovación = Double.parseDouble(texto_a_comprobar);
			if (comprovación < 0) {
				System.out.println("El número ha de ser positivo");
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Debes escribir un número");
			return false;
		}

	}

	public static void GenerarArray(String productos[]) {
		for (int i = 0; i < productos.length; i++) {
			productos[i] = "producto" + i;

		}
	}

	public static void rellenarHashtableIVA(String productos[], Hashtable<String, Double> producto_iva) {
		int mitad = Math.round(productos.length / 2);
		final double IVA4 = 0.04;
		final double IVA21 = 0.21;
		for (int i = 0; i < productos.length; i++) {
			if (i < mitad) {
				producto_iva.put(productos[i], IVA4);
			} else {
				producto_iva.put(productos[i], IVA21);
			}
		}
	}

	public static void rellenarHashtablePrecio(String productos[], Hashtable<String, Double> producto_precio) {
		for (int i = 0; i < productos.length; i++) {
			double rand = Math.round((Math.random() * 50 + 5) * 100) / 100;
			producto_precio.put(productos[i], rand);
		}
	}

	public static void rellenarHashtableCantidad(String productos[], Hashtable<String, Integer> producto_cantidad) {
		for (int i = 0; i < productos.length; i++) {
			int rand = (int) Math.random() * 50 + 5;
			producto_cantidad.put(productos[i], rand);
		}
	}

}
