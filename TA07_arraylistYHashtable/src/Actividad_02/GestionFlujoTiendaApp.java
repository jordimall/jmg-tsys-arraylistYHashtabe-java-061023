package Actividad_02;

import java.util.ArrayList;
import java.util.Scanner;

public class GestionFlujoTiendaApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String[]> cesta_compra = new ArrayList<String[]>();
		double total_pagar = 0;
		final double IVA = 0.21;

		menu(cesta_compra, sc);

		total_pagar = ResumenCompra(cesta_compra, IVA);
		PagarCompra(total_pagar, sc);

	}

	public static void menu(ArrayList<String[]> cesta_compra, Scanner sc) {
		boolean condition = false;
		String opcion = "";
		System.out.println("Escriba el producto a añadir");
		añadirProducto(cesta_compra, sc.nextLine(), sc);
		do {
			System.out.println("Deseas añadir otro producto (s/n)");
			opcion = sc.nextLine();
			System.out.println(opcion.toLowerCase());
			switch (opcion.toLowerCase()) {
			case "s":
				System.out.println("Escriba el producto a añadir");
				añadirProducto(cesta_compra, sc.nextLine(), sc);
				break;
			case "n":
				condition = true;
				break;
			default:
				System.out.println("Debes escribir s o n");
				break;
			}
		} while (!condition);

	}

	public static void añadirProducto(ArrayList<String[]> cesta_compra, String producto, Scanner sc) {
		String cantidad = "";
		String array[] = new String[2];
		if (comprobarNombreProducto(producto, sc)) {
			do {
				System.out.println("escribe la cantidad");
				cantidad = sc.nextLine();
			} while (!comprovarEntradaTextoEsNumero(cantidad));
		}
		int i = ComprovarCesta(cesta_compra, producto);
		if (i == -1) {
			array[0] = producto;
			array[1] = cantidad;
			cesta_compra.add(array);
		} else {
			cesta_compra.get(i)[1] = AumentarCantidad(cantidad, cesta_compra.get(i)[1]);
		}

	}

	public static boolean comprobarNombreProducto(String producto, Scanner sc) {
		while (producto.isEmpty()) {
			System.out.println("Escriba el producto a añadir");
			producto = sc.nextLine();
		}

		return true;
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

	public static double ResumenCompra(ArrayList<String[]> cesta_compra, double IVA) {
		int i = 0;
		int cantidad = 0;
		double precio = 0;
		double precio_iva = 0;
		double total = 0;
		System.out.println("El IVA aplicado es de: " + (IVA * 100) + "%");
		while (i < cesta_compra.size()) {
			cantidad = Integer.parseInt(cesta_compra.get(i)[1]);
			precio = Math.round((Math.random() * 50 + 5) * 100) / 100;
			precio *= cantidad;
			precio_iva = precio + (precio * IVA);
			total += precio_iva;
			System.out.println("Articulo: " + cesta_compra.get(i)[0] + "; cantidad: " + cantidad + "; precio sin IVA: "
					+ precio + "; precio con IVA: " + precio_iva);
			i++;
		}

		System.out.println("total a pagar: " + total);

		return total;
	}

	public static void PagarCompra(double total_pagar, Scanner sc) {
		double cantidad_pagada = 0;
		double devolver = 0;
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
	}

}
