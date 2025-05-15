# Phần mềm Quản lý Y tế Học đường

## Mô tả
Phần mềm Quản lý Y tế Học đường là một ứng dụng web đơn giản được phát triển nhằm hỗ trợ quản lý thông tin sức khỏe của học sinh trong trường học. Dự án này được xây dựng dựa trên nền tảng Maven với các công nghệ HTML, CSS, và JavaScript. Ứng dụng cho phép nhập thông tin học sinh (tên, lớp, tình trạng sức khỏe) và hiển thị danh sách trong một bảng.

## Yêu cầu hệ thống
- **Java Development Kit (JDK)**: Phiên bản 17 hoặc 21 (tải từ [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) hoặc [Adoptium](https://adoptium.net/)).
- **Apache Maven**: Phiên bản 3.6.0 trở lên (tải từ [Maven](https://maven.apache.org/download.cgi)).
- **Trình duyệt web**: Google Chrome, Mozilla Firefox, hoặc bất kỳ trình duyệt hiện đại nào.
- **Môi trường phát triển (tùy chọn)**: Visual Studio Code với Extension Pack for Java.

## Cài đặt

### Bước 1: Cài đặt JDK
1. Tải và cài đặt JDK (ví dụ: JDK 21) từ [Adoptium](https://adoptium.net/).
2. Cấu hình biến môi trường `JAVA_HOME`:
   - Mở `System Properties` (nhấn **Windows + R**, gõ `sysdm.cpl`).
   - Vào **Environment Variables**, tạo biến `JAVA_HOME` với giá trị là đường dẫn đến thư mục JDK (ví dụ: `C:\Program Files\Java\jdk-21`).
   - Thêm `%JAVA_HOME%\bin` vào biến `Path`.
3. Kiểm tra: Mở Command Prompt, gõ `java -version` và `javac -version`.

### Bước 2: Cài đặt Maven
1. Tải Maven từ [Maven](https://maven.apache.org/download.cgi).
2. Giải nén file tải về (ví dụ: `apache-maven-3.9.9-bin.zip`) vào một thư mục (ví dụ: `C:\apache-maven-3.9.9`).
3. Thêm đường dẫn `C:\apache-maven-3.9.9\bin` vào biến `Path`.
4. Kiểm tra: Gõ `mvn -version` trong Command Prompt.

### Bước 3: Clone hoặc tải dự án
- Nếu bạn đã có dự án tại `C:\MyNewJavaProject\myproject`, bỏ qua bước này.
- Nếu tải từ kho lưu trữ (Git), chạy:
  ```bash
  git clone <URL_repository>
