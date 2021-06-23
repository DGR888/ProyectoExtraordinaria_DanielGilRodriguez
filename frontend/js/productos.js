const URL = "http://localhost:8081/E02RestEjemplo/webapi/productos";
const URLId = "http://localhost:8081/E02RestEjemplo/webapi/productos?order=id";
const URLcodigoProducto =
  "http://localhost:8081/E02RestEjemplo/webapi/productos?order=product_code";
const URLnombre =
  "http://localhost:8081/E02RestEjemplo/webapi/productos?order=product_name";
const URLbuscarId = "http://localhost:8081/E02RestEjemplo/webapi/productos/";
const myModal = new bootstrap.Modal(document.getElementById("idModal")); // Para los mensajes de error y avisos
const modalWait = new bootstrap.Modal(document.getElementById("idModalWait")); // Para los mensajes de error y avisos

window.onload = init;

function init() {
  const peticionHTTP = fetch(URL);

  peticionHTTP
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Return not ok");
    })
    .then((productos) => {
      let tblBody = document.getElementById("id_tblProductos");
      for (const producto of productos) {
        let fila = document.createElement("tr");
        let elemento = document.createElement("td");
        elemento.innerHTML = producto.id;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.minimum_reorder_quantity;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_code;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_name;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.description ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.quantity_per_unit ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML =
          `<button class="btn btn-link" onclick="editaProducto(${producto.id})"><i class="bi-pencil"></i></button>` +
          `<button style="color:red;" class="btn btn-link"  onclick="borrarProducto(${producto.id})"><i class="bi-x-circle"></i></button>`;
        fila.appendChild(elemento);

        tblBody.appendChild(fila);
      }

      // Todo ha ido bien hasta aquí, habilito el boton de añadir Producto

      document
        .getElementById("idAddProducto")
        .addEventListener("click", addProducto);
    })
    .catch((error) => {
      muestraMsg(
        "¡M**rd!",
        "¡No he podido recuperar el listado de productos!<br>" + error,
        false,
        "error"
      );
    });

  //Get the button:
  mybutton = document.getElementById("botonArriba");

  mybutton.addEventListener("click", botonArribaFunction);
  // When the user scrolls down 20px from the top of the document, show the button
  window.onscroll = function () {
    scrollFunction();
  };
}

function editaProducto(idProducto) {
  window.location.href = `editarProducto.html?idProducto=${idProducto}`;
}

function addProducto() {
  window.location.href = "añadirProducto.html";
}

function borrarProducto(idProducto) {
  muestraMsg(
    "¡Atención!",
    `¿Estas seguró de querer borrar el producto ${idProducto}?`,
    true,
    "question",
    "Adelante con los faroles!",
    "Naaa, era broma..."
  );
  document.getElementById("idMdlOK").addEventListener("click", () => {
    borrarProductoAPI(idProducto);
  });
}

function borrarProductoAPI(idProducto) {
  myModal.hide();
  modalWait.show();
  opciones = {
    method: "DELETE", // Modificamos la BBDD
  };

  fetch(URL + "/" + idProducto, opciones)
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else {
        throw new Error(
          `Fallo al borrar, el servidor responde con ${respuesta.status}-${respuesta.statusText}`
        );
      }
    })
    .then((respuesta) => {
      modalWait.hide();
      muestraMsg(
        `¡Producto ${idProducto} Borrado!`,
        "¡A tomar por saco!",
        false,
        "success"
      );
      document.getElementById("idMdlClose").addEventListener("click", () => {
        location.reload();
        document.getElementById("idMdlClose").removeEventListener("click");
      });
    })
    .catch((error) => {
      modalWait.hide();
      muestraMsg(
        "Producto NO borrado",
        "El producto esta en algun pedido🤔<br>" + error,
        false,
        "error"
      );
    });
}

