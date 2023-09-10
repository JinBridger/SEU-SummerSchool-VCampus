package app.vcampus.server.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelStudentList {
    @ExcelProperty("学号")
    public String studentNumber;

    @ExcelProperty("姓名")
    public String name;
}
