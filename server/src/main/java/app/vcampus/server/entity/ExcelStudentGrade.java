package app.vcampus.server.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ExcelStudentGrade {
    @ExcelProperty("学号")
    @NonNull
    public String studentNumber;

    @ExcelProperty("姓名")
    @NonNull
    public String name;

    @ExcelProperty("平时分")
    public Integer general;

    @ExcelProperty("期中分")
    public Integer midterm;

    @ExcelProperty("期末分")
    public Integer finalExam;

    @ExcelProperty("总评分")
    public Integer total;

}
