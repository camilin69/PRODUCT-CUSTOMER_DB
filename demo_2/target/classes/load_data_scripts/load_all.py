
import mysql.connector
from mysql.connector import Error
import threading

import load_products 
import load_others



def db_connection():
    try:
        connection = mysql.connector.connect(
            host='localhost', 
            database='consultorio_productos',
            user='camilin',
            password='imaccc'
        )

        if connection.is_connected():
            print("Connection Success")
            return connection
    except Error as e:
        print(f"Connection Error: {e}")
        return None





def main():
    

    connection = db_connection()

    if connection:
        load_others.insert_categories_in_table( connection )
        load_others.insert_providers_in_table( connection )
        load_others.insert_consumers_in_table( connection )
        load_products.insert_all()
        
        connection.close()

if __name__ == '__main__':
    main()

