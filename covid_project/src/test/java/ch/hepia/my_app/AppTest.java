package ch.hepia.covid_manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import javafx.scene.shape.Circle;




class AppTest {

    //Test pour vérifier que les cas et les mort totaux progressent normalement
    @Test
    void elapseDayTest() {

        Country c = new Country("Test", 0, 0,0,0,0,0,0,0,0,10000,"test",0);
        Circle c1[] = c.getCountryCircles();

        int tmp1 = c.playerTotalCases();
        int tmp2 = c.playerTotalDeaths();
        int tmp3 = c.playerTotalCured();

        c.elapseDay();

        assert c.playerTotalCases() >= tmp1;  
        assert c.playerTotalDeaths() >= tmp2;
        assert c.playerTotalCured() >= tmp3;


    }

    @Test
    void perksPurchaseTest(){



    }
}
