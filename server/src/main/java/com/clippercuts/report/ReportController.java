package com.clippercuts.report;

import com.clippercuts.report.dao.*;
import com.clippercuts.report.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/reports")
public class ReportController {

    @Autowired
    private CountByDesignaitonDao countByDesignationDao;

    @Autowired
    private CountByEmpStatusDao countByEmpStatusDao;

    @Autowired
    private CountByCustomerTypeDao countByCustomerTypeDao;

    @Autowired
    private CountByGenderDao countByGenderDao;

    @Autowired
    private CountByAppointmentStatusDao countByAppointmentStatusDao;

    @Autowired
    private RevenueByPaymentMethodDao revenueByPaymentMethodDao;

    @Autowired
    private CountByItemCategoryDao countByItemCategoryDao;

    @Autowired
    private CountByServiceCategoryDao countByServiceCategoryDao;

    @Autowired
    private TotalByPoSupplierDao totalByPoSupplierDao;

    @Autowired
    private RevenueByMonthDao revenueByMonthDao;

    // ---------- 1. Employees by Designation ----------
    @GetMapping(path = "/countbydesignation", produces = "application/json")
    public List<CountByDesignation> countByDesignation() {
        List<CountByDesignation> list = countByDesignationDao.countByDesignation();
        long total = list.stream().mapToLong(CountByDesignation::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 2. Employees by Status ----------
    @GetMapping(path = "/countbyempstatus", produces = "application/json")
    public List<CountByEmpStatus> countByEmpStatus() {
        List<CountByEmpStatus> list = countByEmpStatusDao.countByEmpStatus();
        long total = list.stream().mapToLong(CountByEmpStatus::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 3. Customers by Type ----------
    @GetMapping(path = "/countbycustomertype", produces = "application/json")
    public List<CountByCustomerType> countByCustomerType() {
        List<CountByCustomerType> list = countByCustomerTypeDao.countByCustomerType();
        long total = list.stream().mapToLong(CountByCustomerType::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 4. Customers by Gender ----------
    @GetMapping(path = "/countbygender", produces = "application/json")
    public List<CountByGender> countByGender() {
        List<CountByGender> list = countByGenderDao.countByGender();
        long total = list.stream().mapToLong(CountByGender::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 5. Appointments by Status ----------
    @GetMapping(path = "/countbyappointmentstatus", produces = "application/json")
    public List<CountByAppointmentStatus> countByAppointmentStatus() {
        List<CountByAppointmentStatus> list = countByAppointmentStatusDao.countByAppointmentStatus();
        long total = list.stream().mapToLong(CountByAppointmentStatus::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 6. Revenue by Payment Method ----------
    @GetMapping(path = "/revenuebypaymentmethod", produces = "application/json")
    public List<RevenueByPaymentMethod> revenueByPaymentMethod() {
        List<RevenueByPaymentMethod> list = revenueByPaymentMethodDao.revenueByPaymentMethod();
        BigDecimal total = list.stream().map(RevenueByPaymentMethod::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        list.forEach(r -> r.setPercentage(percentage(r.getTotalAmount(), total)));
        return list;
    }

    // ---------- 7. Items by Category ----------
    @GetMapping(path = "/countbyitemcategory", produces = "application/json")
    public List<CountByItemCategory> countByItemCategory() {
        List<CountByItemCategory> list = countByItemCategoryDao.countByItemCategory();
        long total = list.stream().mapToLong(CountByItemCategory::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 8. Services by Category ----------
    @GetMapping(path = "/countbyservicecategory", produces = "application/json")
    public List<CountByServiceCategory> countByServiceCategory() {
        List<CountByServiceCategory> list = countByServiceCategoryDao.countByServiceCategory();
        long total = list.stream().mapToLong(CountByServiceCategory::getCount).sum();
        list.forEach(r -> r.setPercentage(percentage(r.getCount(), total)));
        return list;
    }

    // ---------- 9. Purchase Order Total by Supplier ----------
    @GetMapping(path = "/totalbyposupplier", produces = "application/json")
    public List<TotalByPoSupplier> totalByPoSupplier() {
        List<TotalByPoSupplier> list = totalByPoSupplierDao.totalByPoSupplier();
        BigDecimal total = list.stream().map(TotalByPoSupplier::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        list.forEach(r -> r.setPercentage(percentage(r.getTotalAmount(), total)));
        return list;
    }

    // ---------- 10. Revenue by Month ----------
    @GetMapping(path = "/revenuebymonth", produces = "application/json")
    public List<RevenueByMonth> revenueByMonth() {
        return revenueByMonthDao.revenueByMonth();
    }

    // ---------- helpers ----------
    private double percentage(long count, long total) {
        if (total == 0) return 0.0;
        double p = (double) count / total * 100;
        return Math.round(p * 100.0) / 100.0;
    }

    private double percentage(BigDecimal value, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) == 0) return 0.0;
        double p = value.doubleValue() / total.doubleValue() * 100;
        return Math.round(p * 100.0) / 100.0;
    }
}
