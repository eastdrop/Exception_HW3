import java.io.*;
import java.nio.file.FileSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {

        try {
            makeRecord();
            System.out.println("success");
        }catch (FileSystemException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public static void makeRecord() throws Exception{
        String text;
        Date birthdate;
        int phone;
        System.out.println("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона " +
                "(число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            text = bf.readLine();
        }catch (IOException e){
            throw new Exception("Произошла ошибка при работе с консолью");
        }
        String[] array = text.split(" ");
        if (array.length != 6){
            throw new Exception("Введено неверное количество параметров");
        }
        String surname = array[0];
        String name = array[1];
        String patronymic = array[2];
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        try {
            localDate = LocalDate.parse(array[3], formatter);
        }catch (Exception e){
            throw new Exception("Неверный формат даты рождения");
        }
        try {
            phone = Integer.parseInt(array[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Неверный формат телефона");
        }
        String gender = array[5];
        if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")){
            throw new RuntimeException("Неверно введен пол");
        }
        String fileName = "files\\" + surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %s %s", surname, name, patronymic, localDate, phone, gender));
        }catch (IOException e){
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }

    }
}