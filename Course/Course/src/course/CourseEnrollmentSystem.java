import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Course {
    String name;
    double price;
    double discountedPrice;
    List<String> subjects;

    Course(String name, double price) {
        this.name = name;
        this.price = price;
        this.discountedPrice = price;
        this.subjects = new ArrayList<>();
    }

    void addSubject(String subject) {
        subjects.add(subject);
    }

    void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    void viewSubjects(JTextArea textArea) {
        textArea.setText("");
        textArea.append("Subjects in " + name + ":\n");
        for (String subject : subjects) {
            textArea.append("- " + subject + "\n");
        }
    }

    @Override
    public String toString() {
        return name + " - RM" + String.format("%.2f", discountedPrice);
    }
}

class Program {
    String name;
    List<Course> courses;

    Program(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    void addCourse(Course course) {
        courses.add(course);
    }

    void viewCourses(JTextArea textArea) {
        textArea.setText("");
        for (int i = 0; i < courses.size(); i++) {
            textArea.append((i + 1) + ". " + courses.get(i) + "\n");
        }
    }
}

class EnrollmentSystem {
    List<Program> programs;
    double discountRate;
    List<Course> enrolledCourses;

    EnrollmentSystem(double discountRate) {
        this.programs = new ArrayList<>();
        this.discountRate = discountRate;
        this.enrolledCourses = new ArrayList<>();
    }

    void addProgram(Program program) {
        programs.add(program);
    }

    Program selectProgram(int level) {
        if (level >= 1 && level <= programs.size()) {
            return programs.get(level - 1);
        }
        return null;
    }

    double applyDiscount(double price, int currentLevel, int nextLevel) {
        if (nextLevel > currentLevel) {
            return price * (1 - discountRate);
        }
        return price;
    }

    void enrollCourse(Course course) {
        double finalPrice = applyDiscount(course.price, 0, 1);
        course.setDiscountedPrice(finalPrice);
        enrolledCourses.add(course);
    }

    void viewEnrolledCourses(JTextArea textArea) {
        textArea.setText("");
        if (enrolledCourses.isEmpty()) {
            textArea.setText("You have not enrolled in any courses.");
        } else {
            textArea.append("Enrolled Courses:\n");
            for (Course course : enrolledCourses) {
                textArea.append(course.toString() + "\n");
            }
        }
    }

    void removeCourse(int index) {
        if (index >= 0 && index < enrolledCourses.size()) {
            enrolledCourses.remove(index);
        }
    }

    double getTotalEnrollmentFee() {
        double total = 0;
        for (Course course : enrolledCourses) {
            total += course.discountedPrice;
        }
        return total;
    }

    int getEnrolledCoursesCount() {
        return enrolledCourses.size();
    }
}

public class CourseEnrollmentSystem extends JFrame {
    private EnrollmentSystem enrollmentSystem;
    private JTextArea courseListArea;
    private JTextArea enrolledCoursesArea;
    private JTextArea subjectListArea;
    private JComboBox<String> programComboBox;
    private JTextField courseNumberField;
    private int currentLevel;

    public CourseEnrollmentSystem() {
        enrollmentSystem = new EnrollmentSystem(0.10);
        currentLevel = 0;

        createPrograms();
        initializeUI();
    }

