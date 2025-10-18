# 🚇 MetroLima GO

**Enlace al prototipo en Figma:** [🔗 Ver diseño en Figma] https://www.figma.com/design/XFOqcYF3YijjVsSWoEDTM9/Rutas-de-Lima?node-id=0-1&p=f&t=0O3J2OJ13KE2mFrp-0

---

## 🧭 Descripción del Proyecto

**MetroLima GO** es una aplicación móvil desarrollada en **Android Studio (Kotlin + Jetpack Compose)** que permite a los ciudadanos y visitantes de Lima planificar sus viajes en el **Metro de Lima**, visualizar estaciones, consultar horarios y simular rutas integradas.

El objetivo principal es brindar una **experiencia rápida, visual y moderna** para desplazarse de manera eficiente utilizando el sistema de transporte metropolitano.

---

## 🎯 Objetivos

- Facilitar la planificación de viajes en el Metro de Lima.  
- Mostrar información actualizada sobre estaciones, líneas y horarios.  
- Simular rutas entre estaciones con tiempo estimado de viaje.  
- Permitir guardar rutas o estaciones favoritas.  
- Ofrecer una interfaz limpia, intuitiva y adaptable (modo claro/oscuro).

---

## ⚙️ Tecnologías Utilizadas

- **Lenguaje:** Kotlin  
- **Framework:** Jetpack Compose (Material Design 3)  
- **Base de datos:** Room  
- **Consumo de API:** Retrofit  
- **Arquitectura:** MVVM  
- **Corrutinas:** Kotlin Coroutines  
- **Navegación:** Navigation Compose  

---

## 🗂️ Módulos Principales

1. **Inicio (Home):** acceso a estaciones, rutas y configuración.  
2. **Estaciones:** listado y detalles (nombre, línea, distrito, horario).  
3. **Rutas:** simulación de trayectos con origen y destino.  
4. **Datos Externos:** alertas y horarios actualizados mediante API.  
5. **Configuración:** cambio de idioma, tema oscuro y créditos.

---

## 👩‍💻 Integrantes del Proyecto

| Nombre | Rol Principal |
|--------|----------------|
| **Moya** | Diseño de interfaz (UI/UX) y navegación Compose |
| **Ruiz** | Base de datos (Room) y manejo de datos locales |
| **Rivera** | API, lógica de rutas y conexión con la interfaz |

---

## 🗓️ Roadmap de Trabajo

### Día 1 – Setup del Proyecto
- Crear estructura en Android Studio.  
- Configurar dependencias (Compose, Room, Retrofit).  
- Diseñar logo e íconos básicos.  
✅ *Proyecto base corriendo con pantalla inicial.*

### Día 2 – Diseño de UI
- Crear pantallas principales (Home, Estaciones, Detalle).  
- Implementar navegación entre pantallas.  
✅ *Navegación funcional y UI prototipo.*

### Día 3 – Base de Datos Local
- Crear entidad `Estacion`, DAO y `MetroDatabase`.  
- Insertar datos locales mock.  
✅ *CRUD básico funcionando con Room.*

### Día 4 – Consumo de API
- Integrar Retrofit con datos simulados o reales.  
- Mostrar alertas y horarios actualizados.  
✅ *Consumo de API funcional.*

### Día 5 – Planificador de Rutas
- Diseñar pantalla para elegir origen y destino.  
- Calcular tiempo estimado (simulado).  
✅ *Lógica de rutas básica implementada.*

### Día 6 – Mejoras Visuales
- Añadir Material 3, modo claro/oscuro.  
- Pulir colores, íconos y animaciones.  
✅ *Diseño final moderno y fluido.*

### Día 7 – Pruebas y Entrega
- Realizar pruebas funcionales.  
- Preparar video demo y documentación.  
✅ *Versión final lista para entrega.*

---

## 📱 Resultado Final

- Aplicación funcional con navegación completa.  
- Diseño limpio y responsivo con Material Design 3.  
- Simulación de rutas y gestión local de estaciones.  
- Integración básica con API externa.  

---

## 🧾 Docente

**JUAN LEON S.**

---

> Proyecto académico desarrollado para mostrar el flujo completo de planificación de viajes en el sistema Metro de Lima.
