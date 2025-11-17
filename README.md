## Descripción del Proyecto

Este proyecto forma parte de **Yestelle** y consiste en la integración de varios archivos Java con una base de datos gestionada en **phpMyAdmin**. El sistema permite registrar usuarios, iniciar sesión y acceder a un dashboard principal, todo mediante conexión **JDBC**.

### Componentes del proyecto

- **Registerframe.java**  
  Contiene el formulario de registro. Permite crear nuevos usuarios, cuyas credenciales se almacenan en la base de datos.

- **Login.java**  
  Implementa el formulario de inicio de sesión. Verifica las credenciales introducidas por el usuario comparándolas con los datos existentes en la base de datos.

- **DBConnection.java**  
  Gestiona la conexión con la base de datos MySQL, permitiendo ejecutar consultas y validar información del usuario desde cualquier parte del proyecto.

- **Dashboardframe.java**  
  Representa el dashboard principal del proyecto, accesible únicamente tras una autenticación correcta. Desde este panel se gestionan las funcionalidades centrales de la aplicación.

- **TestConexion.java**  
  Archivo destinado exclusivamente a comprobar de manera rápida si la aplicación consigue conectarse correctamente a la base de datos. Permite verificar credenciales, drivers y disponibilidad del servidor sin necesidad de cargar el resto del proyecto.

- **MainApp.java**  
  Archivo principal del proyecto. Es el punto de entrada donde se ejecuta la aplicación, inicializando la interfaz y los componentes necesarios para comenzar el flujo del sistema (generalmente redirigiendo al formulario de inicio de sesión).


El objetivo general del proyecto es construir un sistema funcional y completo de registro, autenticación y navegación interna, manteniendo una arquitectura clara entre la interfaz gráfica, la lógica y el acceso a datos.
