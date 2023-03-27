/*	IOC 2022 S2
	DAW Desenvolupament d'Aplicacions Web
	M12B0 Projecte de desenvolupament d'aplicacions web
	Grup 1 Pied Piper: PodCat
	Script Zona ADMIN

Recursos
// fetch	https://jasonwatmore.com/post/2021/09/21/fetch-http-delete-request-examples
// PATCH	https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
*/

// Elementes
var menu = document.getElementsByClassName('menu');
var taula = document.getElementById('taula');
var titol = document.getElementById('titol');
var par = document.getElementById('paragraf');
var checkburg = document.getElementsByClassName('checkburg')[0];

var modUsuari = document.getElementsByClassName('modalModUsuari')[0];
var modificar = document.getElementById('modificar');
var tancar = document.getElementById('tancar');

// Creació de l'objecte en AJAX 
function conObj(){
	var httpRequest;
	// Processament de respostes com a JSON
	if(window.XMLHttpRequest) { // Mozilla, Safari, IE7+
		httpRequest = new XMLHttpRequest();
	}else if(window.ActiveXObject) { // IE 6 i anteriors
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}else{
		console.error("Error! Aquest navegador no suporta AJAX");
	}
	return httpRequest;
}


// MENÚ 
for(let i=0; i<menu.length; i++){
	menu[i].addEventListener('click', function(e){
		taula.innerHTML = "";
		checkburg.checked = false;
		
		// Menú actiu
		for(let j=0; j<menu.length; j++){
			menu[j].parentNode.classList.remove('actiu');
		}
		menu[i].parentNode.classList.add('actiu');
		
		var seccio = this.getAttribute('seccio');
		if(seccio == 'usuaris'){
			titol.innerHTML = "USUARIS";
			par.innerHTML = "Tots els usuaris registrats a la plataforma";
			usuaris();
		}else if(seccio == 'canals'){
			titol.innerHTML = "CANALS";
			par.innerHTML = "Tots els canals de podcasts";
			canals();
		}else if(seccio == 'podcasts'){
			titol.innerHTML = "PODCASTS";
			par.innerHTML = "Tots els podcasts publicats";
			podcasts();
		}
	});
}

