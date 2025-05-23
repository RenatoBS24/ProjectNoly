# ProjectNoly

¡Bienvenido a **ProjectNoly**!

## Descripción

ProjectNoly es un proyecto desarrollado en Java, diseñado para gestionar las ventas y el almacen de un restaurante. Este repositorio contiene todo el código fuente y los recursos necesarios para ejecutar y desplegar la aplicación.

---

## Requisitos Previos
- Java 17 o superior instalado
- Tener instalado MySql y crear la base de datos [Decargar script para la creacion de la base de datos](https://gist.github.com/RenatoBS24/8d41458ab9ae1ba3f5bdb2f16063b1c3)
- Git (opcional)
- Tener instalado MongoDB y crear una base de datos llamda(opcional) con las siguientes colecciones -> sales,tables.
---

## Instalación y Despliegue

### 1. Clona el repositorio

```sh
git clone https://github.com/RenatoBS24/ProjectNoly.git
cd ProjectNoly
```

### 2. Compila el proyecto
```sh
mvn clean package
```

Esto generará un archivo `.jar` en el directorio `target` (Maven) 

### 3. Despliegue usando el archivo JAR

Ejecuta el siguiente comando para iniciar la aplicación:

```sh
java -jar ruta/al/archivo/ProjectNoly.jar
```
> Reemplaza `ruta/al/archivo/ProjectNoly.jar` con la ruta real al archivo generado.

---

### 4. Configurar la aplicacion como un servicio en LInux(Opcional)
```sh
java -jar ruta/al/archivo/ProjectNoly.jar### 4. Configurar la aplicación como un servicio en Linux (Opcional)
```

Para configurar ProjectNoly como un servicio que se inicie automáticamente:

1. Crea un archivo de servicio systemd:
```bash
   sudo nano /etc/systemd/system/projectnoly.service
```
2. Configuramos el archivo nano
```bash
       [Unit]
        Description=ProjectNoly Restaurant Management System
        After=network.target mysql.service
        
        [Service]
        User=tu_usuario
        Group=tu_grupo
        Type=simple
        ExecStart=/usr/bin/java -jar /ruta/completa/a/ProjectNoly.jar
        WorkingDirectory=/ruta/completa/donde/esta/el/jar
        SuccessExitStatus=143
        Restart=always
        RestartSec=10
        Environment="DB_URL=jdbc:mysql://localhost:3306/projectnoly"
        Environment="DB_USER=usuario"
        Environment="DB_PASSWORD=contraseña"
        Environment="PUERTO_APP=8080"
        
        [Install]
        WantedBy=multi-user.target
```
## Configuraciones adicionales

### Configuración de variables de entorno

- **DB_URL**: URL de la base de datos
- **DB_USER**: Usuario de la base de datos
- **DB_PASSWORD**: Contraseña de la base de datos
- **PUERTO_APP**: Puerto en el que se ejecutará la aplicación

Ejemplo de configuración en Linux/MacOS:

```sh
export DB_URL=jdbc:mysql://localhost:3306/projectnoly
export DB_USER=usuario
export DB_PASSWORD=contraseña
export PUERTO_APP=8080
```

En Windows (CMD):

```cmd
set DB_URL=jdbc:mysql://localhost:3306/projectnoly
set DB_USER=usuario
set DB_PASSWORD=contraseña
set PUERTO_APP=8080
```

### Archivo de configuración externo (opcional)

Puedes modificar el archivo `application.properties` para definir las configuraciones en lugar de crear variables de enotorno. Ejemplo:

```properties
db.url=jdbc:mysql://localhost:3306/projectnoly
db.user=usuario
db.password=contraseña
puerto=8080
```

---


