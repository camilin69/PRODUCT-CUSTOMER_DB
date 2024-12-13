const container_content_products = document.querySelector(".container_content_products")
const container_content_distr = document.querySelector(".container_content_distr")

const modalFooter = document.querySelector('#infoModal .modal-dialog .modal-content .modal-footer');


document.querySelectorAll('#products_table tbody tr').forEach(row => {
    row.addEventListener('click', function() {
        
        const productName = this.cells[2].innerText; 
        const productCode = this.cells[1].innerText; 
        const productBrand = this.cells[3].innerText; 
        const productCompany = this.cells[4].innerText; 

        document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
        document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
        document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
        document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;
        set_buttons_modal_info('prod')
        const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
        myModal.show();
    });
});

document.querySelectorAll('#distr_products tbody tr').forEach(row => {
    row.addEventListener('click', function() {
        
        const productName = this.cells[2].innerText; 
        const productCode = this.cells[1].innerText; 
        const productBrand = this.cells[3].innerText; 
        const productCompany = this.cells[4].innerText;

        document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + productName;
        document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + productCode;
        document.getElementById('modal-product-brand').innerText = 'Marca: ' + productBrand;
        document.getElementById('modal-product-company').innerText = 'Empresa: ' + productCompany;

        set_buttons_modal_info('distr')

        const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
        myModal.show();
    });
});

function get_sell_point(id_municipio, id_product){
    
}

function change_view(name){
    container_content_products.style.display = 'none';
    container_content_distr.style.display = 'none';

    if (name === 'products') {
        container_content_products.style.display = 'block';
    } else if (name === 'distribution') {
        container_content_distr.style.display = 'block';
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
     
        
    if(type === 'prod'){
        typeBtn.innerText = 'Distribuir'
    }else if( type === 'distr'){
        typeBtn.innerText = 'Remover'
        typeBtn.style = 'background-color:red;border-color'
    }
    modalFooter.appendChild(typeBtn)
    modalFooter.appendChild(closeBtn)
}