    private void createPrograms() {
        Program matriculation = new Program("Matriculation");
        Course matricEngineering = new Course("Engineering", 100);
        matricEngineering.addSubject("Mathematics");
        matricEngineering.addSubject("Physics");
        matricEngineering.addSubject("Chemistry");
        matricEngineering.addSubject("Introduction to Engineering");
        matriculation.addCourse(matricEngineering);

        Course matricIT = new Course("IT", 80);
        matricIT.addSubject("Mathematics");
        matricIT.addSubject("Programming Fundamentals");
        matricIT.addSubject("Data Structures");
        matricIT.addSubject("Computer Systems");
        matriculation.addCourse(matricIT);

        Course matricBusiness = new Course("Business", 80);
        matricBusiness.addSubject("Mathematics");
        matricBusiness.addSubject("Economics");
        matricBusiness.addSubject("Accounting");
        matricBusiness.addSubject("Management");
        matriculation.addCourse(matricBusiness);

        Course matricLaw = new Course("Law", 80);
        matricLaw.addSubject("Introduction to Law");
        matricLaw.addSubject("Constitutional Law");
        matricLaw.addSubject("Criminal Law");
        matricLaw.addSubject("Contract Law");
        matriculation.addCourse(matricLaw);

        Program undergraduate = new Program("Undergraduate");
        Course undergradEngineering = new Course("Engineering", 200);
        undergradEngineering.addSubject("Advanced Mathematics");
        undergradEngineering.addSubject("Thermodynamics");
        undergradEngineering.addSubject("Mechanics");
        undergradEngineering.addSubject("Engineering Design");
        undergraduate.addCourse(undergradEngineering);

        Course undergradIT = new Course("IT", 180);
        undergradIT.addSubject("Algorithms");
        undergradIT.addSubject("Database Systems");
        undergradIT.addSubject("Operating Systems");
        undergradIT.addSubject("Networks");
        undergraduate.addCourse(undergradIT);

        Course undergradBusiness = new Course("Business", 180);
        undergradBusiness.addSubject("Microeconomics");
        undergradBusiness.addSubject("Macroeconomics");
        undergradBusiness.addSubject("Marketing");
        undergradBusiness.addSubject("Finance");
        undergraduate.addCourse(undergradBusiness);

        Course undergradLaw = new Course("Law", 180);
        undergradLaw.addSubject("Civil Procedure");
        undergradLaw.addSubject("Commercial Law");
        undergradLaw.addSubject("Property Law");
        undergradLaw.addSubject("International Law");
        undergraduate.addCourse(undergradLaw);

        Program postgraduate = new Program("Postgraduate");
        Course postgradEngineering = new Course("Engineering", 300);
        postgradEngineering.addSubject("Research Methods");
        postgradEngineering.addSubject("Advanced Thermodynamics");
        postgradEngineering.addSubject("Finite Element Analysis");
        postgradEngineering.addSubject("Project Management");
        postgraduate.addCourse(postgradEngineering);

        Course postgradIT = new Course("IT", 280);
        postgradIT.addSubject("Machine Learning");
        postgradIT.addSubject("Cloud Computing");
        postgradIT.addSubject("Advanced Database Systems");
        postgradIT.addSubject("Cybersecurity");
        postgraduate.addCourse(postgradIT);

        Course postgradBusiness = new Course("Business", 280);
        postgradBusiness.addSubject("Advanced Finance");
        postgradBusiness.addSubject("Strategic Management");
        postgradBusiness.addSubject("Business Ethics");
        postgradBusiness.addSubject("Global Business");
        postgraduate.addCourse(postgradBusiness);

        Course postgradLaw = new Course("Law", 280);
        postgradLaw.addSubject("International Human Rights");
        postgradLaw.addSubject("Comparative Law");
        postgradLaw.addSubject("Legal Theory");
        postgradLaw.addSubject("Advanced Constitutional Law");
        postgraduate.addCourse(postgradLaw);

        enrollmentSystem.addProgram(matriculation);
        enrollmentSystem.addProgram(undergraduate);
        enrollmentSystem.addProgram(postgraduate);
    }

    private void initializeUI() {
        setTitle("Course Enrollment System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2, 10, 10));
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel programLabel = new JLabel("Select Program:");
        topPanel.add(programLabel);

        String[] programNames = {"Matriculation", "Undergraduate", "Postgraduate"};
        programComboBox = new JComboBox<>(programNames);
        topPanel.add(programComboBox);

        JButton viewCoursesButton = new JButton("View Courses");
        viewCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCourses();
            }
        });
        topPanel.add(viewCoursesButton);

