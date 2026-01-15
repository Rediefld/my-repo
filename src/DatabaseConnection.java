import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接工具类，用于连接student_management数据库
 * 封装连接获取、资源关闭等通用操作，避免重复代码
 */
public class DatabaseConnection {
    // 数据库连接核心配置（根据你的MySQL实际配置修改）
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // MySQL 8.x驱动类
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String DB_USER = "root"; // 你的MySQL用户名（默认root）
    private static final String DB_PASSWORD = "Yzx147159."; // 你的MySQL密码（修改为实际密码）

    // 静态代码块：加载数据库驱动（仅执行一次）
    static {
        try {
            Class.forName(DB_DRIVER);
            System.out.println("数据库驱动加载成功！");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 私有构造方法：禁止外部实例化（工具类设计）
     */
    private DatabaseConnection() {}

    /**
     * 获取数据库连接对象
     * @return Connection 数据库连接对象
     * @throws SQLException 连接失败时抛出异常
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("成功连接student_management数据库！");
        } catch (SQLException e) {
            System.err.println("连接student_management数据库失败：" + e.getMessage());
            throw e; // 抛出异常让调用方处理
        }
        return conn;
    }

    /**
     * 关闭数据库资源（Connection、Statement、ResultSet）
     * @param conn 连接对象
     * @param stmt 执行SQL的对象
     * @param rs 结果集对象
     */
    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        // 关闭ResultSet（若有）
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭Statement
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭Connection
        if (conn != null) {
            try {
                conn.close();
                System.out.println("数据库连接已关闭！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重载：仅关闭Connection和Statement（无ResultSet时使用）
     */
    public static void closeResources(Connection conn, Statement stmt) {
        closeResources(conn, stmt, null);
    }

    // 测试方法：验证连接是否正常
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // 若控制台输出"成功连接student_management数据库！"，说明连接正常
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}