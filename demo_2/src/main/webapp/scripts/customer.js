var products = [];
var pagination_offset = 0;
var number_page = document.getElementById("number_page")

window.addEventListener("scroll", function(){
    var header = document.querySelector("header");
    header.classList.toggle("down", window.scrollY > 0);
})



async function getProducts() {
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



function showProducts(products){
    const tbody = document.getElementById('products_table').querySelector('tbody');
    tbody.innerHTML = ''
    products.forEach(p => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${p.nameDane}</td>
            <td>${p.barcode}</td>
            <td>${p.name}</td>
            <td>${p.brand}</td>
            <td>${p.company}</td>
            <td>${p.priceExplicit}</td>
        `;
        tbody.appendChild(row);
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


getProducts()