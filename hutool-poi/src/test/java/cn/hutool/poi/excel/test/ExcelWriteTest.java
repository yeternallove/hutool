/**
 * 
 */
package cn.hutool.poi.excel.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Ignore;
import org.junit.Test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.style.StyleUtil;

/**
 * 写出Excel单元测试
 * 
 * @author looly
 */
public class ExcelWriteTest {

	@Test
	@Ignore
	public void writeTest2() {
		List<String> row = CollUtil.newArrayList("姓名", "加班日期", "下班时间", "加班时长", "餐补", "车补次数", "车补", "总计");
		ExcelWriter overtimeWriter = ExcelUtil.getWriter("e:/excel/single_line.xlsx");
		overtimeWriter.writeRow(row);
		overtimeWriter.close();
	}

	@Test
	@Ignore
	public void writeWithSheetTest() {
		ExcelWriter writer = ExcelUtil.getWriterWithSheet("表格1");

		// 写出第一张表
		List<String> row = CollUtil.newArrayList("姓名", "加班日期", "下班时间", "加班时长", "餐补", "车补次数", "车补", "总计");
		writer.writeRow(row);

		// 写出第二张表
		writer.setSheet("表格2");
		List<String> row2 = CollUtil.newArrayList("姓名2", "加班日期2", "下班时间2", "加班时长2", "餐补2", "车补次数2", "车补2", "总计2");
		writer.writeRow(row2);

		// 生成文件或导出Excel
		writer.flush(FileUtil.file("f:/test/writeWithSheetTest.xlsx"));

		writer.close();
	}

