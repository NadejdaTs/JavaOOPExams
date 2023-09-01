package aquarium;

import org.junit.Test;
import static org.junit.Assert.*;

public class AquariumTests {

    @Test(expected = NullPointerException.class)
    public void testSetNameShouldFailWhenIsNull(){
        new Aquarium(null, 10);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNameShouldFailWhenNameIsWhitespace(){
        new Aquarium("    ", 10);
    }

    @Test
    public void testSetCorrectName(){
        Aquarium aquarium = new Aquarium("test_name", 10);
        assertEquals("test_name", aquarium.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityShouldThrowWithLessThanZeroCapacity(){
        new Aquarium("test_name", -1);
    }

    @Test
    public void testSetCapacitySuccessfully(){
        Aquarium aquarium = new Aquarium("test_name", 5);
        assertEquals(5, aquarium.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFishShouldFailWhenMaxCapacityIsReached(){
        Aquarium aquarium = new Aquarium("test_name", 0);
        aquarium.add(new Fish("test_fish"));
    }

    @Test
    public void testAddSuccessfully(){
        Aquarium aquarium = new Aquarium("test_name", 1);
        aquarium.add(new Fish("test_fish"));
        assertEquals(1, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFishShouldFailIfNoSuchAdded(){
        Aquarium aquarium = new Aquarium("test_name", 1);
        aquarium.remove("test_name");
    }

    @Test
    public void testRemoveSuccessfully(){
        Aquarium aquarium = new Aquarium("test_name", 1);
        aquarium.add(new Fish("test_fish"));
        aquarium.remove("test_fish");
        assertEquals(0, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSellFishShouldFailIfNoSuchAdded(){
        Aquarium aquarium = new Aquarium("test_name", 1);
        aquarium.sellFish("test_name");
    }

    @Test
    public void testSellFishSuccessfully(){
        Aquarium aquarium = new Aquarium("test_name", 10);
        Fish fish = new Fish("test_fish");
        aquarium.add(fish);
        aquarium.sellFish("test_fish");
        assertFalse(fish.isAvailable());
    }

    @Test
    public void testGetInfo(){
        Aquarium aquarium = new Aquarium("test_name", 10);
        Fish fish = new Fish("test_fish");
        Fish fish2 = new Fish("test_fish2");
        Fish fish3 = new Fish("test_fish3");
        aquarium.add(fish);
        aquarium.add(fish2);
        aquarium.add(fish3);

        String expected = "Fish available at test_name: test_fish, test_fish2, test_fish3";

        assertEquals(expected, aquarium.report());
    }

}

