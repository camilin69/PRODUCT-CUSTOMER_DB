



const container_login = document.querySelector(".container_login")
const container_register = document.querySelector(".container_register")
const regFormBy = document.querySelector('.reg_form_by_rol');
const regRol = document.getElementById('reg_rol');


var selected_role = "";


regRol.addEventListener("change", function() {

    regFormBy.innerHTML = '';

    selected_role = this.value;
    
    


    if (selected_role === 'consumer') {
        regFormBy.innerHTML = `
            <form id="regConsumer" class="row reg_inner g-3 needs-validation" novalidate>
                <div class="form-floating mt-3 col-md-4">
                    <input type="number" class="form-control" id="reg_id" placeholder="Identificacion" required>
                    <label for="reg_id">Identificacion</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Número de identificación personal no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="text" class="form-control" id="reg_name" placeholder="Nombre Completo" required>
                    <label for="reg_name">Nombre Completo</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Nombre no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3 col-md-4">
                    <input type="number" class="form-control" id="reg_age" placeholder="Edad" required>
                    <label for="reg_age">Edad</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Edad no válida! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3 col-md-4">
                    <input type="number" class="form-control" id="reg_status" placeholder="Estrato" required>
                    <label for="reg_status">Estrato</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Estrato no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="email" class="form-control" id="reg_email" placeholder="Email" required>
                    <label for="reg_email">Email</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Email no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="password" class="form-control" id="reg_password" placeholder="Contraseña" required>
                    <label for="reg_password">Contraseña</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Contraseña no válida! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="password" class="form-control" id="reg_password_confirm" placeholder="Confirmar Contraseña" required>
                    <label for="reg_password_confirm">Confirmar Contraseña</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Las contraseñas no coinciden!<span class="reason-feedback"></span></div>
                </div>
                <button type="submit" id="submitButton" class="btn btn-primary mt-3">Registrar</button>
            </form>
        `;
        get_municipios()
    } else if (selected_role === 'provider') {
        regFormBy.innerHTML = `
            <form id="regProvider" class="reg_inner row g-3 needs-validation" novalidate>
                <div class="form-floating mt-3 col-md-4">
                    <input type="number" class="form-control" id="reg_id" placeholder="Identificacion" required>
                    <label for="reg_id">Identificacion</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Número de identificación personal no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="text" class="form-control" id="reg_name" placeholder="Nombre Completo" required>
                    <label for="reg_name">Nombre Completo</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Nombre no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3 col-md-4">
                    <input type="number" class="form-control" id="reg_phone" placeholder="Telefono" required>
                    <label for="reg_phone">Telefono</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Número de teléfono personal no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="email" class="form-control" id="reg_email" placeholder="Email" required>
                    <label for="reg_email">Email</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Email personal no válido! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="password" class="form-control" id="reg_password" placeholder="Contraseña" required>
                    <label for="reg_password">Contraseña</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Contraseña no válida! - <span class="reason-feedback"></span></div>
                </div>
                <div class="form-floating mt-3">
                    <input type="password" class="form-control" id="reg_password_confirm" placeholder="Confirmar Contraseña" required>
                    <label for="reg_password_confirm">Confirmar Contraseña</label>
                    <div class="valid-feedback">Okey!</div>
                    <div class="invalid-feedback">Las contraseñas no coinciden<span class="reason-feedback"></span></div>
                </div>
                <button type="submit" id="submitButton" class="btn btn-primary mt-3">Registrar</button>
            </form>
        `;
    }else{return}
});


regFormBy.addEventListener("submit", async function (event) {
    event.preventDefault();

    if (submit_validations()) {
        // Crear un objeto para los parámetros de la URL
        let params = new URLSearchParams();

        // Asignar los parámetros dependiendo del rol seleccionado
        if (selected_role === "consumer") {
            params.append('id', regFormBy.querySelector('#reg_id').value);
            params.append('name', regFormBy.querySelector('#reg_name').value);
            params.append('email', regFormBy.querySelector('#reg_email').value);
            params.append('password', regFormBy.querySelector('#reg_password').value);
            params.append('age', regFormBy.querySelector('#reg_age').value);
            params.append('status', regFormBy.querySelector('#reg_status').value);
        } else if (selected_role === "provider") {
            params.append('id', regFormBy.querySelector('#reg_id').value);
            params.append('name', regFormBy.querySelector('#reg_name').value);
            params.append('phone', regFormBy.querySelector('#reg_phone').value);
            params.append('email', regFormBy.querySelector('#reg_email').value);
            params.append('password', regFormBy.querySelector('#reg_password').value);
        } else {
            return; 
        }

        try {
            const response = await fetch('../webapi/register/reg_' + selected_role + '?' + params.toString(), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const message = await response.json();
                alert(message.message); 
                location.reload()
            } else {
                alert("Registration failed.");
            }

        } catch (error) {
            console.error('Error during registration:', error);
            showAlert('Hubo un error al registrar el usuario. Intenta nuevamente.');
        }
    } else {
        console.log('Form validation failed');
    }
});



