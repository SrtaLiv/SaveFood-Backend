# Save Food - Backend

Save Food es una aplicación backend desarrollada con **Spring Boot**, diseñada para gestionar comidas y almacenar imágenes en **Cloudinary**. Este proyecto utiliza una base de datos **MySQL** y está preparado para ejecutarse en contenedores **Docker**.

## 🚀 Características

- CRUD para gestionar alimentos.
- Integración con Cloudinary para subir y gestionar imágenes.
- Configuración lista para Docker Compose con MySQL y la aplicación backend.

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **MySQL**
- **Cloudinary**
- **Docker**

## 📂 Estructura del proyecto

```plaintext
src/
├── main/
│   ├── java/com/oliviatodesco/save_food/
│   │   ├── controller/   # Controladores REST
│   │   ├── service/      # Servicios (incluye integración con Cloudinary)
│   │   ├── model/        # Entidades de la base de datos
│   │   ├── repository/   # Repositorios JPA
│   │   └── SaveFoodApplication.java  # Clase principal
│   └── resources/
│       ├── application.properties  # Configuración de Spring
├── test/                           # Pruebas unitarias

```

## 📄 Endpoints principales

| Método | Endpoint          | Descripción                              |
|--------|-------------------|------------------------------------------|
| POST   | `/food`           | Crear un nuevo alimento con imagen.     |
| GET    | `/food`           | Listar todos los alimentos.             |
| GET    | `/food/{id}`      | Obtener un alimento por su ID.          |
| DELETE | `/food/{id}`      | Eliminar un alimento por su ID.         |
| PUT    | `/food`           | Actualizar un alimento existente.       |
| PUT    | `/food/{id}/image`| Actualizar la imagen de un alimento.    |

## ⚠️ Aviso para pruebas
Para probar la aplicación:

<body>
 <ol>
        <li>
            Cambia las credenciales de <strong>Cloudinary</strong> en el archivo <code>docker-compose.yml</code>:
            <pre>
CLOUD_NAME: ${CLOUD_NAME}
CLOUD_KEY: ${CLOUD_KEY}
CLOUD_SECRET: ${CLOUD_SECRET}
            </pre>
        </li>
        <li>Asegúrate de tener <strong>Docker</strong> instalado.</li>
        <li>
            En la terminal, navega a la carpeta raíz del proyecto y ejecuta:
            <pre>
docker-compose build
docker-compose up
            </pre>
        </li>
</ol>
</body>

## 🌐 Frontend en Angular

El proyecto cuenta con un frontend desarrollado en Angular, diseñado para interactuar con este backend. Algunas de sus características incluyen:
Interfaz amigable para gestionar alimentos.
Integración con el backend para realizar operaciones CRUD.
Subida de imágenes y visualización desde Cloudinary.
Puedes encontrar el código del frontend en el repositorio asociado: https://github.com/SrtaLiv/SaveFood-Frontend 

## 🧑‍💻 Autora
<strong>Ana Olivia Todesco</strong> | <a href="https://www.instagram.com/oliviatodesco/">Instagram</a> | <a href="https://www.linkedin.com/in/anaoliviatodesco/">Linkedin</a>  | <a href="https://www.youtube.com/@oliviatodesco">Youtube</a>
