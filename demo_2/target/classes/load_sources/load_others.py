import mysql.connector
import json

def insert_categories_in_table( connection ):
    with open('../categories.json') as file:
        data = json.load(file)
    
    cursor = connection.cursor()

    sql = "INSERT INTO categorias (id, nombre, descripcion) VALUES (%s, %s, %s)"
    
    categories_to_insert = []
    if data:
        for item in data:
            categories_to_insert.append((item['id'], item['name'], item['description']))

        try:
            cursor.executemany(sql, categories_to_insert)
            connection.commit()

        except mysql.connector.Error as err:
            print(f"Error: {err}")
            connection.rollback() 
        finally:
            cursor.close()

def insert_providers_in_table( connection ):
    with open('../providers.json') as file:
        data = json.load(file)
    
    cursor = connection.cursor()

    sql = "INSERT INTO proveedores (id, nombre, telefono, email, contra) VALUES (%s, %s, %s, %s, %s)"
    
    providers_to_insert = []
    if data:
        for item in data:
            providers_to_insert.append((item['id'], item['name'], item['phone'], item['email'],item['password']))

        try:
            cursor.executemany(sql, providers_to_insert)
            connection.commit()

        except mysql.connector.Error as err:
            print(f"Error: {err}")
            connection.rollback() 
        finally:
            cursor.close()


def insert_consumers_in_table( connection ):
    with open('../consumers.json') as file:
        data = json.load(file)
    
    cursor = connection.cursor()

    sql = "INSERT INTO consumidores (id, nombre, edad, estrato, email, contra) VALUES (%s, %s, %s, %s, %s, %s)"
    
    consumers_to_insert = []
    if data:
        for item in data:
            consumers_to_insert.append((item['id'], item['name'], item['age'], item['status'], item['email'], item['password']))

        try:
            cursor.executemany(sql, consumers_to_insert)
            connection.commit()

        except mysql.connector.Error as err:
            print(f"Error: {err}")
            connection.rollback() 
        finally:
            cursor.close()