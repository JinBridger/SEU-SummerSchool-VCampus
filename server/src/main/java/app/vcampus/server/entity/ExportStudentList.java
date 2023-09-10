package app.vcampus.server.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExportStudentList {
    @ExcelProperty("学号")
    private String studentNumber;

    @ExcelProperty("姓名")
    private String name;
}
