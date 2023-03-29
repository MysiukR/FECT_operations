package ua.com.epam.repository;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum JsonKeysConformity {
    TEACHER_FIRST_NAME("teacherName.first", "firstName"),
    TEACHER_SECOND_NAME("teacherName.second", "secondName"),
    TEACHER_ID("teacherId", "teacherId"),
    TEACHER_DESCRIPTION("teacherDescription", "description"),
    TEACHER_SITE_REFERENCE("siteReference", "siteReference"),

    ROOM_ID("roomId", "roomId"),
    ROOM_NAME("roomName", "roomName"),
    ROOM_DESCRIPTION("roomDescription", "description"),

    SUBJECT_ID("subjectId", "subjectId"),
    SUBJECT_NAME("subjectName", "subjectName"),
    SUBJECT_LANGUAGE("subjectLanguage", "subjectLang"),
    SUBJECT_DESCRIPTION("subjectDescription", "description");

    private final String jsonPropertyKey;
    private final String modelPropertyName;

    JsonKeysConformity(String jsonPropertyKey, String modelPropertyName) {
        this.jsonPropertyKey = jsonPropertyKey;
        this.modelPropertyName = modelPropertyName;
    }

    public static String getPropNameByJsonKey(String jsonKey) {
        return Stream.of(JsonKeysConformity.values())
                .filter(k -> k.jsonPropertyKey.equals(jsonKey))
                .findFirst()
                .get()
                .modelPropertyName;
    }

    public static boolean ifJsonKeyExistsInGroup(String jsonKey, JsonKeysConformity.Group group) {
        return JsonKeysConformity.Group.getGroup(group)
                .stream()
                .anyMatch(k -> k.jsonPropertyKey.equals(jsonKey));
    }

    public enum Group {
        TEACHER("teacher"),
        ROOM("room"),
        SUBJECT("subject");

        private final String value;

        Group(String value) {
            this.value = value;
        }

        private static String getGroupByName(String name) {
            return Stream.of(Group.values())
                    .filter(g -> g.value.equals(name))
                    .findFirst()
                    .get()
                    .toString();
        }

        private static List<JsonKeysConformity> getGroup(JsonKeysConformity.Group group) {
            return Stream.of(JsonKeysConformity.values())
                    .filter(jc -> jc.toString().startsWith(Group.getGroupByName(group.value)))
                    .collect(Collectors.toList());
        }
    }
}