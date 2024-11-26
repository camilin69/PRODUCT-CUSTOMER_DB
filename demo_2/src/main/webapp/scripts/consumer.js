const container_content_products = document.querySelector(".container_content_products")
const container_content_favs = document.querySelector(".container_content_favs")
const container_content_providers = document.querySelector(".container_content_providers")

const modalFooter = document.querySelector('#infoModal .modal-dialog .modal-content .modal-footer');

document.querySelectorAll('#products_table tbody tr').forEach(row => {
    row.addEventListener('click', function() {
        
        const productName = this.cells[2].innerText; 
        const productCode = this.cells[1].innerText; 
        const productBrand = this.cells[3].innerText; 
        const productCompany = this.cells[4].innerText;
        const productPrice = this.cells[5].innerText; 

        document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
        document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
        document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
        document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;
        document.getElementById('modal-product-price').innerText = 'Precio: ' + productPrice;
        const typeBtn = document.createElement('button');
        typeBtn.onclick = addToFavorites()
        set_buttons_modal_info('prod', typeBtn)
        const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
        myModal.show();
    });
});

document.querySelectorAll('#favorites_table tbody tr').forEach(row => {
    row.addEventListener('click', function() {
        
        const productName = this.cells[2].innerText; 
        const productCode = this.cells[1].innerText; 
        const productBrand = this.cells[3].innerText; 
        const productCompany = this.cells[4].innerText;
        const productPrice = this.cells[5].innerText; 

        document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
        document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
        document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
        document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;
        document.getElementById('modal-product-price').innerText = 'Precio: ' + productPrice;
        const typeBtn = document.createElement('button');
        typeBtn.onclick = removeFromFavorites()
        set_buttons_modal_info('fav', typeBtn)

        const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
        myModal.show();
    });
});


function change_view(name){
    container_content_products.style.display = 'none';
    container_content_favs.style.display = 'none';
    container_content_providers.style.display = 'none';

    if (name === 'products') 
        container_content_products.style.display = 'block';
    else if (name === 'favorites') 
        container_content_favs.style.display = 'block';
    else if(name === 'providers')
        container_content_providers.style.display = 'block';
    
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

async function removeFromFavorites(){

}