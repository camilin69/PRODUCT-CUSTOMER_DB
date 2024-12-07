const container_login = document.querySelector(".container_login")
const container_register = document.querySelector(".container_register")


document.getElementById("reg_rol").addEventListener("change", function() {
    const regFormBy = document.querySelector('.reg_form_by_rol');

    regFormBy.innerHTML = '';

    const selectedRole = this.value;
    const foot = document.querySelector("#regCard .card-footer")
    


    if (selectedRole === 'consumer') {
        regFormBy.innerHTML = `
            <form id="regConsumer">
                <div class="form-floating mb-3 col-md-4">
                    <input type="number" class="form-control" id="consumer_id" placeholder="Identificacion" required>
                    <label for="consumer_id">Identificacion</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="consumer_name" placeholder="Nombre Completo" required>
                    <label for="consumer_name">Nombre Completo</label>
                </div>
                <div class="form-floating mb-3 col-md-4">
                    <input type="number" class="form-control" id="consumer_age" placeholder="Edad" required>
                    <label for="consumer_age">Edad</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="consumer_municipio" placeholder="Municipio" required>
                    <label for="consumer_municipio">Municipio</label>
                </div>
                <div class="form-floating mb-3 col-md-4">
                    <input type="number" class="form-control" id="consumer_status" placeholder="Estrato" required>
                    <label for="consumer_status">Estrato</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="consumer_email" placeholder="Email" required>
                    <label for="consumer_email">Email</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="consumer_contra" placeholder="Contraseña" required>
                    <label for="consumer_contra">Contraseña</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="consumer_contra_confirm" placeholder="Confirmar Contraseña" required>
                    <label for="consumer_contra_confirm">Confirmar Contraseña</label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Registrar</button>
            </form>
        `;
    } else if (selectedRole === 'provider') {
        regFormBy.innerHTML = `
            <form id="regProvider">
                <div class="form-floating mb-3 col-md-4">
                    <input type="number" class="form-control" id="provider_id" placeholder="Identificacion" required>
                    <label for="provider_id">Identificacion</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="provider_name" placeholder="Nombre Completo" required>
                    <label for="provider_name">Nombre Completo</label>
                </div>
                <div class="form-floating mb-3 col-md-4">
                    <input type="number" class="form-control" id="provider_phone" placeholder="Telefono" required>
                    <label for="provider_phone">Telefono</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="consumer_email" placeholder="Email" required>
                    <label for="consumer_email">Email</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="consumer_contra" placeholder="Contraseña" required>
                    <label for="consumer_contra">Contraseña</label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control" id="consumer_contra_confirm" placeholder="Confirmar Contraseña" required>
                    <label for="consumer_contra_confirm">Confirmar Contraseña</label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Registrar</button>
            </form>
        `;
    }else{return}

});

function change_view(name){
    container_login.style.display = 'none'
    container_register.style.display = 'none'

    if(name === 'login')
        container_login.style.display = 'block'
    else if(name === 'register')
        container_register.style.display = 'block'
        
}

function login(){
    const email = document.getElementById('reg_email').value;
    const password = document.getElementById('reg_password').value;


    if (!email || !password) {
        alert('FIELDS UNFILLED');
        return;
    }else{
        if(email == 'consumer')
            window.location.href = '../index/consumer.html';
        if(email == 'provider')
            window.location.href = '../index/provider.html'
    }
}


function register(){

}

