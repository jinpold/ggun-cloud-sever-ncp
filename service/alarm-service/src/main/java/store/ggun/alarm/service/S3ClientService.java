package store.ggun.alarm.service;


import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface S3ClientService {
    Mono<String> uploadFile(FilePart filePart);
    Flux<String> uploadFiles(Flux<FilePart> fileParts);
}
