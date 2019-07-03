package webservices;
/*

/Applications/XAMPP/bin/mysql -u root -p
create database wstutorials;
create user 'springuser'@'%' identified by '123456';
grant all on wstutorials.* to 'springuser'@'%';

Connect:
/Applications/XAMPP/bin/mysql -u springuser -p -h 192.168.1.34
*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
