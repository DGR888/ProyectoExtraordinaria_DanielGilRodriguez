const URL = "http://localhost:8081/E02RestEjemplo/webapi/productos";
const myModal = new bootstrap.Modal(document.getElementById("idModal")); // Para los mensajes de error y avisos

window.onload = init;

function init() {
  if (window.location.search != "") {
    const queryStr = window.location.search.substring(1);
    const parametro = queryStr.split("=");
    idProducto = parametro[1];

    rellenaProducto(idProducto);
  } else {
    document.getElementById("idId").value = "EditarProducto";
    document.getElementById("idSalvar").disabled = false;
  }

  // Usa el boton de cancelar para volver atrás
  document.getElementById("idCancel").addEventListener("click", (evt) => {
    evt.preventDefault();
    volver();
  });

  // El boton de salvar sólo está activo cuando se carge los datos de un Producto
  // document.getElementById("idSalvar").addEventListener("click", salvarProducto);
  document.getElementById("idFormProducto").addEventListener("submit", salvarProducto);
}

function rellenaProducto(idProducto) {
  const peticionHTTP = fetch(URL + "/" + idProducto);

  peticionHTTP
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Return not ok");
    })
    .then((Producto) => {
      let inputs = document.getElementsByTagName("input");
      for (let input of inputs) {
        input.value = Producto[input.name] ?? "";
      }
      document.getElementById("idSalvar").disabled = false;
    })
    .catch((error) => {
      muestraMsg("¡M**rd!", "No he podido recupera este Producto " + error, false);
    });
}

function salvarProducto(evt) {
  evt.preventDefault();

  // Creo un array con todo los datos formulario
  let Producto = {};

  // Relleno un array Producto con todos los campos del formulario
  let inputs = document.getElementsByTagName("input");
  for (let input of inputs) {
    Producto[input.name] = input.value;
  }

  if (Producto.id == "Nuevo Producto") { // Añadimos Producto
    delete Producto.id;
    opciones = {
      method: "POST", // Añadimos un registro a la BBDD
      body: JSON.stringify(Producto), // Paso el array Producto a un objeto que luego puedo jsonear
      headers: {
        "Content-Type": "application/json",
      },
    };
  } else {  // Modificamos
    opciones = {
      method: "PUT", // Modificamos la BBDD
      body: JSON.stringify(Producto), // Paso el array Producto a un objeto que luego puedo jsonear
      headers: {
        "Content-Type": "application/json",
      },
    };
  }

  fetch(URL, opciones)
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Fallo al actualizar: " + respuesta);
    })
    .then((respuesta) => {
      muestraMsg("Datos Actualizados", "Todo parece haber ido bien 🎉", false, "success");
    })
    .catch((error) => {
      muestraMsg("Oops..", "No he podido actualizar la Base de Datos 🥺 " + error, false, "error");
    });

  return false;
}

function volver() {
  window.history.back();
}

/**
 * Muestra un mensaje en el modal
 */
function muestraMsg(titulo, mensaje, okButton, tipoMsg, okMsg = "OK", closeMsg = "Close") {
  document.getElementById("idMdlOK").innerHTML = okMsg;
  document.getElementById("idMdlClose").innerHTML = closeMsg;

  myModal.hide();
  switch (tipoMsg) {
    case "error":
      {
        titulo = "<i style='color:red ' class='bi bi-exclamation-octagon-fill'></i> " + titulo;
      }
      break;
    case "question":
      {
        titulo = "<i style='color:blue' class='bi bi-question-circle-fill'></i> " + titulo;
      }
      break;
    default:
      {
        titulo = "<i style='color:green' class='bi bi-check-circle-fill'></i> " + titulo;
      }
      break;
  }
  document.getElementById("idMdlTitle").innerHTML = titulo;
  document.getElementById("idMdlMsg").innerHTML = mensaje;
  document.getElementById("idMdlOK").style.display = okButton ? "block" : "none";

  myModal.show();
}