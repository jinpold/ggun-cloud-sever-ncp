package store.ggun.alarm.service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.ggun.alarm.domain.model.FileModel;

public interface FileService {

    Flux<FileModel> saveFiles(String lawyerId, Flux<FilePart> files);
    Mono<String> uploadToS3(FilePart filePart);
    Mono<FileModel> saveFileMetadata(String lawyerId, FilePart filePart, String url);
    Mono<FileModel> getFileById(String id);
    Mono<Void> deleteFileById(String id);
    Mono<byte[]> downloadFile(String url);
    Mono<Void> deleteFileByUrl(String url);
    Mono<Void> deleteAllFiles();
    Flux<FileModel> getAllFiles();
    Flux<FileModel> getFilesById(String Id);
    String getKeyFromUrl(String url);

}
