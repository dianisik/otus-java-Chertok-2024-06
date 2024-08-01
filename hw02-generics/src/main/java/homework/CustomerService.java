package homework;

import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerData = new TreeMap<>((c1, c2) ->
    Long.compare(c1.getScores(), c2.getScores()));

    public Map<Customer, String> getCustomerData() {
        return customerData;
    }

    public Map.Entry<Customer, String> getSmallest() {

        return customerData.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return customerData.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        customerData.put(customer, data);
    }



    }





