package quinzical.scenes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import quinzical.model.Inventory;
import quinzical.model.InventoryLoader;

public class ShopController {
    @FXML
    private void buyHint() {

    }
    @FXML
    private void buyTheme() {

    }
    @FXML
    private ComboBox<Theme> themeBox;

    @FXML
    private void initialize() {

        InventoryLoader loader = new InventoryLoader();
        Inventory inventory = loader.getInventory();
        List<Theme> themes = inventory.getThemes();

        for (Theme theme : themes) {
            themeBox.getItems().add(theme);
        }
    }

}
