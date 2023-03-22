// Elementes
var menu = document.getElementsByClassName('menu');
var taula = document.getElementById('taula');
var titol = document.getElementById('titol');
var par = document.getElementById('paragraf');
var checkburg = document.getElementsByClassName('checkburg')[0];

// Creació de l'objecte en AJAX 
function conObj(){
	var httpRequest;
	// Processament de respostes com a JSON
	if(window.XMLHttpRequest) { // Mozilla, Safari, IE7+
		httpRequest = new XMLHttpRequest();
		// console.log("Objecte creat a partir de XMLHttpRequest");
	}else if(window.ActiveXObject) { // IE 6 i anteriors
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		// console.log("Objecte creat a partir d'ActiveXObject");
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
	usTr.innerHTML = "<th></th><th>Usuari</th><th>Rol</th><th>Nom</th><th>Cognom</th><th>Email</th><th>Data de registre</th><th></th><th></th>";
	taula.appendChild(usTr);
	
	var url = "http://localhost:8080/api/v1/usuaris";
	var dadesI00 = conObj();
	dadesI00.onloadstart = function(){
		console.log("Carregant petició");
	};
	dadesI00.onload = function(){
		var dades = JSON.parse(dadesI00.responseText);
	//	console.log(dades);
		
		var n = 1;
		for(const u in dades){
			var usTr = document.createElement("tr");
				var usTdId = document.createElement("td");
					usTdId.innerHTML = n++;
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
				var usTdDataReg = document.createElement("td");
					usTdDataReg.innerHTML = dades[u].dataRegistre;
					usTr.appendChild(usTdDataReg);
				var usTdEdit = document.createElement("td");
					usTdEdit.innerHTML = "<button class='button button1' onclick='modificarUsuari("+dades[u].id+")'><i class='fas fa-user-edit'></i></button>";
					usTr.appendChild(usTdEdit);
				var usTdDelete = document.createElement("td");
					usTdDelete.innerHTML = "<button class='button button1' onclick='eliminarUsuari("+dades[u].id+")'><i class='fa fa-trash'></i></button>";
					usTr.appendChild(usTdDelete);
				// us.setAttribute("class", "ciutats");
			taula.appendChild(usTr);
		}
		
	};
	dadesI00.error = function(){
		console.log("Error de la petició");
	};
	dadesI00.open('GET', url, true);
	dadesI00.send(null);
}
	

// Modificar Usuari
function modificarUsuari(id){
	console.log("Modificar usuari "+id);
}


// Eliminar Usuari
function eliminarUsuari(id){
	confirm("Estàs segur que vols eliminar aquest usuari?");
	console.log("Eliminar usuari "+id);
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
