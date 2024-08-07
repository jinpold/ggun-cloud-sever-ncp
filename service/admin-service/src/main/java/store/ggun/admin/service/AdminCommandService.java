package store.ggun.admin.service;
import store.ggun.admin.domain.model.AdminMessengerModel;

public interface AdminCommandService<T> {

    AdminMessengerModel save (T t);
    AdminMessengerModel deleteById (Long id);
    AdminMessengerModel modify (T t);
}
