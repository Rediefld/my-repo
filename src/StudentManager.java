import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StudentManager类：封装学生信息的数据库操作功能
 * 实现添加、查询、显示、计算平均分等业务逻辑
 */
public class StudentManager {

    /**
     * 功能1：添加学生信息到数据库
     * @param student 待添加的Student对象（id由数据库自增，无需设置）
     * @throws SQLException 数据库操作异常
     */
    public void addStudent(Student student) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO students(name, gender, class, math_score, java_score) VALUES(?, ?, ?, ?, ?)";

        try {
            // 获取数据库连接
            conn = DatabaseConnection.getConnection();
            // 预编译SQL（防SQL注入）
            pstmt = conn.prepareStatement(sql);
            // 设置SQL参数（与Student对象属性对应）
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getGender());
            pstmt.setString(3, student.getClassName());
            pstmt.setDouble(4, student.getMathScore());
            pstmt.setDouble(5, student.getJavaScore());
            // 执行插入操作
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("学生信息添加成功！");
            }
        } finally {
            // 关闭数据库资源
            DatabaseConnection.closeResources(conn, pstmt);
        }
    }

    /**
     * 功能2：根据ID查询学生信息
     * @param id 学生ID
     * @return 对应的Student对象（无数据则返回null）
     * @throws SQLException 数据库操作异常
     */
    public Student getStudentById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;
        String sql = "SELECT * FROM students WHERE id = ?";

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            // 封装结果集为Student对象
            if (rs.next()) {
                student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setClassName(rs.getString("class"));
                student.setMathScore(rs.getDouble("math_score"));
                student.setJavaScore(rs.getDouble("java_score"));
            }
        } finally {
            DatabaseConnection.closeResources(conn, pstmt, rs);
        }
        return student;
    }

    /**
     * 功能3：显示所有学生信息
     * @return 所有学生的List集合
     * @throws SQLException 数据库操作异常
     */
    public List<Student> getAllStudents() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 遍历结果集，封装为Student对象并加入列表
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setClassName(rs.getString("class"));
                student.setMathScore(rs.getDouble("math_score"));
                student.setJavaScore(rs.getDouble("java_score"));
                studentList.add(student);
            }

            // 打印所有学生信息
            System.out.println("=== 所有学生信息 ===");
            for (Student s : studentList) {
                System.out.printf("ID：%d，姓名：%s，班级：%s，高数：%.2f，Java：%.2f%n",
                        s.getId(), s.getName(), s.getClassName(), s.getMathScore(), s.getJavaScore());
            }
        } finally {
            DatabaseConnection.closeResources(conn, pstmt, rs);
        }
        return studentList;
    }

    /**
     * 功能4：计算学生各科目的平均分数
     * @return 键为科目名、值为平均分的Map
     * @throws SQLException 数据库操作异常
     */
    public Map<String, Double> calculateAverageScores() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<String, Double> avgScores = new HashMap<>();
        String sql = "SELECT AVG(math_score) AS math_avg, AVG(java_score) AS java_avg FROM students";

        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                avgScores.put("高数平均分", rs.getDouble("math_avg"));
                avgScores.put("Java平均分", rs.getDouble("java_avg"));
            }

            // 打印平均分
            System.out.println("=== 各科平均分 ===");
            avgScores.forEach((subject, score) -> System.out.printf("%s：%.2f%n", subject, score));
        } finally {
            DatabaseConnection.closeResources(conn, pstmt, rs);
        }
        return avgScores;
    }


    // 测试方法：验证功能是否正常
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        try {
            // 测试添加学生
            Student student = new Student(0, "张三", "男", "计科2023-1", 88.5, 92.0);
            manager.addStudent(student);

            // 测试查询学生（替换为实际添加的ID）
            Student queryStudent = manager.getStudentById(1);
            if (queryStudent != null) {
                System.out.println("=== 查询到的学生 ===");
                System.out.println("姓名：" + queryStudent.getName() + "，Java成绩：" + queryStudent.getJavaScore());
            }

            // 测试显示所有学生
            manager.getAllStudents();

            // 测试计算平均分
            manager.calculateAverageScores();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}