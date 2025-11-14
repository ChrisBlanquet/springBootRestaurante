var textoEjercicio = document.getElementById("textoCambia");
var selectEjercicio = document.getElementById("selectEjercicio");
var grupoQ2 = document.getElementById("grupoQ2");
var inputQ2 = grupoQ2.querySelector('input[name="q2"]');

let Ejercicios = {
  Ejercicio1: "Crea un método en el repositorio que busque un cliente por su nombre.",
  Ejercicio2: "Implementa un método que recupere todos los clientes cuyo nombre contenga una letra o cadena específica.",
  Ejercicio3: "Crea un método que busque un cliente por su email exacto.",
  Ejercicio4: "Crea otro que busque todos los clientes cuyo email termine con '@gmail.com'.",
  Ejercicio5: "Crea un método que devuelva los clientes cuyo crédito esté entre dos valores.",
  Ejercicio6: "Implementa un método que devuelva los clientes con crédito mayor a un monto dado.",
  Ejercicio7: "Crea un método que devuelva todos los clientes destacados (por ejemplo, destacado = 1).",
  Ejercicio8: "Crea un método que busque clientes por nombre y que tengan un crédito mayor a un valor.",
  Ejercicio9: "Crea un método que devuelva los clientes cuya foto sea 'no_imagen.jpg'.",
  Ejercicio10: "Crea un método que devuelva clientes destacados con crédito mayor a un valor específico.",
  Ejercicio11: "Crea un método que devuelva los 5 clientes con mayor crédito.",
};

function requiereQ2(ejercicio) {
  return ejercicio === "Ejercicio5" || ejercicio === "Ejercicio8" || ejercicio === "Ejercicio10";
}

function actualizarUI() {
  const opcSeleccionada = selectEjercicio.value;


  const descripcion = Ejercicios[opcSeleccionada];
  textoEjercicio.textContent = "Búsqueda Avanzada: " + (descripcion ?? "(selección no válida)");


  const mostrarQ2 = requiereQ2(opcSeleccionada);
  grupoQ2.style.display = mostrarQ2 ? "" : "none";
  inputQ2.disabled = !mostrarQ2;
  inputQ2.setAttribute("aria-hidden", (!mostrarQ2).toString());
  if (!mostrarQ2) {
    inputQ2.value = "";
  }
}


document.addEventListener("DOMContentLoaded", actualizarUI);


selectEjercicio.addEventListener("change", actualizarUI);
