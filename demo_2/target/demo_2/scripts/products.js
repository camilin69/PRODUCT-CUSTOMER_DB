
var products = [];
var pagination_offset = 0;
var number_page = document.getElementById("number_page")
var searched_input = ""
let filters = {
    attribute : "nombre",
    category : "none"
}
let municipios = [];
let id_municipio = 0;
let id_product = 0 ;


window.addEventListener("scroll", function(){
    var header = document.querySelector("header");
    if(header)
        header.classList.toggle("down", window.scrollY > 0);
})

const pagination = document.querySelector('.pagination')

function apply_filters(){
    const attribute = document.querySelector("#filter_by_attribute");
    const category = document.querySelector("#filter_by_category");
    filters = {
        attribute : attribute.value,
        category : category.value
    }
    console.log(filters)
    const myModal = new bootstrap.Modal(document.getElementById('filterModal'));
    myModal.hide();
}

async function get_ten_products() {
    try {
        const url = `../webapi/products/get_ten?offset=${pagination_offset}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        products = await response.json();
        console.log(products); 
        showProducts(products); 
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}



function showProducts(products){
    const selectSellPoint = document.getElementById('select_sell_point');
    selectSellPoint.value = '';
    const selectMunicipios = document.getElementById("select_municipios");
    selectMunicipios.value = ''; 
    const tbody = document.getElementById('products_table').querySelector('tbody');
    tbody.innerHTML = ''
    products.forEach(p => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${p.nameDane}</td>
            <td>${p.barcode}</td>
            <td>${p.name}</td>
            <td>${p.unity}</td>
            <td>${p.brand}</td>
            <td>${p.company}</td>
        `;
        tbody.appendChild(row);

        row.addEventListener('click', function() {
            get_municipios();
            const productName = this.cells[2].innerText; 
            const productCode = this.cells[1].innerText; 
            const productBrand = this.cells[3].innerText; 
            const productCompany = this.cells[4].innerText;
            
            const selectMunicipios = document.getElementById("select_municipios");
            selectMunicipios.addEventListener('change', function() {
                id_municipio = this.value;
                id_product = p.id;
                console.log(`Municipio ID: ${id_municipio} --- Product ID: ${id_product}`);
                get_sell_point(id_municipio, id_product);
                get_product_municipio(id_municipio, id_product);
            })
            
    
            document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
            document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
            document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
            document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;
            const typeBtn = document.createElement('button');
            typeBtn.onclick = addToFavorites()
            set_buttons_modal_info('prod', typeBtn)
            const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
            myModal.show();
        });
    });
    number_page.innerHTML = 'Página ' + ((pagination_offset / 10) + 1)
}



async function get_municipios(){
    try {
        const url = `../webapi/products/get_municipios`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        municipios = await response.json(); 
        municipios_to_select();
        municipios_select2();

    } catch (error) {
        console.error('Error al buscar el usuario:', error);
    }
}

function municipios_to_select() {
    const selectMunicipios = document.getElementById("select_municipios");
    selectMunicipios.disabled = false;

    selectMunicipios.innerHTML = '<option selected disabled value="">Municipios...</option>';

    municipios.forEach(municipio => {
        const option = document.createElement("option");
        option.value = municipio.id; 
        option.textContent = municipio.name; 
        selectMunicipios.appendChild(option);
    });
}

function municipios_select2() {
    $('#select_municipios').select2({
        placeholder: "Seleccione un municipio",
        allowClear: true
    });
}

function sell_point_select2() {
    $('#select_sell_point').select2({
        placeholder: "Seleccione un punto de venta",
        allowClear: true
    });
}

async function get_product_municipio(id_municipio, id_product){
    try {
        const url = `../webapi/products/get_product_municipio?id_municipio=${id_municipio}&id_producto=${id_product}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        p_m = await response.json(); 
        if(p_m){
            console.log(p_m)
            document.getElementById('modal-product-implicit').innerText = 'Precio Implícito: ' + p_m[0].precioExplicito;
            document.getElementById('modal-product-explicit').innerText = 'Precio Explícito: ' + p_m[0].precioImplicito;
            document.getElementById('modal-product-divipola').innerText = 'Divipola: ' + p_m[0].divipola;
        }

    } catch (error) {
        console.error('Error al buscar el producto_municipio:', error);
    }
}

async function find_user(email, password, role) {
    try {
        const url = `../webapi/login/find_user?email=${email}&password=${password}&role=${role}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const userInfo = await response.json();

        
        if (userInfo) {
            saveUserToLocalStorage(userInfo, role, email, password);
            
            return true;  
        }
    } catch (error) {
        console.error('Error al buscar el usuario:', error);
    }

    return false;  
}

function find_products(){
    pagination_offset = 0
    const search_input = document.querySelector("#search_input");
    searched_input = search_input.value
    if(search_input.value || filters.category > 0){
        
        get_searched_products()
    }else{
        get_ten_products()
    }
}

async function get_searched_products(){
    try {
        const url = `../webapi/products/get_searched_products?offset=${pagination_offset}&attribute=${filters.attribute}&category=${filters.category}&search_input=${searched_input}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        products = await response.json();
        console.log(products); 
        showProducts(products);
        

    } catch (error) {
        console.error('Error al buscar el producto:', error);
    }
}

function addToFavorites(){

}

function pagination_go_backward(){
    if(pagination_offset != 1){
        pagination_offset -= 10
        if(searched_input){
            get_searched_products()
        }else{
            get_ten_products() 
        }
        
    }
    
}

function pagination_go_forward(){
    pagination_offset += 10;
    if(searched_input){
        get_searched_products()
    }else{
        get_ten_products() 
    }
}

function saveUserToLocalStorage(id, role, email, password) {
    const userData = {
        id: id,
        role: role,
        email: email,
        password: password
    };
    localStorage.setItem('user', JSON.stringify(userData)); // Guardamos los datos en localStorage
}

function getUserFromLocalStorage() {
    const userData = localStorage.getItem('user'); 
    return userData ? JSON.parse(userData) : null; 
}

if(window.location.href !== "../index/login.html")
    get_ten_products()