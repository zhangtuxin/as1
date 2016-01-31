package bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/28.
 */
public class FuelUseList {
    private ArrayList<FuelUse> Fuels=new ArrayList<FuelUse>();
    public void add (FuelUse Fuel){
        if (Fuels.contains(Fuel))
            throw new IllegalArgumentException("this one is already added");
        Fuels.add(Fuel);
    }

    public Boolean HasFuel(FuelUse Fuel){
        return Fuels.contains(Fuel);
    }

    public FuelUse getFuel(int index){
        return Fuels.get(index);
    }

    public void delete (FuelUse Fuel){
        Fuels.remove(Fuel);
    }
    public int getCount (){
        return Fuels.size();
    }


}
