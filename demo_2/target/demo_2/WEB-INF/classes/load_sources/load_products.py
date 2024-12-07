import pandas as pd
from mysql.connector import Error
import threading
import time
from load_all import db_connection

#PRODUCTS: 601
#MUNICIPIOS:465
#PRODUCTS_MUNICIPIOS: 44570

BATCH_SIZE = 10000
QUERY_BATCH_SIZE = 1000
DOING_SET = "SETTING"
DOING_QUERY = "QUERYING"
QUERY_PRODUCTS = "PRODUCTS"
QUERY_MUNICIPIOS = "MUNICIPIOS"
QUERY_PRODUCTS_MUNICIPIOS = "PRODUCTS_MUNICIPIOS"


file = '../products.xlsx'
df = pd.read_excel(file)

products_category = [
    ["Alimentos y Bebidas", [
        "Aceite de girasol",
        "Arroz para seco",
        "Arveja",
        "Azúcar refinada",
        "Cebolla cabezona",
        "Cebolla en rama",
        "Huevo",
        "Leche larga vida",
        "Naranja",
        "Papa criolla",
        "Papa negra",
        "Queso Campesino"
    ]],
    ["Medicamentos", [
        "Acetaminofén",
        "Amoxicilina",
        "Ibuprofeno",
        "Hidroxicloroquina",
        "IVERMECTINA",
        "Naproxeno"
    ]],
    ["Artículos de Higiene y Cuidado Personal", [
        "Jabón de tocador en barra",
        "Mascarilla quirúrgica rectangular plana, no estéril, de un solo uso (Tapabocas convencionales)",
        "Guantes para examen, no estériles",
        "Guantes Quirúrgicos",
        "Solución o gel desinfectante a base de alcohol"
    ]],
    ["Equipos y Materiales Médicos", [
        "Guantes Quirúrgicos",
        "Mascarilla quirúrgica rectangular plana, no estéril, de un solo uso (Tapabocas convencionales)"
    ]]
]


products = []
municipios = []
products_municipios = []


def set_all( batchData , idProd, idMun):
    idCategory = 0
    for i in batchData:
        

        if i[2] not in {nameP for _, _, _, nameP, _, _, _, _ in products}:
            idProd += 1
            for index, (cat, prod) in enumerate(products_category):
                if i[0] in prod:
                    idCategory = index + 1
                    break
            products.append((idProd, i[0], i[1], i[2], i[3], i[4], i[5], idCategory))
            
        if i[7] not in {nameM for _, nameM in municipios}:
            idMun += 1
            municipios.append((idMun, i[7]))
        
        product_id = next(idP for idP, _, _, nameP, _, _, _, _ in products if nameP == i[2])
        municipio_id = next(idM for idM, nameM in municipios if nameM == i[7])
        p_m = (product_id, municipio_id, i[8], i[9], i[6])
        products_municipios.append(p_m)

    
def query_products( start, end ):
    connection = db_connection()
    cursor = connection.cursor
    if start < len(products):
        cursor = connection.cursor()
        sql_prod = """INSERT INTO productos (id, nombreDANE, codigoBarras, nombre, unidad, marca, empresa, idCategoria) 
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)"""
        
        for i in range(start, end):
            if i < len(products):
                try:
                    cursor.execute(sql_prod, products[i])
                    connection.commit()
                except Error as e:
                    print(f"Error inserting data: {e} ||| PRODUCT:{products[i]}")
            else:
                break
        cursor.close()
        

def query_municipios( start, end ):
    connection = db_connection()
    cursor = connection.cursor
    if start < len(municipios):
        cursor = connection.cursor()
        sql_mun = "INSERT INTO municipios (id, nombre) VALUES (%s, %s)"
        for i in range(start, end):
            if i < len(municipios):
                try:
                    cursor.execute(sql_mun, municipios[i])
                    connection.commit()
                except Error as e:
                    print(f"Error inserting data: {e} ||| MUNICIPIO: {municipios[i]}")
            else:
                break
        cursor.close()