// USUARIS
function usuaris(){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Usuari</th><th>Rol</th><th>Nom</th><th>Cognom</th><th>Email</th><th></th><th></th>";
	taula.appendChild(usTr);
	
	// GET Usuaris
	fetch('/api/v1/usuaris')
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // check for error response
        if (!response.ok) {
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			// console.log(dades);
			var n = 1;
			for(const u in dades){
				var usTr = document.createElement("tr");
					usTr.setAttribute("idus", dades[u].id);
					var usTdId = document.createElement("td");
						usTdId.innerHTML = n++ +".";
						usTr.appendChild(usTdId);
					var usTdUsername = document.createElement("td");
						usTdUsername.innerHTML = dades[u].username;
						usTr.appendChild(usTdUsername);
					var usTdRol = document.createElement("td");
						usTdRol.innerHTML = dades[u].rol;
						usTr.appendChild(usTdRol);
					var usTdNom = document.createElement("td");
						usTdNom.innerHTML = dades[u].nom;
						usTr.appendChild(usTdNom);
					var usTdCognom = document.createElement("td");
						usTdCognom.innerHTML = dades[u].cognom;
						usTr.appendChild(usTdCognom);
					var usTdEmail = document.createElement("td");
						usTdEmail.innerHTML = dades[u].email;
						usTr.appendChild(usTdEmail);
					var usTdEdit = document.createElement("td");
						usTdEdit.innerHTML = "<button class='button button1' onclick='modificarUsuari("+dades[u].id+")'><i class='fas fa-user-edit'></i></button>";
						usTr.appendChild(usTdEdit);
					var usTdDelete = document.createElement("td");
						usTdDelete.innerHTML = "<button class='button button1' onclick='eliminarUsuari("+dades[u].id+")'><i class='fa fa-trash'></i></button>";
						usTr.appendChild(usTdDelete);
				taula.appendChild(usTr);
			}
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}
	

// Modificar Usuari
function modificarUsuari(id){
	// console.log("Modificar usuari "+id);
	// GET Usuari: el posem al formulari com a valor inicial
	fetch('/api/v1/usuaris/'+id)
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const dades = isJson && await response.json();
        // check for error response
        if (!response.ok) {
			// Control d'errors
            missatge("error","No s'ha pogut rebre la petició HTTP");
        }else{
			modUsuari.classList.remove("ocult");
			// console.log(dades);
			document.getElementById('modId').value = dades.id;
			document.getElementById('modUsername').value = dades.username;
			document.getElementById('modNom').value = dades.nom;
			document.getElementById('modCognom').value = dades.cognom;
			document.getElementById('modEmail').value = dades.email;
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
}

// Modificar Usuari / Enviar dades
modificar.onclick = function(){
	modUsuari.classList.add("ocult");
	var idMod = document.getElementById('modId').value;
	// PATCH: Modifiquem l'usuari
	fetch('/api/v1/usuariss/'+idMod, {
		method: 'PATCH',
		body: JSON.stringify({
			username: document.getElementById('modUsername').value,
			nom: document.getElementById('modNom').value,
			cognom: document.getElementById('modCognom').value,
			email: document.getElementById('modEmail').value
		}),
		headers: {
			'Content-type': 'application/json; charset=UTF-8',
		},
	})
	.then(async response => {
		const isJson = response.headers.get('content-type')?.includes('application/json');
		const dades = isJson && await response.json();
		// check for error response
		if (!response.ok) {
			// Control d'errors
			missatge("error","No s'ha pogut establir la petició HTTP");
		}else{
			// console.log(dades);
			// Actualitzem la fila de l'usuari modificat
			var trs = document.getElementsByTagName("tr");
			for(let i=0; i<trs.length; i++){
				if(idMod == trs[i].getAttribute('idus')){
					trs[i].childNodes[1].innerHTML = dades.username;
					trs[i].childNodes[3].innerHTML = dades.nom;
					trs[i].childNodes[4].innerHTML = dades.cognom;
					trs[i].childNodes[5].innerHTML = dades.email;
				}
			}
			missatge("missatge", "S'ha modificat l'usuari");
		}
	})
	.catch((error) => {
		missatge('error', error);
	});
};

// Tancar modal
tancar.onclick = function(){
	modUsuari.classList.add("ocult");
};


// Eliminar Usuari
function eliminarUsuari(id){
	if(confirm("Estàs segur que vols eliminar aquest usuari?")){
		// console.log("Eliminar usuari "+id);
		// DELETE: Eliminem l'usuari
		fetch('/api/v1/usuaris/'+id, { method: 'DELETE' })
		.then(async response => {
			const isJson = response.headers.get('content-type')?.includes('application/json');
			const dades = isJson && await response.json();
			if (!response.ok) {
				// Control d'errors
				missatge('error', "No s'ha pogut fer la petició HTTP");
			}else{
				// Eliminem la fila de la taula de l'usuari que eliminem
				var trs = document.getElementsByTagName("tr");
				for(let i=0; i<trs.length; i++){
					if(id == trs[i].getAttribute('idus')){
						trs[i].remove();
					}
				}
				missatge("missatge", "S'ha eliminat l'usuari");
			}
		})
		.catch(error => {
			missatge('error', error);
		});
	}
}


// Mostrar missatge
function missatge(tipus, m){
	// Creem el popup d'error
	var capaError = document.createElement("div");
	capaError.classList.add("popup");
	
	let textInici = "";
	if(tipus == 'missatge'){
		capaError.classList.add("popupMissatge");
	}else if(tipus == 'alerta'){
		textInici = "<span class='warning'>&#9888;</span> Alerta!";
		capaError.classList.add("popupAlerta");
	}else if(tipus == 'error'){
		textInici = "<span class='warning'>&#9888;</span> Error!";
		capaError.classList.add("popupError");
	}
	capaError.innerHTML = textInici+" "+m;
	document.body.appendChild(capaError);
	// Eliminar la capa al cap de 5 segons
	setTimeout(function(){ capaError.remove(); }, 5000);
}




// CANALS
function canals(){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Títol</th><th>Usuari</th><th>Data de publicació</th><th></th><th></th>";
	taula.appendChild(usTr);
}




// PODCASTS
function podcasts(){
	var usTr = document.createElement("tr");
	usTr.innerHTML = "<th></th><th>Títol</th><th>Arxiu</th><th>Canal</th><th>Usuari</th><th>Data de publicació</th><th></th><th></th>";
	taula.appendChild(usTr);
	
}