async function submit_validations(){
    
    function showFeedback(input, isValid, errorMessage = "") {
        const formGroup = input.closest(".form-floating");

        const valid_feedback = formGroup.querySelector(".valid-feedback");
        const invalid_feedback = formGroup.querySelector(".invalid-feedback");
        const reason_feedback = invalid_feedback.querySelector(".reason-feedback");

        valid_feedback.style.display = "none";
        invalid_feedback.style.display = "none";
        reason_feedback.textContent = "";  

        if (isValid) {
            valid_feedback.style.display = "block";
        } else {
            invalid_feedback.style.display = "block";
            reason_feedback.textContent = errorMessage;
        }
    }

    function checkFormValidity() {
        let form = regFormBy.querySelector('.reg_inner')
        let isValid = true;
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        const inputs = form.querySelectorAll("input");

        
        
        inputs.forEach(input => {

            if(input.type === "text"){
                if (!input.value.match(/^[A-Za-z]+$/)) {
                    showFeedback(input, false, "Solo letras permitidas.");
                    isValid = false;
                } else if (input.value.trim() === "") {
                    showFeedback(input, false, "Este campo no puede estar vacío.");
                    isValid = false;
                } else {
                    showFeedback(input, true);
                }
            }else if(input.type === "number"){
                if(input.value.match()){
                    if (!input.value.match(/^\d+$/)) {
                        showFeedback(input, false, "Solo números permitidos.");
                        isValid = false;
                    } else if (input.value.trim() === "") {
                        showFeedback(input, false, "Este campo no puede estar vacío.");
                        isValid = false;
                    } else {
                        showFeedback(input, true);
                    }
                }
            }else if(input.type === "email"){
                if(input.value.match()){
                    if (!input.value.match(emailPattern)) {
                        showFeedback(input, false, "Por favor ingrese un correo electrónico válido.");
                        isValid = false;
                    } else {
                        showFeedback(input, true);
                    }
                }
            }else if (input.id === "reg_password") {
                const passwordValue = input.value;
                let passwordMessage = "";
        
                if (passwordValue.length < 8) {
                    passwordMessage += "La contraseña debe tener al menos 8 caracteres. ";
                    isValid = false;
                }
                if (!/[A-Z]/.test(passwordValue)) {
                    passwordMessage += "La contraseña debe incluir al menos una letra mayúscula. ";
                    isValid = false;
                }
                if (!/\d/.test(passwordValue)) {
                    passwordMessage += "La contraseña debe incluir al menos un número. ";
                    isValid = false;
                }
                if (!/[@$!%*?&]/.test(passwordValue)) {
                    passwordMessage += "La contraseña debe incluir al menos un carácter especial (@, $, !, %, *, ?, &). ";
                    isValid = false;
                }
        
                if (passwordMessage) {
                    showFeedback(input, false, passwordMessage);
                } else {
                    showFeedback(input, true);
                    const passwordConfirm = form.querySelector("#reg_password_confirm");
                    if (passwordValue !== passwordConfirm.value) {
                        showFeedback(passwordConfirm, false);
                        isValid = false;
                    } else {
                        showFeedback(passwordConfirm, true);
                    }
                }
        
                
            }

        });


        return isValid;
    }

    if(checkFormValidity()){
        if(await find_user_reg(regFormBy.querySelector("#reg_id").value, selected_role) ){
            showFeedback(regFormBy.querySelector("#reg_id"), false, "Usuario ya identificado")
            return false
        }
        return true
    }

    return false;
}

function change_view(name){

    regRol.value = 'none';
    regFormBy.innerHTML = '';
    container_login.style.display = 'none'
    container_register.style.display = 'none'

    if(name === 'login')
        container_login.style.display = 'block'
    else if(name === 'register')
        container_register.style.display = 'block'
        
}

async function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const consumerRoleChecked = document.getElementById('consumer_rol').checked;
    const providerRoleChecked = document.getElementById('provider_rol').checked;

    let role = null;
    if (consumerRoleChecked) {
        role = 'consumer';
    } else if (providerRoleChecked) {
        role = 'provider';
    }

    if (!role) {
        alert('Por favor, selecciona un rol (Consumidor o Proveedor)');
        return;
    }

    try {
        const userResponse = await find_user(email, password, role);

        if (userResponse) {
            

            if (role === 'consumer') {
                window.location.href = '../index/consumer.html'; 
            } else if (role === 'provider') {
                window.location.href = '../index/provider.html';  
            }
        } else {
            alert('Credenciales incorrectas o usuario no encontrado');
        }
    } catch (error) {
        console.error("Error en el login:", error);
        alert('Hubo un error al intentar iniciar sesión. Por favor, inténtalo nuevamente.');
    }
}






async function find_user_reg(id, role) {
    try {
        const url = `../webapi/register/find_user?id=${id}&role=${role}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const consumer_info = await response.json(); 
        if (consumer_info) {
            return true;  
        }
    } catch (error) {
        console.error('Error al buscar el usuario:', error);
    }
    return false;  
}


function showAlert(message) {
    const alertBox = document.getElementById("alertBox");
    const alertMessage = document.getElementById("alertMessage");
    alertMessage.textContent = message;

    alertBox.classList.add("show");  
    alertBox.style.display = "block"; 
    setTimeout(function () {
        closeAlert();
    }, 3000); 
}

function closeAlert() {
    const alertBox = document.getElementById("alertBox");
    alertBox.classList.remove("show");
    setTimeout(function () {
        alertBox.style.display = "none"; 
    }, 500);
}