def query_products_municipios( start, end ):
    connection = db_connection()
    cursor = connection.cursor
    if start < len(products_municipios):
        cursor = connection.cursor()
        sql_prod_mun = """INSERT INTO producto_municipio (idProducto, idMunicipio, precioImplicito, precioExplicito, divipola)
                                    VALUES (%s, %s, %s, %s, %s)"""
        for i in range(start, end):
            if i < len(products_municipios):
                try:
                    
                    cursor.execute(sql_prod_mun, products_municipios[i])
                    connection.commit()
                except Error as e:
                    print(f"Error inserting data: {e} ||| PRODUCT_MUNICIPIO: {products_municipios[i]}")
            else:
                break
        cursor.close()
    
def thread_process(func, doing, queryAux):
    countQuery = 0

    start_time = time.time()
    print(f'----------Start {doing}----------')
    threads = []
    if doing == DOING_SET:
        num_batches = len(df) // BATCH_SIZE + (1 if len(df) % BATCH_SIZE != 0 else 0)
    if doing == DOING_QUERY:
        if queryAux == QUERY_PRODUCTS:
            num_batches = len(products) // QUERY_BATCH_SIZE + (1 if len(products) % QUERY_BATCH_SIZE != 0 else 0)
        elif queryAux == QUERY_MUNICIPIOS:
            num_batches = len(municipios) // QUERY_BATCH_SIZE + (1 if len(municipios) % QUERY_BATCH_SIZE != 0 else 0)
        elif queryAux == QUERY_PRODUCTS_MUNICIPIOS:
            num_batches = len(products_municipios) // QUERY_BATCH_SIZE + (1 if len(products_municipios) % QUERY_BATCH_SIZE != 0 else 0)

    for i in range(num_batches):
        if doing == DOING_SET:
            batchData = df.iloc[i * BATCH_SIZE : (i + 1) * BATCH_SIZE].values.tolist()
            thread = threading.Thread(target=func, args=( batchData, i * BATCH_SIZE, i * BATCH_SIZE ))
        if doing == DOING_QUERY:
            thread = threading.Thread(target=func, args=( countQuery, (countQuery + QUERY_BATCH_SIZE)))
            countQuery += QUERY_BATCH_SIZE
        threads.append(thread)
        thread.start()
        time.sleep(1) if doing == DOING_SET else time.sleep(0.1)
    for t in threads:
        t.join()
    end_time = time.time()
    print(f"----------End {doing} at: {end_time - start_time:.2f} s----------")


def insert_all():
    thread_process(set_all, DOING_SET, "")
    thread_process(query_products, DOING_QUERY, QUERY_PRODUCTS)
    thread_process(query_municipios, DOING_QUERY, QUERY_MUNICIPIOS)
    thread_process(query_products_municipios, DOING_QUERY, QUERY_PRODUCTS_MUNICIPIOS)






def get_hunnin( batchData=df.iloc[0 : 100].values.tolist() ):
    products_municipios = []
    products = []
    idProd = 0
    idCategory = 0

    municipios = []
    idMun = 0
    for i in batchData:
        
        if i[2] not in {nameP for _, _, _, nameP, _, _, _, _ in products}:
            idProd += 1
            products.append((idProd, i[0], i[1], i[2], i[3], i[4], i[5], idCategory))
            for index, (cat, prod) in enumerate(products_category):
                if i[0] in prod:
                    idCategory = index + 1
                    break
            
        if i[7] not in {nameM for _, nameM in municipios}:
            idMun += 1
            municipios.append((idMun, i[7]))
        
        product_id = next(idP for idP, _, _, nameP, _, _, _, _ in products if nameP == i[2])
        municipio_id = next(idM for idM, nameM in municipios if nameM == i[7])
        products_municipios.append((product_id, municipio_id, i[8], i[9], i[6]))
