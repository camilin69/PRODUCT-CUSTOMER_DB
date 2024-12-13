
var products = [];
var pagination_offset = 0;
var number_page = document.getElementById("number_page")
let filters = {
    attribute : "nombre",
    category : "ninguna",
    priceMin : 0,
    priceMax : 0
}
let municipios = [];

let user = {
    id : 0,
    role : "",
    email : "",
    password : ""
}
window.addEventListener("scroll", function(){
    var header = document.querySelector("header");
    if(header)
        header.classList.toggle("down", window.scrollY > 0);
})

const pagination = document.querySelector('.pagination')



async function getProducts() {
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
                const id_municipio = this.value;
                const id_product = p.id;
                console.log(`Municipio ID: ${id_municipio} --- Product ID: ${id_product}`)
                get_sell_point(id_municipio, id_product)
    
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

function pagination_go_backward(){
    if(pagination_offset != 1){
        pagination_offset -= 10
        getProducts()
    }
    
}

function pagination_go_forward(){
    pagination_offset += 10;
    getProducts()
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


getProducts()