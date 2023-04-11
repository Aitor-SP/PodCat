/*	IOC 2022 S2
	DAW Desenvolupament d'Aplicacions Web
	M12B0 Projecte de desenvolupament d'aplicacions web
	Grup 1 Pied Piper: PodCat
	Script Zona ADMIN PODCASTS

Recursos
// fetch	https://jasonwatmore.com/post/2021/09/21/fetch-http-delete-request-examples
// PATCH	https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
*/

// Elementes
// mmm


// PODCASTS
function podcasts(){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Títol</th><th>Canal</th><th>Arxiu</th><th>Imatge</th><th>Data de publicació</th><th></th><th></th>";
	taula.appendChild(usTr);
	
	// GET Podcasts
	fetch('/api/v1/podcasts')
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // Check for error response
        if (!response.ok) {
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			console.log(dades);
			var n = 1;
			for(const u in dades){
				// Primera fila
				var usTr = document.createElement("tr");
					usTr.setAttribute("idtr", dades[u].id);
					var usTdId = document.createElement("td");
						usTdId.setAttribute("rowspan", 3);
						usTdId.innerHTML = n++ +".";
						usTr.appendChild(usTdId);
					var usTdTitol = document.createElement("td");
						usTdTitol.setAttribute("rowspan", 3);
                        usTdTitol.innerHTML = '<strong>'+dades[u].titol+'</strong>';
						usTr.appendChild(usTdTitol);
					var usTdCanal = document.createElement("td");
                        usTdCanal.innerHTML = dades[u].canal.titol;
						usTr.appendChild(usTdCanal);
                    var usTdAudio = document.createElement("td");
                        usTdAudio.innerHTML = dades[u].audio;
						usTr.appendChild(usTdAudio);
					var usTdImatge = document.createElement("td");
                        usTdImatge.innerHTML = dades[u].imatge;
						usTr.appendChild(usTdImatge);
                    var usTdDataPub = document.createElement("td");
                        usTdDataPub.innerHTML = dades[u].dataPublicacio;
						usTr.appendChild(usTdDataPub);
					var usTdEdit = document.createElement("td");
						usTdEdit.setAttribute("rowspan", 3);
						usTdEdit.innerHTML = "<button class='button button1' onclick='modificarPodcast("+dades[u].id+")'><i class='fas fa-user-edit'></i></button>";
						usTr.appendChild(usTdEdit);
					var usTdDelete = document.createElement("td");
						usTdDelete.setAttribute("rowspan", 3);
						usTdDelete.innerHTML = "<button class='button button1' onclick='eliminar("+dades[u].id+",\"podcast\")'><i class='fa fa-trash'></i></button>";
						usTr.appendChild(usTdDelete);
				taula.appendChild(usTr);
				// Segona fila
				var usTr2 = document.createElement("tr");
					usTr2.setAttribute("idtr", dades[u].id);
					var usTdDescT = document.createElement("td");
						usTdDescT.innerHTML = 'Descripció:';
						usTr2.appendChild(usTdDescT);
					var usTdDesc = document.createElement("td");
						usTdDesc.setAttribute("colspan", 3);
                        usTdDesc.innerHTML = dades[u].descripcio;
						usTr2.appendChild(usTdDesc);
				taula.appendChild(usTr2);
				// Tercera fila
				var usTr3 = document.createElement("tr");
					usTr3.setAttribute("idtr", dades[u].id);
				var usTdEtT = document.createElement("td");
					usTdEtT.innerHTML = 'Gènere:';
					usTr3.appendChild(usTdEtT);
				var usTdEt = document.createElement("td");
					usTdEt.setAttribute("colspan", 3);
					usTdEt.innerHTML = dades[u].genere+' | '+dades[u].etiquetes;
					usTr3.appendChild(usTdEt);
				taula.appendChild(usTr3);
			}
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}


// Modificar Podcast
function modificarPodcast(id){
	// console.log("Modificar podcast "+id);
	fetch('/api/v1/podcasts/'+id)
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // Check for error response
        if (!response.ok) {
			// Control d'errors
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			modPodcast.classList.remove("ocult");
			modalFons.classList.remove("ocult");
			console.log(dades);
			document.getElementById('modPoId').value = dades.id;
			document.getElementById('modPoTitol').value = dades.titol;
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}

// Modificar Podcast / Enviar dades
modificarPo.onclick = function(){
	tancarModal();

	// IMPLEMENTAR

};