        JLabel courseNumberLabel = new JLabel("Course Number:");
        topPanel.add(courseNumberLabel);

        courseNumberField = new JTextField();
        topPanel.add(courseNumberField);

        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollCourse();
            }
        });
        topPanel.add(enrollButton);

        JButton viewEnrolledButton = new JButton("View Enrolled Courses");
        viewEnrolledButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewEnrolledCourses();
            }
        });
        topPanel.add(viewEnrolledButton);

        JButton removeCourseButton = new JButton("Remove Course");
        removeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCourse();
            }
        });
        topPanel.add(removeCourseButton);

        JButton viewSubjectsButton = new JButton("View Subjects");
        viewSubjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSubjects();
            }
        });
        topPanel.add(viewSubjectsButton);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        courseListArea = new JTextArea();
        courseListArea.setEditable(false);
        courseListArea.setBorder(BorderFactory.createTitledBorder("Available Courses"));
        centerPanel.add(new JScrollPane(courseListArea));

        enrolledCoursesArea = new JTextArea();
        enrolledCoursesArea.setEditable(false);
        enrolledCoursesArea.setBorder(BorderFactory.createTitledBorder("Enrolled Courses"));
        centerPanel.add(new JScrollPane(enrolledCoursesArea));

        subjectListArea = new JTextArea();
        subjectListArea.setEditable(false);
        subjectListArea.setBorder(BorderFactory.createTitledBorder("Subjects"));
        centerPanel.add(new JScrollPane(subjectListArea));

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void viewCourses() {
        int level = programComboBox.getSelectedIndex() + 1;
        Program program = enrollmentSystem.selectProgram(level);
        if (program != null) {
            courseListArea.setText("");
            courseListArea.append("Courses available in " + program.name + ":\n");
            program.viewCourses(courseListArea);
        }
    }

    private void enrollCourse() {
        int level = programComboBox.getSelectedIndex() + 1;
        Program program = enrollmentSystem.selectProgram(level);
        if (program == null) {
            return;
        }

        try {
            int courseIndex = Integer.parseInt(courseNumberField.getText()) - 1;
            if (courseIndex < 0 || courseIndex >= program.courses.size()) {
                JOptionPane.showMessageDialog(this, "Invalid course number. Try again.");
                return;
            }

            Course course = program.courses.get(courseIndex);
            enrollmentSystem.enrollCourse(course);

            JOptionPane.showMessageDialog(this, "Enrolled in: " + course.name + "\nTotal fee after discount: RM" + String.format("%.2f", course.discountedPrice));

            // Clear course number field after enrollment
            courseNumberField.setText("");

            // Update enrolled courses display
            viewEnrolledCourses();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid course number format. Try again.");
        }
    }

    private void viewEnrolledCourses() {
        enrollmentSystem.viewEnrolledCourses(enrolledCoursesArea);
    }

    private void removeCourse() {
        try {
            int courseIndex = Integer.parseInt(courseNumberField.getText()) - 1;
            if (courseIndex < 0 || courseIndex >= enrollmentSystem.getEnrolledCoursesCount()) {
                JOptionPane.showMessageDialog(this, "Invalid course number. Try again.");
                return;
            }

            enrollmentSystem.removeCourse(courseIndex);

            // Show updated enrolled courses
            viewEnrolledCourses();

            JOptionPane.showMessageDialog(this, "Course removed successfully.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid course number format. Try again.");
        }
    }

    private void viewSubjects() {
        int level = programComboBox.getSelectedIndex() + 1;
        Program program = enrollmentSystem.selectProgram(level);
        if (program != null) {
            int courseIndex = Integer.parseInt(courseNumberField.getText()) - 1;
            Course course = program.courses.get(courseIndex);
            subjectListArea.setText("");
            course.viewSubjects(subjectListArea);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CourseEnrollmentSystem();
            }
        });
    }
}
