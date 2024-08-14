package store.ggun.user.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.ggun.user.domain.ItemModel;
import store.ggun.user.repository.ItemRepository;
import store.ggun.user.service.ItemService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository re;

    @Override
    public List<ItemModel> findByVolume() {
        return re.findByOrderByVolume();
    }

    @Override
    public List<ItemModel> findAll() {
        return re.findAll();
    }

    @Override
    public List<ItemModel> findDetail(Map<String, String> search) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String a = search.get("search");
        String b = search.get("sdate");
        Date b1 = sdf.parse(formatString(b));
        String c = search.get("edate");
        Date c1 = sdf.parse(formatString(c));
        return (re.findDetail(a, b1, c1));
    }

    public static String formatString(String str) {
        if (str.length() != 8) {
            return "문자열 길이가 8자가 아닙니다.";
        }
        return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6);
    }
}