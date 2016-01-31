package bean;
import android.test.ActivityInstrumentationTestCase2;

import mwteck.com.fillingoilhistory.MainActivity;

/**
 * Created by Administrator on 2016/1/28.
 */
public class FuelUseListTest extends ActivityInstrumentationTestCase2{
    public FuelUseListTest(){
        super(MainActivity.class);
    }

    public void TestAddFuel(){
        FuelUseList Fuels=new FuelUseList();
        FuelUse Fuel=new FuelUse();
        Fuels.add(Fuel);
        assertTrue(Fuels.HasFuel(Fuel));
    }

    public void TestHasFuel(){
        FuelUseList Fuels=new FuelUseList();
        FuelUse Fuel=new FuelUse();

        assertFalse(Fuels.HasFuel(Fuel));
        Fuels.add(Fuel);
        assertTrue(Fuels.HasFuel(Fuel));
    }

    public void TestgetFuel(){
        FuelUseList Fuels=new FuelUseList();
        FuelUse Fuel=new FuelUse();

        Fuels.add(Fuel);
        FuelUse ReturnedFuel = Fuels.getFuel(0);
        assertEquals(Fuel,ReturnedFuel);
    }

    public void TestdeleteFuel(){
        FuelUseList Fuels=new FuelUseList();
        FuelUse Fuel=new FuelUse();
        Fuels.add(Fuel);
        Fuels.delete(Fuel);
        assertFalse(Fuels.HasFuel(Fuel));
    }

    public void Testcount(){
        FuelUseList Fuels=new FuelUseList();
        FuelUse Fuel=new FuelUse();
        Fuels.add(Fuel);
        assertEquals(1,Fuels.getCount());
    }

}
