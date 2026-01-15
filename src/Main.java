import java.sql.SQLException;
import java.util.Scanner;

/**
 * Main类：程序入口，提供命令行交互界面，实现用户与学生管理系统的操作交互
 */
public class Main {
    public static void main(String[] args) {
        // 初始化学生管理对象与输入工具
        StudentManager studentManager = new StudentManager();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true; // 控制系统运行状态

        // 交互主循环
        while (isRunning) {
            // 打印操作菜单
            System.out.println("\n=== 学生管理系统 ===");
            System.out.println("1. 添加学生信息");
            System.out.println("2. 根据ID查询学生");
            System.out.println("3. 显示所有学生信息");
            System.out.println("4. 计算各科平均分");
            System.out.println("5. 退出系统");
            System.out.print("请输入操作序号：");

            // 处理用户输入（包含输入合法性校验）
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // 清空输入缓冲区的换行符

                // 根据选择执行对应功能
                switch (choice) {
                    case 1:
                        // 功能1：添加学生
                        try {
                            System.out.print("请输入学生姓名：");
                            String name = scanner.nextLine();
                            System.out.print("请输入学生性别（男/女）：");
                            String gender = scanner.nextLine();
                            System.out.print("请输入学生班级：");
                            String className = scanner.nextLine();
                            System.out.print("请输入高数成绩：");
                            double mathScore = scanner.nextDouble();
                            System.out.print("请输入Java成绩：");
                            double javaScore = scanner.nextDouble();
                            scanner.nextLine(); // 清空缓冲区

                            // 创建Student对象并添加
                            Student student = new Student(0, name, gender, className, mathScore, javaScore);
                            studentManager.addStudent(student);
                        } catch (SQLException e) {
                            System.err.println("添加学生失败：" + e.getMessage());
                        } catch (Exception e) {
                            System.err.println("输入格式错误，请重新输入！");
                            scanner.nextLine(); // 清空错误输入
                        }
                        break;

                    case 2:
                        // 功能2：按ID查询学生
                        try {
                            System.out.print("请输入要查询的学生ID：");
                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Student queryStudent = studentManager.getStudentById(id);
                            if (queryStudent != null) {
                                System.out.println("\n=== 查询结果 ===");
                                System.out.printf("ID：%d，姓名：%s，班级：%s，高数：%.2f，Java：%.2f%n",
                                        queryStudent.getId(), queryStudent.getName(), queryStudent.getClassName(),
                                        queryStudent.getMathScore(), queryStudent.getJavaScore());
                            } else {
                                System.out.println("未找到ID为" + id + "的学生！");
                            }
                        } catch (SQLException e) {
                            System.err.println("查询学生失败：" + e.getMessage());
                        } catch (Exception e) {
                            System.err.println("输入格式错误，请输入数字ID！");
                            scanner.nextLine();
                        }
                        break;

                    case 3:
                        // 功能3：显示所有学生
                        try {
                            studentManager.getAllStudents();
                        } catch (SQLException e) {
                            System.err.println("获取学生列表失败：" + e.getMessage());
                        }
                        break;

                    case 4:
                        // 功能4：计算平均分
                        try {
                            studentManager.calculateAverageScores();
                        } catch (SQLException e) {
                            System.err.println("计算平均分失败：" + e.getMessage());
                        }
                        break;

                    case 5:
                        // 功能5：退出系统
                        System.out.println("系统已退出，再见！");
                        isRunning = false;
                        break;

                    default:
                        System.out.println("无效的操作序号，请输入1-5之间的数字！");
                }
            } else {
                // 处理非数字输入
                System.out.println("输入格式错误，请输入数字序号！");
                scanner.nextLine(); // 清空错误输入
            }
        }

        scanner.close(); // 关闭输入工具
    }
}