package store.ggun.account.service;


public interface UtilService {
    int createRandomInteger (int start, int end);
    double createRandomDouble (double start, double end);

    String createAccountNumber(String acType);
}
