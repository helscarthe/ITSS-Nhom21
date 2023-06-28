
# Hướng dẫn setup

Theo sát các bước này để cấu hình project trên Eclipse. Các bước sau giả sử máy đích chạy trên Windows.


## Yêu cầu cài đặt trước

Cài **Adoptium OpenJDK 11**: [Latest Releases | Adoptium](https://adoptium.net/temurin/releases/?version=11). Tải bản JDK .msi.

Cài **Scene Builder**: [Scene Builder - Gluon (gluonhq.com)](https://gluonhq.com/products/scene-builder/#download). Tải bản 20, Windows Installer. 

Nhớ đường dẫn cài đặt cho cả hai thứ.

## Cấu hình project

 1. `git clone` project này về, với git client tùy chọn.
 2. Cấu hình Eclipse sử dụng **Adoptium OpenJDK 11**:
	 - Trong Eclipse, Windows > Preferences > Java > Installed JREs, chọn nút Add...
	 - Chọn Standard VM > Next
	 - Chọn Directory là đường dẫn cài đặt. (Thường sẽ nằm tại `C:\Program Files\Eclipse Adoptium\jdk-11.0.19.7-hotspot`), rồi Finish để kết thúc.
	 - Tiếp tục từ Installed JREs > Execution Environments, chọn JavaSE-11, và chọn JDK mình vừa cài đặt (thường sẽ là jdk-11.0.19.7-hotspot).
	 - Thoát bằng nút Apply and Close.
 3. Cấu hình **SceneBuilder**:
	 - Trong Eclipse, Windows > Preferences > JavaFX
	 - Ở SceneBuilder executable, chọn Browse
	 - Trỏ đến file .exe trong đường dẫn cài đặt. (Thường sẽ ở `C:\Users\<tên máy>\AppData\Local\SceneBuilder\SceneBuilder.exe`)
	 - Thoát bằng nút Apply and Close.
 4. Cấu hình **e(fx)clipse**:
	 - Trong Eclipse, Help > Eclipse Marketplace
	 - Ô Find, tìm `fx` và Enter
	 - Click Install e(fx)clipse bản mới nhất
	 - Tiếp tục Confirm để tải
 5. Cấu hình VM:
     - Trong Eclipse, vào Run > Run Configurations... > Java Application
     - Chuột phải Java Application, chọn New Launch Configuration, đặt tên bất kỳ.
     - Tab Main, đảm bảo Project được chọn là GroceryManagementProject
     - Tab Arguments, mục VM Arguments, thêm `--module-path lib/win/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml`

Các bước để cấu hình chạy SQLite sẽ được thêm sau. Lỗi inb tuấn hihi