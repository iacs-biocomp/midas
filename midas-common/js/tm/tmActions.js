/**
 * Gestión de historias de usuario / peticiones
 * @param form
 * @returns
 */



/**
 * Ver lista de peticiones coincidentes con criterios del formualrio
 * @param form
 * @returns
 */
function VerPeticiones(form){
    form.method="post";
    form.action="listHKStory";
    if (form.storyForm_sfEstId.value == 7)
    		form.vOcultarPCerradas.value = false;
    form.submit();
}


/**
 * Muestra el formulario de creación de una nueva historia de usuario
 * @param form
 * @returns
 */
function CrearPeticion(form){
    form.method="post";
    form.action="createStory";
    form.submit();
}