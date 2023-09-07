package app.vcampus.server.entity;

import lombok.Data;

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
