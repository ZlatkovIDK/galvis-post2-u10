package com.ufps.tareas.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TareasE2ETest {

    private static WebDriver driver;

    @BeforeAll
    static void setUpDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage",
                          "--disable-gpu", "--window-size=1280,800");
        driver = new ChromeDriver(opts);
    }

    @AfterAll
    static void tearDownDriver() {
        if (driver != null) driver.quit();
    }

    // ── Test 1: la página carga y el título contiene "Tareas" ─────────────────
    @Test
    @Order(1)
    void paginaTareas_cargaCorrectamente() {
        driver.get("http://localhost:8080/tareas");
        TareasPage page = new TareasPage(driver);
        assertThat(page.obtenerTitulo()).containsIgnoringCase("Tareas");
    }

    // ── Test 2: el botón "Nueva Tarea" navega al formulario ──────────────────
    @Test
    @Order(2)
    void btnNueva_navegaAFormulario() {
        driver.get("http://localhost:8080/tareas");
        TareasPage tareasPage = new TareasPage(driver);
        NuevaTareaPage nuevaPage = tareasPage.irANuevaTarea();
        assertThat(nuevaPage.obtenerTituloPagina()).containsIgnoringCase("Nueva");
    }

    // ── Test 3: crear una tarea y verificar que aparece en la lista ───────────
    @Test
    @Order(3)
    void crearTarea_aparecEnLista() {
        driver.get("http://localhost:8080/tareas");
        TareasPage tareasPage = new TareasPage(driver);
        int antes = tareasPage.contarTareas();

        tareasPage.irANuevaTarea()
                  .escribirTitulo("Tarea Selenium E2E")
                  .escribirDescripcion("Creada desde test automatizado")
                  .guardar();

        TareasPage despues = new TareasPage(driver);
        assertThat(despues.contarTareas()).isEqualTo(antes + 1);
        assertThat(despues.existeTareaConTitulo("Tarea Selenium E2E")).isTrue();
    }

    // ── Test 4: formulario de nueva tarea tiene los campos requeridos ─────────
    @Test
    @Order(4)
    void formularioNueva_tieneInputTitulo() {
        driver.get("http://localhost:8080/tareas/nueva");
        NuevaTareaPage nuevaPage = new NuevaTareaPage(driver);
        // Si NuevaTareaPage construye sin excepción, el campo #titulo existe y es visible
        assertThat(nuevaPage.obtenerTituloPagina()).containsIgnoringCase("Nueva");
    }
}
