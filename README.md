# Proyecto 1 - Gestor de Procesos

## Integrantes:
### 2023344598 - Sergio Montoya Badilla

### Estado del proyecto: 1

### Enlace del video: https://youtu.be/WyYrZ-uNSpw
Recordar que el video debe ser **público** y con **sonido** para ser visto y escuchado por el profesor

## Descripción del problema

Este proyecto consiste en una aplicación desarrollada en **Java** que simula el funcionamiento básico de un sistema operativo, enfocándose en la gestión de procesos. Para lograrlo, el programa modela los principales componentes de una computadora: la **CPU**, la **memoria principal (RAM)**, el **almacenamiento secundario** y los **Bloques de Control de Procesos (BCP)**, que guardan la información de cada proceso (identificador, estado, prioridad, recursos requeridos, entre otros).

El problema central es administrar de forma correcta y eficiente los procesos que ingresan al sistema. Cada proceso nuevo debe registrarse, recibir los recursos que necesita (CPU y espacio en memoria) y pasar por los distintos estados de su ciclo de vida (nuevo, listo, en ejecución, bloqueado y terminado). Además, el sistema debe decidir en qué orden se atienden los procesos y liberar los recursos una vez que estos finalizan, evitando conflictos, desperdicio de memoria o procesos que nunca se ejecuten.

Con esta simulación se busca comprender cómo un sistema operativo coordina el uso de recursos limitados entre múltiples procesos que compiten por ellos.

## Objetivos

### Objetivos alcanzados
- [x] Implementar las clases que representan la CPU, la memoria principal y el almacenamiento.
- [x] Crear el Bloque de Control de Procesos (BCP) para almacenar la información de cada proceso.
- [x] Permitir el ingreso de nuevos procesos al sistema.
- [x] Asignar y liberar recursos (memoria y CPU) de forma correcta.
- [x] Manejar los estados de los procesos durante su ejecución.
- [x] Implementar las interrupciones del sistema.
- [x] Manejar los desbordamientos de memoria y pila

### Objetivos no alcanzados
Considerando lo realizado, se llega a la conclusión de que todos los objetivos se realizaron.
