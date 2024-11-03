var products = [];

window.addEventListener("scroll", function(){
    var header = document.querySelector("header");
    header.classList.toggle("down", window.scrollY > 0);
})

function getProducts(){
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams({getType: "get_raw_product_list"});

    xhr.open("GET", `/demo_1_war_exploded/servlet-product?${params.toString()}`, true);

    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    if (xhr.responseText) {
                        products = JSON.parse(xhr.responseText);
                        showProducts();
                    } else {
                        console.error('Empty response received.');
                    }
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                    console.error('Response:', xhr.responseText);
                }
            } else {
                console.error('Error fetching:', xhr.status, xhr.statusText);
            }
        }
    };

    xhr.onerror = () => {
        console.error('Request failed');
    };
    xhr.send();
}

function showProducts(){
    const tbody = document.getElementById('products_table').querySelector('tbody');

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
}


getProducts();