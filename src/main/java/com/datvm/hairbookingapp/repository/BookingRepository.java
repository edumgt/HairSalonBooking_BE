package com.datvm.hairbookingapp.repository;

import com.datvm.hairbookingapp.dto.response.RevenueSalesResponse;
import com.datvm.hairbookingapp.entity.*;
import com.datvm.hairbookingapp.entity.enums.BookingStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("SELECT MAX(b.id) FROM Booking b")
    String findLastId();

    @Query("SELECT b FROM Booking b WHERE b.account = :account ORDER BY b.id DESC LIMIT 1 ")
    Booking findLastBooking(@Param("account")Account account);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.date = ?1 AND b.slot.id = ?2 AND b.salonId = ?3")
    int countBookingInSlot(LocalDate date, Long slotId, String salonId);

    @Query("UPDATE Booking b SET b.status = ?1 WHERE b.slot.id = ?2 AND b.status = ?3" )
    @Modifying
    @Transactional
    int updateBySpecificTime(BookingStatus newStatus ,Long slotId, BookingStatus status);

    @Query("Select b From Booking b Where b.account = ?1")
    List<Booking> findByAccount(Account account);

    @Query("Select b From Booking b Where b.salonId = ?1")
    List<Booking> findBySalon(String salonId);

    @Query("Select b From Booking b Where b.salonId = ?1 and b.stylistId = ?2")
    List<Booking> findBySalonAndStylist(String salonId, Staff staff);

    @Query("SELECT COUNT(b) AS booking_count FROM Booking b WHERE b.stylistId = ?1")
    int countBookingByStylist(Staff staff_id);

    @Query("SELECT COUNT(b) AS booking_count FROM Booking b WHERE b.salonId = ?1")
    int countTotalBookingBySalon(String salonId);

    @Query("SELECT COUNT(b) AS booking_count FROM Booking b")
    int countTotalBooking();

    @Query("SELECT COALESCE(SUM(b.price), 0) AS total_sales FROM Booking b WHERE b.status = 'COMPLETED'")
    int countTotalSales();

    @Query("SELECT COALESCE(SUM(b.price), 0) AS total_sales FROM Booking b WHERE b.status = 'COMPLETED' AND b.salonId = ?1")
    int countTotalSalesBySalon(String salonId);

    @Query("Select month(b.date), year(b.date), COALESCE(sum(b.price), 0) From Booking b Where b.status = 'COMPLETED' Group by month(b.date), year(b.date)")
    List<Object[]> revenueSales();

    @Query("Select month(b.date), year(b.date), COALESCE(sum(b.price), 0) From Booking b Where b.status = 'COMPLETED'" +
            " And b.salonId = ?1 Group by month(b.date), year(b.date)")
    List<Object[]> revenueSalesBySalon(String salonId);

    @Query("Select b from Booking b WHERE b.date = ?1 AND b.slot = ?2 and b.stylistId = ?3 ")
    Booking getBookingByStylistAndSlotDate(LocalDate date, Slot slotId, Staff staff);
}