function muestraMsg(
  titulo,
  mensaje,
  okButton,
  tipoMsg,
  okMsg = "OK",
  closeMsg = "Close"
) {
  document.getElementById("idMdlOK").innerHTML = okMsg;
  document.getElementById("idMdlClose").innerHTML = closeMsg;

  myModal.hide();
  switch (tipoMsg) {
    case "error":
      {
        titulo =
          "<i style='color:red ' class='bi bi-exclamation-octagon-fill'></i> " +
          titulo;
      }
      break;
    case "question":
      {
        titulo =
          "<i style='color:blue' class='bi bi-question-circle-fill'></i> " +
          titulo;
      }
      break;
    default:
      {
        titulo =
          "<i style='color:green' class='bi bi-check-circle-fill'></i> " +
          titulo;
      }
      break;
  }
  document.getElementById("idMdlTitle").innerHTML = titulo;
  document.getElementById("idMdlMsg").innerHTML = mensaje;
  document.getElementById("idMdlOK").style.display = okButton
    ? "block"
    : "none";

  myModal.show();
}

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}
// When the user clicks on the button, scroll to the top of the document
function botonArribaFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}
function orderId() {
  let tblBody = document.getElementById("id_tblProductos");
  tblBody.innerHTML = "";
  const peticionHTTP5 = fetch(URLId);

  peticionHTTP5
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Return not ok");
    })
    .then((productos) => {
      let tblBody = document.getElementById("id_tblProductos");
      for (const producto of productos) {
        let fila = document.createElement("tr");
        let elemento = document.createElement("td");
        elemento.innerHTML = producto.id;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.minimum_reorder_quantity;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_code;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_name;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.description ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.quantity_per_unit ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML =
          `<button class="btn btn-link" onclick="editaProducto(${producto.id})"><i class="bi-pencil"></i></button>` +
          `<button style="color:red;" class="btn btn-link"  onclick="borrarProducto(${producto.id})"><i class="bi-x-circle"></i></button>`;
        fila.appendChild(elemento);

        tblBody.appendChild(fila);
      }

      // Todo ha ido bien hasta aquí, habilito el boton de añadir Producto

      document
        .getElementById("idAddProducto")
        .addEventListener("click", addProducto);
    })
    .catch((error) => {
      muestraMsg(
        "¡M**rd!",
        "¡No he podido recuperar el listado de productos!<br>" + error,
        false,
        "error"
      );
    });
}
function orderCodigoProducto() {
  let tblBody = document.getElementById("id_tblProductos");
  tblBody.innerHTML = "";
  const peticionHTTP2 = fetch(URLcodigoProducto);

  peticionHTTP2
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Return not ok");
    })
    .then((productos) => {
      let tblBody = document.getElementById("id_tblProductos");
      for (const producto of productos) {
        let fila = document.createElement("tr");
        let elemento = document.createElement("td");
        elemento.innerHTML = producto.id;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.minimum_reorder_quantity;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_code;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_name;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.description ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.quantity_per_unit ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML =
          `<button class="btn btn-link" onclick="editaProducto(${producto.id})"><i class="bi-pencil"></i></button>` +
          `<button style="color:red;" class="btn btn-link"  onclick="borrarProducto(${producto.id})"><i class="bi-x-circle"></i></button>`;
        fila.appendChild(elemento);

        tblBody.appendChild(fila);
      }

      // Todo ha ido bien hasta aquí, habilito el boton de añadir Producto

      document
        .getElementById("idAddProducto")
        .addEventListener("click", addProducto);
    })
    .catch((error) => {
      muestraMsg(
        "¡M**rd!",
        "¡No he podido recuperar el listado de productos!<br>" + error,
        false,
        "error"
      );
    });
}
function orderNombre() {
  let tblBody = document.getElementById("id_tblProductos");
  tblBody.innerHTML = "";
  const peticionHTTP3 = fetch(URLnombre);

  peticionHTTP3
    .then((respuesta) => {
      if (respuesta.ok) {
        return respuesta.json();
      } else throw new Error("Return not ok");
    })
    .then((productos) => {
      let tblBody = document.getElementById("id_tblProductos");
      for (const producto of productos) {
        let fila = document.createElement("tr");
        let elemento = document.createElement("td");
        elemento.innerHTML = producto.id;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.minimum_reorder_quantity;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_code;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.product_name;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.description ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = producto.quantity_per_unit ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML =
          `<button class="btn btn-link" onclick="editaProducto(${producto.id})"><i class="bi-pencil"></i></button>` +
          `<button style="color:red;" class="btn btn-link"  onclick="borrarProducto(${producto.id})"><i class="bi-x-circle"></i></button>`;
        fila.appendChild(elemento);

        tblBody.appendChild(fila);
      }

      // Todo ha ido bien hasta aquí, habilito el boton de añadir Producto

      document
        .getElementById("idAddProducto")
        .addEventListener("click", addProducto);
    })
    .catch((error) => {
      muestraMsg(
        "¡M**rd!",
        "¡No he podido recuperar el listado de productos!<br>" + error,
        false,
        "error"
      );
    });
}

function botonBuscar() {
  let botonBuscarId = document.getElementById("botonBuscar");

  botonBuscarId.addEventListener("click", () => {
    var input = document.getElementById("buscarId");
    var valor = input.value;

    let tblBody = document.getElementById("id_tblProductos");
    tblBody.innerHTML = "";
    const peticionHTTP4 = fetch(URLbuscarId + valor);

    peticionHTTP4
      .then((respuesta) => {
        if (respuesta.ok) {
          return respuesta.json();
        } else throw new Error("Return not ok");
      })
      .then((productos) => {
        let tblBody = document.getElementById("id_tblProductos");

        let fila = document.createElement("tr");
        let elemento = document.createElement("td");
        elemento.innerHTML = productos.id;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = productos.minimum_reorder_quantity;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = productos.product_code;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = productos.product_name;
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = productos.description ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML = productos.quantity_per_unit ?? "";
        fila.appendChild(elemento);
        elemento = document.createElement("td");
        elemento.innerHTML =
          `<button class="btn btn-link" onclick="editaProducto(${productos.id})"><i class="bi-pencil"></i></button>` +
          `<button style="color:red;" class="btn btn-link"  onclick="borrarProducto(${productos.id})"><i class="bi-x-circle"></i></button>`;
        fila.appendChild(elemento);

        tblBody.appendChild(fila);

        // Todo ha ido bien hasta aquí, habilito el boton de añadir Producto

        document
          .getElementById("idAddProducto")
          .addEventListener("click", addProducto);
      })
      .catch((error) => {
        muestraMsg(
          "¡M**rd!",
          "¡No he podido recuperar el listado de productos!<br>" + error,
          false,
          "error"
        );
      });
  });
}
