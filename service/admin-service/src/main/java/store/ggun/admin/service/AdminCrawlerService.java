package store.ggun.admin.service;

import store.ggun.admin.domain.model.AdminCrawlerModel;

import java.io.IOException;
import java.util.List;


public interface AdminCrawlerService {
    List<AdminCrawlerModel> findNews() throws IOException;
}