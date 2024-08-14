package store.ggun.admin.repository.etc;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractRepository {

    public abstract Map<String, ?> save(Map<String, ?> paraMap) throws IOException;

}
