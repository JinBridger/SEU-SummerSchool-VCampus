import app.vcampus.server.entity.Course;
import app.vcampus.server.entity.TeachingClass;
import app.vcampus.server.utility.Pair;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IEntityTest {
    private final Gson gson = new Gson();

    @Test
    public void teachingClass() {
        TeachingClass teachingClass = new TeachingClass();
        teachingClass.setUuid(java.util.UUID.randomUUID());
        teachingClass.setCourseUuid(java.util.UUID.randomUUID());
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

    @Test
    public void course() {
        Course course = new Course();
        course.setUuid(java.util.UUID.randomUUID());
        course.setCourseId("courseId");
        course.setCourseName("courseName");
        course.setSchool("school");
        course.setCredit(1);

        List<TeachingClass> teachingClasses = List.of(
                new TeachingClass(),
                new TeachingClass()
        );
        teachingClasses.get(0).setCourseUuid(course.getUuid());
        teachingClasses.get(1).setCourseUuid(course.getUuid());

        course.setTeachingClasses(teachingClasses);

        Course newCourse = gson.fromJson(course.toJson(), Course.class);
        assert newCourse != null;

        assert course.getUuid().equals(newCourse.getUuid());
        assert course.getCourseId().equals(newCourse.getCourseId());
        assert course.getCourseName().equals(newCourse.getCourseName());
        assert course.getSchool().equals(newCourse.getSchool());
        assert course.getCredit() == newCourse.getCredit();

        assert newCourse.getTeachingClasses().size() == 2;
        assert newCourse.getTeachingClasses().get(0).getCourseUuid().equals(newCourse.getUuid());
        assert newCourse.getTeachingClasses().get(1).getCourseUuid().equals(newCourse.getUuid());
    }
}
