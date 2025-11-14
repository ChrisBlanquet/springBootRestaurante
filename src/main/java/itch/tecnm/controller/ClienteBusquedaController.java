package itch.tecnm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itch.tecnm.model.Cliente;
import itch.tecnm.service.IClienteService;

@Controller
@RequestMapping(value = "/busqueda")
public class ClienteBusquedaController {

	@Autowired
	private IClienteService serviceCliente;

	@GetMapping("/cliente")
	public String mostrarListaClientes(Model model) {

		List<Cliente> lista = serviceCliente.bucarTodosClientes();
		model.addAttribute("clienteLista", lista);

		return "busquedas/BusquedasCliente.html";
	}

	@GetMapping("/buscar")
	public String buscar(@RequestParam String ejercicio, @RequestParam(required = false) String q,
			@RequestParam(required = false) String q2, Model model) {

		Map<String, String> descripciones = new HashMap<>();
		descripciones.put("Ejercicio1", "Crea un método en el repositorio que busque un cliente por su nombre.");
		descripciones.put("Ejercicio2",
				"Implementa un método que recupere todos los clientes cuyo nombre contenga una letra o cadena específica.");
		descripciones.put("Ejercicio3", "Crea un método que busque un cliente por su email exacto.");
		descripciones.put("Ejercicio4", "Crea otro que busque todos los clientes cuyo email termine con '@gmail.com'.");
		descripciones.put("Ejercicio5",
				"Crea un método que devuelva los clientes cuyo crédito esté entre dos valores.");
		descripciones.put("Ejercicio6",
				"Implementa un método que devuelva los clientes con crédito mayor a un monto dado.");
		descripciones.put("Ejercicio7",
				"Crea un método que devuelva todos los clientes destacados (por ejemplo, destacado = 1).");
		descripciones.put("Ejercicio8",
				"Crea un método que busque clientes por nombre y que tengan un crédito mayor a un valor.");
		descripciones.put("Ejercicio9", "Crea un método que devuelva los clientes cuya foto sea 'no_imagen.jpg'.");
		descripciones.put("Ejercicio10",
				"Crea un método que devuelva clientes destacados con crédito mayor a un valor específico.");
		descripciones.put("Ejercicio11", "Crea un método que devuelva los 5 clientes con mayor crédito.");

		List<Cliente> clientes = new ArrayList<>();

		switch (ejercicio) {
		case "Ejercicio1": {
			Cliente cliente = new Cliente();
			cliente = serviceCliente.buscarPorNombre(q);
			if (cliente != null) {
				clientes.add(cliente);
			}
		}
			break;

		case "Ejercicio2":
			clientes = serviceCliente.buscarContengaCadena(q);
			break;

		case "Ejercicio3": {
			Cliente cliente = new Cliente();
			cliente = serviceCliente.BuscarPorEmail(q);
			if (cliente != null) {
				clientes.add(cliente);
			}
		}
			break;
		case "Ejercicio4":
			clientes = serviceCliente.buscarDominioEmail(q);
			break;

		case "Ejercicio5":
			try {
				Double valor1 = Double.parseDouble(q);
				Double valor2 = Double.parseDouble(q2);

				clientes = serviceCliente.buscarEntreCreditos(valor1, valor2);
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Los valores ingresados para crédito no son válidos.");
			}
			break;

		case "Ejercicio6":
			try {
				Double valor = Double.parseDouble(q);

				clientes = serviceCliente.buscarCreditoMayor(valor);
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Los valores ingresados para crédito no son válidos.");
			}
			break;

		case "Ejercicio7":
			try {
				Integer valor = Integer.parseInt(q);
				if (valor == 1 || valor == 0) {
					clientes = serviceCliente.buscarDestacados(valor);
				} else {
					model.addAttribute("error", "El Dato Ingresado debe de ser entre 0 y 1");
				}
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Los valores ingresados para crédito no son válidos.");
			}
			break;
		case "Ejercicio8":
			try {
				Double valor2 = Double.parseDouble(q2);
				clientes = serviceCliente.buscarPorNombreYCreditoMayor(q, valor2);
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Los valores ingresados para crédito no son válidos.");
			}
			break;

		case "Ejercicio9":
			clientes = serviceCliente.buscarFotoCliente(q);
			break;
		case "Ejercicio10":
			try {
				Integer destacado = Integer.parseInt(q);
				Double credito = Double.parseDouble(q2);
				if (destacado == 1 || destacado == 0) {
					clientes = serviceCliente.buscarDestacadosYcreditoMayor(destacado, credito);
				} else {
					model.addAttribute("error", "El Dato Ingresado debe de ser entre 0 y 1");
				}
			} catch (NumberFormatException e) {
				model.addAttribute("error", "Los valores ingresados para crédito no son válidos.");
			}
			break;

		case "Ejercicio11":
			clientes = serviceCliente.BuscarMayoresCreditos();
			break;
		}

		String descripcionSeleccionada = descripciones.getOrDefault(ejercicio, "Selección no válida");
		model.addAttribute("descripcionEjercicio", descripcionSeleccionada);
		model.addAttribute("clientes", clientes);
		model.addAttribute("ejercicio", ejercicio);
		model.addAttribute("q", q);
		model.addAttribute("q2", q2);

		return "busquedas/BusquedasCliente.html";
	}
}
