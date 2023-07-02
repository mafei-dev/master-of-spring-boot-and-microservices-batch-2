import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Tester {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 500; i++) {
            int finalI = i;
            new Thread(() -> {
                ResponseEntity<String> forEntity = new RestTemplate().getForEntity(
                        "http://localhost:3344/test/bulkhead/tp?count=" + finalI,
                        String.class
                );

                System.out.println("Thread:" + Thread.currentThread().getName() + ", count:" + forEntity.getBody());
            }).start();
        }


        Thread.sleep(10000000);
    }
}
