const container_content_products = document.querySelector(".container_content_products")
const container_content_favs = document.querySelector(".container_content_favs")
const container_content_providers = document.querySelector(".container_content_providers")

const modalFooter = document.querySelector('#infoModal .modal-dialog .modal-content .modal-footer');




async function get_sell_point(id_municipio, id_producto) {
    try {
        const url = `../webapi/products/get_sell_point_consumer?id_municipio=${id_municipio}&id_producto=${id_producto}`;

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const sellPoints = await response.json(); 
        update_sell_point_select(sellPoints);

    } catch (error) {
        console.error('Error al obtener los puntos de venta:', error);
    }
}

function update_sell_point_select(sellPoints) {
    const selectSellPoint = document.getElementById('select_sell_point');
    selectSellPoint.innerHTML = ''; 

    sellPoints.forEach(sellPoint => {
        const option = document.createElement('option');
        option.value = sellPoint.id; 
        option.textContent = `${sellPoint.name} - ${sellPoint.address}`;
        selectSellPoint.appendChild(option);

        selectSellPoint.addEventListener('change', function() {
            document.getElementById('modal-product-implicit').innerText = 'Precio Implícito: ' + sellPoint.priceImplicit;
            document.getElementById('modal-product-explicit').innerText = 'Precio Explícito: ' + sellPoint.priceExplicit;
            document.getElementById('modal-product-divipola').innerText = 'Divipola: ' + sellPoint.divipola;
        })
    });
    
}

document.querySelectorAll('#favorites_table tbody tr').forEach(row => {
    row.addEventListener('click', function() {
        
        const productName = this.cells[2].innerText; 
        const productCode = this.cells[1].innerText; 
        const productBrand = this.cells[3].innerText; 
        const productCompany = this.cells[4].innerText;

        document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
        document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
        document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
        document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;
        const typeBtn = document.createElement('button');
        typeBtn.onclick = removeFromFavorites()
        set_buttons_modal_info('fav', typeBtn)

        const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
        myModal.show();
    });
});


function change_view(name) {
    container_content_products.style.display = 'none';
    container_content_favs.style.display = 'none';
    container_content_providers.style.display = 'none';

    if (name === 'products') {
        container_content_products.style.display = 'block';
    } else if (name === 'favorites') {
        container_content_favs.style.display = 'block';
    } else if (name === 'providers') {
        container_content_providers.style.display = 'block';
        get_proveedores(); // Llamar a la función para obtener proveedores cuando se muestre esta vista
    }
}



function set_buttons_modal_info(type, typeBtn){
    modalFooter.innerHTML = ''
    const closeBtn = document.createElement('button');
    closeBtn.setAttribute('type', 'button'); 
    closeBtn.classList.add('btn', 'btn-secondary'); 
    closeBtn.setAttribute('data-bs-dismiss', 'modal'); 
    closeBtn.innerText = 'Cerrar';  

    typeBtn.classList.add('btn', 'btn-primary');
    if(type === 'prod'){
        typeBtn.innerText = 'Agregar a Favoritos'

    }else if( type === 'fav'){
        typeBtn.innerText = 'Remover de Favoritos'

        typeBtn.style = 'background-color:red;border-color:red;'
    }
    modalFooter.appendChild(typeBtn)
    modalFooter.appendChild(closeBtn)
}

async function addToFavorites(idProduct){
    try {
        const url = `../webapi/products/get_ten_first?offset=${pagination_offset}`;

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

async function get_proveedores() {
    try {
        const url = `../webapi/products/get_proveedores`; // URL a la API para obtener los proveedores

        const options = {
            method: 'GET',
        };

        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const proveedores = await response.json();
        update_providers_list(proveedores); // Llamada para actualizar la lista de proveedores

    } catch (error) {
        console.error('Error al obtener los proveedores:', error);
    }
}
function update_providers_list(proveedores) {
    const tbody = document.querySelector('#providers_table tbody');
    tbody.innerHTML = ''; // Limpiar contenido previo

    proveedores.forEach(provider => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${provider.name || 'No disponible'}</td>
            <td>${provider.phone || 'No disponible'}</td>
            <td>${provider.email || 'No disponible'}</td>
        `;
        tbody.appendChild(row);
    });
}



async function removeFromFavorites(){

}

