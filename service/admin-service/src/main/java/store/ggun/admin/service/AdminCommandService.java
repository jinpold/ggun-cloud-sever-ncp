package store.ggun.admin.service;
import store.ggun.admin.domain.model.Messenger;

public interface AdminCommandService<T> {

    Messenger save (T t);
    Messenger deleteById (Long id);
    Messenger modify (T t);
}
