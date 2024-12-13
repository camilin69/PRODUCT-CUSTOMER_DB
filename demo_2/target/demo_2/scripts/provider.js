const container_content_products = document.querySelector(".container_content_products")
const container_content_distr = document.querySelector(".container_content_distr")

const modalFooter = document.querySelector('#infoModal .modal-dialog .modal-content .modal-footer');

let id_sell_point = 0;

let sellPoints = {}




function change_view(name){
    container_content_products.style.display = 'none';
    container_content_distr.style.display = 'none';

    if (name === 'products') {
        container_content_products.style.display = 'block';

        const selectMunicipio = document.querySelector(".modal-body .form-floating #select_municipios");
        selectMunicipio.disabled = false;

        const selectSellPoint = document.querySelector(".modal-body .form-floating #select_sell_point");
        selectSellPoint.disabled = false;
    } else if (name === 'distribution') {
        container_content_distr.style.display = 'block';
        get_sell_point();
    }
}

function set_buttons_modal_info(type){
    modalFooter.innerHTML = ''
    const closeBtn = document.createElement('button');
    closeBtn.setAttribute('type', 'button'); 
    closeBtn.classList.add('btn', 'btn-secondary'); 
    closeBtn.setAttribute('data-bs-dismiss', 'modal'); 
    closeBtn.innerText = 'Cerrar';  
    const typeBtn = document.createElement('button');
    typeBtn.classList.add('btn', 'btn-primary');  
    typeBtn.setAttribute('data-bs-dismiss', 'modal');
        
    if(type === 'prod'){
        typeBtn.innerText = 'Distribuir'
        typeBtn.onclick = add_products_sell_point;
    }else if( type === 'distr'){
        typeBtn.innerText = 'Remover'
        typeBtn.style = 'background-color:red;border-color'
        typeBtn.onclick = remove_products_sell_point;
    }
    modalFooter.appendChild(typeBtn)
    modalFooter.appendChild(closeBtn)
}


async function add_products_sell_point(){
    try {

        const url = `../webapi/products/add_products_sell_point?id_municipio=${id_municipio}&id_product=${id_product}&id_punto_venta=${id_sell_point}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const check = await response.json(); 
        console.log(check)

    } catch (error) {
        console.error('Error al añadir productos a puntos de venta:', error);
    }
    
}

async function add_sell_point(){
    try {
        const user = getUserFromLocalStorage();
        const url = `../webapi/products/add_sell_point?id_provider=${user.id}&name=${document.querySelector("#reg_name_sp").value}&address=${document.querySelector('#reg_address_sp').value}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const check = await response.json(); 
        console.log(check)

    } catch (error) {
        console.error('Error al añadir productos a puntos de venta:', error);
    }
} 

async function remove_products_sell_point(){
    try {
        const url = `../webapi/products/remove_product_sell_point?id_municipio=${id_municipio}&id_product=${id_product}&id_punto_venta=${id_sell_point}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const check = await response.json(); 
        console.log(check)

    } catch (error) {
        console.error('Error al añadir productos a puntos de venta:', error);
    }

}

async function get_sell_point(id_municipio=0, id_product=0) {
    try {
        const user = getUserFromLocalStorage();

        const url = `../webapi/products/get_sell_point_provider?id_provider=${user.id}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        sellPoints = await response.json(); 
        
        update_sell_point_select();

    } catch (error) {
        console.error('Error al obtener los puntos de venta:', error);
    }
}

function update_sell_point_select() {
    const selectSellPoint = document.querySelectorAll("#select_sell_point");
    selectSellPoint.forEach(ssp => {
        ssp.innerHTML = ''; 
        id_sell_point = sellPoints[0].id;
        sellPoints.forEach(sellPoint => {
            const option = document.createElement('option');
            option.value = sellPoint.id; 
            option.textContent = `${sellPoint.name} - ${sellPoint.address}`;
            option.addEventListener('click', () => {
                id_sell_point = sellPoint.id;
                if(container_content_products.style.display === 'none')
                    update_sell_point_table();
            })
            ssp.appendChild(option);

        });
    })
    
    
}

async function update_sell_point_table(){
    try {
        get_municipios()
        const url = `../webapi/products/get_product_sell_point?id_sell_point=${id_sell_point}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const products = await response.json(); 
        
        if(products){
            const tbody = document.getElementById('distr_products').querySelector('tbody');
            tbody.innerHTML = ''
            products.forEach(p => {
                console.log(p)
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

                    const selectMunicipio = document.querySelector(".modal-body .form-floating #select_municipios");
                    selectMunicipio.disabled = true;
                    selectMunicipio.value = p.idMunicipio;

                    const selectSellPoint = document.querySelector(".modal-body .form-floating #select_sell_point");
                    selectSellPoint.disabled = true;
                    selectSellPoint.value = id_sell_point;

                    const productName = this.cells[2].innerText; 
                    const productCode = this.cells[1].innerText; 
                    const productBrand = this.cells[3].innerText; 
                    const productCompany = this.cells[4].innerText;
                    
                    document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
                    document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
                    document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
                    document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;
                    document.getElementById('modal-product-implicit').innerText = 'Precio Implícito: ' + p.priceExplicit;
                    document.getElementById('modal-product-explicit').innerText = 'Precio Explícito: ' + p.priceImplicit;
                    document.getElementById('modal-product-divipola').innerText = 'Divipola: ' + p.divipola;
                    set_buttons_modal_info('distr')
                    const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
                    myModal.show();
                });
            });
            number_page.innerHTML = 'Página ' + ((pagination_offset / 10) + 1)
        }

    } catch (error) {
        console.error('Error al obtener los puntos de venta:', error);
    }
}