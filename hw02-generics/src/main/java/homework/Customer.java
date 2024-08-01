package homework;

import java.util.Objects;

public class Customer implements Comparable<Customer> {
    private final long id;
    private static String name;
    private long scores;

    public Customer(long id, String name, long scores) {
        this.id = id;
        Customer.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void setName(String name) {
       Customer.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        if (scores < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
    return Objects.hash(id);
}

    @Override
    public int compareTo(Customer other) {
    return Long.compare(this.scores, other.scores);
}
}






