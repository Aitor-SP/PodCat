/*	IOC 2022 S2
	DAW Desenvolupament d'Aplicacions Web
	M12B0 Projecte de desenvolupament d'aplicacions web
	Grup 1 Pied Piper: PodCat
	Script Zona ADMIN CANALS

Recursos
// fetch	https://jasonwatmore.com/post/2021/09/21/fetch-http-delete-request-examples
// PATCH	https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
*/


// CANALS
function canals(){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Títol</th><th>Descripció</th><th>Imatge</th><th>Usuari</th><th></th><th></th>";
	taula.appendChild(usTr);

	// GET Canals
	fetch('/api/v1/canals')
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // Check for error response
        if (!response.ok) {
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			// console.log(dades);
			var n = 1;
			for(const u in dades){
				var usTr = document.createElement("tr");
					usTr.setAttribute("idtr", dades[u].id);
					var usTdId = document.createElement("td");
						usTdId.innerHTML = n++ +".";
						usTr.appendChild(usTdId);
					var usTdTitol = document.createElement("td");
                        usTdTitol.innerHTML = dades[u].titol;
						usTr.appendChild(usTdTitol);
					var usTdDesc = document.createElement("td");
                        usTdDesc.innerHTML = dades[u].descripcio;
						usTr.appendChild(usTdDesc);
					var usTdImatge = document.createElement("td");
                        usTdImatge.innerHTML = dades[u].imatge;
						usTr.appendChild(usTdImatge);
					var usTdUsuari = document.createElement("td");
                        usTdUsuari.innerHTML = dades[u].usuari.username;
						usTr.appendChild(usTdUsuari);
					var usTdEdit = document.createElement("td");
						usTdEdit.innerHTML = "<button class='button button1' onclick='modificarCanal("+dades[u].id+")'><i class='fas fa-user-edit'></i></button>";
						usTr.appendChild(usTdEdit);
					var usTdDelete = document.createElement("td");
						usTdDelete.innerHTML = "<button class='button button1' onclick='eliminar("+dades[u].id+",\"canal\")'><i class='fa fa-trash'></i></button>";
						usTr.appendChild(usTdDelete);
				taula.appendChild(usTr);
			}
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}
