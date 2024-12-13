document.addEventListener('DOMContentLoaded', () => {
    const container_content_products = document.querySelector(".container_content_products");
    const container_content_favs = document.querySelector(".container_content_favs");
    const container_content_providers = document.querySelector(".container_content_providers");

    const modalFooter = document.querySelector('#infoModal .modal-dialog .modal-content .modal-footer');
    const providerModal = new bootstrap.Modal(document.getElementById('providerModal'));
    const currentConsumerId = 1; // Cambiar dinámicamente según el usuario logueado

    window.change_view = function(name) {
        container_content_products.style.display = 'none';
        container_content_favs.style.display = 'none';
        container_content_providers.style.display = 'none';

        if (name === 'products') {
            container_content_products.style.display = 'block';
        } else if (name === 'favorites') {
            container_content_favs.style.display = 'block';
            getFavorites(currentConsumerId);
        } else if (name === 'providers') {
            container_content_providers.style.display = 'block';
            get_proveedores();
        }
    };

    window.addToFavorites = async function(idProduct) {
        try {
            const pagination_offset = 0; // Asegúrate de inicializar esta variable
            const url = `../webapi/products/get_ten_first?offset=${pagination_offset}`;
            const response = await fetch(url);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const products = await response.json();
            console.log(products);
            showProducts(products);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    window.get_sell_point = async function(id_municipio, id_producto) {
        try {
            const url = `../webapi/products/get_sell_point_consumer?id_municipio=${id_municipio}&id_producto=${id_producto}`;

            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const sellPoints = await response.json();
            update_sell_point_select(sellPoints);
        } catch (error) {
            console.error('Error al obtener los puntos de venta:', error);
        }
    };

    function update_sell_point_select(sellPoints) {
        const selectSellPoint = document.getElementById('select_sell_point');
        selectSellPoint.innerHTML = ''; // Limpia el dropdown antes de llenarlo

        sellPoints.forEach(sellPoint => {
            const option = document.createElement('option');
            option.value = sellPoint.id; // Asegúrate de que este valor sea el ID correcto del punto de venta
            option.textContent = `${sellPoint.name} - ${sellPoint.address}`;
            selectSellPoint.appendChild(option);
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
            typeBtn.onclick = () => addToFavorites(currentConsumerId, selectedProductPuntoVentaId);

            set_buttons_modal_info('fav', typeBtn);

            const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
            myModal.show();
        });
    });

    async function get_proveedores() {
        try {
            const url = `../webapi/products/get_proveedores`;
            const response = await fetch(url);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const proveedores = await response.json();
            update_providers_list(proveedores);
        } catch (error) {
            console.error('Error al obtener los proveedores:', error);
        }
    }

    function update_providers_list(proveedores) {
        const tbody = document.querySelector('#providers_table tbody');
        tbody.innerHTML = '';

        proveedores.forEach(provider => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${provider.name || 'No disponible'}</td>
                <td>${provider.phone || 'No disponible'}</td>
                <td>${provider.email || 'No disponible'}</td>
            `;
            row.addEventListener('click', () => show_provider_modal(provider));
            tbody.appendChild(row);
        });
    }

    function show_provider_modal(provider) {
        document.getElementById('provider-modal-name').innerText = `Nombre: ${provider.name || 'No disponible'}`;
        document.getElementById('provider-modal-phone').innerText = `Teléfono: ${provider.phone || 'No disponible'}`;
        document.getElementById('provider-modal-email').innerText = `Email: ${provider.email || 'No disponible'}`;
        providerModal.show();
    }
    async function addToFavorites(idConsumidor, idProductoPuntoVenta) {
        if (!idConsumidor || !idProductoPuntoVenta) {
            console.error('Faltan parámetros necesarios para añadir a favoritos.');
            alert('No se pudo añadir a favoritos. Intente nuevamente.');
            return;
        }

        try {
            const url = '../webapi/products/add_favorite';
            const data = { idConsumidor, idProductoPuntoVenta };

            const response = await fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error(`Error adding favorite: ${response.statusText}`);
            }

            const result = await response.json();
            console.log(result.message);
            alert('Producto añadido a favoritos con éxito.');

            // Actualiza la tabla de favoritos
            getFavorites(idConsumidor);
        } catch (error) {
            console.error('Error adding favorite:', error.message || error);
            alert('No se pudo añadir a favoritos. Intente nuevamente.');
        }
    }




    async function getFavorites(idConsumidor) {
        try {
            const url = `../webapi/products/get_favorites?idConsumidor=${idConsumidor}`;
            const response = await fetch(url);

            if (!response.ok) {
                throw new Error(`Error fetching favorites: ${response.statusText}`);
            }

            const favorites = await response.json();
            if (Array.isArray(favorites)) {
                updateFavoritesTable(favorites);
            } else {
                console.error("Unexpected response format:", favorites);
            }
        } catch (error) {
            console.error('Error fetching favorites:', error.message || error);
        }
    }
    function updateFavoritesTable(favorites) {
        const tbody = document.querySelector('#favorites_table tbody');
        tbody.innerHTML = ''; // Limpiar la tabla antes de actualizar

        if (favorites.length === 0) {
            const row = document.createElement('tr');
            row.innerHTML = '<td colspan="4" style="text-align: center;">No hay productos en favoritos</td>';
            tbody.appendChild(row);
            return;
        }

        favorites.forEach(favorite => {
            const row = document.createElement('tr');
            row.innerHTML = `
            <td>${favorite.productName}</td>
            <td>${favorite.brand}</td>
            <td>${favorite.sellPointName}</td>
            <td>
                <button onclick="removeFromFavorites(${favorite.idConsumidor}, ${favorite.idProductoPuntoVenta})">
                    Eliminar
                </button>
            </td>
        `;
            tbody.appendChild(row);
        });
    }

    window.removeFromFavorites = async function(idConsumidor, idProductoPuntoVenta) {
        try {
            const url = '../webapi/products/remove_favorite';
            const data = { idConsumidor, idProductoPuntoVenta };

            const response = await fetch(url, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error(`Error removing favorite: ${response.statusText}`);
            }

            console.log("Favorite removed successfully.");
            await getFavorites(idConsumidor); // Actualiza la tabla de favoritos
        } catch (error) {
            console.error('Error removing favorite:', error.message || error);
            alert('No se pudo eliminar el producto de favoritos.');
        }
    };




    window.set_buttons_modal_info = function(type, typeBtn) {
        modalFooter.innerHTML = '';
        const closeBtn = document.createElement('button');
        closeBtn.setAttribute('type', 'button');
        closeBtn.classList.add('btn', 'btn-secondary');
        closeBtn.setAttribute('data-bs-dismiss', 'modal');
        closeBtn.innerText = 'Cerrar';

        typeBtn.classList.add('btn', 'btn-primary');
        if (type === 'prod') {
            typeBtn.innerText = 'Agregar a Favoritos';
            typeBtn.onclick = function () {
                const idProductoPuntoVenta = document.getElementById('select_sell_point').value; // Obtiene el punto de venta seleccionado
                if (idProductoPuntoVenta) {
                    addToFavorites(currentConsumerId, parseInt(idProductoPuntoVenta));
                } else {
                    alert("Seleccione un punto de venta antes de añadir a favoritos.");
                }
            };

        } else if (type === 'fav') {
            typeBtn.innerText = 'Remover de Favoritos';
            typeBtn.style = 'background-color:red;border-color:red;';
        }
        modalFooter.appendChild(typeBtn);
        modalFooter.appendChild(closeBtn);
    };
    function showProductModal(product) {
        document.getElementById('modal-product-name').innerText = 'Nombre del Producto: ' + product.name;
        document.getElementById('modal-product-code').innerText = 'Código de Barras: ' + product.code;
        document.getElementById('modal-product-brand').innerText = 'Marca: ' + product.brand;
        document.getElementById('modal-product-company').innerText = 'Empresa: ' + product.company;

        // Llama a la función para obtener los puntos de venta
        get_sell_point(product.municipioId, product.id);

        // Configura el botón dinámico
        const typeBtn = document.createElement('button');
        typeBtn.setAttribute('id', 'add-to-favorites-btn'); // Opcional: para depuración
        set_buttons_modal_info('prod', typeBtn);

        // Muestra el modal
        const myModal = new bootstrap.Modal(document.getElementById('infoModal'));
        myModal.show();
    }


});
