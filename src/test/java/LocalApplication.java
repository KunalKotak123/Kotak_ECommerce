import com.kotak.inventoryservice.Config.ContainerConfig;
import com.kotak.inventoryservice.inventoryServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.regions.Region;

public class LocalApplication
{

    public static void main(String[] args) {
        System.setProperty("region", Region.AP_SOUTH_1.toString());
        SpringApplication.from(inventoryServiceApplication::main).with(ContainerConfig.class).run(args);
    }
}
