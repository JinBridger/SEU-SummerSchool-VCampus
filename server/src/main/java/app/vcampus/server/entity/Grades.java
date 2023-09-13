package app.vcampus.server.entity;

import lombok.Data;

/**
 *  Grades entity
 *  For students to record all kinds of grades.
 */
@Data
public class Grades {
    public Integer general;
    public Integer midterm;
    public Integer finalExam;
    public Integer total;

    public Integer classMax;
    public Integer classMin;
    public Double classAvg;
}
