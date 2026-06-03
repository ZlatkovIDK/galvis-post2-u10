package com.ufps.tareas.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NuevaTareaPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By INPUT_TITULO       = By.id("titulo");
    private static final By INPUT_DESCRIPCION  = By.id("descripcion");
    private static final By BTN_GUARDAR        = By.id("btn-guardar");

    public NuevaTareaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Esperar a que el formulario esté listo
        wait.until(ExpectedConditions.visibilityOfElementLocated(INPUT_TITULO));
    }

    public NuevaTareaPage escribirTitulo(String titulo) {
        driver.findElement(INPUT_TITULO).clear();
        driver.findElement(INPUT_TITULO).sendKeys(titulo);
        return this;
    }

    public NuevaTareaPage escribirDescripcion(String desc) {
        driver.findElement(INPUT_DESCRIPCION).clear();
        driver.findElement(INPUT_DESCRIPCION).sendKeys(desc);
        return this;
    }

    public TareasPage guardar() {
        driver.findElement(BTN_GUARDAR).click();
        return new TareasPage(driver);
    }

    public String obtenerTituloPagina() {
        return driver.getTitle();
    }
}
