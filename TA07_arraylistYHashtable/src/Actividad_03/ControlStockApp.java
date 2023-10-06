package Actividad_03;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Scanner;

public class ControlStockApp {

	public static void main(String[] args) {
		Hashtable<String, Double> producto_iva = new Hashtable<String, Double>();
		Hashtable<String, Double> producto_precio = new Hashtable<String, Double>();
		Hashtable<String, Integer> producto_cantidad = new Hashtable<String, Integer>();
		String productos[] = new String[10];

		GenerarArray(productos);
		rellenarHashtableIVA(productos, producto_iva);
		rellenarHashtablePrecio(productos, producto_precio);
		rellenarHashtableCantidad(productos, producto_cantidad);
		Menu(producto_iva, producto_precio, producto_cantidad);

	}

	public static void Menu(Hashtable<String, Double> producto_iva, Hashtable<String, Double> producto_precio,
			Hashtable<String, Integer> producto_cantidad) {
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
				MostrarProducto(producto_iva, producto_precio, producto_cantidad, sc);
				break;
			case "4":
				MostrarTodosLosProductos(producto_iva, producto_precio, producto_cantidad);
				break;
			case "5":
				MostrarMenu();
				break;
			case "6":
				System.out.println("Hasta la proxima");
				condition = !condition;
				break;
			default:
				System.out.println("opciópn no valida");
				break;
			}
			MostrarMenu();
		}
		sc.close();
	}

	public static void MostrarMenu() {
		System.out.println("1) Añadir articulo");
		System.out.println("2) Modificar articulo");
		System.out.println("3) consultar información de un producto");
		System.out.println("4) Listar todos los productos");
		System.out.println("5) Mostrar opciones");
		System.out.println("6) Salir del programa");
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
