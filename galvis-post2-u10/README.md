# galvis-post2-u10 — Pruebas E2E con Selenium, Postman y Newman

Proyecto de la **Unidad 10: Pruebas de Software en Aplicaciones Web**  
Programación Web — Ingeniería de Sistemas — UFPS 2026

---

## Prerrequisitos

| Herramienta | Versión mínima |
|---|---|
| Java JDK | 17 |
| Maven | 3.8+ |
| Google Chrome | última versión estable |
| Node.js + npm | 18+ |
| Newman | `npm install -g newman` |
| Postman Desktop | v10+ (para el Runner visual) |

---

## Estructura del repositorio

```
galvis-post2-u10/
├── src/
│   ├── main/java/com/galvis/todo/     ← Código fuente Spring Boot
│   └── test/java/com/galvis/todo/e2e/ ← Page Objects + Tests Selenium
├── postman/
│   ├── ColeccionToDo.json             ← Colección Postman (5 requests)
│   ├── env-local.json                 ← Entorno local
│   └── env-ci.json                    ← Entorno CI/CD
├── .github/workflows/
│   └── api-tests.yml                  ← Pipeline GitHub Actions
└── README.md
```

---

## Levantar la aplicación localmente

```bash
# Clonar y entrar al proyecto
git clone https://github.com/TU_USUARIO/galvis-post2-u10.git
cd galvis-post2-u10

# Compilar y arrancar
mvn spring-boot:run
```

La app queda en: **http://localhost:8080/tareas**  
Health check en: **http://localhost:8080/actuator/health**

---

## Checkpoint 1 — Tests de Selenium (E2E)

> **Requisito:** La aplicación debe estar corriendo (`mvn spring-boot:run`) antes de ejecutar los tests E2E.

### Ejecutar en IntelliJ

1. Abre IntelliJ IDEA → importar proyecto Maven.
2. Espera que se descarguen las dependencias.
3. Asegúrate de que la app está corriendo en el puerto 8080.
4. Haz clic derecho sobre `TareasE2ETest.java` → **Run 'TareasE2ETest'**.
5. Los tests se ejecutan en modo **headless** (sin abrir Chrome visualmente).

### Ejecutar desde terminal

```bash
# Primero levanta la app en otra terminal:
mvn spring-boot:run

# Luego, en otra terminal, ejecuta los tests E2E:
mvn test -Pe2e
```

**Evidencia esperada:** 4 tests en verde en la consola de IntelliJ o terminal.

---

## Checkpoint 2 — Colección Postman con Test Scripts

### Opción A: Postman Desktop (Runner visual)

1. Abre Postman Desktop.
2. **Import** → selecciona `postman/ColeccionToDo.json`.
3. **Import** → selecciona `postman/env-local.json`.
4. Selecciona el entorno **ToDoApp-Local** en el selector de entorno (arriba a la derecha).
5. Clic en la colección **"API ToDoApp"** → **Run collection**.
6. En el Runner, asegúrate de que el orden es correcto (1→5) y haz clic en **Run API ToDoApp**.

**Evidencia esperada:** 0 failures en los 5 requests del Runner.

### Opción B: Newman desde terminal

```bash
# Con la app corriendo:
newman run postman/ColeccionToDo.json --environment postman/env-local.json
```

---

## Checkpoint 3 — Newman en GitHub Actions

### Configurar el repositorio

```bash
# Inicializar git (si no lo has hecho)
git init
git add .
git commit -m "feat: implementacion inicial pruebas E2E Selenium y Postman"

# Conectar al repositorio remoto
git remote add origin https://github.com/TU_USUARIO/galvis-post2-u10.git
git push -u origin main
```

### Ver el pipeline

1. Ve a tu repositorio en GitHub.
2. Pestaña **Actions**.
3. El workflow **"API Tests con Newman"** se dispara automáticamente con cada push.
4. Espera que el job `api-test` muestre ✅ verde.

**Evidencia esperada:** El workflow aparece con estado "passing" (check verde) en la pestaña Actions.

---

## Resumen de endpoints de la API

| Método | URL | Descripción |
|---|---|---|
| GET | `/tareas` | Vista web con lista de tareas |
| GET | `/api/tareas` | Lista todas las tareas (JSON) |
| POST | `/api/tareas` | Crea nueva tarea → `201 Created` |
| GET | `/api/tareas/{id}` | Obtiene tarea por ID → `200` o `404` |
| PATCH | `/api/tareas/{id}/completar` | Marca como completada → `200` |
| DELETE | `/api/tareas/{id}` | Elimina tarea → `204` |
| GET | `/actuator/health` | Health check para CI |

---

## Commits descriptivos (mínimo 3)

```
feat: estructura base Spring Boot ToDo app con API REST
feat: page objects Selenium (TareasPage, NuevaTareaPage) y tests E2E headless  
feat: coleccion Postman 5 requests con test scripts y entornos local/ci
feat: pipeline GitHub Actions Newman con health check y bail on failure
```
