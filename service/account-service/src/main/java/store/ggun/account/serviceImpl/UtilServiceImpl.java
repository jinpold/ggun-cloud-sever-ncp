package store.ggun.account.serviceImpl;

import org.springframework.stereotype.Component;
import store.ggun.account.service.UtilService;

@Component
public class UtilServiceImpl implements UtilService {


    @Override
    public int createRandomInteger(int min, int max) {

        return min + (int)(Math.random()*(max-min));
    }

    @Override
    public double createRandomDouble(double min, double max){

        return Math.round((min +
                (Math.random()*(max-min)))*10)/10.0;
    }
    @Override
    public String createAccountNumber(String acnoType){
        String acno = "";
        if (acnoType.equals("01")) {
            acno = createRandomInteger(20000000, 29999999) + "-" + acnoType;
        } else if (acnoType.equals("02")) {
            acno = createRandomInteger(50000000, 59999999) + "-" + acnoType;
        }
        return acno;
    }


}
