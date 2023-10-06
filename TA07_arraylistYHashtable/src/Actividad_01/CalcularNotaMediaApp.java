package Actividad_01;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

public class CalcularNotaMediaApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Hashtable<String, ArrayList<Double>> notas_alumnos = new Hashtable<String, ArrayList<Double>>();
		Hashtable<String, Double> notas_media_Alumnos = new Hashtable<String, Double>();
		boolean condicion = false;

		crearAlumno(notas_alumnos);
		do {
			System.out.println("quieres introducir otro alumno? (si/no)");
			switch (sc.nextLine().toUpperCase()) {
			case "SI":
				crearAlumno(notas_alumnos);
				break;
			case "NO":

				condicion = true;
				calculoNotaMedia(notas_media_Alumnos, notas_alumnos);
				break;

			default:
				System.out.println("Respuesta no valida");
				break;
			}
		} while (!condicion);
		mostrarMedia(notas_media_Alumnos);

	}

	public static void crearAlumno(Hashtable<String, ArrayList<Double>> notas_alumno) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Double> notas = new ArrayList<Double>();
		String nombre_alumno = "";
		boolean comprobante = false;

		System.out.println("Introduce el nombre del alumno");
		nombre_alumno = sc.nextLine();

		while (nombre_alumno.isEmpty()) {
			System.out.println("El nombre del alumno no puede estar en blanco");
			nombre_alumno = sc.nextLine();
		}

		notas.add(introducirNotas(sc));

		do {
			System.out.println("Deseas introducir mas notas (si/no)");

			switch (sc.nextLine().toLowerCase()) {
			case "si":
				notas.add(introducirNotas(sc));
				break;
			case "no":
				comprobante = true;
				break;
			default:
				System.out.println("Escriba si o no");
				break;
			}
		} while (!comprobante);

		notas_alumno.put(nombre_alumno, notas);
	}

	public static double introducirNotas(Scanner sc) {
		String entrada_texto = "";

		System.out.println("Introduce una nota");
		entrada_texto = sc.nextLine();

		while (!comprovarEntradaTextoEsNumero(entrada_texto)) {
			System.out.println("Has de introducir un valor numerico y a de estar entre 0 y 10");
			entrada_texto = sc.nextLine();
			comprovarEntradaTextoEsNumero(entrada_texto);
		}
		return Double.parseDouble(entrada_texto);
	}

	public static boolean comprovarEntradaTextoEsNumero(String texto_a_comprobar) {
		try {
			double comprovación = Double.parseDouble(texto_a_comprobar);
			if (comprovación < 0 || comprovación > 10) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public static void calculoNotaMedia(Hashtable<String, Double> notas_media_Alumnos,
			Hashtable<String, ArrayList<Double>> notas_alumnos) {
		Enumeration<String> nombre_alumnos = notas_alumnos.keys();
		double media = 0;

		while (nombre_alumnos.hasMoreElements()) {
			String nombre_alumno = nombre_alumnos.nextElement();
			ArrayList<Double> notas = notas_alumnos.get(nombre_alumno);
			Iterator<Double> it = notas.iterator();
			while (it.hasNext()) {
				media = media + it.next();
			}

			media = media / notas.size();

			notas_media_Alumnos.put(nombre_alumno, media);
		}
	}

	public static void mostrarMedia(Hashtable<String, Double> notas_media_Alumnos) {
		Enumeration<String> nombres_alumnos = notas_media_Alumnos.keys();

		while (nombres_alumnos.hasMoreElements()) {
			String nombre_alumno = nombres_alumnos.nextElement();
			double media = notas_media_Alumnos.get(nombre_alumno);
			System.out.println("La nota media del alumno/a " + nombre_alumno + " es de: " + media);
		}
	}

}
