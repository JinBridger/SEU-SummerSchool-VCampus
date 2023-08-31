import app.vcampus.server.entity.TeachingClass;
import app.vcampus.server.utility.Pair;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class IEntityTest {
    private final Gson gson = new Gson();

    @Test
    public void teachingClass() {
        TeachingClass teachingClass = new TeachingClass();
        teachingClass.setUuid(java.util.UUID.randomUUID());
        teachingClass.setCourseUuid(java.util.UUID.randomUUID());
        teachingClass.setCourseName("courseName");
        teachingClass.setTeacherId(1);
        teachingClass.setPlace("place");
        teachingClass.setCapacity(1);
        teachingClass.setSchedule(List.of(
                new Pair<>(
                        new Pair<>(1, 2),
                        new Pair<>(3, new Pair<>(4, 5))
                )
        ));

        TeachingClass newTeachingClass = gson.fromJson(teachingClass.toJson(), TeachingClass.class);
        assert newTeachingClass != null;

        assert teachingClass.getUuid().equals(newTeachingClass.getUuid());
        assert teachingClass.getCourseUuid().equals(newTeachingClass.getCourseUuid());
        assert teachingClass.getCourseName().equals(newTeachingClass.getCourseName());
        assert teachingClass.getTeacherId().equals(newTeachingClass.getTeacherId());
        assert teachingClass.getPlace().equals(newTeachingClass.getPlace());
        assert newTeachingClass.getSchedule().stream().allMatch(
                pair -> pair.getFirst().getFirst().equals(1) &&
                        pair.getFirst().getSecond().equals(2) &&
                        pair.getSecond().getFirst().equals(3) &&
                        pair.getSecond().getSecond().getFirst().equals(4) &&
                        pair.getSecond().getSecond().getSecond().equals(5)
        );
    }
}
