import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestAnimalZoo {


    @ParameterizedTest
    @DisplayName("Check all items in the list")
    @ValueSource(strings = {"Marty", "Alex", "Pauly", "Steve"})
    void checkAllItems(String expectedName)
    {
          ArrayList<Animal> testZoo = new ArrayList<>();
          Animal Zebra = new Zebra();
          Animal Lion = new Lion();
          Animal Bird = new Birds();
          Animal Snake = new Snakes();

          Zebra.setName(expectedName);
          assertEquals(expectedName, Zebra.getName());
          Lion.setName(expectedName);
          assertEquals(expectedName, Lion.getName());
          Bird.setName(expectedName);
          assertEquals(expectedName, Bird.getName());
          Snake.setName(expectedName);
          assertEquals(expectedName, Snake.getName());

    }


}
