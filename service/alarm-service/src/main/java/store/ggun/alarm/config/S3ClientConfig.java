package store.ggun.alarm.config;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ClientConfig {

    private final String endPoint = "https://kr.object.ncloudstorage.com";
    private final String regionName = "kr-standard";
    private final String accessKey = "732FBC033EF979B8BCCA";
    private final String secretKey = "EB60F9AD4CFF01DE398E1703293761BDB9E7B408";
    private final String bucketName = "bucket-ggun";

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
    @Bean
    public String bucketName() {
        return bucketName;
    }
}