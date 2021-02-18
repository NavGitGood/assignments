import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Students {
    private Set<Student> students = new HashSet<Student>();

    public boolean addStudent(Student student) {
        return students.add(student);
    }

    @Override
    public String toString() {
        return "Students:\n"
                + students.stream()
                .map(student -> "Age: " + student.getAge() + "\n" + "Name: " +
                        student.getName() + "\n" + "Father's Name: " + student.getFathersName() + "\n")
                .collect(Collectors.joining());
    }
}
