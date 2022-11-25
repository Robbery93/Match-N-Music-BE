package nl.robbertij.matchnmusic.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentTeacherKey implements Serializable {

    // attributes
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "teacher_id")
    private Long teacherId;

    public StudentTeacherKey() {
    }

    public StudentTeacherKey(Long studentId, Long teacherId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    // getters & setters


    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(final Object o) {
        if(o == this) return true;
        if(!(o instanceof final StudentTeacherKey other)) return false;
        if(!other.canEqual((Object) this)) return false;
        final Object this$teacherId = this.getTeacherId();
        final Object other$teacherId = other.getTeacherId();
        if (!Objects.equals(this$teacherId, other$teacherId)) return false;
        final Object this$studentId = this.getStudentId();
        final Object other$studentId = other.getStudentId();
        if (!Objects.equals(this$studentId, other$studentId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StudentTeacherKey;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $teacherId = this.getTeacherId();
        result = result * PRIME + ($teacherId == null ? 43 : $teacherId.hashCode());
        final Object $studentId = this.getStudentId();
        result = result * PRIME + ($studentId == null ? 43 : $studentId.hashCode());
        return result;
    }

}
