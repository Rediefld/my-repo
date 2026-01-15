/**
 * Student类：封装学生信息，与数据库students表的字段一一映射
 * 用于在代码中承载学生的ID、姓名、性别等数据
 */
public class Student {
    // 对应students表的字段，使用private修饰保证封装性
    private int id; // 学生ID
    private String name; // 姓名
    private String gender; // 性别
    private String className; // 班级（避免与Java关键字class重名，命名为className）
    private double mathScore; // 高数成绩
    private double javaScore; // Java成绩

    // 无参构造方法（创建对象时默认调用）
    public Student() {
    }

    // 全参构造方法（快速初始化对象）
    public Student(int id, String name, String gender, String className, double mathScore, double javaScore) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.className = className;
        this.mathScore = mathScore;
        this.javaScore = javaScore;
    }

    // id的get/set方法
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // name的get/set方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // gender的get/set方法
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    // className的get/set方法
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    // mathScore的get/set方法
    public double getMathScore() {
        return mathScore;
    }
    public void setMathScore(double mathScore) {
        this.mathScore = mathScore;
    }

    // javaScore的get/set方法
    public double getJavaScore() {
        return javaScore;
    }
    public void setJavaScore(double javaScore) {
        this.javaScore = javaScore;
    }
}