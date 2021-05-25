package com.wujie.ac.app.framework.util.sqlite;

import com.wujie.common.dto.sqlite.Room;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SqliteTest {
    public static void main(String[] args) {
        testHelper();
    }

    public static void testHelper() {
        try {
            SqliteHelper h = new SqliteHelper("C:\\Users\\Administrator\\Desktop\\TCube_Buss_Devtype.db");
//            h.executeUpdate("drop table if exists test;");
//            h.executeUpdate("create table test(name varchar(20));");
//            h.executeUpdate("insert into test values('sqliteHelper test');");
            List<Room> sList = h.executeQuery("select * from Room", new RowMapper<Room>() {
                @Override
                public Room mapRow(ResultSet rs, int index)
                        throws SQLException {
                    System.out.print(index);
                    Room room = new Room();
                    room.setRoomName(rs.getString("RoomName"));
                    room.setRoomNum(rs.getString("RoomNum"));
                    return room;
                }
            });
            System.out.println("data:");
            System.out.println(sList.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
