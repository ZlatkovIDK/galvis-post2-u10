package com.ufps.tareas.e2e;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TareasPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By BTN_NUEVA   = By.id("btn-nueva");
    private static final By LIST_ITEMS  = By.cssSelector(".tarea-item");
    private static final By SIN_TAREAS  = By.id("sin-tareas");

    public TareasPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public int contarTareas() {
        return driver.findElements(LIST_ITEMS).size();
    }

    public boolean sinTareasVisible() {
        return !driver.findElements(SIN_TAREAS).isEmpty();
    }

    public NuevaTareaPage irANuevaTarea() {
        wait.until(ExpectedConditions.elementToBeClickable(BTN_NUEVA)).click();
        return new NuevaTareaPage(driver);
    }

    public String obtenerTitulo() {
        return driver.getTitle();
    }

    public List<WebElement> obtenerItems() {
        return driver.findElements(LIST_ITEMS);
    }

    public boolean existeTareaConTitulo(String titulo) {
        return driver.findElements(LIST_ITEMS).stream()
                .anyMatch(e -> e.getText().contains(titulo));
    }
}
