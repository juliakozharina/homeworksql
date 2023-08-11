package org.example;

import tables.DevicesTable;
import db.IDBConnector;
import db.MySQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<String, String> userColumns = new HashMap<String, String>() {{
        put("name", "varchar(100)");
    }};

    public static void main(String[] args) {
        IDBConnector db = new MySQLConnector();
        DevicesTable table = new DevicesTable();
        String sqlRequest = String.format("create table if not exists Curator (\n" +
                "id int not null primary key,\n" +
                "fio varchar(100) not null);");
        db.executeRequest(sqlRequest);
        System.out.println("Таблица создана.");

        sqlRequest = String.format("create table if not exists Group_info(\n" +
                "id int not null primary key,\n" +
                "name varchar(100) not null,\n" +
                "id_curator int,\n" +
                "foreign key (id_curator) references Curator(id));");
        db.executeRequest(sqlRequest);
        System.out.println("Таблица создана.");

        sqlRequest = String.format("create table if not exists Student ( \n" +
                "id int not null primary key,\n" +
                "fio varchar(100) not null,\n" +
                "sex char(1) not null,\n" +
                "id_group int,\n" +
                "foreign key (id_group) references Group_info(id));");
        db.executeRequest(sqlRequest);
        System.out.println("Таблица создана.");

        //

//        String truncate = "truncate table otusqa.devices;";
//        db.executeRequest(truncate);
//        System.out.println("Таблица очищена.");

        ////////////////////////

        String insertQuery1 = "insert into Student (id,fio,sex,id_group) Values (1,'Примаченко Дарина Васильевна','w',11);";
        int count = db.executeInsert(insertQuery1);
        count += db.executeInsert(getInsertSql(2, "Акимова Виктория Павловна", "w", 22));
        count += db.executeInsert(getInsertSql(3, "Полякова Виктория Александровна", "w", 11));
        count += db.executeInsert(getInsertSql(4, "Кожарина Юлия Андреевна", "w", 11));
        count += db.executeInsert(getInsertSql(5, "Викторов Геннадий Алексеевич", "m", 22));
        count += db.executeInsert(getInsertSql(6, "Петров Петр Петрович", "m", 33));
        count += db.executeInsert(getInsertSql(7, "Сидоров Виктор Сергеевич", "m", 11));
        count += db.executeInsert(getInsertSql(8, "Кожарина Наталья Алексеевна", "w", 22));
        count += db.executeInsert(getInsertSql(9, "Терещенко Юрий Петрович", "m", 33));
        count += db.executeInsert(getInsertSql(10, "Фомина Наталья Ильинична", "w", 11));
        count += db.executeInsert(getInsertSql(11, "Рвачев Роман Сергеевич", "m", 33));
        count += db.executeInsert(getInsertSql(12, "Мурашко Сергей Владимирович", "m", 22));
        count += db.executeInsert(getInsertSql(13, "Ахмедов Ахмед Ахмедович", "m", 11));
        count += db.executeInsert(getInsertSql(14, "Фионова Мария Константиновна", "w", 22));
        count += db.executeInsert(getInsertSql(15, "Самсонова Мария Владимировна", "w", 33));
        System.out.println("Вставлено записей: " + count);

        String insertQuery2 = "insert into Curator(id,fio) Values (1,'Андреев Александр Александрович');";
        int count = db.executeInsert(insertQuery2);
        count += db.executeInsert(getInsertSql(2, "Иванов Иван Иванович"));
        count += db.executeInsert(getInsertSql(3, "Сергеева Мария Федоровна"));
        count += db.executeInsert(getInsertSql(4, "Примаков Валерий Сергеевич"));

        String insertQuery3 = "insert into Group_info (id,name,id_curator) Values (11,'группа 1',3);";
        int count = db.executeInsert(insertQuery3);
        count += db.executeInsert(getInsertSql(22, "группа 2", 3));
        count += db.executeInsert(getInsertSql(33, "группа 3", 3));


        ///////////////////////

        String select = "select fio,id from otusqa.devices;";
        ResultSet result = db.executeRequestWithAnswer(select);
        try {
            int i = 1;
            while (result.next()) {
                System.out.println("Запись №" + i + ": id=" + result.getInt("id") + "\t name=" + result.getString("fio"));
                i++;
            }
        } catch (SQLException q) {
            q.printStackTrace();
        }


    }

    public static String getInsertSql(int id, String name, String w, int i) {
        //return "insert into otusqa.devices(id,fio) Values (" + id + ",'" + name + "');";
        return String.format("insert into otusqa.devices(id,fio) Values ( %d ,'%s');", id, name);
    }
}