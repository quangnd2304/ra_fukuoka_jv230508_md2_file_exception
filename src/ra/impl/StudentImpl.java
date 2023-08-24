package ra.impl;

import ra.entity.Student;

import java.io.*;
import java.util.*;

public class StudentImpl {
    static List<Student> listStudent = new ArrayList<>();

    public static void main(String[] args) {
        //Đọc dữ liệu từ file listStudent.txt
        readDataFromFile();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhật thông tin sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên");
            System.out.println("8. Thoát");
            System.out.print("Sự lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        StudentImpl.inputListStudent(scanner);
                        break;
                    case 2:
                        StudentImpl.calListAge();
                        break;
                    case 3:
                        StudentImpl.calAvg_rank();
                        break;
                    case 4:
                        StudentImpl.sortStudent();
                        break;
                    case 5:
                        StudentImpl.thongKe();
                        break;
                    case 6:
                        StudentImpl.updateStudent(scanner);
                        break;
                    case 7:
                        StudentImpl.searchStudentByName(scanner);
                        break;
                    case 8:
                        writeDataToFile();
                        System.exit(0);
                    default:
                        System.err.println("Vui lòng chọn từ 1-8");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng chọn số");
            } catch (Exception ex) {
                System.err.println("Có lỗi không xác định, vui lòng liên hệ hệ thống");
            }
        } while (true);
    }

    public static void readDataFromFile() {
        File file = new File("listStudent.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            if (ois.readObject() != null) {
                listStudent = (List<Student>) ois.readObject();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tồn tại file");
        } catch (IOException e) {
            System.err.println("Lỗi IO");
        } catch (ClassNotFoundException e) {
            System.err.println("Lớp không tồn tại");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.err.println("Lỗi runtime");
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    System.err.println("Lỗi IO");
                }
            }
        }
    }

    public static void writeDataToFile() {
        File file = new File("listStudent.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listStudent);
            oos.flush();
        } catch (FileNotFoundException e) {
            System.err.println("Lỗi runtime");
        } catch (IOException e) {
            System.err.println("Lỗi IO");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("Lỗi runtime");
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.err.println("Lỗi IO");
                }
            }
        }

    }

    public static void inputListStudent(Scanner scanner) {
        System.out.println("Nhập vào số sinh viên cần nhập dữ liệu:");
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < number; i++) {
                    Student student = new Student();
                    student.inputData(scanner, listStudent);
                    listStudent.add(student);
                }
                break;
            } catch (NumberFormatException nfe) {
                System.err.println();
            }
        } while (true);
    }

    public static void calListAge() {
        for (Student student : listStudent) {
            student.calAge();
        }
        System.out.println("Đã tính xong tuổi cho tất cả sinh viên");
    }

    public static void calAvg_rank() {
        for (Student student : listStudent) {
            student.calAvgMark_Rank();
        }
        System.out.println("Đã tính xong điểm trung bình và xếp loại cho tất cả các sinh viên");
    }

    public static void sortStudent() {
        Collections.sort(listStudent, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();
            }
        });
    }

    public static void thongKe() {
        int cntYeu = 0;
        int cntTb = 0;
        int cntKha = 0;
        int cntGioi = 0;
        int cntXS = 0;
        for (Student student : listStudent) {
            if (student.getRank().equals("Yếu")) {
                cntYeu++;
            } else if (student.getRank().equals("Trung bình")) {
                cntTb++;
            } else if (student.getRank().equals("Khá")) {
                cntKha++;
            } else if (student.getRank().equals("Giỏi")) {
                cntGioi++;
            } else {
                cntXS++;
            }
        }
        System.out.printf("Thống kê: Xuất sắc: %d - Giỏi: %d - Khá: %d - Trung bình: %d - Yếu: %d\n", cntXS, cntGioi, cntKha, cntTb, cntYeu);

    }

    public static void updateStudent(Scanner scanner) {
        System.out.println("Nhập vào mã sinh viên cần cập nhật:");
        String studentId = scanner.nextLine();
        int index = -1;
        for (int i = 0; i < listStudent.size(); i++) {
            if (listStudent.get(i).getStudentId().equals(studentId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            //Cập nhật
            listStudent.get(index).setStudentName(Student.validateStudentName(scanner));
            listStudent.get(index).setSex(Student.validateSex(scanner));
            listStudent.get(index).setBirthDay(Student.validateBirthDay(scanner));
            listStudent.get(index).setMark_html(Student.validateMarkHTML(scanner));
            listStudent.get(index).setMark_css(Student.validateMarkCss(scanner));
            listStudent.get(index).setMark_javascript(Student.validateMarkJavascript(scanner));
            listStudent.get(index).calAge();
            listStudent.get(index).calAvgMark_Rank();
        } else {
            System.out.println("Không tồn tại mã sinh viên như vậy");
        }
    }

    public static void searchStudentByName(Scanner scanner) {
        System.out.println("Nhập vào tên sinh viên cần tìm: ");
        String studentName = scanner.nextLine();
        for (Student student : listStudent) {
            if (student.getStudentName().toLowerCase().contains(studentName.toLowerCase())){
                student.displayData();
            }
        }
    }
}