	@Test
	@Ignore
	public void writeTest() {
		List<?> row1 = CollUtil.newArrayList("aaaaa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
		List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
		List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
		List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
		List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

		List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
		for (int i = 0; i < 400; i++) {
			// 超大列表写出测试
			rows.add(ObjectUtil.clone(row1));
		}

		String filePath = "f:/test/writeTest.xlsx";
		FileUtil.del(filePath);
		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter(filePath);
		// 通过构造方法创建writer
		// ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

		// 跳过当前行，既第一行，非必须，在此演示用
		writer.passCurrentRow();
		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(row1.size() - 1, "测试标题");
		// 一次性写出内容，使用默认样式
		writer.write(rows);
		writer.autoSizeColumn(0, true);
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void mergeTest() {
		List<?> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
		List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
		List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
		List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
		List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

		List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("e:/mergeTest.xlsx");
		CellStyle style = writer.getStyleSet().getHeadCellStyle();
		StyleUtil.setColor(style, IndexedColors.RED, FillPatternType.SOLID_FOREGROUND);

		// 跳过当前行，既第一行，非必须，在此演示用
		writer.passCurrentRow();
		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(row1.size() - 1, "测试标题");
		// 一次性写出内容，使用默认样式
		writer.write(rows);

		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(7, 10, 4, 10, "测试Merge", false);

		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void mergeTest2() {
		Map<String, Object> row1 = new LinkedHashMap<>();
		row1.put("姓名", "张三");
		row1.put("年龄", 23);
		row1.put("成绩", 88.32);
		row1.put("是否合格", true);
		row1.put("考试日期", DateUtil.date());

		Map<String, Object> row2 = new LinkedHashMap<>();
		row2.put("姓名", "李四");
		row2.put("年龄", 33);
		row2.put("成绩", 59.50);
		row2.put("是否合格", false);
		row2.put("考试日期", DateUtil.date());

		ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("f:/test/writeMapTest.xlsx");
		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(row1.size() - 1, "一班成绩单");

		// 一次性写出内容，使用默认样式，强制输出标题
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void writeMapTest() {
		Map<String, Object> row1 = new LinkedHashMap<>();
		row1.put("姓名", "张三");
		row1.put("年龄", 23);
		row1.put("成绩", 88.32);
		row1.put("是否合格", true);
		row1.put("考试日期", DateUtil.date());

		Map<String, Object> row2 = new LinkedHashMap<>();
		row2.put("姓名", "李四");
		row2.put("年龄", 33);
		row2.put("成绩", 59.50);
		row2.put("是否合格", false);
		row2.put("考试日期", DateUtil.date());

		ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("e:/excel/writeMapTest.xlsx");

		// 设置内容字体
		Font font = writer.createFont();
		font.setBold(true);
		font.setColor(Font.COLOR_RED);
		font.setItalic(true);
		writer.getStyleSet().setFont(font, true);

		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(row1.size() - 1, "一班成绩单");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void writeMapTest2() {
		Map<String, Object> row1 = MapUtil.newHashMap(true);
		row1.put("姓名", "张三");
		row1.put("年龄", 23);
		row1.put("成绩", 88.32);
		row1.put("是否合格", true);
		row1.put("考试日期", DateUtil.date());

		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("e:/writeMapTest2.xlsx");

		// 一次性写出内容，使用默认样式
		writer.writeRow(row1, true);
		// 关闭writer，释放内存
		writer.close();
	}
	
	@Test
	@Ignore
	public void writeMapWithStyleTest() {
		Map<String, Object> row1 = MapUtil.newHashMap(true);
		row1.put("姓名", "张三");
		row1.put("年龄", 23);
		row1.put("成绩", 88.32);
		row1.put("是否合格", true);
		row1.put("考试日期", DateUtil.date());

		// 通过工具类创建writer
		String path = "f:/test/writeMapWithStyleTest.xlsx";
		FileUtil.del(path);
		ExcelWriter writer = ExcelUtil.getWriter(path);
		writer.setStyleSet(null);
		
		// 一次性写出内容，使用默认样式
		writer.writeRow(row1, true);
		
		// 设置某个单元格样式
		CellStyle orCreateRowStyle = writer.getOrCreateCellStyle(0, 1);
		StyleUtil.setColor(orCreateRowStyle,IndexedColors.RED.getIndex(),FillPatternType.SOLID_FOREGROUND );
		
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void writeMapAliasTest() {
		Map<Object, Object> row1 = new LinkedHashMap<>();
		row1.put("name", "张三");
		row1.put("age", 22);
		row1.put("isPass", true);
		row1.put("score", 66.30);
		row1.put("examDate", DateUtil.date());
		Map<Object, Object> row2 = new LinkedHashMap<>();
		row2.put("name", "李四");
		row2.put("age", 233);
		row2.put("isPass", false);
		row2.put("score", 32.30);
		row2.put("examDate", DateUtil.date());

		List<Map<Object, Object>> rows = CollUtil.newArrayList(row1, row2);
		// 通过工具类创建writer
		String file = "e:/writeMapAlias.xlsx";
		FileUtil.del(file);
		ExcelWriter writer = ExcelUtil.getWriter(file);
		// 自定义标题
		writer.addHeaderAlias("name", "姓名");
		writer.addHeaderAlias("age", "年龄");
		writer.addHeaderAlias("score", "分数");
		writer.addHeaderAlias("isPass", "是否通过");
		writer.addHeaderAlias("examDate", "考试时间");
		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(4, "一班成绩单");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void writeMapOnlyAliasTest() {
		Map<Object, Object> row1 = new LinkedHashMap<>();
		row1.put("name", "张三");
		row1.put("age", 22);
		row1.put("isPass", true);
		row1.put("score", 66.30);
		row1.put("examDate", DateUtil.date());
		Map<Object, Object> row2 = new LinkedHashMap<>();
		row2.put("name", "李四");
		row2.put("age", 233);
		row2.put("isPass", false);
		row2.put("score", 32.30);
		row2.put("examDate", DateUtil.date());

		List<Map<Object, Object>> rows = CollUtil.newArrayList(row1, row2);
		// 通过工具类创建writer
		String file = "f:/test/test_alias.xlsx";
		FileUtil.del(file);
		ExcelWriter writer = ExcelUtil.getWriter(file);
		writer.setOnlyAlias(true);
		// 自定义标题
		writer.addHeaderAlias("name", "姓名");
		writer.addHeaderAlias("age", "年龄");
		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(4, "一班成绩单");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();
	}
	
	@Test
	@Ignore
	public void writeMapOnlyAliasTest2() {
		Map<Object, Object> row1 = new LinkedHashMap<>();
		row1.put("name", "张三");
		row1.put("age", 22);
		row1.put("isPass", true);
		row1.put("score", 66.30);
		row1.put("examDate", DateUtil.date());
		Map<Object, Object> row2 = new LinkedHashMap<>();
		row2.put("name", "李四");
		row2.put("age", 233);
		row2.put("isPass", false);
		row2.put("score", 32.30);
		row2.put("examDate", DateUtil.date());

		List<Map<Object, Object>> rows = CollUtil.newArrayList(row1, row2);
		// 通过工具类创建writer
		String file = "f:/test/test_alias.xls";
		ExcelWriter writer = ExcelUtil.getWriter(file, "test1");
//		writer.setOnlyAlias(true);
		// 自定义标题
		writer.addHeaderAlias("name", "姓名");
		writer.addHeaderAlias("age", "年龄");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void writeBeanTest() {
		TestBean bean1 = new TestBean();
		bean1.setName("张三");
		bean1.setAge(22);
		bean1.setPass(true);
		bean1.setScore(66.30);
		bean1.setExamDate(DateUtil.date());

		TestBean bean2 = new TestBean();
		bean2.setName("李四");
		bean2.setAge(28);
		bean2.setPass(false);
		bean2.setScore(38.50);
		bean2.setExamDate(DateUtil.date());

		List<TestBean> rows = CollUtil.newArrayList(bean1, bean2);
		// 通过工具类创建writer
		String file = "e:/writeBeanTest.xlsx";
		FileUtil.del(file);
		ExcelWriter writer = ExcelUtil.getWriter(file);
		// 自定义标题
		writer.addHeaderAlias("name", "姓名");
		writer.addHeaderAlias("age", "年龄");
		writer.addHeaderAlias("score", "分数");
		writer.addHeaderAlias("isPass", "是否通过");
		writer.addHeaderAlias("examDate", "考试时间");
		// 合并单元格后的标题行，使用默认标题样式
		writer.merge(4, "一班成绩单");
		// 一次性写出内容，使用默认样式
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();
	}

	@Test
	@Ignore
	public void writeCellValueTest() {
		ExcelWriter writer = new ExcelWriter("d:/cellValueTest.xls");
		writer.writeCellValue(3, 5, "aaa");
		writer.writeCellValue(3, 5, "aaa");
		writer.close();
	}
	
	@Test
	@Ignore
	public void addSelectTest() {
		List<String> row = CollUtil.newArrayList("姓名", "加班日期", "下班时间", "加班时长", "餐补", "车补次数", "车补", "总计");
		ExcelWriter overtimeWriter = ExcelUtil.getWriter("f:/excel/single_line.xlsx");
		overtimeWriter.writeCellValue(3, 4, "AAAA");
		overtimeWriter.addSelect(3, 4, row.toArray(new String[row.size()]));
		overtimeWriter.close();
	}
}
