package co.edu.uptc.demo_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsumerHandler {
    List<Consumer> consumers;

    public ConsumerHandler(){
        consumers = new ArrayList<>();
    }

    public Consumer findConsumer(String id_consumer){
        Optional<Consumer> consumer = consumers.stream().filter(c -> c.getId_consumer().equals(id_consumer)).findFirst();
        return consumer.orElse(null);
    }

    public boolean addConsumer(String id_consumer, int id, String name, int age, String municipio, short status, String email){
        if(findConsumer(id_consumer) == null){
            Consumer consumer = new Consumer(id_consumer, id, name, age, municipio, status, email);
            consumers.add(consumer);
            return true;
        }
        return false;
    }

    public boolean removeConsumer(String id_consumer){
        Consumer consumer = findConsumer(id_consumer);
        if(consumer != null){
            consumers.remove(consumer);
            return true;
        }
        return false;
    }
}
