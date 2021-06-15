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

       long tmp1 = c.playerTotalCases();
       long tmp2 = c.playerTotalDeaths();
       long tmp3 = c.playerTotalCured();

        c.elapseDay();

        assert c.playerTotalCases() >= tmp1;  
        assert c.playerTotalDeaths() >= tmp2;
        assert c.playerTotalCured() >= tmp3;


    }

    @Test
    void perksPurchaseTest(){

        Virus v = Virus.getInstance();

        Perk p1 = Perk.perkFactory(1, "test1", "desc1", 0, 10, "infectivity");
        Perk p2 = Perk.perkFactory(2, "test2", "desc2", 0, 10, "resistance");
        Perk p3 = Perk.perkFactory(3, "test3", "desc3", 0, 10, "lethality");

        double tmp1 = v.infectivity();
        double tmp2 = v.resistance();
        double tmp3 = v.lethality();

        v.upgrade(p1);
        v.upgrade(p2);
        v.upgrade(p3);

        assert v.infectivity() >= tmp1;
        assert v.resistance() >= tmp2;
        assert v.lethality() >= tmp3;

    }

    @Test
    void countryHistoryTest(){

    /*
    
    */

    }

    @Test
    void casesCountTest(){



    }

    @Test
    void rewardsInMapTest(){



    }
}
