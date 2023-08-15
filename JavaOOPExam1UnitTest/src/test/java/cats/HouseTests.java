package cats;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HouseTests {
    private Cat cat;
    private Cat cat2;
    private static final int CAPACITY = 2;
    private List<Cat> cats = new ArrayList<>();
    private House house;


    @Before
    public void setUp(){
        cat = new Cat("Cat1");
        cats.add(cat);
        house = new House("House1", CAPACITY);
        house.addCat(cat);
    }

    @Test
    public void testConstructor(){
        House house2 = new House("House2", 1);
        assertEquals("House2", house2.getName());
        assertEquals(1, house2.getCapacity());
    }

    @Test
    public void testSetNameSuccessfully(){
        House house2 = new House("House2", 1);
        assertEquals("House2", house2.getName());
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameWhenNameIsNull(){
        House house2 = new House(null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameWhenNameIsEmpty(){
        House house2 = new House("", 1);
    }

    @Test
    public void testSetCapacitySuccessfully(){
        House house2 = new House("House1", 1);
        assertEquals(1, house2.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityWhenLessThanZero(){
        House house2 = new House("House1", -1);
    }

    @Test
    public void testAddCatSuccessfully(){
        int beforeAddition = house.getCount();
        cat2 = new Cat("Cat2");
        house.addCat(cat2);
        int afterAddition = house.getCount();

        assertEquals(beforeAddition + 1, afterAddition);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCatWhenCapasityIsZero(){
        cat2 = new Cat("Cat2");
        Cat cat3 = new Cat("Cat3");
        house.addCat(cat2);
        house.addCat(cat3);
    }

    @Test
    public void testGetCountOfCats(){
        cat2 = new Cat("Cat2");
        house.addCat(cat2);
        int afterAddition = house.getCount();
        assertEquals(2, afterAddition);
    }

    @Test
    public void testRemoveCatSuccessfully(){
        cat2 = new Cat("Cat2");
        cats.add(cat2);
        house.addCat(cat2);
        int beforeSize = house.getCount();

        cats.remove(cat2);
        house.removeCat("Cat2");
        int afterSize = house.getCount();

        assertEquals(beforeSize - 1, afterSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveCatWithNoExistingName(){
        house.removeCat(null);
    }

    @Test
    public void testCatForSaleSuccessfully(){
        cat2 = new Cat("Cat2");
        cats.add(cat2);
        house.addCat(cat2);
        Cat catForSale = house.catForSale("Cat2");

        assertEquals(cat2, catForSale);
        //assertFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCatForSaleWhenThereIsNoCat(){
        house.catForSale(null);
    }

    @Test
    public void testStatistics(){
        cat2 = new Cat("Cat2");
        cats.add(cat2);
        house.addCat(cat2);
        String names = "Cat1, Cat2";
        String expected = String.format("The cat %s is in the house %s!%n", names, house.getName());

        assertEquals(expected.trim(), house.statistics());
    }
}
