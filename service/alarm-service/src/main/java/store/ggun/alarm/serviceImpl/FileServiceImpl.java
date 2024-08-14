package store.ggun.alarm.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.model.FileModel;
import store.ggun.alarm.repository.FileRepository;
import store.ggun.alarm.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final AmazonS3 s3Client;
    private final String bucketName = "bucket-ggun";

    public Mono<FileModel> save(FileModel fileModel) {
        return fileRepository.save(fileModel);
    }

    public Mono<FileModel> getFile(String id) {
        return fileRepository.findById(id);
    }


    @Override
    public Flux<FileModel> saveFiles(String Id, Flux<FilePart> files) {
        return fileRepository.findById(Id)
                .flatMapMany(lawyer -> files.flatMap(filePart -> uploadToS3(filePart)
                        .flatMap(url -> saveFileMetadata(Id, filePart, url))));
    }

    @Override
    public Mono<String> uploadToS3(FilePart filePart) {
        String key = UUID.randomUUID().toString() + "_" + filePart.filename();
        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(bytes.length);
                    metadata.setContentType(filePart.headers().getContentType().toString());

                    s3Client.putObject(new PutObjectRequest(bucketName, key, new ByteArrayInputStream(bytes), metadata));
                    return Mono.just(s3Client.getUrl(bucketName, key).toString());
                });
    }

    @Override
    public Mono<FileModel> saveFileMetadata(String lawyerId, FilePart filePart, String url) {
        FileModel fileModel = new FileModel();
        fileModel.setFilename(filePart.filename());
        fileModel.setContentType(filePart.headers().getContentType().toString());
        fileModel.setUrl(url);
        return fileRepository.save(fileModel);
    }

    @Override
    public Mono<FileModel> getFileById(String id) {
        return fileRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteFileById(String id) {
        return fileRepository.deleteById(id);
    }

    @Override
    public Mono<byte[]> downloadFile(String url) {
        return Mono.fromCallable(() -> {
            String key = getKeyFromUrl(url);
            com.amazonaws.services.s3.model.S3Object s3Object = s3Client.getObject(bucketName, key);
            try (InputStream inputStream = s3Object.getObjectContent()) {
                return inputStream.readAllBytes();
            }
        });
    }

    @Override
    public Mono<Void> deleteFileByUrl(String url) {
        return Mono.fromRunnable(() -> s3Client.deleteObject(bucketName, getKeyFromUrl(url)));
    }

    @Override
    public Mono<Void> deleteAllFiles() {
        return fileRepository.deleteAll();
    }

    @Override
    public Flux<FileModel> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public Flux<FileModel> getFilesById(String Id) {
        return fileRepository.findAllById(Collections.singleton(Id));
    }

    @Override
    public String getKeyFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
