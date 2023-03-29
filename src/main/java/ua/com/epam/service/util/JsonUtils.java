package ua.com.epam.service.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonUtils {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public <T> String sortJsonsByKey(List<T> jsons, String key) {
        Map<Object, Object> sorted = new TreeMap<>(Comparator.comparingInt(Object::hashCode));
        for (Object single : jsons) {
            Object keyValue = JsonPath.read(gson.toJson(single), "$." + key);
            sorted.put(keyValue, single);
        }

        return new JSONArray(sorted.values()).toString();
    }

    public <T> String sortJsonsByKeyAndLimit(List<T> jsons, String key, int count) {
        Map<Object, Object> sorted = new TreeMap<>(Comparator.comparingInt(Object::hashCode));
        for (Object single : jsons) {
            Object keyValue = JsonPath.read(gson.toJson(single), "$." + key);
            sorted.put(keyValue, single);
        }

        return new JSONArray(Stream
                .of(sorted.values())
                .limit(count)
                .collect(Collectors.toList()))
                .toString();
    }

    public <T> List<T> filterByJsonPath(List<T> dtos, Map<String, String> params, Class<T> clazz) {
        if (params.isEmpty()) {
            return dtos;
        }

        String jsonPathFilterStr = parseParamMapToJsonPathFilter(params);
        String jsonArrayFromDTOs = new JSONArray(dtos).toString();
        String jsonArrayFilteredStr = JsonPath.read(jsonArrayFromDTOs, jsonPathFilterStr).toString();
        JSONArray jsonArrayFiltered = new JSONArray(jsonArrayFilteredStr);

        return Stream.of(jsonArrayFiltered)
                .map(s -> gson.fromJson(s.toString(), clazz))
                .collect(Collectors.toList());
    }

    private String parseParamMapToJsonPathFilter(Map<String, String> params) {
        String prefix = "$.[?", beforeFilter = "@.", suffix = "]";

        List<String> andFilters = new ArrayList<>();

        StringBuilder path = new StringBuilder();
        path.append(prefix);

        for (Map.Entry<String, String> pair : params.entrySet()) {
            String key = pair.getKey();
            List<String> orFilters = new ArrayList<>();
            for (String value : pair.getValue().split(",")) {
                orFilters.add(beforeFilter + key + "==" + "'" + value + "'");
            }
            andFilters.add(orFilters.stream().collect(Collectors.joining("||", "(", ")")));
        }

        path.append(andFilters.stream().collect(Collectors.joining("&&", "(", ")"))).append(suffix);
        return path.toString();
    }
}
