package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final Map<Customer, String> customerData = new TreeMap<>((c1, c2) ->
    Long.compare(c1.getScores(), c2.getScores()));

    public Map<Customer, String> getCustomerData() {
        return customerData;
    }

    public Map.Entry<Customer, String> getSmallest() {
       Map.Entry<Customer, String> entry = ((TreeMap<Customer, String> ) customerData).firstEntry();
       return getCustomerEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return ((TreeMap<Customer, String>) customerData).higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        customerData.put(customer, data);
    }

    private Map.Entry<Customer, String> getCustomerEntry(Map.Entry<Customer, String> entry) {
        if (entry != null) {
            Customer customer = new Customer(entry.getKey());
            return new AbstractMap.SimpleEntry<>(customer, entry.getValue());
        }
        return null;
    }

}





