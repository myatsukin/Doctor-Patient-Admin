package com.hms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hms.entity.Specialist;

public class SpecialistDAO {

    private Connection conn;

    public SpecialistDAO(Connection conn) {
        super();
        this.conn = conn;
    }

    // Add a specialist
    public boolean addSpecialist(String sp) {
        String sql = "INSERT INTO SYS.SPECIALIST (specialist_name) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sp);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all specialists
    public List<Specialist> getAllSpecialist() {
        List<Specialist> spList = new ArrayList<>();

        String sql = "SELECT * FROM SYS.SPECIALIST ORDER BY id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Specialist specialistObj = new Specialist();
                specialistObj.setId(rs.getInt("id"));
                specialistObj.setSpecialistName(rs.getString("specialist_name"));
                spList.add(specialistObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return spList;
    }
}
