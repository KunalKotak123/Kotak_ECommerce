import com.kotak.inventoryservice.Config.ContainerConfig;
import com.kotak.inventoryservice.inventoryServiceApplication;
import org.springframework.boot.SpringApplication;
import software.amazon.awssdk.regions.Region;

public class LocalApplication
{

    public static void main(String[] args) {
        System.setProperty("region", Region.AP_SOUTH_1.toString());
        System.setProperty("aws.dynamodb.endpointOverride", "");
        SpringApplication.from(inventoryServiceApplication::main).with(ContainerConfig.class).run(args);
    }
}
