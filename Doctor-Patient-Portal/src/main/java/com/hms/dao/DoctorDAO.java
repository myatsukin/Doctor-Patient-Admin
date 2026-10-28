package com.hms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hms.entity.Doctor;

public class DoctorDAO {

    private Connection conn;

    public DoctorDAO(Connection conn) {
        super();
        this.conn = conn;
    }

    // Register doctor
    public boolean registerDoctor(Doctor doctor) {
        boolean f = false;

        try {
            String sql = "INSERT INTO SYS.DOCTOR "
                    + "(fullName, dateOfBirth, qualification, specialist, email, phone, password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, doctor.getFullName());
            pstmt.setString(2, doctor.getDateOfBirth());
            pstmt.setString(3, doctor.getQualification());
            pstmt.setString(4, doctor.getSpecialist());
            pstmt.setString(5, doctor.getEmail());
            pstmt.setString(6, doctor.getPhone());
            pstmt.setString(7, doctor.getPassword());

            f = pstmt.executeUpdate() > 0;
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

    // Get all doctors
    public List<Doctor> getAllDoctor() {
        List<Doctor> docList = new ArrayList<>();

        String sql = "SELECT * FROM SYS.DOCTOR ORDER BY id DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setFullName(rs.getString("fullName"));
                doctor.setDateOfBirth(rs.getString("dateOfBirth"));
                doctor.setQualification(rs.getString("qualification"));
                doctor.setSpecialist(rs.getString("specialist"));
                doctor.setEmail(rs.getString("email"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setPassword(rs.getString("password"));
                docList.add(doctor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return docList;
    }

    // Get doctor by ID
    public Doctor getDoctorById(int id) {
        Doctor doctor = null;

        String sql = "SELECT * FROM SYS.DOCTOR WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setId(rs.getInt("id"));
                    doctor.setFullName(rs.getString("fullName"));
                    doctor.setDateOfBirth(rs.getString("dateOfBirth"));
                    doctor.setQualification(rs.getString("qualification"));
                    doctor.setSpecialist(rs.getString("specialist"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setPhone(rs.getString("phone"));
                    doctor.setPassword(rs.getString("password"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctor;
    }

    // Update doctor
    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE SYS.DOCTOR SET fullName=?, dateOfBirth=?, "
                + "qualification=?, specialist=?, email=?, phone=?, password=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getFullName());
            pstmt.setString(2, doctor.getDateOfBirth());
            pstmt.setString(3, doctor.getQualification());
            pstmt.setString(4, doctor.getSpecialist());
            pstmt.setString(5, doctor.getEmail());
            pstmt.setString(6, doctor.getPhone());
            pstmt.setString(7, doctor.getPassword());
            pstmt.setInt(8, doctor.getId());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete doctor
    public boolean deleteDoctorById(int id) {
        String sql = "DELETE FROM SYS.DOCTOR WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Doctor login
    public Doctor loginDoctor(String email, String password) {
        Doctor doctor = null;

        String sql = "SELECT * FROM SYS.DOCTOR WHERE email = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setId(rs.getInt("id"));
                    doctor.setFullName(rs.getString("fullName"));
                    doctor.setDateOfBirth(rs.getString("dateOfBirth"));
                    doctor.setQualification(rs.getString("qualification"));
                    doctor.setSpecialist(rs.getString("specialist"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setPhone(rs.getString("phone"));
                    doctor.setPassword(rs.getString("password"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctor;
    }

    // Count doctors
    public int countTotalDoctor() {
        return countRows("SELECT COUNT(*) FROM SYS.DOCTOR");
    }

    // Count all appointments
    public int countTotalAppointment() {
        return countRows("SELECT COUNT(*) FROM SYS.APPOINTMENT");
    }

    // Count appointments for a doctor
    public int countTotalAppointmentByDoctorId(int doctorId) {
        String sql = "SELECT COUNT(*) FROM SYS.APPOINTMENT WHERE doctorId = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Count users
    public int countTotalUser() {
        return countRows("SELECT COUNT(*) FROM SYS.USER_DETAILS");
    }

    // Count specialists
    public int countTotalSpecialist() {
        return countRows("SELECT COUNT(*) FROM SYS.SPECIALIST");
    }

    // Helper method for row counts
    private int countRows(String sql) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Check old password
    public boolean checkOldPassword(int doctorId, String oldPassword) {
        String sql = "SELECT 1 FROM SYS.DOCTOR WHERE id = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            pstmt.setString(2, oldPassword);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Change password
    public boolean changePassword(int doctorId, String newPassword) {
        String sql = "UPDATE SYS.DOCTOR SET password = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, doctorId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Edit doctor profile
    public boolean editDoctorProfile(Doctor doctor) {
        String sql = "UPDATE SYS.DOCTOR SET fullName=?, dateOfBirth=?, "
                + "qualification=?, specialist=?, email=?, phone=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getFullName());
            pstmt.setString(2, doctor.getDateOfBirth());
            pstmt.setString(3, doctor.getQualification());
            pstmt.setString(4, doctor.getSpecialist());
            pstmt.setString(5, doctor.getEmail());
            pstmt.setString(6, doctor.getPhone());
            pstmt.setInt(7, doctor.getId());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
